package orgl.repository.dto.implementation;

import orgl.repository.dto.Dto;

import java.util.Collection;
import java.util.Map;

public class RequestSaverDto implements Dto {
    public Map<String, Collection<String>> multimap;
}
