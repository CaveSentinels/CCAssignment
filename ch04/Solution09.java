import java.util.*;


public class Solution09 {

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

    // find_all_sequences() will return all the possible sequences of the given
    // binary search tree root.
    private static ArrayList<LinkedList<Integer>> find_all_sequences(TreeNode root) {
        ArrayList<LinkedList<Integer>> seqs = new ArrayList<>();

        if (root == null) {
            // If the tree is empty, then there is no sequence for the tree.
            seqs.add(new LinkedList<Integer>());
            return seqs;
        }

        // Otherwise, put the root node to the prefix, because the root node
        // must be the first element in the sequence.
        LinkedList<Integer> prefix = new LinkedList<>();
        prefix.add(root.value);

        // Find the sequences of the left and right subtrees.
        ArrayList<LinkedList<Integer>> left_seqs = find_all_sequences(root.left);
        ArrayList<LinkedList<Integer>> right_seqs = find_all_sequences(root.right);

        // Now, merge the two sequences by weaving. Because left_seqs and right_seqs
        // have ALL the possible sequences from the left and right subtrees,
        // we need to consider each pair. That is, we need to weave every
        // combination by a sequence from the left subtree and a sequence from
        // the right subtree.
        for (int i = 0; i < left_seqs.size(); ++i) {
            LinkedList<Integer> left = left_seqs.get(i);
            for (int j = 0; j < right_seqs.size(); ++j) {
                LinkedList<Integer> right = right_seqs.get(j);
                ArrayList<LinkedList<Integer>> weaved = new ArrayList<>();
                weave_lists(left, right, weaved, prefix);
                seqs.addAll(weaved);
            }
        }

        return seqs;
    }

    // Weave the lists in all possible ways.
    private static void weave_lists(LinkedList<Integer> first,
                                    LinkedList<Integer> second,
                                    ArrayList<LinkedList<Integer>> results,
                                    LinkedList<Integer> prefix) {
        /* One list is empty. Add remainder to [a cloned] prefix and store result. */
        if (first.size() == 0 || second.size() == 0) {
            LinkedList<Integer> result = (LinkedList<Integer>) prefix.clone();
            result.addAll(first);
            result.addAll(second);
            results.add(result);
            return;
        }

        // Weave: Let the "first" sequence go first.
        int headFirst = first.removeFirst();
        prefix.addLast(headFirst);
        weave_lists(first, second, results, prefix);
        prefix.removeLast();
        first.addFirst(headFirst);

        // Weave: Let the "second" sequence go first.
        int headSecond = second.removeFirst();
        prefix.addLast(headSecond);
        weave_lists(first, second, results, prefix);
        prefix.removeLast();
        second.addFirst(headSecond);
    }

    public static void main(String []args) {
        boolean pass = true;

        {
            final int N = 3;
            int [] nodes = { 2, 1, 3 };
            TreeNode root = bt_create(nodes, N, 0, -1);
            ArrayList<LinkedList<Integer>> seqs = find_all_sequences(root);
            for (int i = 0; i < seqs.size(); ++i) {
                LinkedList<Integer> lst = seqs.get(i);
                ListIterator<Integer> listIterator = lst.listIterator();
                while (listIterator.hasNext()) {
    	            System.out.print(listIterator.next() + ", ");
    	        }
                System.out.println("");
            }
        }

        System.out.println("==========");

        {
            final int N = 7;
            int [] nodes = { 2, 1, 3, -1, -1, -1, 4 };
            TreeNode root = bt_create(nodes, N, 0, -1);
            ArrayList<LinkedList<Integer>> seqs = find_all_sequences(root);
            for (int i = 0; i < seqs.size(); ++i) {
                LinkedList<Integer> lst = seqs.get(i);
                ListIterator<Integer> listIterator = lst.listIterator();
                while (listIterator.hasNext()) {
    	            System.out.print(listIterator.next() + ", ");
    	        }
                System.out.println("");
            }
        }

        System.out.println("==========");

        {
            final int N = 7;
            int [] nodes = { 5, 3, 7, 2, 4, 6, 8 };
            TreeNode root = bt_create(nodes, N, 0, -1);
            ArrayList<LinkedList<Integer>> seqs = find_all_sequences(root);
            for (int i = 0; i < seqs.size(); ++i) {
                LinkedList<Integer> lst = seqs.get(i);
                ListIterator<Integer> listIterator = lst.listIterator();
                while (listIterator.hasNext()) {
    	            System.out.print(listIterator.next() + ", ");
    	        }
                System.out.println("");
            }
        }

        System.out.println("==========");

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
