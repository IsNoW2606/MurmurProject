package orgl.server;

import orgl.client.ClientApplicationThread;
import orgl.client.ClientAuthentificationThread;
import orgl.client.ClientBuffer;
import orgl.model.Domain;
import orgl.model.Tag;
import orgl.model.User;
import orgl.repository.JsonRepository;
import orgl.request.RequestSaver;
import orgl.task.Request;

import javax.net.ssl.SSLSocket;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class MurmurServerRunnable extends Thread {
    private final ServerData data = new ServerData();
    private final ServerConfig config = new ServerConfig();
    private final Map<String, ClientApplicationThread> clientList = Collections.synchronizedMap(new HashMap<>());
    private MurmurExecutor executor;
    private final RequestSaver requestSaver;

    public MurmurServerRunnable() {
        this.requestSaver = new JsonRepository().loadRequestSaver("server");
    }

    @Override
    public void run() {
        try {
            this.executor = new MurmurExecutor(this, 250);
            new RelaySocketReceiver(this).start();

            SSLConnection.setKeyStore();
            SSLConnection sslConnection = new SSLConnection(config.unicastPort);
            while(true) {
                SSLSocket clientSocket = sslConnection.waitForConnection();

                new ClientAuthentificationThread(this, clientSocket);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public void registerClient(User user, ClientBuffer clientBuffer) {
        data.registerUser(user);

        new JsonRepository().save(this);
        System.out.printf("Server -> Client %s has been registered\n", user.getLogin());

        connectClient(user, clientBuffer);
    }

    public void connectClient(User user, ClientBuffer clientBuffer) {
        clientList.put(user.getLogin(), new ClientApplicationThread(this, user, clientBuffer));
        System.out.printf("Server -> Client %s has been connected\n", user.getLogin());

        sendSavedRequest(user.getLogin());
    }

    public void sendRequestToExecutor(Request request) {
        executor.handleRequest(request);
    }

    public void sendRequestToClient(String client, String requestString) {
        if (clientList.containsKey(client)) {
            clientList.get(client).sendMessage(requestString);
        } else if (data.users.containsKey(client)) {
            saveRequest(client, requestString);
        }
    }

    public void senderFollowReceiver(String sender, String receiver) {
        if (receiver.charAt(0) == '#') {
            if (config.currentDomain.inDomain(sender)) {
                User senderUser = data.users.get(Domain.removeDomain(sender));
                senderUser.registerUserTag(receiver);
            } else {
                String followRequest = String.format("FOLLOW %s", sender);
                sendRequestToExecutor(new Request(receiver, followRequest));
            }

            String tagName = Domain.removeDomain(receiver);
            Tag tag = data.tags.get(tagName);
            if (tag == null) {
                tag = new Tag(tagName);
                data.registerTag(tag);
            }

            tag.registerFollower(sender);

            new JsonRepository().save(this);
            System.out.printf("Server -> Client %s now follow the tag %s\n", sender, receiver);
        } else if (sender.charAt(0) == '#') {
            User receiverUser = data.users.get(Domain.removeDomain(receiver));
            receiverUser.registerUserTag(sender);

            new JsonRepository().save(this);
            System.out.printf("Server -> Client %s now follow the tag %s\n", receiver, sender);
        } else {
            User receiverUser = data.users.get(Domain.removeDomain(receiver));

            if (receiverUser != null) {
                receiverUser.registerFollower(sender);

                new JsonRepository().save(this);
                System.out.printf("Server -> Client %s now follow %s\n", sender, receiver);
            }
        }
    }

    public void saveRequest(String receiver, String requestString) {
        requestSaver.put(receiver, requestString);

        new JsonRepository().save("server", requestSaver);
        System.out.printf("Server -> Requête \"%s\" sauvegardée pour %s\n", requestString, receiver);
    }

    public void removeRequest(String receiver, String requestString) {
        requestSaver.removeRequest(receiver, requestString);

        new JsonRepository().save("server", requestSaver);
        System.out.printf("Server -> Requête \"%s\" supprimée de la sauvegarde pour %s\n", requestString, receiver);
    }

    private void sendSavedRequest(String receiver) {
        if (requestSaver.hasRequestWaiting(receiver)) {
            Collection<String> requests = requestSaver.removeAllRequest(receiver);
            requests.forEach(request -> sendRequestToClient(receiver, request));

            new JsonRepository().save("server", requestSaver);
            System.out.printf("Server -> Toutes les requêtes de %s ont été supprimées de la sauvegarde\n", receiver);
        }
    }

    public ServerConfig getConfig() {
        return config;
    }

    public ServerData getData() {
        return data;
    }

    public static void main(String[] args) {
        new JsonRepository().loadServer().start();
    }
}