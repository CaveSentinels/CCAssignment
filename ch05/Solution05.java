import java.util.*;


public class Solution05 {

    private static boolean is_zero_or_power_of_2(int n) {
        // This statement tests if n is a power of 2 or if it is 0.
        return (n & (n-1)) == 0;
    }

    public static void main(String []args) {
        boolean pass = true;

        pass = pass
            && is_zero_or_power_of_2(0)
            && is_zero_or_power_of_2(1)
            && is_zero_or_power_of_2(2)
            && is_zero_or_power_of_2(4)
            && !is_zero_or_power_of_2(3)
            && !is_zero_or_power_of_2(5)
            && !is_zero_or_power_of_2(6)
            && !is_zero_or_power_of_2(10)
            ;

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
