package orgl.repository.dto.implementation;

import orgl.repository.dto.Dto;

import java.util.Collection;
import java.util.LinkedList;

public class ServerDataDto implements Dto {
    public final Collection<UserDto> users = new LinkedList<>();
    public final Collection<TagDto> tags = new LinkedList<>();
}
