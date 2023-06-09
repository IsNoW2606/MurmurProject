package orgl.repository.dto.implementation;

import orgl.repository.dto.Dto;

import java.util.HashSet;
import java.util.Set;

public class TagDto implements Dto {
    public String tag;
    public Set<String> followers = new HashSet<>();
}
