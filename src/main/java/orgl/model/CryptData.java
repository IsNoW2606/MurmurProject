package orgl.model;

public class CryptData {
    public String bcryptHash;
    public short bcryptRound;
    public String bcryptSalt;

    private CryptData(String bcryptHash, short bcryptRound, String bcryptSalt) {
        this.bcryptHash = bcryptHash;
        this.bcryptRound = bcryptRound;
        this.bcryptSalt = bcryptSalt;
    }

    public static CryptData withHashRoundAndSalt(String bcryptHash, short bcryptRound, String bcryptSalt) {
        return new CryptData(bcryptHash, bcryptRound, bcryptSalt);
    }

    public static CryptData fromBCryptHash(String bcryptHash) {
        return new CryptData(bcryptHash.substring(29), Short.parseShort(bcryptHash.substring(4, 6)), bcryptHash.substring(7, 29));
    }
}
