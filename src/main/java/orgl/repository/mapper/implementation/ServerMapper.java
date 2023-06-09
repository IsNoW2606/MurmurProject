package orgl.repository.mapper.implementation;

import orgl.server.ServerConfig;
import orgl.repository.dto.implementation.ServerDto;
import orgl.repository.mapper.Mapper;
import orgl.server.MurmurServerRunnable;
import orgl.server.ServerData;

public class ServerMapper implements Mapper<MurmurServerRunnable, ServerDto> {
    /**
     * This method convert an object into his DTO (Data Transfert Object) representation.
     *
     * @param object the object to convert into his dto representation
     * @return the dto form of the object
     */
    @Override
    public ServerDto toDto(MurmurServerRunnable object) {
        ServerDto dto = new ServerDto();

        ServerConfig serverConfig = object.getConfig();
        dto.serverConfig.unicastPort = serverConfig.unicastPort;
        dto.serverConfig.saltSizeInBytes = serverConfig.saltSizeInBytes;
        dto.serverConfig.relayPort = serverConfig.relayPort;
        dto.serverConfig.tls = serverConfig.tls;
        dto.serverConfig.currentDomain = new DomainMapper().toDto(serverConfig.currentDomain);
        dto.serverConfig.multicastData = new MulticastDataMapper().toDto(serverConfig.multicastData);

        ServerData serverData = object.getData();
        serverData.users.values().forEach( user -> dto.serverData.users.add(new UserMapper().toDto(user)) );
        serverData.tags.values().forEach( tag -> dto.serverData.tags.add(new TagMapper().toDto(tag)) );

        return dto;
    }

    /**
     * This method convert a DTO (Data Transfert Object) into his object representation.
     *
     * @param dto the dto to convert into his object representation
     * @return the object form of the dto
     */
    @Override
    public MurmurServerRunnable fromDto(ServerDto dto) {
        if (dto == null) { return null; }
        MurmurServerRunnable object = new MurmurServerRunnable();

        ServerConfig serverConfig = object.getConfig();
        serverConfig.unicastPort = dto.serverConfig.unicastPort;
        serverConfig.saltSizeInBytes = dto.serverConfig.saltSizeInBytes;
        serverConfig.relayPort = dto.serverConfig.relayPort;
        serverConfig.tls = dto.serverConfig.tls;
        serverConfig.currentDomain = new DomainMapper().fromDto(dto.serverConfig.currentDomain);
        serverConfig.multicastData = new MulticastDataMapper().fromDto(dto.serverConfig.multicastData);

        ServerData serverData = object.getData();
        dto.serverData.users.forEach( user -> serverData.registerUser(new UserMapper().fromDto(user)) );
        dto.serverData.tags.forEach( tag -> serverData.registerTag(new TagMapper().fromDto(tag)) );

        return object;
    }
}
