import java.util.*;


public class Solution03 {

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

    private static ArrayList< ArrayList< TreeNode > > list_of_depth(TreeNode root) {
        ArrayList< ArrayList< TreeNode > > lists = new ArrayList< ArrayList< TreeNode > >();

        // Handle the special cases.
        if (root == null) {
            return lists;
        }

        // Traverse the tree level by level and create a list after each level.
        ArrayList< TreeNode > curr_level = new ArrayList< TreeNode >();
        curr_level.add(root);

        while (!curr_level.isEmpty()) {
            // First, push the current level into the lists.
            lists.add(curr_level);

            // Create a list for the next level.
            ArrayList< TreeNode > next_level = new ArrayList< TreeNode >();

            for (int i = 0; i < curr_level.size(); ++i) {
                TreeNode curr_node = curr_level.get(i);

                // If this node has child nodes, put them to the next level.
                if (curr_node.left != null) {
                    next_level.add(curr_node.left);
                }
                if (curr_node.right != null) {
                    next_level.add(curr_node.right);
                }
            }

            // Swap the current level and the next level to make the next level
            // the current.
            curr_level = next_level;
        }

        return lists;
    }

    public static void main(String []args) {
        boolean pass = true;

        {
            final int N = 7;
            int [] nodes = { 1, 2, 3, 4, -1, -1, 7 };
            TreeNode root = bt_create(nodes, N, 0, -1);
            ArrayList< ArrayList< TreeNode > > lists = list_of_depth(root);
            for (int i = 0; i < lists.size(); ++i) {
                ArrayList< TreeNode > list = lists.get(i);
                for (int j = 0; j < list.size(); ++j) {
                    System.out.print(list.get(j).value);
                    System.out.print(' ');
                }
                System.out.println("");
            }
        }

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
