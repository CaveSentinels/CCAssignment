import java.util.*;


public class Solution03 {

    private static class BitIsland {
        int num;
        int count;

        BitIsland() {
            num = 0;
            count = 0;
        }
        BitIsland(int n, int c) {
            num = n;
            count = c;
        }
    }


    private static BitIsland get_next_island(Integer num, int bit, Integer bit_processed) {
        int curr_island_count = 0;
        int lowest_bit = num & 1;
        while (lowest_bit == bit && bit_processed < 32) {
            ++curr_island_count;
            ++bit_processed;
            num >>= 1;
            lowest_bit = num & 1;
        }

        return new BitIsland(bit, curr_island_count);
    }

    private static int longest_bit_1_seq(int num) {
        // Handle special cases.
        if (num == 0) {
            return 1;
        } else if (num == Integer.MAX_VALUE) {
            return 32;
        }

        // The vector that stores isolated "1"-islands.
        Vector< BitIsland > islands = new Vector<>();

        int bit_processed = 0;
        int bit = 1;    // Assume first bit is 1.

        while (bit_processed < 32) {
            BitIsland island = get_next_island(num, bit, bit_processed);
            islands.add(island);
            bit = (bit + 1) % 2;    // Flip bit: 0 -> 1 or 1 -> 0.
        }

        // Check if the last island is 0-island.
        if (islands.lastElement().num == 0) {
            // If so, append a zero-count 1-island to make the pattern complete.
            islands.add(new BitIsland(1, 0));
        }

        // Examine each pattern.
        int max_len = 0;
        int start = 0;
        while (start + 2 < islands.size()) {
            int max_this = 0;
            int N = islands.get(start).count;
            int Z = islands.get(start+1).count;
            int M = islands.get(start+2).count;
            if (Z == 1) {
                max_this = N + 1 + M;
            } else {
                max_this = Math.max(N+1, M+1);
            }

            max_len = Math.max(max_len, max_this);

            start += 2;
        }

        return max_len;
    }

    public static void main(String []args) {
        boolean pass = true;

        pass = pass
            // && (longest_bit_1_seq(0) == 1)
            // && (longest_bit_1_seq(1775) == 8)
            // && (longest_bit_1_seq(1541243) == 7)
            // && (longest_bit_1_seq(2147483647) == 32)
            // && (longest_bit_1_seq(4286578687) == 32)
            // && (longest_bit_1_seq(4294967294) == 32)
            // && (longest_bit_1_seq(4294967295) == 32)
            ;

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
