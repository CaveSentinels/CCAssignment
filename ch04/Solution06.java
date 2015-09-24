import java.util.*;


public class Solution06 {

    private static class TreeNode {
        public int value;  // data
        public TreeNode left;  // left child
        public TreeNode right; // right child
        public TreeNode parent; // parent

        public TreeNode(int v) {
            value = v;
            left = null;
            right = null;
            parent = null;
        }

        public TreeNode(int v, TreeNode l, TreeNode r, TreeNode p) {
            value = v;
            left = l;
            right = r;
            parent = p;
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
        if (left_subtree != null) {
            left_subtree.parent = root;
        }
        if (right_subtree != null) {
            right_subtree.parent = root;
        }

        return root;
    }

    private static TreeNode bst_find_minimum(TreeNode root) {
        if (root == null) {
            return null;
        }

        TreeNode curr = root;
        while (curr.left != null) {
            curr = curr.left;
        }

        return curr;
    }


    private static TreeNode bst_find_successor(TreeNode node) {
        TreeNode curr = node;

        if (curr.right != null) {
            return bst_find_minimum(curr.right);
        }

        TreeNode parent = curr.parent;
        while (parent != null && parent.right == curr) {
            curr = parent;
            parent = parent.parent;
        }

        return parent;
    }

    public static void main(String []args) {
        boolean pass = true;

        {
            final int N = 15;
            int [] nodes = { 15, 6, 18, 3, 7, 17, 20, 2, 4, -1, 13, -1, -1, -1, -1 };
            TreeNode root = bt_create(nodes, N, 0, -1);
            TreeNode node15 = root;
            pass = pass && (bst_find_successor(node15).value == 17);
            TreeNode node13 = root.left.right.right;
            pass = pass && (bst_find_successor(node13).value == 15);
            TreeNode node20 = root.right.right;
            pass = pass && (bst_find_successor(node20) == null);
        }

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
