/**
 *  @algorithm:
 *  1). Because both the rows and columns are sorted in ascending order, we
 *      can use a 2-D binary search.
 *  2). For example, given the M x N matrix:
 *      | 01, 02, 03, 04, 05 |
 *      | 06, 07, 08, 09, 10 |
 *      | 11, 12, 13, 14, 15 |
 *      | 16, 17, 18, 19, 20 |
 *      | 21, 22, 23, 24, 25 |
 *      | 26, 27, 28, 29, 30 |
 *      If we want to find 12, we can set up a row_lower / row_upper and
 *      col_lower / col_upper. For sake of ease, let's use RL / RH and CL / CH
 *      to indicate row_lower / row_upper and col_lower / col_upper.
 *  3). On the row dimension, we try RM(which means row_middle) = (RL + RH) / 2
 *      = 2; on the column dimension, CM(which means col_middle) =
 *      (CL + CH) / 2 = 2.
 *  4). RL-RM-RH and CL-CM-CH divide the entire matrix into four areas:
 *      | 01 - 02 - 03 - 04 - 05 |
 *      |            |           |
 *      | 06   07   08   09   10 |
 *      |            |           |
 *      | 11 - 12 - 13 - 14 - 15 |
 *      |            |           |
 *      | 16   17   18   19   20 |
 *      |            |           |
 *      | 21   22   23   24   25 |
 *      |            |           |
 *      | 26 - 27 - 28 - 29 - 30 |
 *      What we need to do is determine which area the target value falls into.
 *  5). The example above might be a little bit special because it has all
 *      unique numbers and the smallest number in one row is always bigger than
 *      the biggest number in the rows above.
 *  6). We need to look at another example:
 *      | 01, 03, 05, 07, 09 |
 *      | 02, 04, 06, 08, 10 |
 *      | 03, 05, 07, 09, 11 |
 *      | 04, 06, 08, 10, 12 |
 *  7). Looking at the two examples above, we know that it is guaranteed that:
 *      a[i][j] <= a[i][j+1] <= a[i+1][j+1];
 *      a[i][j] <= a[i+1][j] <= a[i+1][j+1];
 *      therefore:
 *      a[i][j] <= a[i+1][j+1];
 *      However, the relationship between a[i][j+1] and a[i+1][j] is not
 *      guaranteed. The relationship could be =, > or <.
 *  8). Because the relationship between a[i+1][j] and a[i][j+1] is not
 *      guaranteed, it's hard to determine if the given value is contained
 *      in a particular quadrant.
 *  9). We can only use the fact that a[i][j] is always the smallest and
 *      a[i+1][j+1] is the biggest in the quadrant to determine if the given
 *      value is *NOT* in the quadrant.
 *  10). Once we eliminate a quadrant, we don't need to search in it. We just
 *      need to continue the same search in the remaining quadrants recursively.
 */


import java.util.*;


public class Solution09 {

    private static class Coordinate {
        public int row;
        public int col;

        public Coordinate() {
            row = -1;
            col = -1;
        }

        public Coordinate(int r, int c) {
            row = r;
            col = c;
        }

        boolean equals(Coordinate rhs) {
            return (row == rhs.row && col == rhs.col);
        }

        boolean invalid() {
            return (row == -1 && col == -1);
        }
    };

