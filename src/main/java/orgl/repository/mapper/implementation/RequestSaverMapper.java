package orgl.repository.mapper.implementation;

import orgl.repository.dto.implementation.DomainDto;
import orgl.repository.dto.implementation.RequestSaverDto;
import orgl.repository.mapper.Mapper;
import orgl.request.RequestSaver;

public class RequestSaverMapper implements Mapper<RequestSaver, RequestSaverDto> {
    /**
     * This method convert an object into his DTO (Data Transfert Object) representation.
     *
     * @param object the object to convert into his dto representation
     * @return the dto form of the object
     */
    @Override
    public RequestSaverDto toDto(RequestSaver object) {
        RequestSaverDto dto = new RequestSaverDto();
        dto.multimap = object.toMap();
        return dto;
    }

    /**
     * This method convert a DTO (Data Transfert Object) into his object representation.
     *
     * @param dto the dto to convert into his object representation
     * @return the object form of the dto
     */
    @Override
    public RequestSaver fromDto(RequestSaverDto dto) {
        if (dto == null) { return null; }
        RequestSaver object = new RequestSaver();
        object.setMap(dto.multimap);
        return object;
    }
}
