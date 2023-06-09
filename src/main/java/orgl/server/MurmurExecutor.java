package orgl.server;

import orgl.crypt.AESEncryptor;
import orgl.model.Domain;
import orgl.multicast.MulticastPublisher;
import orgl.task.Request;
import orgl.task.Task;
import orgl.task.TaskBuilder;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MurmurExecutor extends Thread {
    private final BlockingQueue<Task> taskQueue;
    private final MurmurServerRunnable server;
    private final TaskBuilder taskBuilder;

    public MurmurExecutor(MurmurServerRunnable server, int queueCapacity) {
        this.taskQueue = new ArrayBlockingQueue<>(queueCapacity);
        this.server = server;
        this.taskBuilder = new TaskBuilder(server.getConfig().currentDomain, server.getData());
        this.start();
    }

    public void handleRequest(Request request) {
        taskQueue.addAll(taskBuilder.fromRequest(request));
    }

    @Override
    public void run() {
        while (true) {
            try {
                executeTask(taskQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void executeTask(Task task) {
        switch (task.getType()) {
            case MSGS -> handleMsgs(task);
            case FOLLOW -> handleFollow(task);
            case SEND -> handleSend(task);
        }
    }

    private void handleSend(Task task) {
        ServerConfig serverConfig = server.getConfig();

        AESEncryptor aesEncryptor = new AESEncryptor(serverConfig.currentDomain.getBase64AES());
        String cryptedContent = aesEncryptor.encrypt(task.getContent());

        String sendRequest = String.format("SEND %s %s %s %s", task.getTaskId(), task.getSender(), task.getReceiver(), cryptedContent);
        server.saveRequest("Relay", sendRequest);
        new MulticastPublisher(serverConfig.multicastData).multicast(sendRequest);
    }

    private void handleMsgs(Task task) {
        if (!task.getSender().equals(task.getReceiver())) {
            server.sendRequestToClient(Domain.removeDomain(task.getReceiver()), task.getContent());
        }
    }

    private void handleFollow(Task task) {
        server.senderFollowReceiver(task.getSender(), task.getReceiver());
    }
}
