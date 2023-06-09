package orgl.repository.dto.implementation;

import orgl.repository.dto.Dto;

import java.util.Collection;
import java.util.LinkedList;

public class RelayDataDto implements Dto {
    public Collection<DomainDto> configuredDomains = new LinkedList<>();
}
