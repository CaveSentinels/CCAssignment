import java.util.*;


public class Solution08 {

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


    private static boolean in_tree(TreeNode root, TreeNode p) {
        if (root == null) {
            // If root is null, p has no way to be in it.
            return false;
        }

        if (root == p) {
            return true;
        }

        return in_tree(root.left, p) || in_tree(root.right, p);
    }


    // The parameter dummy is just used to distinguish this method and the one
    // below.
    private static TreeNode find_common_ancestor(TreeNode root, TreeNode p, TreeNode q, int dummy) {
        // Handle special cases.
        if (root == null) {
            // If the tree is empty, then the common ancestor is null.
            return null;
        } else if (root == p) {
            // If the root is p, then the common ancestor is p.
            return p;
        } else if (root == q) {
            // If the root is q, then the common ancestor is q.
            return q;
        }

        // Check which side do p and q fall into.
        boolean p_on_left = in_tree(root.left, p);
        boolean q_on_left = in_tree(root.left, q);

        if (p_on_left != q_on_left) {
            // p and q are on different sides.
            return root;
        } else {
            // p and q are on the same side.
            TreeNode side = (p_on_left ? root.left : root.right);
            return find_common_ancestor(side, p, q, dummy);
        }
    }


    private static TreeNode find_common_ancestor(TreeNode root, TreeNode p, TreeNode q) {
        // Handle special case: if either p or q is not in the tree,
        // just return null.
        if (!in_tree(root, p) || !in_tree(root, q)) {
            return null;
        }

        return find_common_ancestor(root, p, q, 0/*any value will work*/);
    }


    public static void main(String []args) {
        boolean pass = true;

        {
            final int N = 7;
            int [] nodes = { 1, 2, 3, 4, 5, 6, 7 };
            TreeNode root = bt_create(nodes, N, 0, -1);
            TreeNode n1 = root;
            TreeNode n2 = root.left;
            TreeNode n4 = root.left.left;
            TreeNode n5 = root.left.right;
            TreeNode n7 = root.right.right;
            pass = pass && (find_common_ancestor(root, n1, n4) == n1)
                && (find_common_ancestor(root, n5, n4) == n2)
                && (find_common_ancestor(root, n5, n7) == n1)
                ;
        }

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
