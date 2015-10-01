/**
 *  @assumption:
 *  1). Because the original question requires to deal with a very large scale
 *      of data and it is almost untestable in my laptop, I reduce the scale
 *      of the problem to make it testable.
 *  2). Also, for sake of ease, I use the memory as the data source so I don't
 *      have to make another file and the source code is fully self-contained.
 *  3). To mimick reading data from a disk file, I wrote a simple class
 *      "ArrayReader" to read the data in the same manner of reading the numbers
 *      from a file. Later, I can easily change it to a real file reader.
 */

import java.util.*;


public class Solution07_02 {

    private static class ArrayReader {
        // The index of the next number to be read.
        private int next_index;
        // The array that has all the numbers.
        private int [] array;

        public ArrayReader(int [] arr) {
            array = arr;
            next_index = 0;
        }

        public boolean has_next() {
            return next_index < array.length;
        }

        public int next() {
            return array[next_index++];
        }
    }

    // Four billion is too much! In order to make the program testable, I
    // reduce the scale to 400,000.
    private static final int TOTAL_NUM_COUNT = 400000;

    // ========================================================================
    // ORIGINAL

    private static final int AVAILABLE_MEM_BYTES_ORIGINAL = 100000;

    private static int find_missing_number_original(int [] array) {
        byte[] bitset = new byte[TOTAL_NUM_COUNT / 8];

        ArrayReader ar = new ArrayReader(array);
        while (ar.has_next()) {
            int value = ar.next();
            bitset[value / 8] |= (1 << (value % 8));
        }

        for (int i = 0; i < bitset.length; ++i) {
            for (int j = 0; j < 8; ++j) {
                if ((bitset[i] & (1 << j)) == 0) {
                    // (i * 8 + j) is the index of the number that should have
                    // been present at this position but missing.
                    // Therefore, the current array[i*8+j] must be the number
                    // that follows this number, so we should subtract 1 from
                    // it to get the missing number.
                    return array[i * 8 + j] - 1;
                }
            }
        }

        return -1;
    }

    // ========================================================================
    // FOLLOW UP

    // Because I reduce the scale of numbers, I also reduce the available
    // memory to just 1000 bytes.
    private static final int AVAILABLE_MEM_BYTES = 1000;

    /**
     *  How to calculate the block size (aka the rangeSize in the given
     *  solution):
     *
     *  1). We have 1000 bytes of memory available, which is 250(= 1000 / 4)
     *      ints.
     *  2). Therefore, the number of blocks cannot exceed 250:
     *          BLOCK_COUNT = TOTAL_NUM_COUNT / BLOCK_SIZE <= 250
     *      So we have:
     *          BLOCK_SIZE >= TOTAL_NUM_COUNT / 250 = 400,000 / 250 = 1600
     *  3). Later we will use one bit to represent one number in a block. To
     *      represent the minimum size of a block, we need:
     *          1600 bits / 8 bits/byte / = 200 bytes
     *      We know that at most we can use 1000 bytes. We use a value in
     *      between, say, 500 bytes. So totally we have:
     *          500 bytes * 8 bit/byte = 4000 bits
     *      So we can arrange 4000 numbers in one block.
     */
    private static final int BLOCK_SIZE = 4000;

    // Count how many numbers occur in each block.
    private static int [] get_block_count(int [] array) {
        int [] blocks = new int[TOTAL_NUM_COUNT / BLOCK_SIZE];
        ArrayReader ar = new ArrayReader(array);
        while (ar.has_next()) {
            int value = ar.next();
            int block_index = value / BLOCK_SIZE;
            blocks[block_index]++;
        }
        return blocks;
    }

    // Find the block that has the missing number.
    private static int find_broken_block(int [] blocks) {
        for (int i = 0; i < blocks.length; ++i) {
            if (blocks[i] < BLOCK_SIZE) {
                return i;
            }
        }
        return -1;
    }

    // Create the bitset.
    private static byte [] get_block_bitset(int [] array, int block_index) {
        int start = block_index * BLOCK_SIZE;
        int end = start + BLOCK_SIZE;
        byte [] bitset = new byte[BLOCK_SIZE / Byte.SIZE];
        for (int i = 0; i < array.length; ++i) {
            int value = array[i];
            if (start <= value && value < end) {
                int offset = value - start;
                int mask = (1 << (offset % Byte.SIZE));
                bitset[offset / Byte.SIZE] |= mask;
            }
        }
        return bitset;
    }

    private static int find_missing_offset(byte b) {
        for (int i = 0; i < Byte.SIZE; i++) {
            int mask = 1 << i;
            if ((b & mask) == 0) {
                return i;
            }
        }
        return -1;
    }

    private static int find_missing_index(byte[] bitset) {
        for (int i = 0; i < bitset.length; ++i) {
            if (bitset[i] != ~0) {
                int bit_index = find_missing_offset(bitset[i]);
                return i * Byte.SIZE + bit_index;
            }
        }
        return -1;
    }

    private static int find_missing_number_follow_up(int [] array) {

        // Get the count of numbers in each block.
        int [] blocks = get_block_count(array);

        // Find a block that has a missing value.
        int block_index = find_broken_block(blocks);
        if (block_index < 0) {
            return -1;
        }

        // Create bit vector for items within this range.
        byte [] bitset = get_block_bitset(array, block_index);

        // Find the zero from the bitset.
        int offset = find_missing_index(bitset);
        if (offset < 0) {
            return -1;
        }

        // Compute the missing value.
        return block_index * BLOCK_SIZE + offset;
    }

    public static void main(String []args) {
        boolean pass = true;

        // Test the original
        {
            // Create the original array.
            int [] array = new int [TOTAL_NUM_COUNT - 1];
            int curr_num = 0;
            for (int i = 0; i < array.length; ++i, ++curr_num) {
                if (curr_num == 400) {
                    // Increment curr_num one more time to create a hole.
                    ++curr_num;
                }
                array[i] = curr_num;
            }

            // System.out.println("Missing number: " + find_missing_number_original(array));
            pass = pass && (find_missing_number_original(array) == 400);
        }

        // Test the follow-up
        {
            // Create the original array.
            int [] array = new int [TOTAL_NUM_COUNT - 1];
            int curr_num = 0;
            for (int i = 0; i < array.length; ++i, ++curr_num) {
                if (curr_num == 400) {
                    // Increment curr_num one more time to create a hole.
                    ++curr_num;
                }
                array[i] = curr_num;
            }

            // System.out.println("Missing number: " + find_missing_number_follow_up(array));
            pass = pass && (find_missing_number_follow_up(array) == 400);
        }

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
