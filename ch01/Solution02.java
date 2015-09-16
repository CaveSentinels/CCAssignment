public class Solution02 {

    /**
     *  @brief: Determine if str1 is a permutation of str2.
     *  @param: [in] str1: The string to be checked.
     *  @param; [in] str2: The string to be checked against.
     *  @return: bool
     *      - true: str1 is a permutation of str2.
     *      - false: str1 is not a permutation of str2.
     */
    private static boolean is_permutation_of(String str1, String str2) {
        boolean result = true;

        // Store the number of occurences of each char in str2.
        int [] str2chars = new int[256];
        for (int i = 0; i < 256; ++i) {
            str2chars[i] = 0;
        }

        for (int i = 0; i < str2.length(); ++i) {
            int pos = (int)str2.charAt(i);
            ++str2chars[pos];
        }

        // Store the number of occurences of each char in str1.
        int [] str1chars = new int[256];
        for (int i = 0; i < 256; ++i) {
            str1chars[i] = 0;
        }

        for (int i = 0; i < str1.length(); ++i) {
            int pos = (int)str1.charAt(i);
            ++str1chars[pos];
        }

        // Finally, we compare str1chars[] and str2chars[].
        // If they are identical, that means they have exactly the same chars
        // so one is the permutation of the other.
        for (int i = 0; i < 256; ++i) {
            if (str1chars[i] != str2chars[i]) {
                // If any of them is not equal, that means these two strings have
                // at least one different character or at least one character
                // occurrs different times.
                result = false;
                break;
            }
        }

        return result;
    }

    public static void main(String []args) {
        boolean pass = true;

        pass = pass && is_permutation_of("", "") && !is_permutation_of("", "a") &&
            !is_permutation_of("a", "") && is_permutation_of("bac", "abc") &&
            is_permutation_of("aabbcc", "bbccaa") && !is_permutation_of("abc", "aabc");

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