    /**
     *  @brief: Search in the given sorted matrix to find the given value.
     *  @param: [in] m: The sorted matrix. Each row and each column are sorted
     *      in the ascending order.
     *  @param: [in] M, N: The number of rows and columns of the matrix m.
     *  @param: [in] tl, br: The top-left and bottom-right Coordinates of the matrix.
     *  @param: [in] x: The value to be search for.
     *  @return: Coordinate: The coordinate of the given value in the matrix. If
     *      the value does not exist in the matrix, then (-1, -1) is returned.
     */
    private static Coordinate search_sorted_matrix(int[][] m, final int M, final int N, Coordinate tl, Coordinate br, int x) {
        // Validate the parameters.

        if (tl.row >= M || br.row >= M || tl.col >= N || br.col >= N) {
            // The coordinates must be within the matrix boundaries.
            return new Coordinate();
        }

        if (tl.row > br.row || tl.col > br.col) {
            // The top-left coordiate must not be to the bottom-right of the
            // bottom-right coordiate.
            return new Coordinate();
        }

        // Handle the edge case.
        if (tl.equals(br)) {
            // If the matrix becomes an element, we just compare it with x.
            if (m[tl.row][tl.col] == x) {
                return tl;
            } else {
                return new Coordinate();
            }
        }

        // If m is still a matrix, we then try to narrow the area that x might be in.
        int mid_row = (tl.row + br.row) / 2;
        int mid_col = (tl.col + br.col) / 2;

        // We then determine if x might be in each quadrant. If it might be, we
        // search in that quadrant.
        if (m[tl.row][tl.col] <= x && x <= m[mid_row][mid_col]) {
            Coordinate r = search_sorted_matrix(m, M, N, new Coordinate(tl.row, tl.col), new Coordinate(mid_row, mid_col), x);
            if (!r.invalid()) {
                // The returned coordiate is not (-1, -1) so we found it!
                // Just return, no need to continue any search.
                return r;
            }
        }

        if (m[tl.row][mid_col+1] <= x && x <= m[mid_row][br.col]) {
            Coordinate r = search_sorted_matrix(m, M, N, new Coordinate(tl.row, mid_col+1), new Coordinate(mid_row, br.col), x);
            if (!r.invalid()) {
                // The returned coordiate is not (-1, -1) so we found it!
                // Just return, no need to continue any search.
                return r;
            }
        }

        if (m[mid_row+1][tl.col] <= x && x <= m[br.row][mid_col]) {
            Coordinate r = search_sorted_matrix(m, M, N, new Coordinate(mid_row+1, tl.col), new Coordinate(br.row, mid_col), x);
            if (!r.invalid()) {
                // The returned coordiate is not (-1, -1) so we found it!
                // Just return, no need to continue any search.
                return r;
            }
        }

        if (m[mid_row+1][mid_col+1] <= x && x <= m[br.row][br.col]) {
            Coordinate r = search_sorted_matrix(m, M, N, new Coordinate(mid_row+1, mid_col+1), new Coordinate(br.row, br.col), x);
            if (!r.invalid()) {
                // The returned coordiate is not (-1, -1) so we found it!
                // Just return, no need to continue any search.
                return r;
            }
        }

        // If we've still not found it, then we cannot find it.
        return new Coordinate();
    }

    public static void main(String []args) {
        boolean pass = true;

        {
            final int M = 4;
            final int N = 4;
            int [][] m = {
                { 15, 20, 40, 85 },
                { 20, 35, 80, 95 },
                { 30, 55, 95, 105 },
                { 40, 80, 100, 120 },
            };

            pass = pass
                && (search_sorted_matrix(m, M, N, new Coordinate(0, 0), new Coordinate(M-1, N-1), 15).equals(new Coordinate(0, 0)))
                && (search_sorted_matrix(m, M, N, new Coordinate(0, 0), new Coordinate(M-1, N-1), 85).equals(new Coordinate(0, 3)))
                && (search_sorted_matrix(m, M, N, new Coordinate(0, 0), new Coordinate(M-1, N-1), 30).equals(new Coordinate(2, 0)))
                && (search_sorted_matrix(m, M, N, new Coordinate(0, 0), new Coordinate(M-1, N-1), 120).equals(new Coordinate(3, 3)))
                && (search_sorted_matrix(m, M, N, new Coordinate(0, 0), new Coordinate(M-1, N-1), 55).equals(new Coordinate(2, 1)))
                ;

            // The search of duplicate element may be tricky.
            Coordinate r = search_sorted_matrix(m, M, N, new Coordinate(0, 0), new Coordinate(M-1, N-1), 40);
            pass = pass && (r.equals(new Coordinate(3, 0)) || r.equals(new Coordinate(0, 2)));
        }

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
