public class Solution04 {
    /**
     *  @brief: Check if the given string is a permutation of a palindrome.
     *  @param: [in] str: The string to be checked.
     *  @return: boolean
     *      - true: str is a permutation of some palindrome.
     *      - false: str is not a permutation of any palindrome.
     */
    private static boolean is_palindrome_permutation(String str) {
        int str_length = str.length();

        // Handle the special cases.
        if (str_length <= 1) {
            // An empty or 1-char string is a palindrome itself.
            return true;
        }

        // We should ignore the spaces; upper-/lower-case is not important, either.
        // If the string size (without spaces) is an even number, then the number
        // of occurences of every letter must be an even number, such as "a AAa";
        // if the string size (without spaces) is an odd number, then there must be
        // one letter occurring once and the others occurring even numbers of times,
        // such as "aA bc b".
        int [] ch_counter = new int[26];

        // The string length without spaces. Initially it's the same with str_length.
        int str_length_no_space = str_length;
        for (int i = 0; i < str_length; ++i) {
            char ch = str.charAt(i);
            if (ch != ' ') {
                int index = (int)(('a' <= ch && ch <= 'z') ? (ch - 'a') : (ch - 'A'));
                ++ch_counter[index];
            } else {
                --str_length_no_space;
            }
        }

        // The number of letters that occurred odd number of times.
        int odd_occurrence = 0;
        for (int i = 0; i < 26; ++i) {
            if (ch_counter[i] % 2 != 0) {
                ++odd_occurrence;
            }
        }

        // Return true if either of the two cases happens.
        return ((str_length_no_space % 2 == 0 && odd_occurrence == 0) ||
            (str_length_no_space % 2 != 0 && odd_occurrence == 1)
        );
    }

    public static void main(String []args) {
        boolean pass = true;

        pass = pass && is_palindrome_permutation("")
            && is_palindrome_permutation("a")
            && !is_palindrome_permutation("ab")
            && is_palindrome_permutation("a A")
            && is_palindrome_permutation("Tact Coa")
            ;

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
