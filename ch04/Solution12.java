import java.util.*;


public class Solution12 {

    private static class TreeNode {
        public int value;  // data
        public TreeNode left;  // left child
        public TreeNode right; // right child

        public TreeNode(int v) {
            value = v;
            left = null;
            right = null;
        }

        public TreeNode(int v, TreeNode l, TreeNode r) {
            value = v;
            left = l;
            right = r;
        }
    };


    private static TreeNode bt_create(int [] nodes, int count, int start, int sep) {
        // Handle the special cases.
        if ((0 == count) || (start >= count)) {
            return null;
        }

        // If the current node indicates an empty tree, then just return null.
        if (nodes[start] == sep) {
            return null;
        }

        // Otherwise, there is at least one node in nodes.

        // Create the root node.
        TreeNode root = new TreeNode(nodes[start]);
        // Create the left subtree, if existing.
        TreeNode left_subtree = bt_create(nodes, count, 2 * start + 1, sep);
        // Create the right subtree, if existing.
        TreeNode right_subtree = bt_create(nodes, count, 2 * start + 2, sep);

        // Link the nodes.
        root.left = left_subtree;
        root.right = right_subtree;

        return root;
    }

    private static int M(final TreeNode N, final int V) {
        if (null == N) {
            // If the node is a null, sure the result is 0.
            return 0;
        }

        return (N.value == V ? 1 : 0);
    }


    private static int G(final TreeNode R, int V) {
        if (null == R) {
            return 0;
        }

        return M(R, V) + G(R.left, V - R.value) + G(R.right, V - R.value);
    }


    private static int F(TreeNode R, int V) {
        if (null == R) {
            return 0;
        }

        return G(R, V) + F(R.left, V) + F(R.right, V);
    }


    private static int paths_with_sum(TreeNode root, int value) {
        return F(root, value);
    }

    public static void main(String []args) {
        boolean pass = true;

        {
            final int N = 0;
            int [] nodes = {};
            TreeNode root = bt_create(nodes, N, 0, -1);
            pass = pass && (paths_with_sum(root, 0/*target value*/) == 0);
        }

        {
            final int N = 1;
            int [] nodes = { 1 };
            TreeNode root = bt_create(nodes, N, 0, -1);
            pass = pass && (paths_with_sum(root, 0/*target value*/) == 0);
            pass = pass && (paths_with_sum(root, 1/*target value*/) == 1);
        }

        {
            final int N = 3;
            int [] nodes = { 0, 0, 0 };
            TreeNode root = bt_create(nodes, N, 0, -1);
            pass = pass && (paths_with_sum(root, 0/*target value*/) == 5);
        }

        {
            final int N = 3;
            int [] nodes = { 0, 1, -1 };
            TreeNode root = bt_create(nodes, N, 0, -99);
            pass = pass && (paths_with_sum(root, 0/*target value*/) == 1);
        }

        {
            final int N = 5;
            int [] nodes = { 0, 1, -1, -99, -1 };
            TreeNode root = bt_create(nodes, N, 0, -99);
            pass = pass && (paths_with_sum(root, 0/*target value*/) == 3);
        }

        {
            final int N = 7;
            int [] nodes = { 0, 1, -1, -99, -1, -99, 0 };
            TreeNode root = bt_create(nodes, N, 0, -99);
            pass = pass && (paths_with_sum(root, 0/*target value*/) == 4);
        }

        {
            final int N = 7;
            int [] nodes = { 0, 1, -1, -1, -1, 1, 0 };
            TreeNode root = bt_create(nodes, N, 0, -99);
            pass = pass && (paths_with_sum(root, 0/*target value*/) == 8);
        }

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
