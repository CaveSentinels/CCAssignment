import java.util.*;


public class Solution10 {

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

    private static boolean same_tree(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) {
            return true;
        } else if (t1 != null && t2 != null) {
            if (t1.value != t2.value) {
                return false;
            } else {
                return (same_tree(t1.left, t2.left) && same_tree(t1.right, t2.right));
            }
        } else {
            return false;
        }
    }


    private static boolean check_subtree(TreeNode t1, TreeNode t2) {
        if (t2 == null) {
            return true;
        }

        if (t1 == null) {
            return false;
        }

        if (t1.value == t2.value) {
            if (same_tree(t1, t2)) {
                return true;
            }
        }

        return (check_subtree(t1.left, t2) || check_subtree(t1.right, t2));
    }

    public static void main(String []args) {
        boolean pass = true;

        {
            // This is the first tree to compare against.
            final int N = 1;
            int [] nodes = { 1 };
            TreeNode root = bt_create(nodes, N, 0, -1);

            {
                // The second tree is empty.
                final int M = 0;
                int [] nodes2 = {};  // Empty tree
                TreeNode root2 = bt_create(nodes2, M, 0, -1);
                pass = pass && check_subtree(root, root2);
            }

            {
                // The second tree has only one node and the same value.
                final int M = 1;
                int [] nodes2 = { 1 };
                TreeNode root2 = bt_create(nodes2, M, 0, -1);
                pass = pass && check_subtree(root, root2);
            }

            {
                // The second tree has only one node but different value.
                final int M = 1;
                int [] nodes2 = { 2 };
                TreeNode root2 = bt_create(nodes2, M, 0, -1);
                pass = pass && !check_subtree(root, root2);
            }

            {
                // The second tree has only one node but different value.
                final int M = 3;
                int [] nodes2 = { 1, 2, -1 };
                TreeNode root2 = bt_create(nodes2, M, 0, -1);
                pass = pass && !check_subtree(root, root2);
            }
        }

        {
            // This is the first tree.
            final int N = 7;
            int [] nodes = { 1, 2, 3, 4, -1, -1, 7 };
            TreeNode root = bt_create(nodes, N, 0, -1);

            {
                // The second tree is empty.
                final int M = 0;
                int [] nodes2 = {};  // Empty tree
                TreeNode root2 = bt_create(nodes2, M, 0, -1);
                pass = pass && check_subtree(root, root2);
            }

            {
                // The second tree is equal to a leaf node of the first tree.
                final int M = 3;
                int [] nodes2 = { 7, -1, -1 };
                TreeNode root2 = bt_create(nodes2, M, 0, -1);
                pass = pass && check_subtree(root, root2);
            }

            {
                // The second tree is equal to a non-leaf node of the first tree.
                final int M = 3;
                int [] nodes2 = { 3, -1, -1 };
                TreeNode root2 = bt_create(nodes2, M, 0, -1);
                pass = pass && !check_subtree(root, root2);
            }

            {
                // The second tree is equal to a subtree of the first tree.
                final int M = 3;
                int [] nodes2 = { 3, -1, 7 };
                TreeNode root2 = bt_create(nodes2, M, 0, -1);
                pass = pass && check_subtree(root, root2);
            }
        }

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
