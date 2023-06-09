package orgl.repository.mapper.implementation;

import orgl.model.CryptData;
import orgl.model.User;
import orgl.repository.dto.implementation.UserDto;
import orgl.repository.mapper.Mapper;

public class UserMapper implements Mapper<User, UserDto> {
    /**
     * This method convert an object into his DTO (Data Transfert Object) representation.
     *
     * @param object the object to convert into his dto representation
     * @return the dto form of the object
     */
    @Override
    public UserDto toDto(User object) {
        UserDto dto = new UserDto();
        dto.login = object.getLogin();
        dto.cryptData = new CryptDataMapper().toDto(object.getCryptData());
        dto.followers.addAll(object.getFollowers());
        dto.userTags.addAll(object.getUserTags());
        dto.lockoutCounter = object.getLockoutCounter();
        return dto;
    }

    /**
     * This method convert a DTO (Data Transfert Object) into his object representation.
     *
     * @param dto the dto to convert into his object representation
     * @return the object form of the dto
     */
    @Override
    public User fromDto(UserDto dto) {
        User object = new User(dto.login, CryptData.withHashRoundAndSalt(
                dto.cryptData.bcryptHash, dto.cryptData.bcryptRound, dto.cryptData.bcryptSalt
        ));
        dto.followers.forEach(object::registerFollower);
        dto.userTags.forEach(object::registerUserTag);
        object.setLockoutCounter(dto.lockoutCounter);
        return object;
    }
}
