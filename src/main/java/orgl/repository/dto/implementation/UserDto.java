package orgl.repository.dto.implementation;

import orgl.repository.dto.Dto;

import java.util.HashSet;
import java.util.Set;

public class UserDto implements Dto {
    public String login;
    public CryptDataDto cryptData;
    public Set<String> followers = new HashSet<>();
    public Set<String> userTags = new HashSet<>();
    public short lockoutCounter;
}
