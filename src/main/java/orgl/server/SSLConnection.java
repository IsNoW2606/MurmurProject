package orgl.server;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.IOException;

public class SSLConnection {
    private final SSLServerSocket sslServerSocket;

    public SSLConnection(int port) throws IOException {
        this.sslServerSocket = (SSLServerSocket) SSLServerSocketFactory.getDefault().createServerSocket(port);
    }

    public static void setKeyStore() {
        System.setProperty("javax.net.ssl.keyStore", "src/main/resources/star.godswila.guru.p12");
        System.setProperty("javax.net.ssl.keyStorePassword", "labo2023");
    }

    public SSLSocket waitForConnection() throws IOException {
        return (SSLSocket) sslServerSocket.accept();
    }
}
