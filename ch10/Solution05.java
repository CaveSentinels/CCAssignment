/**
 *  @assumptions:
 *  1). The string to be searched for is not an empty string.
 *
 *  @algorithm:
 *  1). Because the array of strings are sorted, if we ignore all the empty
 *      strings, we can try to solve this problem in a similar way of binary
 *      search.
 *  2). It is very likely that the middle value we find is an empty string, and
 *      we don't which direction to move on. A possible solution is to just
 *      pick up one direction and move to that, say, always move leftward.
 *  3). If we always move leftward, we may come to the very beginning of the
 *      array but find no meaningful strings. In this case, we can change the
 *      direction temporarily and move rightward.
 *  4). If we move rightward but still cannot find a meaningful string, that
 *      means the array is full of empty strings and we just return -1.
 *  5). Otherwise, we should always come across some non-empty string. We set
 *      up the new lower or upper bound and continue with the binary search.
 */


import java.util.*;


public class Solution05 {

    /**
     *  @brief: Find the index of str in strings, an sparsed array of strings.
     *  @param: [in] strings: An array of strings.
     *  @param: [in] str: The string of which index is to be searched for.
     *  @return: int: The index of str in strings.
     *      - n: The index of str if it is in strings.
     *      - -1: If str is not in strings.
     */
    private static int search_sparsed_array(String[] strings, String str) {

        int lower = 0;
        int upper = strings.length - 1;
        int mid = -1;

        while (lower <= upper) {
            mid = (lower + upper) / 2;

            if (strings[mid].isEmpty()) {
                // Remember the old position in case we need to move rightward.
                int curr_mid = mid;

                // Keep moving leftward until coming across a non-empty string.
                do {
                    --mid;
                } while (mid >= lower && strings[mid].isEmpty());

                if (mid < lower) {
                    // If we quit the while loop above because (mid > lower) failed,
                    // that means we didn't find any non-empty string on the left,
                    // and we should try the right side.
                    mid = curr_mid;
                    do {
                        ++mid;
                    } while (mid <= upper && strings[mid].isEmpty());

                    if (mid > upper) {
                        // We've come to the end but still find nothing.
                        return -1;
                    }
                }
            }

            int result = strings[mid].compareTo(str);
            if (result == 0) {
                // We've found str and just return its index.
                return mid;
            } else if (result < 0) {
                // Need to search in the upper half.
                lower = mid + 1;
            } else if (result > 0) {
                // Need to search in the lower half.
                upper = mid - 1;
            } else {
                // A case that should never happen.
                return -1;
            }
        }

        return -1;
    }

    public static void main(String []args) {
        boolean pass = true;

        {
            // Empty array of strings.
            String [] strings = {};
            pass = pass && (search_sparsed_array(strings, "ball") == -1)
                ;
        }

        {
            // Only empty strings.
            String [] strings = { "", "", "" };
            pass = pass && (search_sparsed_array(strings, "ball") == -1)
                ;
        }

        {
            // Only one string.
            String [] strings = {
                "at"
            };
            pass = pass && (search_sparsed_array(strings, "at") == 0)
                && (search_sparsed_array(strings, "ball") == -1)
                ;
        }

        {
            // Normal array of strings.
            String [] strings = {
                "at", "", "", "", "ball", "", "", "car", "", "", "dad"
            };
            pass = pass && (search_sparsed_array(strings, "at") == 0)
                && (search_sparsed_array(strings, "ball") == 4)
                && (search_sparsed_array(strings, "car") == 7)
                && (search_sparsed_array(strings, "dad") == 10)
                ;
        }

        {
            // No empty strings.
            String [] strings = {
                "at", "ball", "car", "dad"
            };
            pass = pass && (search_sparsed_array(strings, "at") == 0)
                && (search_sparsed_array(strings, "ball") == 1)
                && (search_sparsed_array(strings, "car") == 2)
                && (search_sparsed_array(strings, "dad") == 3)
                ;
        }

        {
            // Have empty strings in both ends.
            String [] strings = {
                "", "", "", "", "", "at", "", "", "ball", "", "", "car", "", "", "dad", "", "", "", "", ""
            };
            pass = pass && (search_sparsed_array(strings, "at") == 5)
                && (search_sparsed_array(strings, "ball") == 8)
                && (search_sparsed_array(strings, "car") == 11)
                && (search_sparsed_array(strings, "dad") == 14)
                ;
        }

        {
            // A very sparsed array.
            String [] strings = {
                "at", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "dad", ""
            };
            pass = pass && (search_sparsed_array(strings, "at") == 0)
                && (search_sparsed_array(strings, "ball") == -1)
                && (search_sparsed_array(strings, "car") == -1)
                && (search_sparsed_array(strings, "dad") == 21)
                ;
        }

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
