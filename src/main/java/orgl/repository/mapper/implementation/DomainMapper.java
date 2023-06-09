package orgl.repository.mapper.implementation;

import orgl.model.Domain;
import orgl.repository.dto.implementation.DomainDto;
import orgl.repository.mapper.Mapper;

public class DomainMapper implements Mapper<Domain, DomainDto> {
    /**
     * This method convert an object into his DTO (Data Transfert Object) representation.
     *
     * @param object the object to convert into his dto representation
     * @return the dto form of the object
     */
    @Override
    public DomainDto toDto(Domain object) {
        DomainDto dto = new DomainDto();
        dto.host = object.getHost();
        dto.base64AES = object.getBase64AES();
        return dto;
    }

    /**
     * This method convert a DTO (Data Transfert Object) into his object representation.
     *
     * @param dto the dto to convert into his object representation
     * @return the object form of the dto
     */
    @Override
    public Domain fromDto(DomainDto dto) {
        return dto == null ? null : Domain.fromHostWithBase64AES(dto.host, dto.base64AES);
    }

}
