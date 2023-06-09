package orgl.relay;

import orgl.crypt.AESEncryptor;
import orgl.model.Domain;
import orgl.multicast.MulticastReceiver;
import orgl.pattern.RequestPattern;
import orgl.repository.JsonRepository;
import orgl.request.RequestSaver;
import orgl.task.Request;
import orgl.task.TaskType;
import orgl.utility.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Relay extends Thread {
    private final RelayConfig config = new RelayConfig();
    private final RelayData data = new RelayData();
    private final Map<String, RelaySocketSender> servers = Collections.synchronizedMap(new HashMap<>());
    private final RequestSaver requestSaver;

    public Relay() {
        this.requestSaver = new JsonRepository().loadRequestSaver("relay");
    }

    @Override
    public void run() {
        new MulticastReceiver(this).start();
    }

    public void handleRequest(Request request) {
        if (!RequestPattern.IN_RELAY.matcher(request.getRequestString()).matches()) {
            System.out.println("Relay -> Wrong request format");
            return;
        }

        TaskType taskType = TaskType.fromRequestString(request.getRequestString());
        switch (taskType) {
            case SEND : handleSend(request); break;
            case ACK : handleAck(request); break;
            case CR : handleCr(request); break;
        }
    }

    public void handleSend(Request request) {
        System.out.println("Relay -> Send received");

        String senderDomain = Domain.getDomain(request.getParam(3));
        sendAckToSender(senderDomain, request.getRequestString());

        String receiverDomain = Domain.getDomain(request.getParam(4));
        if (data.configuredDomains.containsKey(receiverDomain)) {
            String sendRequestWithoutContent = StringUtils.getFirstNWords(4, request.getRequestString());

            AESEncryptor aesEncryptor = new AESEncryptor(data.configuredDomains.get(senderDomain).getBase64AES());
            String uncryptedContent = aesEncryptor.decrypt(request.getLastParam(5));

            aesEncryptor = new AESEncryptor(data.configuredDomains.get(receiverDomain).getBase64AES());
            String newCryptedContent = aesEncryptor.encrypt(uncryptedContent);
            String newRequestString = sendRequestWithoutContent + " " + newCryptedContent;
            sendRequestToReceiver(receiverDomain, newRequestString);

            saveRequest(request.getSender(), newRequestString);
        }
    }

    private void sendAckToSender(String sender, String requestString) {
        RelaySocketSender serverSender = servers.get(sender);

        String ackRequest = String.format("ACK %s", requestString);
        serverSender.sendMessage(ackRequest);
    }

    private void sendRequestToReceiver(String receiver, String requestString) {
        RelaySocketSender serverReceiver = servers.get(receiver);

        if (serverReceiver != null) {
            serverReceiver.sendMessage(requestString);
        }
    }


    public void handleAck(Request request) {
        removeRequest(request.getSender(), request.getLastParam(2));
    }

    public void handleCr(Request request) {
        String domain = request.getParam(2);
        int port = Integer.parseInt(request.getLastParam(3));

        servers.put(domain, new RelaySocketSender(request.getSender(), port));
        System.out.printf("Relay -> Connection with %s has been made\n", domain);

        sendSavedRequest(request.getSender());
    }

    public void saveRequest(String receiver, String requestString) {
        requestSaver.put(receiver, requestString);

        new JsonRepository().save("relay", requestSaver);
        System.out.printf("Relay -> Requête \"%s\" sauvegardée pour %s\n", requestString, receiver);
    }

    public void removeRequest(String receiver, String requestString) {
        requestSaver.removeRequest(receiver, requestString);

        new JsonRepository().save("relay", requestSaver);
        System.out.printf("Relay -> Requête \"%s\" supprimée de la sauvegarde pour %s\n", requestString, receiver);
    }

    private void sendSavedRequest(String receiver) {
        if (requestSaver.hasRequestWaiting(receiver)) {
            Collection<String> requests = requestSaver.removeAllRequest(receiver);
            requests.forEach(request -> sendRequestToReceiver(receiver, request));

            new JsonRepository().save("relay", requestSaver);
            System.out.printf("Relay -> Toutes les requêtes de %s ont été supprimées de la sauvegarde\n", receiver);
        }
    }

    public RelayConfig getConfig() {
        return config;
    }

    public RelayData getData() {
        return data;
    }

    public static void main(String[] args) {
        new JsonRepository().loadRelay().start();
    }
}
