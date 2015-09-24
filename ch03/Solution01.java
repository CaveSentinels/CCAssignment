import java.util.*;


public class Solution01 {

    private static class ArrayStack {


        // A helper class that manages just a particular portion on the array.
        private static class _array_stack {
            private int [] _array;
            private int _F;     // floor
            private int _C;     // ceiling
            private int _T;     // top pointer

        public _array_stack() {
                _array = null;
                _F = -1;
                _C = -1;
                _T = _F;
            }

        public void init(int [] array, int floor, int ceiling) throws Exception {
                // Validate arguments
                if (null == array) {
                    throw new Exception("The storage array is null.");
                } else if (ceiling < floor) {
                    throw new Exception("The stack's ceiling is lower than its floor.");
                }

                _array = array;
                _F = floor;
                _C = ceiling;
                _T = floor;
            }

        public void push(int val) throws Exception {
                if (_T < _C) {
                    ++_T;
                    _array[_T] = val;
                } else {
                    throw new Exception("Stack is already full.");
                }
            }

        public void pop() throws Exception {
                if (_T > _F) {
                    --_T;
                } else {
                    throw new Exception("Stack is already empty.");
                }
            }

        public int peek() {
                return _array[_T];
            }

        public boolean empty() {
                return (_T == _F);
            }

        public boolean full() {
                return (_T == _C);
            }
        };

    private int [] _array;
    private int _C;
    private int _N;
    private _array_stack [] _stacks;

    private boolean _in_range(int index) {
            return (0 <= index && index < _N);
        }

    public ArrayStack(int capacity, int stacks) throws Exception {
            // Validate the parameters
            // Make sure the capacity is at least 1.
            _C = (capacity >= 1 ? capacity : 1);
            // Make sure the N is at most the same as C.
            _N = (stacks <= capacity ? stacks : capacity);

            // Now allocate the space.
            _array = new int[_C];

            // Initialize the internal stacks.
            int stack_size = _C / _N;
            _stacks = new _array_stack[_N];
            for (int i = 0; i < _N-1; ++i) {
                _stacks[i] = new _array_stack();
                _stacks[i].init(
                    _array,
                    i * stack_size - 1,     // Must reduce 1 because it is the floor
                    (i+1) * stack_size - 1
                );
            }
            // Need to handle the last stack because it will take all the space
            // after the second to the last stack.
            _stacks[_N-1] = new _array_stack();
            _stacks[_N-1].init(_array, (_N-1) * stack_size - 1, _C - 1);
        }

    public int N() {
            return _N;
        }

    public void Push(int index, int val) throws Exception {
            if (_in_range(index)) {
                _stacks[index].push(val);
                return;
            }

            throw new Exception("Given index is out of range.");
        }

    public void Pop(int index) throws Exception {
            if (_in_range(index)) {
                _stacks[index].pop();
                return;
            }

            throw new Exception("Given index is out of range.");
        }

    public int Peek(int index) throws Exception {
            if (_in_range(index)) {
                return _stacks[index].peek();
            }

            throw new Exception("Given index is out of range.");
        }

    public boolean Empty(int index) throws Exception {
            if (_in_range(index)) {
                return _stacks[index].empty();
            }

            throw new Exception("Given index is out of range.");
        }

    public boolean Full(int index) throws Exception {
            if (_in_range(index)) {
                return _stacks[index].full();
            }

            throw new Exception("Given index is out of range.");
        }
    };

    public static void main(String []args) throws Exception {
        boolean pass = true;

        {
            final int CAPACITY = 6;
            final int STACKS = 3;
            ArrayStack as = new ArrayStack(CAPACITY, STACKS);

            // Initially, all the stacks are empty.
            for (int i = 0; i < STACKS; ++i) {
                pass = pass && as.Empty(i);
            }

            // We can push CAPACITY / STACKS elements to each stack and they will become full.
            for (int i = 0; i < STACKS; ++i) {
                for (int j = 0; j < CAPACITY / STACKS; ++j) {
                    as.Push(i, (i * 10 + j));
                }
                pass = pass && as.Full(i);
            }

            // If we push one more element to the stacks, they will throw exceptions.
            for (int i = 0; i < STACKS; ++i) {
                try {
                    as.Push(i, -1);
                } catch(Exception e) {
                    pass = pass && (e.toString().contains("Stack is already full."));
                }
            }

            // Now we peek at the top element of each stack.
            for (int i = 0; i < STACKS; ++i) {
                pass = pass && (as.Peek(i) == (i * 10 + CAPACITY / STACKS - 1));
            }

            // We pop CAPACITY / STACKS elements out of each stack and they will
            // become empty.
            for (int i = 0; i < STACKS; ++i) {
                for (int j = 0; j < CAPACITY / STACKS; ++j) {
                    as.Pop(i);
                }
                pass = pass && (as.Empty(i));
            }

            // If we try to pop again, the stack will throw exceptions.
            for (int i = 0; i < STACKS; ++i) {
                try {
                    as.Pop(i);
                } catch(Exception e) {
                    pass = pass && (e.toString().contains("Stack is already empty."));
                }
            }
        }

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
