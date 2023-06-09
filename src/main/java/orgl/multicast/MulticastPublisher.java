package orgl.multicast;

import orgl.model.MulticastData;

import java.io.IOException;
import java.net.*;

public class MulticastPublisher {
    private MulticastSocket socket;
    private InetAddress group;
    private final MulticastData multicastData;

    public MulticastPublisher(MulticastData multicastData) {
        this.multicastData = multicastData;
    }

    public void multicast(String multicastMessage) {
        initMulticast();
        sendMessagesAndCloseConnection(multicastMessage);
    }

    private void initMulticast() {
        try {
            socket = new MulticastSocket(multicastData.multicastPort);
            socket.setNetworkInterface(NetworkInterface.getByName(multicastData.networkInterface));
            group = InetAddress.getByName(multicastData.multicastAddress);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("[MulticastError] Refer to the ReadMe file to setup your NetworkInterface, Port and MulticastAddress properly");
        }

    }

    private void sendMessagesAndCloseConnection(String multicastMessages) {
        sendMessageToMulticast(multicastMessages);
        socket.close();
    }

    private void sendMessageToMulticast(String multicastMessage) {
        try {
            socket.send(convertStringToPacket(multicastMessage));
            System.out.printf("Server multicast out -> \"%s\" has been send to %s\n", multicastMessage, multicastData.multicastAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private DatagramPacket convertStringToPacket(String multicastMessage) {
        byte[] buf = multicastMessage.getBytes();
        return new DatagramPacket(buf, buf.length, group, multicastData.multicastPort);
    }
}
