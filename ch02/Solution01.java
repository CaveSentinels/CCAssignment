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


public class Solution01 {

    /**
     *  @brief: Remove the duplicated elements in the unsorted linked list.
     *  @param: [in] head: The head pointer to the unsorted linked list.
     *  @return: ListNode
     *      The head pointer to the result linked list of which the duplicated
     *      elements are removed.
     */
    private static ListNode remove_dups_with_buffer(ListNode head) {
        // Handle special cases.
        if (head == null) {
            return null;
        }
        if (head.getNext() == null) {
            // If there is zero or only one element in the linked list, then
            // there is no duplicated elements so we do not need to do anything.
            return head;
        }

        // Use a set to store the elements that have appeared once.
        Set<Integer> elements = new HashSet<>();
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            if (elements.contains(curr.getValue())) {
                // If the value has appeared already...
                // Remove the duplicated element.
                prev.setNext(curr.getNext());
                curr = prev.getNext();
            } else {
                elements.add(curr.getValue());
                prev = curr;
                curr = curr.getNext();
            }
        }

        return head;
    }

    /**
     *  @brief: Remove the duplicated elements in the unsorted linked list.
     *  @param: [in] head: The head pointer to the unsorted linked list.
     *  @return: ListNode
     *      The head pointer to the result linked list of which the duplicated
     *      elements are removed.
     */
    private static ListNode remove_dups_no_buffer(ListNode head) {
        // Handle the special cases.
        if (head == null) {
            return null;
        }

        if (head.getNext() == null) {
            // If there is only one element in the linked list, then there is no
            // duplicated elements so we do not need to do anything.
            return head;
        }

        ListNode prev = head;
        ListNode curr = head.getNext();
        while (curr != null) {
            ListNode comp = head;
            while (comp != curr) {
                if (comp.getValue() == curr.getValue()) {
                    // Duplicated. Remove.
                    prev.setNext(curr.getNext());
                    curr = prev.getNext();
                    break;
                }
                comp = comp.getNext();
            }

            if (comp == curr) {
                // Not duplicated.
                prev = curr;
                curr = curr.getNext();
            }
        }

        return head;
    }

    public static void main(String []args) {
        boolean pass = true;

        {
            int [] nums = { };
            ListNode list = LinkedList.create(nums);
            ListNode result = remove_dups_with_buffer(list);
            LinkedList.print(result);
        }

        {
            int [] nums = { 1 };
            ListNode list = LinkedList.create(nums);
            ListNode result = remove_dups_with_buffer(list);
            LinkedList.print(result);
        }

        {
            int [] nums = { 1, 1, 1, 1 };
            ListNode list = LinkedList.create(nums);
            ListNode result = remove_dups_with_buffer(list);
            LinkedList.print(result);
        }

        {
            int [] nums = { 1, 3, 2, 1, 2 };
            ListNode list = LinkedList.create(nums);
            ListNode result = remove_dups_with_buffer(list);
            LinkedList.print(result);
        }

        {
            int [] nums = { };
            ListNode list = LinkedList.create(nums);
            ListNode result = remove_dups_no_buffer(list);
            LinkedList.print(result);
        }

        {
            int [] nums = { 1 };
            ListNode list = LinkedList.create(nums);
            ListNode result = remove_dups_no_buffer(list);
            LinkedList.print(result);
        }

        {
            int [] nums = { 1, 1, 1, 1 };
            ListNode list = LinkedList.create(nums);
            ListNode result = remove_dups_no_buffer(list);
            LinkedList.print(result);
        }

        {
            int [] nums = { 1, 3, 2, 1, 2 };
            ListNode list = LinkedList.create(nums);
            ListNode result = remove_dups_no_buffer(list);
            LinkedList.print(result);
        }

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
