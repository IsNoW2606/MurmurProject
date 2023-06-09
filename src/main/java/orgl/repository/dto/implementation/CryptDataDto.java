package orgl.repository.dto.implementation;

import orgl.repository.dto.Dto;

public class CryptDataDto implements Dto {
    public String bcryptHash;
    public short bcryptRound;
    public String bcryptSalt;
}
