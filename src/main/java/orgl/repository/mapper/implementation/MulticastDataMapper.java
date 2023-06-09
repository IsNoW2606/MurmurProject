package orgl.repository.mapper.implementation;

import orgl.model.MulticastData;
import orgl.repository.dto.implementation.MulticastDataDto;
import orgl.repository.mapper.Mapper;

public class MulticastDataMapper implements Mapper<MulticastData, MulticastDataDto> {
    /**
     * This method convert an object into his DTO (Data Transfert Object) representation.
     *
     * @param object the object to convert into his dto representation
     * @return the dto form of the object
     */
    @Override
    public MulticastDataDto toDto(MulticastData object) {
        MulticastDataDto dto = new MulticastDataDto();
        dto.multicastAddress = object.multicastAddress;
        dto.multicastPort = object.multicastPort;
        dto.networkInterface = object.networkInterface;
        return dto;
    }

    /**
     * This method convert a DTO (Data Transfert Object) into his object representation.
     *
     * @param dto the dto to convert into his object representation
     * @return the object form of the dto
     */
    @Override
    public MulticastData fromDto(MulticastDataDto dto) {
        return dto == null ? null : MulticastData.withAddressPortAndNetworkInterface(dto.multicastAddress, dto.multicastPort, dto.networkInterface);
    }
}
