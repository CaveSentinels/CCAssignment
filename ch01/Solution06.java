public class Solution06 {

    /**
     *  @brief: Given the count of one char's occurrence, calculate how many chars
     *      are needed to compress the sequence of chars.
     *  @param: [in] count: The number of occurrences of one char.
     *  @return: int: The number of chars needed to store the compressed char
     *      sequence.
     *  @example:
     *      1). If we have a char sequence of "a", then we need 2 chars to store
     *          the compressed sequence: "a1".
     *      2). If we have a char sequence of "aa", then we need 2 chars to store
     *          the compressed sequence: "a2".
     *      3). If we have a char sequence of "aaaa", then we need 2 chars to store
     *          the compressed sequence: "a4".
     *  @note:
     *      1). count < 10 (= 10 ^ 1), we need 2 chars;
     *      2). count < 100 ( = 10 ^ 2), we need 3 chars;
     *      3). count < 1000 ( = 10 ^ 3), we need 4 chars;
     *      ...
     *      In general, we need (N+1) chars to store a sequence of chars which has
     *      the length of less than (10 ^ N).
     */
    private static int chars_needed(int count) {
        int num = 0;
        while (count > 0) {
            count /= 10;
            ++num;
        }
        return num + 1;
    }


    /**
     *  @brief: Count how many chars can be saved.
     *  @param: [in] occurrences: The number of occurrence of the char.
     *  @return: int
     *      The number of chars that is saved by compression.
     *  @example:
     *      1). Given "a", the compression is "a1", -1 char is saved;
     *      2). Given "aa", the compression is "a2", 0 char is saved;
     *      3). Given "aaaaa", the compression is "a5", 3 chars are saved;
     *      ...
     */
    private static int count_save(int occurrences) {
        if (occurrences == 1) {
            // -1 char is saved.
            return -1;
        } else if (occurrences > 2) {
            // Multiple chars are saved.
            return (occurrences - chars_needed(occurrences));
        }

        // "occurrences" is 2. No save.
        return 0;
    }


    /**
     *  @brief: Compress the given string.
     *  @param: [in] str: The string to be compressed.
     *  @return: std::string
     *      The compressed string if the length becomes shorter; otherwise, the
     *      original string is returned.
     *  @time: O(N); O(2N); O(2N);
     *  @space: O(1); O(N); O(N);
     */
    private static String compress_string(String str) {
        // In general, the algorithm takes two steps:
        // Step 1: Calculate the length of the compressed string.
        // Step 2: If the length of the compressed string is shorter, then
        // generate the compressed string.

        int str_length = str.length();
        if (str_length <= 2) {
            // 1). If str is empty, we have nothing to compress.
            // 2). If str has only one char, compression will make the result
            // longer, so we won't compress it.
            // 3). If str has two chars and they are different, the compressed
            // string will have four chars; if the two chars are the same, the
            // compressed string also has two chars. In both cases, we should
            // return the original string.
            return str;
        }

        // Step 1: Calculate the length of the compressed string.
        int i = 0;  // The index of the char that we are looking at.
        char curr = str.charAt(0); // The char that we are counting now.
        int curr_occurrence = 0;   // Count the occurrences of current char.
        int save = 0;   // Count how many chars are saved by the compression.
        while (i < str_length) {
            if (str.charAt(i) == curr) {
                ++curr_occurrence;
                ++i;
            } else {
                curr = str.charAt(i);
                save += count_save(curr_occurrence);
                curr_occurrence = 0;   // Reset
                // We do not increment i here.
            }
        }

        // Handle the last char sequence.
        save += count_save(curr_occurrence);

        int result_length = str_length - save;
        if (result_length >= str_length) {
            // If the compression doesn't help, we just return the original string.
            return str;
        }

        // Step 2: Now allocate space to compress the string.
        StringBuilder sb = new StringBuilder(result_length);
        i = 0;  // The index of the original string.
        curr = str.charAt(0);
        curr_occurrence = 0;
        while (i < str_length) {
            if (str.charAt(i) == curr) {
                ++curr_occurrence;
                ++i;
            } else {
                sb.append(curr).append(curr_occurrence);
                curr = str.charAt(i);
                curr_occurrence = 0;    // Reset
            }
        }

        // Handle the last char sequence.
        sb.append(curr).append(curr_occurrence);

        return sb.toString();
    }

    public static void main(String []args) {
        boolean pass = true;

        pass = pass && (compress_string("").equals(""))
            && (compress_string("a").equals("a"))
            && (compress_string("aa").equals("aa"))
            && (compress_string("aaa").equals("a3"))
            && (compress_string("aaaab").equals("a4b1"))
            && (compress_string("aaaabc").equals("aaaabc"))
            && (compress_string("aaaabcccc").equals("a4b1c4"))
            ;

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
