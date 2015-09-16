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


public class Solution04 {

    private static ListNode partition(ListNode list, final int value) {
        // Essentially, the partition is like to reorganize the linked list.
        // We create two new linked lists: the small and the big.
        // We then iterate through the original linked list, and put the node
        // that is smaller than the partition value to the "small" list, and the
        // node that is greater than or equal to the partition value to the "big"
        // list. Finally, we link these two lists together.

        ListNode small = new ListNode();
        ListNode tail_small = small;

        ListNode big = new ListNode();
        ListNode tail_big = big;

        ListNode curr = list;
        while (curr != null) {
            if (curr.getValue() < value) {
                tail_small.setNext(curr);
                tail_small = curr;
            } else {
                tail_big.setNext(curr);
                tail_big = curr;
            }

            curr = curr.getNext();
        }

        // Link the small list before the big list.
        // Be careful! Don't link to the fake head node.
        tail_small.setNext(big.getNext());

        // Also make sure the big list ends up with NULL.
        tail_big.setNext(null);

        return small.getNext();
    }

    public static void main(String []args) {
        boolean pass = true;

        {
            int [] nums = { 3, 5, 8, 5, 10, 2, 1 };
            ListNode list = LinkedList.create(nums);
            ListNode result = partition(list, 5);
            LinkedList.print(result);
        }

        {
            int [] nums = { 3, 5, 8, 5, 10, 2, 1 };
            ListNode list = LinkedList.create(nums);
            ListNode result = partition(list, 6);
            LinkedList.print(result);
        }

        {
            int [] nums = { 3, 5, 8, 5, 10, 2, 1 };
            ListNode list = LinkedList.create(nums);
            ListNode result = partition(list, 0);
            LinkedList.print(result);
        }

        {
            int [] nums = { 3, 5, 8, 5, 10, 2, 1 };
            ListNode list = LinkedList.create(nums);
            ListNode result = partition(list, 50);
            LinkedList.print(result);
        }

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
