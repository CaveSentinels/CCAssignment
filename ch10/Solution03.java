import java.util.*;


public class Solution03 {

    private static int binary_search(int [] arr, int lower, int upper, int value) {
        int mid = -1;
        while (lower <= upper) {
            mid = (lower + upper) / 2;
            if (arr[mid] == value) {
                // We've found it!
                return mid;
            }
            else if (arr[mid] < value) {
                // Need to search in the upper half.
                lower = mid + 1;
            } else {
                // Need to search in the lower half.
                upper = mid - 1;
            }
        }

        // Cannot find the value.
        return -1;
    }

    /**
     *  @brief: Search for the index of the specified value in a rotated sorted
     *      array.
     *  @param: [in] arr: The rotated sorted array. The original array had an
     *      increasing order.
     *  @param: [in] size: The size of the array.
     *  @param: [in] value: The value to be searched in the array.
     *  @return: int: The index of the value in the array.
     *      - n: If the value can be found.
     *      - -1: If the value cannot be found.
     */
    private static int search_rotated_sorted_array(int [] arr, int size, int value) {
        // Handle special cases.
        if (arr == null || size <= 0) {
            return -1;  // No way to find value. Return -1.
        }

        if (size == 1) {
            // If there is only one element in the array, it's very easy to
            // figure out if the value can be found.
            return (arr[0] == value ? 0 : -1);
        }

        // Now we know there are at least two elements in the array.

        // Find the position where the tail meets the head.
        // Because the array was originally sorted in an increasing order,
        // the arr[i] should be always <= arr[i+1]. After rotation, it is
        // possible that at a certain position j that arr[j] > arr[j+1].
        // arr[j] is the maximum value in the array.
        int j = 0;
        while (j < size - 1) {
            if (arr[j] > arr[j+1]) {
                break;
            }
            ++j;
        }

        if (j == size - 1) {
            // That means the whole array is still sorted. Just perform a
            // binary search in this case.
            return binary_search(arr, 0, size-1, value);
        } else {
            // Now arr[j] is the maximum value of the array.
            // The entire array is divided into two parts: [0, j] and [j+1, size-1].
            // Both arrays have an increasing order. We can find the range of each
            // array, and perform a binary search once we know which array the given
            // value falls into.
            if (arr[0] <= value && value <= arr[j]) {
                return binary_search(arr, 0, j, value);
            } else if (arr[j+1] <= value && value <= arr[size-1]) {
                return binary_search(arr, j+1, size-1, value);
            }
        }

        // Otherwise, the value does not fall into the range of the array.
        // Return -1.
        return -1;
    }

    public static void main(String []args) {
        boolean pass = true;

        {
            int [] arr = { 15, 16, 19, 20, 25, 1, 3, 4, 5, 7, 10, 14 };
            int size = arr.length;
            pass = pass
                && (search_rotated_sorted_array(arr, size, 15) == 0)
                && (search_rotated_sorted_array(arr, size, 19) == 2)
                && (search_rotated_sorted_array(arr, size, 25) == 4)
                && (search_rotated_sorted_array(arr, size, 1) == 5)
                && (search_rotated_sorted_array(arr, size, 5) == 8)
                && (search_rotated_sorted_array(arr, size, 14) == 11)
                && (search_rotated_sorted_array(arr, size, 99) == -1)
                && (search_rotated_sorted_array(arr, size, -99) == -1)
                ;
        }

        {
            int [] arr = { 1, 3, 4, 5, 7, 10, 14, 15, 16, 19, 20, 25 };
            int size = arr.length;
            pass = pass
                && (search_rotated_sorted_array(arr, size, 1) == 0)
                && (search_rotated_sorted_array(arr, size, 10) == 5)
                && (search_rotated_sorted_array(arr, size, 25) == 11)
                && (search_rotated_sorted_array(arr, size, 99) == -1)
                && (search_rotated_sorted_array(arr, size, -99) == -1)
                ;
        }

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
