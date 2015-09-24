import java.util.*;


public class Solution04 {

    private static class MyQueue<_Ty> {

        private Stack< _Ty > _s1 = new Stack<>();  // Used to receive new elements.
        private Stack< _Ty > _s2 = new Stack<>();  // Used to pop up old elements.

        private void _shift_elements() {
            // When pushing new elements to the back, the new elements will always
            // go to _s1.
            // When popping up the front element, we need to use _s2 as a buffer
            // because at this time, the front element is actually at the bottom
            // of _s1.
            // What we do is: Pop all elements from _s1 and push them into _s2.
            // By doing so we can reverse the order of the elements in _s1 and make
            // the bottom element now the top.
            // One noticeable thing is: We only shift elements when _s2 becomes
            // empty because otherwise the _s1 elements will be put on top of the
            // existing _s2 elements which makes the elements out of order.
            if (_s2.empty()) {
                // Pop all the elements in _s1 and push them into _s2 so the
                // bottom element in _s1 becomes the top element in _s2.
                while (!_s1.empty()) {
                    _s2.push(_s1.peek());
                    _s1.pop();
                }
            }
        }

        public boolean Empty() {
            return (_s1.empty() && _s2.empty());
        }

        public void Push(_Ty val) {
            _s1.push(val);
        }

        public void Pop() throws Exception {
            _shift_elements();

            if (!_s2.empty()) {
                _s2.pop();
            } else {
                throw new Exception("The stack is already empty.");
            }
        }

        public _Ty Front() throws Exception {
            _shift_elements();

            if (!_s2.empty()) {
                return _s2.peek();
            } else {
                throw new Exception("The stack is already empty.");
            }
        }
    };

    public static void main(String []args) throws Exception {
        boolean pass = true;

        {
            final int T = 5;
            MyQueue< Integer > q = new MyQueue<>();

            // Initially, the queue is empty.
            pass = pass && q.Empty();

            // Push T elements into it.
            for (int i = 0; i < T; ++i) {
                q.Push(i);
            }

            // Pop all the elements. Make sure they are popped up in the same order
            // as they were pushed in.
            for (int i = 0; i < T; ++i) {
                pass = pass && (q.Front() == i);
                q.Pop();
            }

            // Now the queue should be empty again.
            pass = pass && q.Empty();
        }

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
