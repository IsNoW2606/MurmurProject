package orgl.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import orgl.relay.Relay;
import orgl.repository.dto.implementation.RelayDto;
import orgl.repository.dto.implementation.RequestSaverDto;
import orgl.repository.dto.implementation.ServerDto;
import orgl.repository.mapper.implementation.RelayMapper;
import orgl.repository.mapper.implementation.RequestSaverMapper;
import orgl.repository.mapper.implementation.ServerMapper;
import orgl.request.RequestSaver;
import orgl.server.MurmurServerRunnable;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class JsonRepository {
    public void save(MurmurServerRunnable server) {
        try (Writer writer = Files.newBufferedWriter(Path.of(Paths.get("").toAbsolutePath().toString(), "src", "main", "resources", "server", "config.json"), StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE)) {
            writer.write(new Gson().toJson(new ServerMapper().toDto(server), new TypeToken<ServerDto>(){}.getType()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void save(Relay relay) {
        try (Writer writer = Files.newBufferedWriter(Path.of(Paths.get("").toAbsolutePath().toString(), "src", "main", "resources", "relay", "config.json"), StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE)) {
            writer.write(new Gson().toJson(new RelayMapper().toDto(relay), new TypeToken<RelayDto>(){}.getType()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void save(String dir, RequestSaver requestSaver) {
        try (Writer writer = Files.newBufferedWriter(Path.of(Paths.get("").toAbsolutePath().toString(), "src", "main", "resources", dir, "request.json"), StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE)) {
            writer.write(new Gson().toJson(new RequestSaverMapper().toDto(requestSaver), new TypeToken<RequestSaverDto>(){}.getType()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MurmurServerRunnable loadServer() {
        try (Reader reader = Files.newBufferedReader(Path.of(Paths.get("").toAbsolutePath().toString(), "src", "main", "resources", "server", "config.json"), StandardCharsets.UTF_8)) {
            return new ServerMapper().fromDto(new Gson().fromJson(reader, new TypeToken<ServerDto>(){}.getType()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Relay loadRelay() {
        try (Reader reader = Files.newBufferedReader(Path.of(Paths.get("").toAbsolutePath().toString(), "src", "main", "resources", "relay", "config.json"), StandardCharsets.UTF_8)) {
            return new RelayMapper().fromDto(new Gson().fromJson(reader, new TypeToken<RelayDto>(){}.getType()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public RequestSaver loadRequestSaver(String dir) {
        try (Reader reader = Files.newBufferedReader(Path.of(Paths.get("").toAbsolutePath().toString(), "src", "main", "resources", dir, "request.json"), StandardCharsets.UTF_8)) {
            return new RequestSaverMapper().fromDto(new Gson().fromJson(reader, new TypeToken<RequestSaverDto>(){}.getType()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
