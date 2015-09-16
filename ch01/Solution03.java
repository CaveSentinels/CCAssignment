public class Solution03 {

    /**
     *  @brief: Replace the spaces in the given string with "%20".
     *  @param: [in/out] buf: The buffer that contains the string.
     *  @param: [in] str_length: The "true" length of the string.
     *  @param: [in] buf_size: The size of the buffer.
     *  @return: N/A
     */
    private static void urlify(char [] buf, int str_length, int buf_size) {
        // Validate the parameters.
        if (buf == null) {
            // Buffer cannot be null.
            return;
        }
        if (str_length < 0 || buf_size < 0 || buf_size < str_length) {
            // String length and buffer size must be valid.
            return;
        }

        // We set two pointers. The first pointer iterates backward from the end
        // of the string, while the second pointer iterates backward from the end
        // of the buffer. We copy each character pointed by the first pointer to
        // the position that's pointed by the second pointer. When it comes to a
        // space, we fill in a "%20" at the position of the second pointer.
        int i = str_length - 1;     // The first pointer
        int j = buf_size - 1;   // The second pointer
        while (i >= 0) {
            if (buf[i] != ' ') {
                // Just copy it.
                buf[j--] = buf[i--];
            } else {
                // We need to fill in a "%20".
                buf[j--] = '0';
                buf[j--] = '2';
                buf[j--] = '%';
                --i;
            }
        }
    }

    public static void main(String []args) {
        boolean pass = true;

        {
            char [] buf = {};
            urlify(buf, 0, 0);
            System.out.println(buf);
        }

        {
            char [] buf = { ' ', ' ', ' ' };
            urlify(buf, 1, 3);
            System.out.println(buf);
        }

        {
            char [] buf = { 'M', 'r', ' ', 'J', 'o', 'h', 'n', ' ', 'S', 'm', 'i', 't', 'h', ' ', ' ', ' ', ' ' };
            urlify(buf, 13, 17);
            System.out.println(buf);
        }

        {
            char [] buf = { 'M', 'r', 'J', 'o', 'h', 'n', 'S', 'm', 'i', 't', 'h' };
            urlify(buf, 11, 11);
            System.out.println(buf);
        }

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
