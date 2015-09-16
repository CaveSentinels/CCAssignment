public class Solution05 {

    /**
     *  @brief: If str1 and str2 are 1 edit away.
     *  @param: [in] str1, str2: The two strings to be checked.
     *  @return: bool
     *      - true: str1 and str2 are not more than 1 edit away.
     *      - false: str1 and str2 are more than 1 edit away.
     */
    private static boolean one_edit_away(String str1, String str2) {
        int str1_length = str1.length();
        int str2_length = str2.length();

        // Always make sure that str1 is longer than str2.
        if (str1_length < str2_length) {
            return one_edit_away(str2, str1);
        }

        // Now we are guaranteed that str1 is longer than str2.

        if (str1_length-str2_length >= 2) {
            // If the length difference is >= 2, these two strings have no way
            // to be 1 edit away.
            return false;
        }

        // Given the fact that str1 is longer than str2, we use str1(the longer) as
        // the base line to search for the first different character from left
        // to right, as well as from right to left. If they are really just
        // one-edit away, the two directions should end up at the same character
        // on str1(the longer).

        // Compare from the left-most char, until we get the first different char.
        int left = 0;
        while (left < str1_length && left < str2_length && str1.charAt(left) == str2.charAt(left)) {
            ++left;
        }

        if (left == str1_length && left == str2_length) {
            // str1 and str2 do not have any different characters so we return true.
            return true;
        }

        // Compare from the right-most char, until we get the first different char
        // or right2 goes beyond the lowest index (don't forget it is guaranteed
        // that str1.size() >= str2.size()).
        int right1 = str1_length - 1;
        int right2 = str2_length - 1;
        while (right1 >= 0 && right2 >= 0 && str1.charAt(right1) == str2.charAt(right2)) {
            --right1;
            --right2;
        }

        // Now if left and right1 are pointing at the same index of str1,
        // then str1 and str2 are one-edit away.
        return (left == right1);
    }

    public static void main(String [] args) {
        boolean pass = true;

        pass = pass && one_edit_away("", "")
            && one_edit_away("a", "")
            && one_edit_away("a", "b")
            && one_edit_away("a", "ab")
            && !one_edit_away("a", "bc")
            && one_edit_away("pale", "pal")
            && !one_edit_away("pale", "bake")
            ;

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
