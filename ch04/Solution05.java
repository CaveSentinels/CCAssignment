import java.util.*;


public class Solution05 {

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

    private static boolean bst_validate(TreeNode root) {
        // Initially, the feasible range for root node is [min, max].
        return bst_validate(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private static boolean bst_validate(TreeNode node, int min, int max) {
        // Handle special cases.
        if (node == null) {
            // If the tree is empty, it is a valid BST.
            return true;
        }

        if (node.value <= min || node.value > max) {
            // If the node value is out of range, the tree can't be a valid BST.
            return false;
        }

        // When we move to the left child, the upper limit is changed;
        // when we move to the right child, the lower limit is changed.
        if (!bst_validate(node.left, min, node.value) ||
            !bst_validate(node.right, node.value, max)) {
            return false;
        }

        return true;
    }

    public static void main(String []args) {
        boolean pass = true;

        {
            final int N = 0;
            int [] nodes = { };
            TreeNode root = bt_create(nodes, N, 0, -1);
            pass = pass && bst_validate(root);
        }

        {
            final int N = 1;
            int [] nodes = { 1 };
            TreeNode root = bt_create(nodes, N, 0, -1);
            pass = pass && bst_validate(root);
        }

        {
            final int N = 2;
            int [] nodes = { 1, -1, 2 };
            TreeNode root = bt_create(nodes, N, 0, -1);
            pass = pass && bst_validate(root);
        }

        {
            final int N = 2;
            int [] nodes = { 1, 2, -1 };
            TreeNode root = bt_create(nodes, N, 0, -1);
            pass = pass && !bst_validate(root);
        }

        {
            final int N = 7;
            int [] nodes = { 3, 1, 5, -1, 2, 4, 6 };
            TreeNode root = bt_create(nodes, N, 0, -1);
            pass = pass && bst_validate(root);
        }

        {
            final int N = 7;
            int [] nodes = { 3, 1, 6, -1, 2, 4, 5 };
            TreeNode root = bt_create(nodes, N, 0, -1);
            pass = pass && !bst_validate(root);
        }

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
