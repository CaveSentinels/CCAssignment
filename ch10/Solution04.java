/**
 *  @assumptions:
 *  1). The elements in the listy are in increasing order, for example:
 *          A: 1, 3, 5, 7, 9;
 *
 *  @algorithm:
 *  1). Generally, we need to do two things to search for the given value:
 *      a). Determine the length of the listy.
 *      b). Perform a binary search to find the value.
 *  2). We need to consider the special cases first:
 *      a). If the listy is null, we return -1.
 *      b). If the listy is empty, we return -1.
 *  3). Because we don't know the length of the listy, we must test to find
 *      its boundary. One way of doing so is to mimick the C++ vector memory
 *      reallocation strategy: Start from N, double it when boundary is reached.
 *      So we can start from 1. If listy[1] is not -1, we extend it to 2(=1*2).
 *      If listy[2] is not -1, we try 4(=2*2). If listy[4] is not -1, we try
 *      8(=4*2). And it goes on. At a certain number M, we will see listy[M]
 *      is not -1 but listy[M*2] is -1. This means the array boundary must
 *      be somewhere between listy[M+1] and listy[M*2-1]. We can perform a
 *      binary search to find the boundary.
 *  4). After we determine the boundary, say, [0, K], we just perform a binary
 *      search on the listy and find the index of x.
 *  5). However, we may not always have to find the boundary before we can
 *      find the value x. Consider the example below:
 *          listy: 1, 3, 5, 7, 9, ..., 41, 43;
 *          index: 0, 1, 2, 3, 4, ..., 20, 21;
 *              x: 5
 *      When we start to look for the boundary K, which is 21 in this case, we
 *      will go through this process:
 *          listy[1], listy[2], listy[4]
 *      When we check listy[4] which is 9 which is not -1, we realize that its
 *      value is already greater than the given value x which is 5. Because
 *      we know that the listy is sorted, we can perform the binary search
 *      between listy[0] and listy[4] to find x's index.
 *  6). Then why do we have to perfrom the binary search from listy[0]? In
 *      fact, during the course of searching for the boundary, we will know
 *      that listy[2] is smaller than x and listy[4] is greater than x, so
 *      we can limit the binary search between listy[2] and listy[4].
 */


import java.util.*;


public class Solution04 {

    // A Listy class.
    private static class Listy {

        private Vector< Integer > _array = new Vector<>();   // Internal storage

        public Listy() {
            // Empty
        }

        public Listy(final int [] arr, int size) {
            for (int i = 0; i < size; ++i) {
                _array.add(arr[i]);
            }
        }

        public void PushBack( int v ) {
            _array.add(v);
        }

        public void PopBack() {
            _array.remove(_array.size() - 1);
        }

        public int Front() {
            return _array.firstElement();
        }

        public int Back() {
            return _array.lastElement();
        }

        public int ElementAt( int i ) {
            if (i < 0 || i >= _array.size()) {
                return -1;
            }

            return _array.get(i);
        }
    }

