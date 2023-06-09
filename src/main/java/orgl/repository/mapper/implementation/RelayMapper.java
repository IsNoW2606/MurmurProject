package orgl.repository.mapper.implementation;

import orgl.relay.Relay;
import orgl.relay.RelayConfig;
import orgl.relay.RelayData;
import orgl.repository.dto.implementation.RelayDto;
import orgl.repository.mapper.Mapper;

public class RelayMapper implements Mapper<Relay, RelayDto> {
    /**
     * This method convert an object into his DTO (Data Transfert Object) representation.
     *
     * @param object the object to convert into his dto representation
     * @return the dto form of the object
     */
    @Override
    public RelayDto toDto(Relay object) {
        RelayDto dto = new RelayDto();

        RelayConfig relayConfig = object.getConfig();
        dto.relayConfig.multicastData = new MulticastDataMapper().toDto(relayConfig.multicastData);

        RelayData relayData = object.getData();
        relayData.configuredDomains.values().forEach( (domain) -> dto.relayData.configuredDomains.add(new DomainMapper().toDto(domain)) );

        return dto;
    }

    /**
     * This method convert a DTO (Data Transfert Object) into his object representation.
     *
     * @param dto the dto to convert into his object representation
     * @return the object form of the dto
     */
    @Override
    public Relay fromDto(RelayDto dto) {
        if (dto == null) { return null; }
        Relay object = new Relay();

        RelayConfig relayConfig = object.getConfig();
        relayConfig.multicastData = new MulticastDataMapper().fromDto(dto.relayConfig.multicastData);

        RelayData relayData = object.getData();
        dto.relayData.configuredDomains.forEach( (domain) -> relayData.registerDomain(new DomainMapper().fromDto(domain)) );

        return object;
    }
}
