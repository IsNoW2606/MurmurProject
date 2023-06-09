package orgl.relay;

import orgl.model.Domain;
import orgl.model.User;

import java.util.HashMap;
import java.util.Map;

public class RelayData {
    public Map<String, Domain> configuredDomains = new HashMap<>();

    public void registerDomain(Domain domain) {
        configuredDomains.put(domain.getHost(), domain);
    }
}
