import java.util.*;


public class Solution05 {

    private static void sort_stack(Stack<Integer> s) {
        Stack<Integer> t = new Stack<>();  // Temporary stack.

        // In order to sort the stack s in descending order (top the smallest and
        // bottom the biggest), we should somehow sort the element in t in
        // ascending order (top the biggest and bottom the smallest). Finally,
        // we just pop and push all the elements in t back into s.

        // To make the code easier, we push the minimum int value into t so we do
        // not need to check if t is empty or not.
        t.push(Integer.MIN_VALUE);

        // Now assume that t is a sorted stack.

        // Examine each element in s and put it to the appropriate position in t.
        while (!s.empty()) {
            // Pop the top element in s.
            int top_s = s.peek();
            s.pop();

            // Peek at the top element of t.
            int top_t = t.peek();
            while (top_t > top_s) {
                // Put the larger elements back to s for temporary storage.
                s.push(top_t);
                t.pop();
                top_t = t.peek();
            }

            // Now all the remaining elements in t are <= top_s. We can put top_s
            // into t.
            t.push(top_s);
        }

        // Now all the elements in s are put and sorted in t. Just move all the
        // elememts back to s.
        while (!t.empty()) {
            s.push(t.peek());
            t.pop();
        }

        // Finally, we need to pop out the minimum int we put at the very beginning.
        // This element is now at the top of s.
        s.pop();

        // DONE!
    }

    public static void main(String []args) {
        boolean pass = true;

        {
            Stack<Integer> s = new Stack<>();
            for (int i = 0; i < 10; ++i) {
                s.push(i);
            }

            sort_stack(s);

            for (int i = 0; i < 10; ++i) {
                pass = pass && (s.peek() == i);
                s.pop();
            }
        }

        {
            Stack<Integer> s = new Stack<>();
            s.push(3);
            s.push(1);
            s.push(2);
            sort_stack(s);
            for (int i = 1; i <= 3; ++i) {
                pass = pass && (s.peek() == i);
                s.pop();
            }
        }

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
