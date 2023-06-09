package orgl.crypt;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import java.security.SecureRandom;
import java.util.Base64;

public class AESEncryptor {
    private final byte[] key;
    private byte[] IV;

    public AESEncryptor(String key) {
        this.key = Base64.getDecoder().decode(key);
    }

    public String encrypt(String plainText) {
        try {
            this.IV = initIV();
            Cipher cipher = setupCipherWithMode(Cipher.ENCRYPT_MODE);

            String IVBase64 = Base64.getEncoder().encodeToString(IV);
            String resultBase64 = Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes()));

            return IVBase64 + resultBase64;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String decrypt(String cipherText) {
        if (cipherText == null) { return ""; }
        try {
            this.IV = Base64.getDecoder().decode(cipherText.substring(0, 16));
            Cipher cipher = setupCipherWithMode(Cipher.DECRYPT_MODE);

            return new String(cipher.doFinal(Base64.getDecoder().decode(cipherText.substring(16))));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Cipher setupCipherWithMode(int cipherMode) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(cipherMode, new AESEncryptorKey(key), new GCMParameterSpec(16 * 8, IV));

        return cipher;
    }

    private static byte[] initIV() {
        byte[] IV = new byte[12];
        SecureRandom random = new SecureRandom();
        random.nextBytes(IV);
        return IV;
    }

    public static String getBase64AES() {
        return Base64.getEncoder().encodeToString(AESEncryptorKey.getKey());
    }

    public static void main(String[] args) {
        System.out.println(getBase64AES());
    }
}