    private static int binary_search(Listy ly, int lower, int upper, int value) {
        int mid = -1;
        while (lower <= upper) {
            mid = (lower + upper) / 2;
            if (ly.ElementAt(mid) == value) {
                // We've found it!
                return mid;
            }
            else if (ly.ElementAt(mid) < value) {
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
     *  @brief: Find the index of the given value x in the listy ly.
     *  @param: [in] ly: The listy that contains a sequence of numbers. The
     *      elements in ly are guaranteed to be positive numbers and sorted.
     *  @param: [in] x: The value to be searched.
     *  @return: int: The index of x in ly.
     *      - n: The index of x in ly. If x occurs multiple times, just return
     *          one of the indexes.
     *      - -1: If ly doesn't have x.
     */
    private static int search_sorted(Listy ly, final int x) {
        // Handle special cases.
        if (ly.ElementAt(0) == -1) {
            // If the ly[0] is -1, that means the listy is empty and we will not
            // be able to find x.
            return -1;
        }

        if (ly.ElementAt(0) == x) {
            // Now ly[0] is not -1. Test and see if we are lucky.
            return 0;
        }

        if (x < ly.ElementAt(0)) {
            // Because the listy is sorted, if ly[0] is greater than x, there
            // won't be an x in the listy.
            return -1;
        }

        // Now it is guaranteed that ly[0] is smaller than x.

        // Start to look for the boundary of ly. Meanwhile, also keep a track of
        // the possible range that x can be found.

        int lastB = 0;  // The boundary index we tried last time.
        int B = 1;  // B is for the boundary. We start with 1.
        while (ly.ElementAt(B) != -1) {
            // Try to see if x may fall into the range of ly[lastB] and ly[B].
            if (x == ly.ElementAt(B)) {
                // If ly[B] is x, we can return directly.
                return B;
            } else if (x < ly.ElementAt(B)) {
                // OK we've found a range that x falls into even though we haven't
                // found the actual boundary of the listy.
                // Perform a binary search.
                return binary_search(ly, lastB, B, x);
            } else {
                // Now x is still greater than ly[B], we need to continue the
                // search for the boundary as well as the range.
                lastB = B;
            }

            // When ly[B] != -1, that means we haven't found the boundary.
            // Double B and try again.
            B *= 2;
        }

        // When we are here, that means we've reached a B that ly[lastB] is not -1
        // and smaller than x, and ly[B] is -1 which is beyond the boundary of the
        // listy:
        //              ly[lastB], ..., ly[K], ..., ly[B]
        // where the ly[K] is the actual boundary.

        // We can perform a binary-search-like operation to find the K while still
        // keeping an eye to see if we can find a range that x falls into.
        int K = -1;
        while (lastB < B) {
            K = (lastB + B) / 2;
            if (ly.ElementAt(K) == -1) {
                // The current K is still beyond the actual boundary. Lower B for
                // a new search.
                B = K - 1;
            } else if (ly.ElementAt(K) == x) {
                // Lucky! We just come across x.
                return K;
            } else if (ly.ElementAt(K) < x) {
                // Move lastB higher to search in the higher half.
                lastB = K + 1;
            } else if (ly.ElementAt(K) > x) {
                // OK, now we find a range that x may possibly fall into:
                // [ly[lastB], ly[K]]. Perform a binary search to find the actual
                // index of x.
                return binary_search(ly, lastB, K, x);
            } else {
                // Hmm... We've examined every possible situation above but we
                // still are here.. That means something really bad is happening.
                return -1;
            }
        }

        // We've tried our best but couldn't find x.
        return -1;
    }

    public static void main(String []args) {
        boolean pass = true;

        {
            int [] arr = { };
            final int SIZE = arr.length;
            Listy ly = new Listy(arr, SIZE);
            pass = pass && (search_sorted(ly, 1) == -1)
                && (search_sorted(ly, 9) == -1)
                && (search_sorted(ly, 19) == -1)
                && (search_sorted(ly, -99) == -1)
                && (search_sorted(ly, 99) == -1)
                ;
        }

        {
            int [] arr = { 1 };
            final int SIZE = arr.length;
            Listy ly = new Listy(arr, SIZE);
            pass = pass && (search_sorted(ly, 1) == 0)
                && (search_sorted(ly, 9) == -1)
                && (search_sorted(ly, 19) == -1)
                && (search_sorted(ly, -99) == -1)
                && (search_sorted(ly, 99) == -1)
                ;
        }

        {
            int [] arr = { 1, 3, 5, 7, 9, 9, 9, 15, 17, 19 };
            final int SIZE = arr.length;
            Listy ly = new Listy(arr, SIZE);
            int index9 = search_sorted(ly, 9);
            pass = pass && (index9 == 4 || index9 == 5 || index9 == 6);
        }

        {
            int [] arr = { 1, 1, 1, 1 };
            final int SIZE = arr.length;
            Listy ly = new Listy(arr, SIZE);
            int index1 = search_sorted(ly, 1);
            pass = pass && (index1 == 0 || index1 == 1 || index1 == 2 || index1 == 3)
                && (search_sorted(ly, -99) == -1)
                && (search_sorted(ly, 99) == -1)
                ;
        }

        {
            int [] arr = { 1, 3, 5, 7, 9, 11, 13, 15, 17, 19 };
            final int SIZE = arr.length;
            Listy ly = new Listy(arr, SIZE);
            pass = pass && (search_sorted(ly, 1) == 0)
                && (search_sorted(ly, 9) == 4)
                && (search_sorted(ly, 19) == 9)
                && (search_sorted(ly, -99) == -1)
                && (search_sorted(ly, 99) == -1)
                ;
        }

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
