package orgl.crypt;

import javax.crypto.KeyGenerator;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;

public class AESEncryptorKey extends SecretKeySpec {
    private static final int AES_KEY_SIZE = 256;

    /**
     * Constructs a secret key from the given byte array.
     *
     * <p>This constructor does not check if the given bytes indeed specify a
     * secret key of the specified algorithm. For example, if the algorithm is
     * DES, this constructor does not check if <code>key</code> is 8 bytes
     * long, and also does not check for weak or semi-weak keys.
     * In order for those checks to be performed, an algorithm-specific
     * <i>key specification</i> class (in this case:
     * {@link DESKeySpec DESKeySpec})
     * should be used.
     *
     * @throws IllegalArgumentException if <code>algorithm</code>
     *                                  is null or <code>key</code> is null or empty
     *                                  or algorithm is inexistant.
     *
     */
    public AESEncryptorKey(byte[] IV) {
        super(IV, "AES");
    }

    public static byte[] getKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(AES_KEY_SIZE);
            return keyGenerator.generateKey().getEncoded();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
