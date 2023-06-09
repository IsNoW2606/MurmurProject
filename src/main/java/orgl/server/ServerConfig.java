package orgl.server;

import orgl.model.Domain;
import orgl.model.MulticastData;

public class ServerConfig {
    public Domain currentDomain;
    public MulticastData multicastData;
    public int unicastPort;
    public int relayPort;
    public int saltSizeInBytes;
    public boolean tls;
}
