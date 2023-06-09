package orgl.repository.mapper.implementation;

import orgl.model.Tag;
import orgl.repository.dto.implementation.TagDto;
import orgl.repository.mapper.Mapper;

public class TagMapper implements Mapper<Tag, TagDto> {
    /**
     * This method convert an object into his DTO (Data Transfert Object) representation.
     *
     * @param object the object to convert into his dto representation
     * @return the dto form of the object
     */
    @Override
    public TagDto toDto(Tag object) {
        TagDto dto = new TagDto();
        dto.tag = object.getTag();
        dto.followers.addAll(object.getFollowers());
        return dto;
    }

    /**
     * This method convert a DTO (Data Transfert Object) into his object representation.
     *
     * @param dto the dto to convert into his object representation
     * @return the object form of the dto
     */
    @Override
    public Tag fromDto(TagDto dto) {
        Tag object = new Tag(dto.tag);
        dto.followers.forEach(object::registerFollower);
        return object;
    }
}
