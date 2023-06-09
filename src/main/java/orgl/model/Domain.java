package orgl.model;

public class Domain {
    private final String host;
    private final String base64AES;

    private Domain(String host, String base64AES) {
        this.host = host;
        this.base64AES = base64AES;
    }

    public static Domain fromHostWithBase64AES(String host, String base64AES) {
        return new Domain(host, base64AES);
    }

    public static String removeDomain(String domain) {
        return domain.substring(0, domain.indexOf('@'));
    }

    public static String getDomain(String domain) {
        return domain.substring(domain.indexOf('@') + 1);
    }

    public boolean inDomain(String address) {
        return address.endsWith("@" + host);
    }

    public String buildAddress(String prefix) {
        return String.format("%s@%s", prefix, host);
    }

    public String getHost() {
        return host;
    }

    public String getBase64AES() {
        return base64AES;
    }

}
