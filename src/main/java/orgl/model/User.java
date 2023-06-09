package orgl.model;

import java.util.*;

public class User {
    private final String login;
    private final CryptData cryptData;
    private final Set<String> followers = new HashSet<>();
    private final Map<String, String> userTags = new HashMap<>();
    private short lockoutCounter;

    public User(String login, CryptData cryptData) {
        this.login = login;
        this.cryptData = cryptData;
    }

    public String getLogin() {
        return login;
    }

    public CryptData getCryptData() {
        return cryptData;
    }

    public Collection<String> getFollowers() {
        return followers;
    }

    public Collection<String> getUserTags() {
        return userTags.values();
    }
    public String getUserTag(String tag) {
        return userTags.get(tag);
    }

    public short getLockoutCounter() {
        return lockoutCounter;
    }

    public void registerFollower(String follower) {
        followers.add(follower);
    }

    public void registerUserTag(String userTag) {
        createKeyAndRegisterUserTag(userTag.substring(0, userTag.indexOf('@')), userTag);
    }

    private void createKeyAndRegisterUserTag(String tag, String userTag) {
        if (!userTags.containsKey(tag)) {
            userTags.put(tag, userTag);
        }
    }

    public boolean isSubscribedToTag(String tag) {
        return userTags.containsKey(tag);
    }

    public void setLockoutCounter(short lockoutCounter) {
        this.lockoutCounter = lockoutCounter;
    }

    @Override
    public int hashCode() {
        return login.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User)) {
            return false;
        }

        return Objects.equals(((User) o).getLogin(), this.login);
    }
}
