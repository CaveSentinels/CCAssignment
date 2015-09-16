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


public class Solution03 {

    /**
     *  @brief: Delete the node in the middle of a linked list.
     *  @param: [in] node: The node in the middle of a linked list. node is
     *      guaranteed to be in the middle. It is neither head nor tail.
     *  @return: N/A
     */
    private static void delete_middle_node(ListNode node) {
        // The key point is: We want the node that's pointed by 'node' to
        // disappear from the linked list, but we are not saying that this
        // node must be removed physically.
        // The trick here is to copy the value of the next node, and remove
        // the 'next' node physically.
        ListNode next = node.getNext();
        node.setValue(next.getValue());
        node.setNext(next.getNext());
    }

    public static void main(String []args) {
        boolean pass = true;

        {
            int [] nums = { 1, 3, 2, 1, 2 };
            ListNode list = LinkedList.create(nums);
            ListNode node = list.getNext();
            delete_middle_node(node);
            LinkedList.print(list);
        }

        {
            int [] nums = { 1, 3, 2, 1, 2 };
            ListNode list = LinkedList.create(nums);
            ListNode node = list.getNext().getNext();
            delete_middle_node(node);
            LinkedList.print(list);
        }

        {
            int [] nums = { 1, 3, 2, 1, 2 };
            ListNode list = LinkedList.create(nums);
            ListNode node = list.getNext().getNext().getNext();
            delete_middle_node(node);
            LinkedList.print(list);
        }

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
