package orgl.relay;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class RelaySocketSender {
    private String senderAddress;
    private int port;
    private PrintWriter out;
    public RelaySocketSender(String senderAddress, int port) {
        this.senderAddress = senderAddress;
        this.port = port;
        initConnection();
    }

    public void initConnection() {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(InetAddress.getByName(senderAddress), port));
            out = new PrintWriter(new DataOutputStream(socket.getOutputStream()), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        out.println(message);
        System.out.printf("Relay out [%s:%d] -> \"%s\" has been sent\n", senderAddress, port, message);
    }
}
