import java.util.*;


public class Solution02 {

    private static String bin_to_str(double v) {

        // We need to prepend "0." to the result binary representation. Therefore,
        // we only try to convert at most 30 characters.
        StringBuilder sb = new StringBuilder("0.");
        double r = v;
        for (int i = 0; i < 30; ++i) {
            r *= 2;

            // Check the relationship between r and 1.0.
            int comp = Double.compare(r, 1.0);
            if (comp == 0) {
                // Equal. Conversion complete.
                sb.append('1');
                break;
            } else if (comp < 0) {
                sb.append('0');
            } else {
                sb.append('1');
                r -= 1.0;
            }
        }

        // Check if we've finished the conversion.
        String str = sb.toString();
        if (Double.compare(r, 1.0) != 0) {
            // If r is not 1.0, that means v cannot be represented in 32 chars.
            str = "ERROR";
        }

        return str;
    }

    public static void main(String []args) {
        boolean pass = true;

        pass = pass
            && (bin_to_str(0.5).equals("0.1"))
            && (bin_to_str(0.25).equals("0.01"))
            && (bin_to_str(0.875).equals("0.111"))
            && (bin_to_str(0.1).equals("ERROR"))
            && (bin_to_str(0.05).equals("ERROR"))
            && (bin_to_str(0.01).equals("ERROR"))
            ;

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
