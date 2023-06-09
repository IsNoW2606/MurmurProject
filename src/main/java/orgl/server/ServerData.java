package orgl.server;

import orgl.model.Tag;
import orgl.model.User;

import java.util.HashMap;
import java.util.Map;

public class ServerData {
    public final Map<String, User> users = new HashMap<>();
    public final Map<String, Tag> tags = new HashMap<>();

    public void registerUser(User user) {
        users.put(user.getLogin(), user);
    }

    public void registerTag(Tag tag) {
        tags.put(tag.getTag(), tag);
    }
}
