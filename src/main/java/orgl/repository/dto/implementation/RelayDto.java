package orgl.repository.dto.implementation;

import orgl.model.Domain;
import orgl.relay.RelayData;
import orgl.repository.dto.Dto;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class RelayDto implements Dto {
    public RelayConfigDto relayConfig = new RelayConfigDto();
    public RelayDataDto relayData = new RelayDataDto();
}
