package orgl.model;

import java.util.HashSet;
import java.util.Set;

public class Tag {
    private final String tag;
    private final Set<String> followers = new HashSet<>();

    public Tag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public Set<String> getFollowers() {
        return followers;
    }

    public void registerFollower(String follower) {
        this.followers.add(follower);
    }

    @Override
    public String toString() {
        return tag;
    }
}
