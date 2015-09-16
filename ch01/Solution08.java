/**
 *  @assumptions:
 *  1). ...
 */

public class Solution08 {

    private static void zero_matrix(int[][] matrix, final int M, final int N) {
        // Handle the special cases.
        if ((M == 0 && N == 0) || (M == 1 && N == 1)) {
            // Nothing to zero.
            // When M, N == 0, obviously there is nothing to process.
            // When M, N == 1 and the only element in matrix is 0, then we should
            // zero the matrix, but since the element itself is already 0, we
            // still have nothing to do.
            // When M, N == 1 and the only element in matrix is not 0, then we do
            // not need to zero any other elements.
            return;
        }

        // Create the helper vectors.
        int [] rows = new int[M];
        int [] cols = new int[N];

        // Iterate the matrix.
        for (int row = 0; row < M; ++row) {
            for (int col = 0; col < N; ++col) {
                if (matrix[row][col] == 0) {
                    rows[row] = 1;
                    cols[col] = 1;
                }
            }
        }

        // Zero the matrix rows.
        for (int row = 0; row < M; ++row) {
            if (rows[row] == 1) {
                for (int col = 0; col < N; ++col) {
                    matrix[row][col] = 0;
                }
            }
        }

        // Zero the matrix columns.
        for (int col = 0; col < N; ++col) {
            if (cols[col] == 1) {
                for (int row = 0; row < M; ++row) {
                    matrix[row][col] = 0;
                }
            }
        }
    }

    private static int[][] create_matrix(final int M, final int N) {
        int[][] matrix = new int[M][N];
        for (int row = 0; row < M; ++row) {
            for (int col = 0; col < N; ++col) {
                matrix[row][col] = (row + 1) * 10 + col;
            }
        }
        return matrix;
    }

    private static void print_matrix(int[][] matrix, final int M, final int N) {
        for (int i = 0; i < M; ++i) {
            System.out.print("| ");
            for (int j = 0; j < N; ++j) {
                System.out.print(matrix[i][j]);
                System.out.print(' ');
            }
            System.out.println('|');
        }
    }

    public static void main(String []args) {
        boolean pass = true;

        {
            final int M = 4;
            final int N = 4;
            int [][] matrix = create_matrix(M, N);
            zero_matrix(matrix, M, N);
            print_matrix(matrix, M, N);
        }

        {
            final int M = 4;
            final int N = 4;
            int [][] matrix = create_matrix(M, N);
            matrix[0][0] = 0;
            matrix[1][3] = 0;
            zero_matrix(matrix, M, N);
            print_matrix(matrix, M, N);
        }

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
