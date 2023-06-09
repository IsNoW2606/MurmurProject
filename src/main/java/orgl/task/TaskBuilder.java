package orgl.task;

import orgl.crypt.AESEncryptor;
import orgl.model.Domain;
import orgl.model.Tag;
import orgl.model.User;
import orgl.server.ServerData;
import orgl.utility.StringUtils;
import orgl.utility.TagsFinder;

import java.util.*;

public class TaskBuilder {
    private final Domain domain;
    private final ServerData serverData;
    private Collection<String> receivers;
    private Collection<Task> result;

    public TaskBuilder(Domain domain, ServerData serverData) {
        this.domain = domain;
        this.serverData = serverData;
    }

    public Collection<Task> fromRequest(Request request) {
        result = new LinkedList<>();
        receivers = new HashSet<>();

        TaskType taskType = TaskType.fromRequestString(request.getRequestString());
        switch (taskType) {
            case MSG : makeMsgsTask(request); break;
            case FOLLOW : makeFollowTask(request); break;
            case SEND : makeFromSendTask(request); break;
        }

        return result;
    }

    private void makeMsgsTask(Request request) {
        User user = serverData.users.get(Domain.removeDomain(request.getSender()));

        receivers.addAll(user.getFollowers());
        addTagsFollowers(user, request);

        String msgsRequestString = String.format("MSGS %s %s", request.getSender(), request.getLastParam(2));
        buildTasks(TaskType.MSGS, request, msgsRequestString);
    }

    private void addTagsFollowers(User user, Request request) {
        for (String tagName : TagsFinder.findTag(request.getLastParam(2))) {
            if (user.isSubscribedToTag(tagName)) {
                String tagDomain = user.getUserTag(tagName);
                if (domain.inDomain(tagDomain)) {
                    Tag tag = serverData.tags.get(tagName);
                    receivers.addAll(tag.getFollowers());
                } else {
                    receivers.add(tagDomain);
                }
            }
        }
    }

    private void makeFollowTask(Request request) {
        String receiver = request.getParam(2);
        receivers.add(receiver);

        buildTasks(TaskType.FOLLOW, request, request.getRequestString());
    }

    private void buildTasks(TaskType taskType, Request request, String content) {
        buildTasks(request.getRequestId(), taskType, request.getSender(), content);
    }

    private void buildTasks(String requestId, TaskType taskType, String sender, String content) {
        for (String receiver : receivers) {
            Task task = new Task(requestId, domain.inDomain(receiver) ? taskType : TaskType.SEND);
            task.setSender(sender);
            task.setReceiver(receiver);
            task.setContent(content);
            result.add(task);
        }
    }

    private void makeFromSendTask(Request request) {
        AESEncryptor aesEncryptor = new AESEncryptor(domain.getBase64AES());
        String uncryptedContent = aesEncryptor.decrypt(request.getLastParam(5));

        TaskType taskType = TaskType.fromRequestString(uncryptedContent);
        String receiver = request.getParam(4);
        if (receiver.charAt(0) == '#' && taskType == TaskType.MSGS) {
            Tag tag = serverData.tags.get(Domain.removeDomain(receiver));
            receivers.addAll(tag.getFollowers());
        } else {
            receivers.add(receiver);
        }

        buildTasks(request.getParam(2), taskType, request.getParam(3), uncryptedContent);
    }
}
