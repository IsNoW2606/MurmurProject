package orgl.repository.dto.implementation;

import orgl.repository.dto.Dto;

public class ServerConfigDto implements Dto {
    public DomainDto currentDomain;
    public MulticastDataDto multicastData;
    public int unicastPort;
    public int relayPort;
    public int saltSizeInBytes;
    public boolean tls;
}
