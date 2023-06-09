package orgl.pattern;

import java.util.regex.Pattern;

public class RequestPattern {
    public static Pattern REGISTER = Pattern.compile(
            String.format("REGISTER %s %s %s", WordPattern.NOM_UTILISATEUR, WordPattern.SALT_SIZE, WordPattern.BCRYPT));
    public static Pattern CONNECT = Pattern.compile(
            String.format("CONNECT %s", WordPattern.NOM_UTILISATEUR));
    public static Pattern CONFIRM = Pattern.compile(
            String.format("CONFIRM %s", WordPattern.SHA3_HEX));
    public static Pattern FOLLOW = Pattern.compile(
            String.format("FOLLOW (%s|%s)", WordPattern.NOM_DOMAINE, WordPattern.TAG_DOMAINE));
    public static Pattern MSG = Pattern.compile(
            String.format("MSG %s", WordPattern.MESSAGE));
    public static Pattern MSGS = Pattern.compile(
            String.format("MSGS %s %s", WordPattern.NOM_DOMAINE, WordPattern.MESSAGE));
    public static Pattern SEND = Pattern.compile(
            String.format("SEND %s %s (%s|%s) (%s|%s)", WordPattern.ID_DOMAINE, WordPattern.NOM_DOMAINE, WordPattern.NOM_DOMAINE, WordPattern.TAG_DOMAINE, FOLLOW.pattern(), MSGS.pattern()));
    public static Pattern CRYPTED_SEND = Pattern.compile(
            String.format("SEND %s %s (%s|%s) .+", WordPattern.ID_DOMAINE, WordPattern.NOM_DOMAINE, WordPattern.NOM_DOMAINE, WordPattern.TAG_DOMAINE));
    public static Pattern CR = Pattern.compile(
            String.format("CR %s %s", WordPattern.DOMAINE, WordPattern.PORT));
    public static Pattern ACK = Pattern.compile(
            String.format("ACK %s", CRYPTED_SEND.pattern()));
    public static Pattern AUTH_REQUEST = Pattern.compile(
        String.format("(%s|%s|%s)", REGISTER.pattern(), CONNECT.pattern(), CONFIRM.pattern()));
    public static Pattern APP_REQUEST = Pattern.compile(
            String.format("(%s|%s)", MSG.pattern(), FOLLOW.pattern()));
    public static Pattern OUT_RELAY = Pattern.compile(
            String.format("(%s|%s)", CRYPTED_SEND.pattern(), ACK.pattern()));
    public static Pattern IN_RELAY = Pattern.compile(
            String.format("(%s|%s|%s)", CRYPTED_SEND.pattern(), ACK.pattern(), CR.pattern()));

}
