package orgl.client;

import orgl.model.CryptData;
import orgl.server.ServerConfig;
import orgl.model.User;
import orgl.pattern.RequestPattern;
import orgl.server.MurmurServerRunnable;
import orgl.server.ServerData;
import orgl.task.Request;
import orgl.task.TaskType;
import orgl.utility.RandomString;
import orgl.utility.StringUtils;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class ClientAuthentificationThread extends ClientThread {
    private String challenge;

    public ClientAuthentificationThread(MurmurServerRunnable server, Socket client) {
        super(server);
        this.challenge = RandomString.ofLength(22);
        initInputAndOutput(client);
        informClientThatConnectionHasBeenMade();

        this.start();
    }

    private void initInputAndOutput(Socket client) {
        try {
            clientBuffer.in = new BufferedReader(new InputStreamReader(client.getInputStream(), StandardCharsets.UTF_8));
            clientBuffer.out = new PrintWriter(new OutputStreamWriter(client.getOutputStream(), StandardCharsets.UTF_8), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void informClientThatConnectionHasBeenMade() {
        ServerConfig config = server.getConfig();
        sendMessage(String.format("HELLO %s %s", config.currentDomain.getHost(), challenge));
    }

    @Override
    protected void handleRequest(Request request) {
        if (!RequestPattern.AUTH_REQUEST.matcher(request.getRequestString()).matches()) {
            return;
        }

        TaskType taskType = TaskType.fromRequestString(request.getRequestString());
        switch (taskType) {
            case REGISTER : handleRegistration(request); break;
            case CONNECT : handleConnection(request); break;
            case CONFIRM : handleConfirmation(request); break;
        }
    }

    public void handleRegistration(Request request) {
        ServerData serverData = server.getData();

        String login = request.getParam(2);
        if (!serverData.users.containsKey(login)) {
            String bCryptHash = request.getParam(4);
            user = new User(login, CryptData.fromBCryptHash(bCryptHash));
            server.registerClient(user, clientBuffer);

            close();
        } else {
            sendMessage("-ERR");
        }
    }

    public void handleConnection(Request request) {
        ServerData serverData = server.getData();

        String login = request.getParam(2);
        if (serverData.users.containsKey(login)) {
            user = serverData.users.get(login);
            sendMessage(String.format("PARAM %s %s", user.getCryptData().bcryptRound, user.getCryptData().bcryptSalt));
        } else {
            sendMessage("-ERR");
        }
    }

    public void handleConfirmation(Request request) {
        String Sha3Hex = request.getParam(2);
        if (isGoodPassword(Sha3Hex)) {
            server.connectClient(user, clientBuffer);

            close();
        } else {
            sendMessage("-ERR");
        }
    }

    private boolean isGoodPassword(String receivedHash) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA3-256");
            byte[] result = md.digest(String.format("%s$2b$%d$%s%s", challenge, user.getCryptData().bcryptRound, user.getCryptData().bcryptSalt, user.getCryptData().bcryptHash).getBytes());
            return receivedHash.equals(StringUtils.toHexString(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void close() {
        isConnected = false;
    }


}