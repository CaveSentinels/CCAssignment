/**
 *  @thought_process:
 *
 *  1). For problems that involve trees, we can try and see if recursion can
 *      be applied to tackle the problem because trees are by nature a recursive
 *      structure, recursion might be the most possible way to solve it.
 *  2). To solve a problem with recursion, we must somehow re-express the
 *      original problem in a recursive form.
 *  3). A good start might be just define a function that represents the
 *      original problem itself, without any changes. This is where we come
 *      to the function F:
 *
 *          Define
 *                          F: (T, V) => C
 *          as the function that counts how many paths are there in the tree T
 *          that sum up to the given value V.
 *          The "path" may not have to start or end at the root or a leaf.
 *          The number of such paths is recorded by C.
 *
 *  4). This problem is an "open-ended" problem: There is no tight bound to
 *      the ends of a path. It could start with the root, but could not; it
 *      could end at a leaf, but could not.
 *  5). A good trick to attack such "open-ended" problem is to bound one end,
 *      usually the starting side, because once we have something "bounded",
 *      we immeidately have something "determined" and easier to be expressed
 *      in a determined form.
 *  6). Therefore, we define the function G:
 *
 *          Define
 *                          G: (T, V) => C
 *          as the function that counts how many paths are there in the Tree T
 *          that sum up to the given value V.
 *          The "path" *always* starts from the root of T, but may or may not
 *          end at a leaf.
 *
 *  7). G can help us redefine, or, "break", the original function F into
 *      smaller parts:
 *
 *              F(T, V) = G(T, V) + F(T.left, V) + F(T.right, V);
 *
 *      G(T, V) covers all the possible paths that start with the root, and
 *      F(T.left, V) and F(T.right, V) covers all the possible paths that
 *      start in the middle but still sum to V.
 *  8). For G, we need to break it into two parts: the root node and the
 *      children. Because the root node itself is also a path that starts
 *      with the root, if its value is V, we need to count it as one path.
 *      Then, we need to search in the subtrees to see how many paths are
 *      there that sum up to (V-R.v), because these paths together with the
 *      root node will form the wanted paths.
 *  9). If we define M:
 *
 *      Define
 *                          M: (N, V) => 0 if N.r != V, or
 *                                       1 if N.r == V
 *      where N is a node.
 *      Then we can express G as:
 *
 *              G(T, V) = M(T, V) + G(T.left, V - T.v) + G(T.right, V - T.v)
 *  10). Once we have the recursive expression of the problem, we need to work
 *      on the edge conditions:
 *          F(NULL, V) => 0: Given an empty tree, there is no path that sums
 *                              up to the value V.
 *          G(NULL, V) => 0: Same reason above.
 *          M(NULL, V) => 0: Same reason above.
 *          F(leaf, V) = G(leaf, V) = M(leaf, V).
 *  11). We are done.
 *  12). This is NOT the most optimal algorithm but I really want to use this
 *      solution to record how to think about such recursive problems.
 */


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
