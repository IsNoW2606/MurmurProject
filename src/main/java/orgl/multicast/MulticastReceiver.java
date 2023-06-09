package orgl.multicast;

import orgl.model.MulticastData;
import orgl.relay.Relay;
import orgl.relay.RelayConfig;
import orgl.task.Request;

import java.io.IOException;
import java.net.*;

public class MulticastReceiver extends Thread {
    private MulticastSocket socket = null;
    private final byte[] buf = new byte[256];
    private final Relay controller;

    public MulticastReceiver(Relay controller) {
        this.controller = controller;
    }

    public void run() {
        try {
            connectToMulticastAndHandleConnection(controller.getConfig().multicastData);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("[MulticastError] Refer to the ReadMe file to setup your NetworkInterface, Port and MulticastAddress properly");
        }
    }

    private void connectToMulticastAndHandleConnection(MulticastData multicastData) throws IOException {
        socket = new MulticastSocket(multicastData.multicastPort);
        joinAddressReceivePacketAndLeave(new InetSocketAddress(InetAddress.getByName(multicastData.multicastAddress), multicastData.multicastPort), NetworkInterface.getByName(multicastData.networkInterface));
        socket.close();
    }

    private void joinAddressReceivePacketAndLeave(SocketAddress address, NetworkInterface networkInterface) throws IOException {
        socket.joinGroup(address, networkInterface);
        receivePacketAndSendToController();
        socket.leaveGroup(address, networkInterface);
    }

    private void receivePacketAndSendToController() throws IOException {
        while (true) {
            readMessageAndSendToController(receivePacket());
        }
    }

    private void readMessageAndSendToController(DatagramPacket packet) {
        sendToController(packet.getAddress(), new String(packet.getData(), 0, packet.getLength()));
    }

    private void sendToController(InetAddress address, String message) {
        System.out.printf("Server multicast in -> \"%s\" has been received from %s\n", message, address.getHostAddress());
        controller.handleRequest(new Request(address.getHostAddress(), message));
    }

    private DatagramPacket receivePacket() throws IOException {
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        return packet;
    }

}