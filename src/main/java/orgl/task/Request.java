package orgl.task;

import orgl.model.Domain;

import java.util.UUID;

public class Request {
    private String requestId;
    private String sender;
    private String request;

    public Request(String sender, String request) {
        this.requestId = String.format("%s@%s", UUID.randomUUID().toString().substring(0, 5), Domain.getDomain(sender));
        this.sender = sender;
        this.request = request;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getSender() {
        return sender;
    }

    public String getRequestString() {
        return request;
    }
    public String getParam(int pos) {
        return request.split(" ", pos + 1)[pos - 1];
    }

    public String getLastParam(int pos) {
        return request.split(" ", pos)[pos - 1];
    }
}
