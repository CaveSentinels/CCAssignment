public class Solution02 {

    private static class BTNode {
        public int value;  // data
        public BTNode left;  // left child
        public BTNode right; // right child

        public BTNode(int v) {
            value = v;
            left = null;
            right = null;
        }

        public BTNode(int v, BTNode l, BTNode r) {
            value = v;
            left = l;
            right = r;
        }
    }

    private static void bt_print_in(BTNode root) {
        if (root != null) {
            bt_print_in(root.left);
            StringBuilder sb = new StringBuilder();
            sb.append(root.value).append(" [").append(root.left != null ? root.left.value : -1)
                .append(", ").append(root.right != null ? root.right.value : -1).append("]; ")
                ;
            System.out.print(sb.toString());
            bt_print_in(root.right);
        }
    }

    /**
     *  @brief: Create the binary-search tree of the minimal height.
     *  @param: [in] arr: The sorted array with unique elements.
     *  @param: [in] si: The starting index of the array.
     *  @param: [in] ei: The ending index of the array.
     *  @return: BTNode
     *      The root node of the binary-search tree.
     */
    private static BTNode create_min_height_bst(final int [] arr, final int si, final int ei) {
        // The completion condition.
        if (si > ei) {
            return null;
        }

        // Find and create the root node of the current array.
        int mi = (ei + 1 + si) / 2;
        BTNode root = new BTNode(arr[mi]);

        // Create its subtrees.
        BTNode left = create_min_height_bst(arr, si, mi-1);
        BTNode right = create_min_height_bst(arr, mi+1, ei);

        // Connect them.
        root.left = left;
        root.right = right;

        return root;
    }

    public static void main(String []args) {
        boolean pass = true;

        {
            final int N = 0;
            int [] arr = { };
            BTNode bst = create_min_height_bst(arr, 0, N-1);
            System.out.println("===== Tree =====");
            bt_print_in(bst); System.out.println("");
            System.out.println("================");
        }

        {
            final int N = 5;
            int [] arr = { 1, 2, 3, 4, 5 };
            BTNode bst = create_min_height_bst(arr, 0, N-1);
            System.out.println("===== Tree =====");
            bt_print_in(bst); System.out.println("");
            System.out.println("================");
        }

        {
            final int N = 6;
            int [] arr = { 1, 2, 3, 4, 5, 6 };
            BTNode bst = create_min_height_bst(arr, 0, N-1);
            System.out.println("===== Tree =====");
            bt_print_in(bst); System.out.println("");
            System.out.println("================");
        }

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
