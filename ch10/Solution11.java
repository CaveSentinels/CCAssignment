/**
 *  @algorithm:
 *  1). The problem requires us to sort the array of integers into an
 *      alternative sequence of peaks and valleys, that is:
 *          peak, valley, peak, valley, ...
 *  2). The integers in a sorted array of the descending order have the
 *      following relationships:
 *                      array[i] >= array[i+1]
 *      for all i = 0, 1, 2, ... N-2 where N is the number of integers in the
 *      array.
 *  3). The relationships between the numbers in the desired peak-valley
 *      sequence are:
 *          peak >= valley <= peak >= valley <= ...
 *  4). If we skip the first number and start to flip every two adjacent numbers
 *      in a descending array, we will get an array of the same relationships:
 *
 *      ORIGINAL: arr[0] >= arr[1] >= arr[2] >= arr[3] >= arr[4] >= ...
 *
 *      NEW: arr[0] >= arr[2] <= arr[1] >= arr[4] <= arr[3] >= ...
 *  5). Therefore, we can just sort the original array, and then flip every
 *      two adjacent numbers from arr[1].
 */


import java.util.*;


public class Solution11 {

    /**
     *  @brief: Convert the given array into an alternative sequence of peaks and
     *      valleys.
     *  @param: [in/out] array: The original array and the result array.
     *  @return: N/A
     */
    private static void convert_peaks_valleys(Integer [] array) {
        // Handle special cases
        if (array.length <= 1) {
            // If the array is empty or has only one number, we don't need to do
            // anything.
            return;
        }

        // Sort the array in descending order.
        Arrays.sort(array, Collections.reverseOrder());

        // Starting from the second position(array[1]), flip every two numbers.
        for (int i = 1; i < array.length; i+=2) {
            if (i+1 < array.length) {
                int tmp = array[i+1];
                array[i+1] = array[i];
                array[i] = tmp;
            } else {
                // array[i+1] exceeds the boundary so just stop.
            }
        }
    }

    private static void print_array(Integer [] array) {
        for (int i = 0; i < array.length; ++i) {
            System.out.print(array[i] + " ");
        }
        System.out.println("");
    }

    public static void main(String []args) {
        boolean pass = true;

        {
            // Empty array.
            Integer [] array = {};
            convert_peaks_valleys(array);
            print_array(array);
        }

        {
            // Array of one element.
            Integer [] array = { 0 };
            convert_peaks_valleys(array);
            print_array(array);
        }

        {
            // Array of small even number of elements.
            Integer [] array = { 1, 2 };
            convert_peaks_valleys(array);
            print_array(array);
        }

        {
            // Array of small odd number of elements.
            Integer [] array = { 1, 2, 3 };
            convert_peaks_valleys(array);
            print_array(array);
        }

        {
            // Array of bigger even number of elements.
            Integer [] array = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
            convert_peaks_valleys(array);
            print_array(array);
        }

        {
            // Array of bigger odd number of elements.
            Integer [] array = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
            convert_peaks_valleys(array);
            print_array(array);
        }

        {
            // Array of repeated elements.
            Integer [] array = { 1, 2, 2, 2, 3, 3, 4, 5, 5, 5 };
            convert_peaks_valleys(array);
            print_array(array);
        }

        {
            // The example from text book.
            Integer [] array = { 5, 3, 1, 2, 3 };
            convert_peaks_valleys(array);
            print_array(array);
        }

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
