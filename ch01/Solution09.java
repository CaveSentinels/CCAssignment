public class Solution09 {

    private static boolean isSubstring(String str1, String str2) {
        return str2.contains(str1);
    }

    private static boolean is_rotation(String str1, String str2) {
        int str1_len = str1.length();
        int str2_len = str2.length();

        // Handle special cases.
        if (str1_len != str2_len) {
            // If the two strings are in different length, they have no way to
            // be the rotation of each other.
            return false;
        }

        // From now on it is guaranteed that str1_len == str2_len.

        if (str1_len == 0) {
            // If two strings are empty, one is of course the rotation of the
            // other.
            return true;
        }

        if (str1_len == 1 && str1.charAt(0) == str2.charAt(0)) {
            // If the two strings are both of one char and that char is the same,
            // surely one is the rotation of the other.
            return true;
        }

        // For multiple-character strings, we need to check if str2 is a substring
        // of str1+str1;
        return isSubstring(str2, str1+str1);
    }

    public static void main(String [] args) {
        boolean pass = true;

        pass = pass && is_rotation("", "")
            && is_rotation("a", "a")
            && !is_rotation("a", "b")
            && is_rotation("abc", "bca")
            && is_rotation("waterbottle", "erbottlewat")
            ;

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
