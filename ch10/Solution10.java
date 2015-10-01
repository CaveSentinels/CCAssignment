/**
 *  @algorithm:
 *  1). Use a linked list to maintain all the elements. The linked list grows
 *      as the integers flow in.
 *  2). We will try to maintain an ascending list. When the N-th integer flows
 *      in, we can safely assume that the [1, N-1] integers form an ordered
 *      list.
 *  3). When we need to get the rank of an integer, we just traverse the list
 *      from the head and count each element that is less than or equal to the
 *      given integer. The rank is the (count - 1) because we should exclude
 *      the value itself.
 *  4). For example, if we have the integer stream as the textbook provides:
 *          Stream: 5, 1, 4, 4, 5, 9, 7, 13, 3
 *      We will build our linked list in the steps below:
 *      (1) 5;
 *      (2) 1 -> 5;
 *      (3) 1 -> 4 -> 5;
 *      (4) 1 -> 4 -> 4 -> 5;
 *      (5) 1 -> 4 -> 4 -> 5 -> 5;
 *      (6) 1 -> 4 -> 4 -> 5 -> 5 -> 9;
 *      (7) 1 -> 4 -> 4 -> 5 -> 5 -> 7 -> 9;
 *      (8) 1 -> 4 -> 4 -> 5 -> 5 -> 7 -> 9 -> 13;
 *      (9) 1 -> 3 -> 4 -> 4 -> 5 -> 5 -> 7 -> 9 -> 13;
 *  5). When we need to get the rank of 1, we just start from the first
 *      element, which is 1. The next element is 3 so we stop. The rank of 1
 *      is therefore 0(=1-1).
 *  6). When we need to get the rank of 4, we still start from the first
 *      element. We continue until we reach 5 and stop. The count of elements
 *      that have been gone through is 4, so the rank of 4 is 3(=4-1).
 */


import java.util.*;


public class Solution10 {

    private static class StreamRanker {

        private Vector< Integer > _list = new Vector<>();

        public void Track(int x) {
            int i = 0;
            while (i < _list.size() && x > _list.get(i)) {
                ++i;
            }
            _list.add(i, x);
        }

        public int GetRank(int x) {
            int rank = 0;
            int i = 0;
            while (i < _list.size() && x >= _list.get(i)) {
                ++rank;
                ++i;
            }

            return (rank - 1);
        }

    };

    public static void main(String []args) {
        boolean pass = true;

        {
            StreamRanker ranker = new StreamRanker();
            int [] nums = { 1, 1, 1, 1, 1 };
            for (int i = 0; i < nums.length; ++i) {
                ranker.Track(nums[i]);
            }
            pass = pass
                && (ranker.GetRank(1) == 4)
                ;
        }

        {
            StreamRanker ranker = new StreamRanker();
            int [] nums = { 5, 1, 4, 4, 5, 9, 7, 13, 3 };
            for (int i = 0; i < nums.length; ++i) {
                ranker.Track(nums[i]);
            }
            pass = pass
                && (ranker.GetRank(1) == 0)
                && (ranker.GetRank(3) == 1)
                && (ranker.GetRank(4) == 3)
                && (ranker.GetRank(5) == 5)
                && (ranker.GetRank(7) == 6)
                && (ranker.GetRank(9) == 7)
                && (ranker.GetRank(13) == 8)
                ;
        }

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
