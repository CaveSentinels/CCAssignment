/**
 *  @assumptions:
 *  1). Given the two nodes, A and B, in the directed graph, my algorithm will
 *      return if there exists a route from A to B. The question can be
 *      interpreted to ask the the existance of a route from either A to B or
 *      B to A, but this can be easily resolved by applying my algorithm twice.
 */


import java.util.*;


public class Solution01 {

    private static class GNode {
        public int index;
        public int data;   // The data associated with this node.
        public boolean visited;
        public Vector<Integer> adj_list;

        public GNode(int i) {
            index = i;
            data = 0;
            visited = false;
            adj_list = new Vector<>();
        }

        public GNode(int i, int d, boolean v) {
            index = i;
            data = d;
            visited = v;
            adj_list = new Vector<>();
        }
    };

    private static class Graph {

        private Vector<GNode> _adj_lists = new Vector<>();

        public Graph(int[][] adj_matrix) {
            final int N = adj_matrix.length;

            for (int i = 0; i < N; ++i) {
                GNode gnode = new GNode(i);

                for (int j = 0; j < N; ++j) {
                    if (adj_matrix[i][j] == 1) {
                        // Node i connects to node j.
                        gnode.adj_list.add(j);
                    }
                }

                _adj_lists.add(gnode);
            }
        }

        /**
         *  @brief: Given the indexes of two nodes, find if there exists one route
         *      from node#1 to node#2.
         *  @param: [in] node1, node2: The two nodes.
         *  @return: boolean
         *      - true: if there exists a route from node1 to node2.
         *      - false: if there doesn't exist a route from node1 to node2.
         */
        public boolean route_exists(final int node1, final int node2) {
            // Validate the arguments.
            if (node1 < 0 || node1 >= _adj_lists.size() ||
                node2 < 0 || node2 >= _adj_lists.size()) {
                // Given nodes are invalid. Just return false.
                return false;
            }

            // Handle special cases.
            if (node1 == node2) {
                // If the given nodes are the same one, return true because they
                // of course have a route to connect them.
                return true;
            }

            // Now the two nodes are different and we will apply BFS to search for
            // a possible route.

            Vector<Integer> nodes_to_search = new Vector<>();
            nodes_to_search.add(node1);   // We start from node1.

            boolean route_found = false;
            while (!nodes_to_search.isEmpty()) {
                // Get the node to examine next.
                int node = nodes_to_search.get(0);
                nodes_to_search.remove(0);

                if (node2 == node) {
                    route_found = true;
                    break;
                }

                // Set the node as visited.
                _adj_lists.get(node).visited = true;

                // Put all the adjacent nodes of the current node to the end
                // of nodes_to_search so we can examine them later.
                Vector<Integer> adj_list = _adj_lists.get(node).adj_list;
                for (int i = 0; i < adj_list.size(); ++i) {
                    int next_node = adj_list.get(i);
                    if (!_adj_lists.get(next_node).visited) {
                        // Only put the nodes that haven't been visisted to the
                        // nodes_to_search list.
                        nodes_to_search.add(next_node);
                    }
                }
            }

            return route_found;
        }

    };

    public static void main(String []args) {
        boolean pass = true;

        {
            final int N = 4;
            final int T = 1;
            final int F = 0;
            int [][] adj_matrix = {
                // 0, 1, 2, 3
                 { F, T, F, F },
                 { F, F, T, T },
                 { F, F, F, T },
                 { F, F, F, F },
            };
            Graph g = new Graph(adj_matrix);
            pass = pass && g.route_exists(0, 0)
                && g.route_exists(0, 1)
                && g.route_exists(0, 3)
                && !g.route_exists(3, 0)
                && !g.route_exists(3, 1)
                ;
        }

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
