import java.util.*;


class ListNode {

    private int value;
    private ListNode next;

    public ListNode() {
        value = 0;
        next = null;
    }

    public ListNode(int v) {
        value = v;
        next = null;
    }

    public ListNode(int v, ListNode n) {
        value = v;
        next = n;
    }

    public ListNode getNext() {
        return next;
    }

    public void setNext(ListNode n) {
        next = n;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int v) {
        value = v;
    }
}

class LinkedList {

    public static ListNode create(int [] nums) {
        ListNode head = new ListNode(-99, null);
        ListNode tail = head;
        for (int i = 0; i < nums.length; ++i) {
            ListNode node = new ListNode(nums[i], null);
            tail.setNext(node);
            tail = node;
        }
        return head.getNext();
    }

    public static void print(ListNode head) {
        ListNode curr = head;
        System.out.print("{ ");
        while (curr != null) {
            System.out.print(curr.getValue());
            System.out.print(' ');
            curr = curr.getNext();
        }
        System.out.println("}");
    }

    public static int [] transform(ListNode head) {
        int length = 0;
        ListNode curr = head;
        while (curr != null) {
            ++length;
            curr = curr.getNext();
        }

        int [] nums = new int[length];
        curr = head;
        int i = 0;
        while (curr != null) {
            nums[i] = curr.getValue();
            ++i;
            curr = curr.getNext();
        }

        return nums;
    }
}


public class Solution06 {

    private static boolean ll_palindrome(ListNode list) {
        // Handle special cases.
        if (list == null) {
            // If list is empty, it is palindrome because it is always empty
            // regardless you look at it from left side or from right side.
            return true;
        }

        if (list.getNext() == null) {
            // If the list has only one element, sure it is palindrome.
            return true;
        }

        // Get the length of the list.
        int length = 0;
        ListNode curr = list;
        while (curr != null) {
            ++length;
            curr = curr.getNext();
        }

        Stack<Integer> s = new Stack<Integer>();

        // Push the first half of the list into the stack.
        curr = list;
        for (int i = 0; i < length / 2; ++i) {
            s.push(curr.getValue());
            curr = curr.getNext();
        }

        // If the list has an odd number of nodes, then skip the middle node.
        if (length % 2 != 0) {
            curr = curr.getNext();
        }

        // Check if the remaining half of the list can match every top node
        // in the stack.
        for (int i = 0; i < length / 2; ++i) {
            int val = s.peek();
            if (curr.getValue() == val) {
                // Match.
                s.pop();
                curr = curr.getNext();
            } else {
                // Find a node from the second half that does not match the
                // the corresponding node in the first half.
                break;
            }
        }

        return s.empty();
    }

    public static void main(String []args) {
        boolean pass = true;

        {
            int [] nums = { };
            ListNode list = LinkedList.create(nums);
            pass = pass && ll_palindrome(list);
        }

        {
            int [] nums = { 1 };
            ListNode list = LinkedList.create(nums);
            pass = pass && ll_palindrome(list);
        }

        {
            int [] nums = { 1, 2, 2, 1 };
            ListNode list = LinkedList.create(nums);
            pass = pass && ll_palindrome(list);
        }

        {
            int [] nums = { 1, 2, 1, 2, 1 };
            ListNode list = LinkedList.create(nums);
            pass = pass && ll_palindrome(list);
        }

        {
            int [] nums = { 1, 2, 2, 3 };
            ListNode list = LinkedList.create(nums);
            pass = pass && !ll_palindrome(list);
        }

        {
            int [] nums = { 1, 2, 1, 3, 1 };
            ListNode list = LinkedList.create(nums);
            pass = pass && !ll_palindrome(list);
        }

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
