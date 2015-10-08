import java.util.*;


public class Solution07 {

    private static int swap_bits(final int N) {
        int mask_odd = 0xAAAAAAAA;
        int mask_even = 0x55555555;
        return ((((N & mask_odd)) >>> 1) | ((N & mask_even) << 1));
    }

    public static void main(String []args) {
        boolean pass = true;

        pass = pass
            && (swap_bits(0) == 0)
            && (swap_bits(1) == 2)
            && (swap_bits(2) == 1)
            && (swap_bits(3) == 3)
            && (swap_bits(0x55555555) == 0xAAAAAAAA)
            && (swap_bits(0xAAAAAAAA) == 0x55555555)
            && (swap_bits(0xC0000000) == 0xC0000000)
            && (swap_bits(0x40000000) == 0x80000000)
            && (swap_bits(0x80000000) == 0x40000000)
            ;

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
