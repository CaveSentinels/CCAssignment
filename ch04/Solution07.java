import java.util.*;


public class Solution07 {

    private static class Dependency {
        public int proj;
        public int dependsOn;

        public Dependency(int p, int d) {
            proj = p;
            dependsOn = d;
        }
    }

    private static class GNode {
        public int project;
        public Vector<Integer> dependsOn = new Vector<>();
    };

    private static Vector<Integer> find_build_order(Vector<Integer> proj_vect,  Vector<Dependency> dependencies) {

        Vector<Integer> order = new Vector<>();

        // Build the graph.
        Vector<GNode> projects = new Vector<>();
        for (int i = 0; i < proj_vect.size(); ++i) {
            GNode gn = new GNode();
            gn.project = proj_vect.get(i);
            projects.add(gn);
        }

        for (int i = 0; i < dependencies.size(); ++i) {
            Dependency dep = dependencies.get(i);
            for (int j = 0; j < projects.size(); ++j) {
                GNode gn = projects.get(j);
                if (gn.project == dep.proj) {
                    gn.dependsOn.add(dep.dependsOn);
                    break;
                }
            }
        }
        // Graph building completed.

        // Find a possible build order.
        while (!projects.isEmpty()) {
            boolean found = false;
            int prj = 0;
            int i = 0;
            while (i < projects.size()) {
                GNode gn = projects.get(i);
                if (gn.dependsOn.isEmpty()) {
                    // OK, we've found a project that doesn't depend on any other
                    // projects now.
                    prj = gn.project;
                    found = true;
                    break;
                } else {
                    ++i;
                }
            }

            // If no project is found, that means there exists a circular
            // dependency which is an error case.
            if (!found) {
                // Return an empty vector to indicate an error.
                order.clear();
                return order;
            }

            // Now we can find a project to work on. Put it to the end of the
            // build order list.
            order.add(prj);

            // Remove this project from the project list.
            projects.remove(i);

            // Remove this project from all the other projects that depend on it.
            for (int j = 0; j < projects.size(); ++j) {
                GNode gn = projects.get(j);
                for (int k = 0; k < gn.dependsOn.size(); ++k) {
                    int dep = gn.dependsOn.get(k);
                    if (dep == prj) {
                        gn.dependsOn.remove(k);
                        break;
                    }
                }
            }
        }

        return order;
    }

    private static void print_graph(Vector<GNode> projects) {
        for (int i = 0; i < projects.size(); ++i) {
            GNode gn = projects.get(i);
            System.out.print(gn.project + ": ");
            for (int j = 0; j < gn.dependsOn.size(); ++j) {
                System.out.print(gn.dependsOn.get(j) + " -> ");
            }
            System.out.println("(END)");
        }
    }

    private static void print_build_order(Vector<Integer> build_order) {
        for (int i = 0; i < build_order.size(); ++i) {
            System.out.print(build_order.get(i) + " ");
        }
        System.out.println("");
    }

    public static void main(String []args) {
        boolean pass = true;

        {
            Vector<Integer> projs = new Vector<>();
            for (int i = 0; i < 3; ++i) {
                projs.add(i);
            }

            Vector<Dependency> dependencies = new Vector<>();
            dependencies.add(new Dependency(0, 1));
            dependencies.add(new Dependency(1, 2));

            Vector<Integer> build_order = find_build_order(projs, dependencies);
            print_build_order(build_order);
        }

        {
            Vector<Integer> projs = new Vector<>();
            for (int i = 0; i < 3; ++i) {
                projs.add(i);
            }

            Vector<Dependency> dependencies = new Vector<>();
            dependencies.add(new Dependency(0, 1));
            dependencies.add(new Dependency(1, 0));

            Vector<Integer> build_order = find_build_order(projs, dependencies);
            print_build_order(build_order); // Circular dependencies. Should print nothing.
        }

        {
            Vector<Integer> projs = new Vector<>();
            for (int i = 0; i < 6; ++i) {
                projs.add(i+1);
            }

            Vector<Dependency> dependencies = new Vector<>();
            dependencies.add(new Dependency(4, 1));
            dependencies.add(new Dependency(2, 6));
            dependencies.add(new Dependency(4, 2));
            dependencies.add(new Dependency(1, 6));
            dependencies.add(new Dependency(3, 4));

            Vector<Integer> build_order = find_build_order(projs, dependencies);
            print_build_order(build_order);
        }

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
