import java.util.*;


public class Solution03 {

    private static class SetOfStacks<_Ty> {

        private Stack<Stack<_Ty>> _stacks = new Stack<>();
        private final int _C = 2;   // The size of each stack. For testing purpose I make it small.

        public void Push(_Ty val) {
            if (_stacks.isEmpty()) {
                // If we don't have any stacks yet, create one.
                _stacks.push(new Stack<_Ty>());
            }

            // We will always use the stack at the end of the stack list.
            // Because we create a new stack once the current stack is full and
            // append the new stack to the end of the stack list, we can guarantee
            // that the very last stack is always the one we will operate on.
            Stack<_Ty> s = _stacks.peek();
            s.push(val);

            // Now we need to check if the stack is full or not. If it is full,
            // we should create a new stack.
            if (s.size() == _C) {
                // Create a new stack.
                _stacks.push(new Stack<_Ty>());
            }
        }

        public void Pop() {
            // We will always use the stack at the end of the stack list.
            // Because we remove the current stack once it becomes empty, we can
            // guarantee that the very last stack is always the one we will
            // operate on.
            Stack<_Ty> s = _stacks.peek();
            s.pop();

            // Now we need to check if the stack is empty or not. If it is empty,
            // we should remove it.
            if (s.empty()) {
                _stacks.pop();
            }
        }

        public _Ty Top() {
            Stack<_Ty> s = _stacks.peek();
            return s.peek();
        }

        public boolean Empty() {
            return _stacks.empty();
        }

    // FOLLOW-UP operation
        public void PopAt(int index) {
            if (0 <= index && index < _stacks.size()) {
                // Find the stack.
                Stack<_Ty> s = _stacks.get(index);

                // Pop the element in the stack.
                s.pop();

                // Check if the stack at this position is empty or not. If it is
                // empty, just remove it.
                if (s.empty()) {
                    _stacks.remove(index);
                }
            }
        }
    };

    public static void main(String []args) {
        boolean pass = true;

        {
            final int C = 2;
            final int T = 7;
            SetOfStacks< Integer > s = new SetOfStacks<>();

            // Initially, it is empty.
            pass = pass && s.Empty();

            // Pushing T elements to the stack results in (T/C+1) internal stacks.
            for (int i = 0; i < T; ++i) {
                s.Push(i);
            }

            // The top element should be (T-1).
            pass = pass && (s.Top() == (T-1));

            // Popping up C elements results in the removal of one stack.
            for (int i = 0; i < C; ++i) {
                s.Pop();
            }

            // Always pop up from the first stack (index of 0) for (T-C) times.
            // This will result in all the stacks removed.
            for (int i = 0; i < T-C; ++i) {
                s.PopAt(0);
            }

            // Now check if the entire stack is empty.
            pass = pass && s.Empty();
        }

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
