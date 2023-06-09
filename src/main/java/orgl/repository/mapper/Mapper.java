package orgl.repository.mapper;

import orgl.repository.dto.Dto;

public interface Mapper<T, D extends Dto> {
    /**
     * This method convert an object into his DTO (Data Transfert Object) representation.
     *
     * @param object the object to convert into his dto representation
     * @return the dto form of the object
     */
    D toDto(T object);

    /**
     * This method convert a DTO (Data Transfert Object) into his object representation.
     *
     * @param dto the dto to convert into his object representation
     * @return the object form of the dto
     */
    T fromDto(D dto);
}
