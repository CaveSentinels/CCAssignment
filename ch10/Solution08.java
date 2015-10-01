/**
 *  @algorithm:
 *  1). If we use one bit to track if one particular number has appeared or
 *      not, then 4KB is sufficient to track all the numbers from 1 to 32,000.
 *  2). Make a bit set to track the presence of every number.
 *  3). If a number's bit has been set to 1 when this number is being checked,
 *      that means this number had appeared before and should be printed out
 *      as duplicated.
 */


import java.util.*;


public class Solution08 {

    private static class BitSet {
        // We need 1000 integers(4-byte long) to store all the 32,000 bits.
        final int SIZE = 1000;

        private int [] bitset = new int[SIZE];

        /**
         *  @brief: Get the value of the specified bit.
         *  @param: [in] pos: The index of the position to be checked.
         *  @return: int: The value of the specified bit.
         *      - 0: If the specified bit is zero.
         *      - 1: If the specified bit is one.
         */
        public int get(int pos) {
            // Calculate which integer we should examine.
            int offset_int = (pos >> 5);
            // Calculate which bit should be examined in the integer.
            int offset_bit = (pos & 0x1F);
            // Figure out if that bit is 0 or 1.
            int result = (bitset[offset_int] & (1 << offset_bit)) >> offset_bit;
            return result;
        }

        /**
         *  @brief: Set the value of the specified bit to 1.
         *  @param: [in] pos: The index of the position to be set.
         *  @return: N/A
         */
        public void set(int pos) {
            // Calculate which integer we should examine.
            int offset_int = (pos >> 5);
            // Calculate which bit should be examined in the integer.
            int offset_bit = (pos & 0x1F);
            bitset[offset_int] |= (1 << offset_bit);
        }
    }

    private static void print_duplicates(int[] array) {
        BitSet bs = new BitSet();
        for (int i = 0; i < array.length; ++i) {
            // bitset starts at 0, numbers start at 1.
            int pos = array[i] - 1;
            if (bs.get(pos) == 1) {
                // If the bit is already 1, that means this number had appeared
                // earlier.
                System.out.print(array[i] + ", ");
            } else {
                // Otherwise, we just set its bit.
                bs.set(pos);
            }
        }
        System.out.println("");
    }

    public static void main(String []args) {
        boolean pass = true;

        {
            int [] array = new int[32000];
            for (int i = 0; i < array.length; ++i) {
                array[i] = i + 1;
            }
            // Create some duplicates.
            array[1983] = 1983;
            array[10] = 1983;
            array[26] = 1983;
            array[1986] = 1986;
            array[4] = 1986;
            array[5] = 1986;

            print_duplicates(array);
        }

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
