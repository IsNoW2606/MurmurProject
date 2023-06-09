package orgl.server;

import orgl.multicast.MulticastPublisher;
import orgl.pattern.RequestPattern;
import orgl.task.Request;
import orgl.task.TaskType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.Timer;
import java.util.TimerTask;

public class RelaySocketReceiver extends Thread {
    private MurmurServerRunnable server;
    private boolean isConnected;

    public RelaySocketReceiver(MurmurServerRunnable server) {
        this.server = server;
    }

    @Override
    public void run() {
        ServerConfig serverConfig = server.getConfig();

        try (ServerSocket serverSocket = new ServerSocket(serverConfig.relayPort)) {
            serverSocket.setSoTimeout(10000);

            while (!isConnected) {

                sendConnectionRequestToRelay(2000L);

                System.out.println("Server -> Waiting for relay connection");
                try (Socket socket = serverSocket.accept()) {
                    System.out.println("Server -> Connection with the relay has been made");
                    isConnected = true;

                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                    String ligne;
                    while (isConnected && (ligne = in.readLine()) != null) {
                        System.out.printf("Relay in [%s] -> %s\n", serverConfig.currentDomain.getHost(), ligne);
                        handleRequest(new Request("Relay", ligne));
                    }

                } catch (SocketTimeoutException e) {
                    System.out.println("Server -> Unable to create connection with the relay");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void handleRequest(Request request) {
        if (!RequestPattern.OUT_RELAY.matcher(request.getRequestString()).matches()) {
            return;
        }

        TaskType taskType = TaskType.fromRequestString(request.getRequestString());
        switch (taskType) {
            case SEND : handleSend(request); break;
            case ACK : handleAck(request); break;
        }
    }

    private void handleSend(Request request) {
        ServerConfig serverConfig = server.getConfig();

        new MulticastPublisher(serverConfig.multicastData).multicast("ACK " + request.getRequestString());

        server.sendRequestToExecutor(request);
    }

    private void handleAck(Request request) {
        server.removeRequest("Relay", request.getLastParam(2));
    }

    private void sendConnectionRequestToRelay(long delay) {
        ServerConfig serverConfig = server.getConfig();

        String crRequest = String.format("CR %s %s", serverConfig.currentDomain.getHost(), serverConfig.relayPort);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                new MulticastPublisher(serverConfig.multicastData).multicast(crRequest);
            }
        }, delay);
    }
}

