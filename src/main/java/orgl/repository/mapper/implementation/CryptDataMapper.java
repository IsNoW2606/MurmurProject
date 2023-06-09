package orgl.repository.mapper.implementation;

import orgl.model.CryptData;
import orgl.repository.dto.implementation.CryptDataDto;
import orgl.repository.mapper.Mapper;

public class CryptDataMapper implements Mapper<CryptData, CryptDataDto> {
    /**
     * This method convert an object into his DTO (Data Transfert Object) representation.
     *
     * @param object the object to convert into his dto representation
     * @return the dto form of the object
     */
    @Override
    public CryptDataDto toDto(CryptData object) {
        CryptDataDto dto = new CryptDataDto();
        dto.bcryptHash = object.bcryptHash;
        dto.bcryptRound = object.bcryptRound;
        dto.bcryptSalt = object.bcryptSalt;
        return dto;
    }

    /**
     * This method convert a DTO (Data Transfert Object) into his object representation.
     *
     * @param dto the dto to convert into his object representation
     * @return the object form of the dto
     */
    @Override
    public CryptData fromDto(CryptDataDto dto) {
        return null;
    }
}
