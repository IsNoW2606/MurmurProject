package orgl.utility;

import java.util.StringJoiner;

public class StringUtils {

    public static String getFirstWord(String string) {
        return string.split(" ", 2)[0];
    }

    public static String toHexString(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();

        for (byte aByte : bytes) {
            String hex = Integer.toHexString(0xFF & aByte);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }

    public static String getFirstNWords(int n, String str) {
        StringJoiner sj = new StringJoiner(" ");

        String[] sArr = str.split(" ");
        for(int i = 0; i < n; i++) {
            sj.add(sArr[i]);
        }

        return sj.toString();
    }
}
