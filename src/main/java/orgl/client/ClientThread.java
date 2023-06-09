package orgl.client;

import orgl.model.User;
import orgl.server.MurmurServerRunnable;
import orgl.task.Request;

import java.io.IOException;

public abstract class ClientThread extends Thread {
    protected ClientBuffer clientBuffer = new ClientBuffer();
    protected MurmurServerRunnable server;
    protected User user;
    protected String senderString = "Undefined";
    protected boolean isConnected = true;

    public ClientThread(MurmurServerRunnable server) {
        this.server = server;
        this.user = new User("?", null);
    }

    @Override
    public void run() {
        try {
            createTaskWhileConnected();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        System.out.printf("Client out [%s] -> %s\n", senderString, message);
        clientBuffer.out.println(message);
    }

    private void createTaskWhileConnected() throws IOException {
        String ligne;
        while(isConnected && (ligne = clientBuffer.in.readLine()) != null && !ligne.equals("DISCONNECT")) {
            System.out.printf("Client in [%s] -> %s\n", senderString, ligne);
            handleRequest(new Request(senderString, ligne));
        }
    }
    protected abstract void handleRequest(Request request);
}
