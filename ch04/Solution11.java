import java.util.*;


public class Solution11 {

    private static class TreeNode {
        public int value;  // data
        public TreeNode left;  // left child
        public TreeNode right; // right child
        public int size;

        public TreeNode(int v) {
            value = v;
            left = null;
            right = null;
            size = 1;
        }

        public TreeNode(int v, TreeNode l, TreeNode r) {
            value = v;
            left = l;
            right = r;
            size = 1;
        }
    };

    private static class Tree {
        private TreeNode root;

        public TreeNode getRandomNode() {
            if (root == null) {
                return root;
            }

            TreeNode curr = root;
            while (true) {
                int left_size = (curr.left == null ? 0 : curr.left.size);
                Random rnd = new Random();
                int index = rnd.nextInt(curr.size);
                if (index < left_size) {
                    curr = curr.left;
                } else if (index == left_size) {
                    break;
                } else {
                    curr = curr.right;
                }
            }

            return curr;
        }

        public void insert(int v) {
            if (root == null) {
                root = new TreeNode(v);
                return;
            }

            TreeNode curr = root;
            while (true) {
                ++curr.size;
                if (v <= curr.value) {
                    if (curr.left == null) {
                        curr.left = new TreeNode(v);
                        break;
                    } else {
                        curr = curr.left;
                    }
                } else {
                    if (curr.right == null) {
                        curr.right = new TreeNode(v);
                        break;
                    } else {
                        curr = curr.right;
                    }
                }
            }
        }

        private void print(TreeNode node) {
            if (node != null) {
                System.out.print("[v: " + node.value + ", s: " + node.size + "] ");
                print(node.left);
                print(node.right);
            }
        }

        public void print() {
            System.out.println("========== Tree ==========");
            print(root);
            System.out.println("");
            System.out.println("==========================");
        }
    }

    public static void main(String []args) {
        boolean pass = true;

        {
            Tree tree = new Tree();
            tree.insert(5);
            for (int i = 1; i < 5; ++i) {
                tree.insert(i);
                tree.insert(10 - i);
            }

            tree.print();

            HashMap<Integer, Integer> counter = new HashMap<Integer, Integer>();
            for (int i = 0; i < 100000; ++i) {
                TreeNode n = tree.getRandomNode();
                Integer v = counter.get(n.value);
                counter.put(n.value, (v == null ? 0 : v+1));
            }

            Iterator itor = counter.entrySet().iterator();
            while (itor.hasNext()) {
                Map.Entry pair = (Map.Entry)itor.next();
                System.out.println(pair.getKey() + " = " + pair.getValue());
            }
        }

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
