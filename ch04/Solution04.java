/**
 *  @assumptions:
 *  1). ...
 */


import java.util.*;


public class Solution04 {

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

    private static class CheckResult {
        public int height; // height of the tree.
        public boolean balanced;  // whether the tree is balanced or not.

        public CheckResult() {
            height = 0;
            balanced = true;
        }

        public CheckResult(int h, boolean b) {
            height = h;
            balanced = b;
        }
    };

    private static CheckResult calc_height_and_check_balanced(TreeNode root) {
        // Handle special cases.
        if (null == root) {
            // If the tree is empty, its height is 0 and it is balanced.
            return new CheckResult();
        }

        // Calculate the heights of the subtrees and check if it is balanced or not.
        CheckResult left = calc_height_and_check_balanced(root.left);
        if (!left.balanced) {
            // If one of the subtree is not balanced, then the entire tree cannot
            // be balanced.
            // Note that in the unbalanced case, the height is not fully calculated
            // so only the "balanced" field is correct.
            return left;
        }

        CheckResult right = calc_height_and_check_balanced(root.right);
        if (!right.balanced) {
            // If one of the subtree is not balanced, then the entire tree cannot
            // be balanced.
            // Note that in the unbalanced case, the height is not fully calculated
            // so only the "balanced" field is correct.
            return right;
        }

        // If both subtrees are balanced, we calculate the height of this tree
        // for future check.
        int tree_height = 1 + Math.max(left.height, right.height);
        boolean balanced = (Math.abs(left.height - right.height) <= 1);

        return new CheckResult(tree_height, balanced);
    }

    /**
     *  @brief: Given a binary tree, check if the tree is balanced or not.
     *  @param: [in] root: The root node of the binary tree.
     *  @return: bool
     *      - true: The given tree is balanced.
     *      - false: The genven tree is not balanced which means there exists at
     *          least one node whose two subtrees' heights differ more than one.
     */
    private static boolean check_balanced(TreeNode root) {
        CheckResult tree = calc_height_and_check_balanced(root);
        return tree.balanced;
    }


    public static void main(String []args) {
        boolean pass = true;

        {
            final int N = 0;
            int [] nodes = { };
            TreeNode root = bt_create(nodes, N, 0, -1);
            pass = pass && check_balanced(root);
        }

        {
            final int N = 1;
            int [] nodes = { 1 };
            TreeNode root = bt_create(nodes, N, 0, -1);
            pass = pass && check_balanced(root);
        }

        {
            final int N = 3;
            int [] nodes = { 1, 2, -1 };
            TreeNode root = bt_create(nodes, N, 0, -1);
            pass = pass && check_balanced(root);
        }

        {
            final int N = 7;
            int [] nodes = { 1, 2, 3, 4, -1, -1, 7 };
            TreeNode root = bt_create(nodes, N, 0, -1);
            pass = pass && check_balanced(root);
        }

        {
            final int N = 7;
            int [] nodes = { 1, 2, -1, 3, -1, -1, -1 };
            TreeNode root = bt_create(nodes, N, 0, -1);
            pass = pass && !check_balanced(root);
        }

        {
            final int N = 15;
            int [] nodes = { 1, 2, 3, -1, -1, 4, 5, -1, -1, -1, -1, -1, -1, -1, 6 };
            TreeNode root = bt_create(nodes, N, 0, -1);
            pass = pass && !check_balanced(root);
        }

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
