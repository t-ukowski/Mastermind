package utils;

import javafx.scene.control.TextField;

import java.util.regex.Pattern;

/**
 * Provides tools for analyzing and manipulating Strings
 */
public class StringHelper {
    public static boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    /**
     * Checks whether given character is to be accepted by system (non-white printable ascii character)
     *
     * @param ch analyzed character
     * @return is the character acceptable
     */
    public static boolean isAcceptable(char ch) {
        return 32 < ch && ch < 127;
    }

    /**
     * Checks whether given String contains unacceptable characters
     *
     * @param s given Sting
     * @return {@code true} if String ought to be rejected
     */
    public static boolean isInvalid(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!isAcceptable(s.charAt(i))) return true;
        }
        return false;
    }

    /**
     * Checks whether any of the given Strings contains unacceptable characters
     *
     * @param strings Strings to be analyzed
     * @return {@code true} if any of the Strings should be rejected
     */
    public static boolean anyInvalid(String... strings) {
        for (String s : strings) {
            if (isInvalid(s)) return true;
        }
        return false;
    }

    /**
     * Checks whether any of the given TextFields contains unacceptable characters
     *
     * @param fields TextFields to be analyzed
     * @return {@code true} if any of the TextFields should be rejected
     */
    public static boolean anyInvalid(TextField... fields) {
        for (TextField f : fields) {
            if (isInvalid(f.getText())) return true;
        }
        return false;
    }

    /**
     * Checks whether any of the given TextFields is blank
     *
     * @param fields TextFields to be analyzed
     * @return {@code true} if any of the TextFields is empty or contains only white characters
     */
    public static boolean anyBlank(TextField... fields) {
        for (TextField f : fields) {
            if (f.getText().isBlank()) return true;
        }
        return false;
    }

    /**
     * Creates a hex string from byte array
     *
     * @param bytes array of bytes
     * @return String made of two hexadecimal digits from each byte, concatenated together
     */
    public static String stringToHex(byte[] bytes) {
        StringBuilder ret = new StringBuilder();
        String hex;
        for (byte b : bytes) {
            hex = "00" + Integer.toHexString(b);
            ret.append(hex.substring(hex.length() - 2));
        }
        return ret.toString();
    }
}
