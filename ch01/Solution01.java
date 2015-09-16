public class Solution01 {

    /**
     *  @brief: Given a string, check if the string has all unique chars.
     *  @param: [in] str: The string to be checked.
     *  @return: bool
     *      - true: The string has all unique characters.
     *      - false: The string has duplicated characters.
     */
    private static boolean has_unique_chars(String str) {
        boolean result = true;

        // Use a char array to store all the characters that have appeared in
        // the string, that is, store str[i] to ch[(int)str[i]].
        Character [] ch = new Character[256];
        for (int i = 0; i < 256; ++i) {
            ch[i] = '\0';
        }

        int str_length = str.length();
        for (int i = 0; i < str_length; ++i) {
            int pos = str.charAt(i);
            if ('\0' == ch[pos]) {
                ch[pos] = str.charAt(i);
            } else {
                // ch[pos] had been occupied by some other characters in str,
                // so str has duplicated characters.
                result = false;
                break;  // Do not need to check any more.
            }
        }

        return result;
    }

    public static void main(String []args) {
        boolean pass = has_unique_chars("") && has_unique_chars("a") &&
            has_unique_chars("abc") && !has_unique_chars("aabc");

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
