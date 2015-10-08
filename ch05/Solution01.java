import java.util.*;


public class Solution01 {

    private static int insert_int(int N, int M, final int i, final int j) {
        int M2 = (M << i);  // M: Shift i bits leftwards.

        // Make the mask.
        final int LENGTH = j - i;
        int S = 1;
        for (int k = 0; k < LENGTH; ++k) {
            S <<= 1; // X: Shift 1 bit leftwards.
            S |= 1; // Fill the lowest bit with 1.
        }
        S <<= i;
        int T = ~S;

        int N2 = N & T;

        return M2 | N2;
    }

    public static void main(String []args) {
        boolean pass = true;

        pass = pass && (insert_int(38493/*N*/, 19/*M*/, 2, 6) ==  38477);

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
