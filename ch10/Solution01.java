/**
 *  @assumptions:
 *  1). The arrays A and B are both in ascending order, for example:
 *          A: 1, 3, 5, 7, 9;
 *          B: 2, 4, 6, 8;
 *  2). "large enough" may not mean the buffer is of the exact size of array B.
 *      It could be larger than B's actual size.
 */


import java.util.*;


public class Solution01 {

    /**
     *  @brief: Merge the two sorted arrays A and B. A has large enough buffer to
     *      hold B.
     *  @param: [in/out] A: The array A.
     *  @param: [in] LEN_A: The actual length of A (number of filled in elements).
     *  @param: [in] B: The array B.
     *  @return: int: The length of the merged array. For example, if A has 5
     *      elements and B has 3 elements, then 8(=5+3) is returned.
     */
    private static int merge_sorted_arrays(int [] A, final int LEN_A, int [] B) {
        final int CAPACITY_A = A.length;
        final int LEN_B = B.length;

        // Handle special cases
        if (0 == LEN_B) {
            // If B is empty, we have nothing to merge.
            return LEN_A;
        }

        // The index of the last element in the merged array.
        final int LAST_INDEX = LEN_A + LEN_B - 1;
        int ptr_A = LEN_A - 1;
        int ptr_B = LEN_B - 1;

        // Merge as many elements as possible.
        int last = LAST_INDEX;
        while (ptr_A >= 0 && ptr_B >= 0) {
            if (A[ptr_A] >= B[ptr_B]) {
                A[last] = A[ptr_A];
                --ptr_A;
            } else {
                A[last] = B[ptr_B];
                --ptr_B;
            }
            --last;
        }

        // Merge the remaining part, if any, of A.
        while (ptr_A >= 0) {
            A[last] = A[ptr_A];
            --last;
            --ptr_A;
        }

        // Merge the remaining part, if any, of B.
        while (ptr_B >= 0) {
            A[last] = B[ptr_B];
            --last;
            --ptr_B;
        }

        return (LEN_A + LEN_B);
    }


    private static boolean vec_compare(final int [] v, int [] nums)
    {
        if (v.length > nums.length) {
            // Here we do not apply a strict equality comparison.
            // As long as the vector contains the elements in nums in the same
            // sequence, they are treated equal.
            return false;
        }

        for (int i = 0; i < v.length; ++i) {
            if (v[i] != nums[i]) {
                return false;
            }
        }

        return true;
    }

    public static void main(String []args) {
        boolean pass = true;

        {
            int [] A = {};
            int [] B = {};
            int s = merge_sorted_arrays(A, 0, B);
            int [] r = {};
            pass = pass && vec_compare(A, r);
        }

        {
            int [] A = { 1, 3, 5, 0, 0, 0, 0, 0 };
            int [] B = { 2, 4, 6, 8, 10 };
            int s = merge_sorted_arrays(A, 3, B);
            int [] r = {1, 2, 3, 4, 5, 6, 8, 10 };
            pass = pass && vec_compare(A, r);
        }

        {
            int [] A = { 1, 3, 5, 0, 0, 0, 0, 0, 0, 0, 0 };  // More buffer than required
            int [] B = { 2, 4, 6, 8, 10 };
            int s = merge_sorted_arrays(A, 3, B);
            int r[] = {1, 2, 3, 4, 5, 6, 8, 10, 0, 0, 0 };
            pass = pass && vec_compare(A, r);
        }

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
