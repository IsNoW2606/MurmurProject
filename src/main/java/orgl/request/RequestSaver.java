package orgl.request;

import orgl.utility.MultiMap;

import java.util.Collection;
import java.util.Map;

public class RequestSaver {
    private MultiMap<String, String> multiMap = new MultiMap<>();
    public void put(String address, String request) {
        multiMap.put(address, request);
    }
    public void removeRequest(String address, String requestString) {
        multiMap.remove(address, requestString);
    }
    public Collection<String> removeAllRequest(String address) {
        return multiMap.remove(address);
    }

    public boolean hasRequestWaiting(String address) {
        return multiMap.containsKey(address);
    }

    public Map<String, Collection<String>> toMap() {
        return multiMap.getMap();
    }

    public void setMap(Map<String, Collection<String>> map) {
        multiMap.setMap(map);
    }
}
