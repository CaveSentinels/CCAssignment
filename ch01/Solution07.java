/**
 *  @assumptions:
 *  1). Matrix rotation is a clock-wise rotation, that is, a matrix of:
 *          | 11, 12, 13 |
 *          | 21, 22, 23 |
 *          | 31, 32, 33 |
 *  will be rotated to a matrix of
 *          | 31, 21, 11 |
 *          | 32, 22, 12 |
 *          | 33, 23, 13 |
 */

public class Solution07 {

    /**
     *  @brief: Rotate the matrix by 90 degress.
     *  @param: [in/out] matrix: The matrix to be rotated and the result of the
     *      rotation.
     *  @param: [in] N: The size of the matrix.
     *  @return: N/A
     *  @complexity:
     *          Best    |    Average    |    Worst
     *       O[2*N^2]       O[2*N^2]        O[2*N^2]
     */
    private static void rotate_matrix_90(int[][] matrix, final int N) {
        // Handle the special cases.
        if (N == 0 || N == 1) {
            // No rotation is needed.
            return;
        }

        // Now do the actual rotation. A clock-wise 90-degree rotation can be
        // done in two steps:

        // 1). Flip the matrix vertically. For example, the matrix  of:
        //          | 11, 12, 13 |
        //          | 21, 22, 23 |
        //          | 31, 32, 33 |
        //      will be rotated to a matrix of
        //          | 31, 32, 33 |
        //          | 21, 22, 23 |
        //          | 11, 12, 13 |
        for (int row = 0; row < N / 2; ++row) {
            for (int col = 0; col < N; ++col) {
                int tmp = matrix[row][col];
                matrix[row][col] = matrix[N-1-row][col];
                matrix[N-1-row][col] = tmp;
            }
        }

        // 2). Flip the matrix against the diagonal. For example, the matrix of:
        //          | 31, 32, 33 |
        //          | 21, 22, 23 |
        //          | 11, 12, 13 |
        //      will be rotated to a matrix of
        //          | 31, 21, 11 |
        //          | 32, 22, 12 |
        //          | 33, 23, 13 |
        for (int row = 0; row < N; ++row) {
            for (int col = 0; col < row; ++col) {
                int tmp = matrix[row][col];
                matrix[row][col] = matrix[col][row];
                matrix[col][row] = tmp;
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
            final int N = 0;
            int[][] matrix = create_matrix(N, N);
            rotate_matrix_90(matrix, N);
            print_matrix(matrix, N, N);
        }

        {
            final int N = 1;
            int[][] matrix = create_matrix(N, N);
            rotate_matrix_90(matrix, N);
            print_matrix(matrix, N, N);
        }

        {
            final int N = 2;
            int[][] matrix = create_matrix(N, N);
            rotate_matrix_90(matrix, N);
            print_matrix(matrix, N, N);
        }

        {
            final int N = 4;
            int[][] matrix = create_matrix(N, N);
            rotate_matrix_90(matrix, N);
            print_matrix(matrix, N, N);
        }

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
