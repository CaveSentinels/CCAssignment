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


public class Solution07 {

    private static ListNode ll_move_to_tail(ListNode list) {
        while (list != null && list.getNext() != null) {
            list = list.getNext();
        }
        return list;
    }

    private static boolean lists_intersect(ListNode list1, ListNode list2) {
        ListNode tail1 = ll_move_to_tail(list1);
        ListNode tail2 = ll_move_to_tail(list2);
        return tail1 == tail2;
    }

    public static void main(String []args) {
        boolean pass = true;

        {
            int [] nums1 = { 7, 1, 6 };
            ListNode list1 = LinkedList.create(nums1);
            int [] nums2 = { 5, 9, 2 };
            ListNode list2 = LinkedList.create(nums2);

            pass = pass && !lists_intersect(list1, list2);
        }

        {
            int [] nums1 = { 1, 2, 3, 4 };
            ListNode list1 = LinkedList.create(nums1);
            int [] nums2 = { 5, 6, 7 };
            ListNode list2 = LinkedList.create(nums2);

            ListNode curr1 = list1.getNext().getNext(); // 3
            ListNode curr2 = list2.getNext().getNext(); // 7
            curr2.setNext(curr1);

            pass = pass && lists_intersect(list1, list2);
        }

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
