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


public class Solution02 {

    /**
     *  @brief: Return the Kth element to the last.
     *  @param: [in] head: The linked list.
     *  @param: [in] K: The node to be returned.
     *  @return: ListNode: The pointer to the Kth node to the last.
     *      - NULL: If the given linked list does not have at least K nodes.
     *      - Not NULL: The Kth node.
     */
    private static ListNode find_kth_to_last(ListNode head, final int K) {
        // Handle special cases.
        if (head == null || K <= 0) {
            return null;
        }

        // We set two pointers. The first pointer is used to detect if we have
        // reached the tail of the linked list. The second pointer is used to
        // locate the Kth node to the last.

        // Move the first pointer K steps.
        ListNode first = head;
        int i = 0;
        while (i < K && first != null) {
            first = first.getNext();
            ++i;
        }

        if (i < K) {
            // The given linked list does not have at least K nodes.
            return null;
        }

        // Set the second pointer to the head, and move both them together to the
        // end. When first reaches the tail NULL, second is pointing at the Kth
        // node to the last.
        ListNode second = head;
        while (first != null) {
            first = first.getNext();
            second = second.getNext();
        }

        return second;
    }

    public static void main(String []args) {
        boolean pass = true;

        {
            int [] nums = { 1, 2, 3 };
            ListNode list = LinkedList.create(nums);

            ListNode result = find_kth_to_last(list, 1);
            pass = pass && (result.getValue() == 3);

            result = find_kth_to_last(list, 2);
            pass = pass && (result.getValue() == 2);

            result = find_kth_to_last(list, 3);
            pass = pass && (result.getValue() == 1);

            result = find_kth_to_last(list, 4);
            pass = pass && (result == null);
        }

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
