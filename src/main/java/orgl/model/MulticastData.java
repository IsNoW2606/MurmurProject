package orgl.model;

public class MulticastData {
    public final String multicastAddress;
    public final int multicastPort;
    public String networkInterface;

    private MulticastData(String multicastAddress, int multicastPort, String networkInterface) {
        this.multicastAddress = multicastAddress;
        this.multicastPort = multicastPort;
        this.networkInterface = networkInterface;
    }

    public static MulticastData withAddressPortAndNetworkInterface(String multicastAddress, int multicastPort, String networkInterface) {
        return new MulticastData(multicastAddress, multicastPort, networkInterface);
    }
}
