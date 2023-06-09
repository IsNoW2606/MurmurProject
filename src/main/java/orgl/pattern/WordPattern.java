package orgl.pattern;

public class WordPattern {
    public static final String CHIFFRES = "[0-9]";
    public static final String LETTRES = "[a-zA-Z]";
    public static final String CARACTERE_IMPRIMABLE = "[\\x20-\\x7E]";
    public static final String CRLF = "\\r\\n";
    public static final String SYMBOLE = "[!\"#$%&'()*+,-./:;<=>?@\\[\\\\\\]^_`]+";
    public static final String ESPACE = "\\s";
    public static final String DOMAINE = "[a-zA-Z0-9.]{5,200}";
    public static final String PORT = "[0-9]{1,5}";
    public static final String ROUND = "[0-9]{2}";
    public static final String BCRYPT = "\\$2b\\$[0-9]{2}\\$[a-zA-Z0-9./]{1,70}";
    public static final String SHA3_HEX = "[a-zA-Z0-9]{30,200}";
    public static final String SALT_SIZE = "[0-9]{2}";
    public static final String RANDOM22 = "([a-zA-Z0-9.]|[!\"#$%&'()*+,-./:;<=>?@\\[\\]^_`]){22}";
    public static final String MESSAGE = "[\\x20-\\x7E]{1,200}";
    public static final String NOM_UTILISATEUR = "[a-zA-Z0-9.]{5,20}";
    public static final String TAG = "#[a-zA-Z0-9.]{5,20}";
    public static final String NOM_DOMAINE = "[a-zA-Z0-9.]{5,20}@[a-zA-Z0-9.]{5,200}";
    public static final String TAG_DOMAINE = "#[a-zA-Z0-9.]{5,20}@[a-zA-Z0-9.]{5,200}";
    public static final String ID_DOMAINE = "[a-zA-Z0-9]{1,5}@[a-zA-Z0-9.]{5,200}";
}
