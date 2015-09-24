class Stack {

    private class Slot {
        public int value;   // The data in this slot
        public Slot min;    // The slot that holds the minimum value in the "substack"
        public Slot above;  // The slot above this slot.
        public Slot below;  // The slot below this slot.

        public Slot(int v) {
            value = v;
            min = null;
            above = null;
            below = null;
        }
    }

    // We will maintain a list of slots and this is the bottom slot of all
    // the other slots.
    private Slot bottom = new Slot(0);
    private Slot top; // The pointer that always points to the top element.

    private boolean empty() {
        return (top == bottom);
    }

    public Stack() {
        // Initially, top pointer points at the bottom of the stack.
        top = bottom;
    }

    public void Push(int val) {
        Slot new_slot = new Slot(val);

        if (empty()) {
            // If now the stack is empty, then this is the first element
            // and it is the smallest in its "substack".
            new_slot.min = new_slot;   // Pointing at itself.
            bottom.above = new_slot;
            new_slot.below = bottom;
            top = new_slot;
        } else {
            // Otherwise, there are elements in the current stack. We need to
            // figure out if the new value is smaller than the currently
            // smallest element.

            // Get the smallest element from the top element's min pointer.
            Slot min_elm = top.min;

            // Now we push the new value to the top of the stack.
            top.above = new_slot;
            new_slot.below = top;
            top = new_slot;

            // If the new value is smaller, its min pointer points to itself;
            // otherwise it will point to the min_elm.
            new_slot.min = (val < min_elm.value ? new_slot : min_elm);
        }
    }

    public int Peek() {
        return top.value;
    }

    public void Pop() {
        if (!empty()) {
            // Remember the currently top element so we can delete it later.
            Slot top_elm = top;

            // Move the top pointer down.
            top = top.below;
        } else {
            // If the stack is already empty, we simply do nothing.
        }
    }

    public int Min() {
        if (!empty()) {
            // Get the min element and return its value.
            return top.min.value;
        }

        // If the stack is already empty, we simply return 0 which may make
        // no sense to the caller.
        // It is the caller's responsibility to check if the stack is empty
        // or not before calling the Min() method.
        return 0;
    }

    public boolean Empty() {
        return empty();
    }
}

public class Solution02 {

    public static void main(String []args) {
        boolean pass = true;

        {
            Stack s = new Stack();
            s.Push(1);
            s.Push(5);
            s.Push(-3);
            s.Push(4);
            pass = pass && (s.Min() == -3);
            s.Pop();
            pass = pass && (s.Min() == -3);
            s.Pop();
            pass = pass && (s.Min() == 1);
            s.Pop();
            pass = pass && (s.Min() == 1);
            s.Pop();
            pass = pass && s.Empty();
        }

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
