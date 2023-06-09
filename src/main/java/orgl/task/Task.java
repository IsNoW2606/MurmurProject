package orgl.task;

public class Task {
    private final String taskId;
    private final TaskType taskType;
    private String content;
    private String sender;
    private String receiver;

    public Task(String taskId, TaskType taskType) {
        this.taskId = taskId;
        this.taskType = taskType;
    }
    public String getTaskId() {
        return taskId;
    }

    public TaskType getType() {
        return taskType;
    }

    public String getContent() {
        return content;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}

