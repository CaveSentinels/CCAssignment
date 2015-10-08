import java.util.*;


public class Solution06 {

    private static int count_flips(int A, int B) {
        int count = 0;
        int mask = 1;
        while (mask != 0) {
            if ((A & mask) != (B & mask)) {
                ++count;
            }
            mask <<= 1;
        }
        return count;
    }

    public static void main(String []args) {
        boolean pass = true;

        pass = pass
            && (count_flips(1, 0) == 1)
            && (count_flips(2, 0) == 1)
            && (count_flips(3, 0) == 2)
            && (count_flips(4, 0) == 1)
            && (count_flips(0x80000000, 0x7FFFFFFF) == 32)
            && (count_flips(29, 15) == 2)
            ;

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
