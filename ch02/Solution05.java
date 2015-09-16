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


public class Solution05 {

    private static ListNode sum_lists_backward(ListNode list1, ListNode list2) {
        ListNode curr1 = list1;
        ListNode curr2 = list2;
        int carry = 0;
        int digit = 0;

        ListNode fakehead = new ListNode();
        ListNode tail = fakehead;

        while (curr1 != null && curr2 != null) {
            digit = curr1.getValue() + curr2.getValue() + carry;
            if (digit >= 10) {
                carry = 1;
                digit -= 10;
            } else {
                carry = 0;
            }

            // Create the new linked list node and link it to the end.
            ListNode node = new ListNode(digit);
            tail.setNext(node);
            tail = node;

            curr1 = curr1.getNext();
            curr2 = curr2.getNext();
        }

        // Handle the remaining part of list1.
        while (curr1 != null) {
            digit = curr1.getValue() + carry;
            if (digit >= 10) {
                carry = 1;
                digit -= 10;
            } else {
                carry = 0;
            }

            // Create the new linked list node and link it to the end.
            ListNode node = new ListNode(digit);
            tail.setNext(node);
            tail = node;

            curr1 = curr1.getNext();
        }

        // Handle the remaining part of list2.
        while (curr2 != null) {
            digit = curr2.getValue() + carry;
            if (digit >= 10) {
                carry = 1;
                digit -= 10;
            } else {
                carry = 0;
            }

            // Create the new linked list node and link it to the end.
            ListNode node = new ListNode(digit);
            tail.setNext(node);
            tail = node;

            curr2 = curr2.getNext();
        }

        // Handle the carry flag.
        if (carry > 0) {
            // Create the new linked list node and link it to the end.
            ListNode node = new ListNode(carry);
            tail.setNext(node);
            tail = node;
        }

        return fakehead.getNext();
    }


    private static int convert_to_num(ListNode list) {
        ListNode curr = list;
        int num = 0;
        while (curr != null) {
            num = num * 10 + curr.getValue();
            curr = curr.getNext();
        }
        return num;
    }

    private static ListNode convert_to_list(int num) {
        ListNode fakehead = new ListNode();
        ListNode head = fakehead;

        int digit = 0;
        while (num > 0) {
            digit = num - (num / 10 * 10);
            ListNode node = new ListNode(digit);

            node.setNext(head.getNext());
            head.setNext(node);

            num /= 10;
        }

        return fakehead.getNext();
    }

    private static ListNode sum_lists_forward(ListNode list1, ListNode list2) {
        int num1 = convert_to_num(list1);
        int num2 = convert_to_num(list2);
        return convert_to_list(num1 + num2);
    }

    public static void main(String []args) {
        boolean pass = true;

        // ========================================================================
        // Test backward

        {
            int [] nums1 = { 1, 2, 3 };
            ListNode list1 = LinkedList.create(nums1);
            int [] nums2 = { 3, 2, 1 };
            ListNode list2 = LinkedList.create(nums2);

            ListNode result = sum_lists_backward(list1, list2);
            LinkedList.print(result);
        }

        {
            int [] nums1 = { 9, 9, 9 };
            ListNode list1 = LinkedList.create(nums1);
            int [] nums2 = { 9, 9, 9 };
            ListNode list2 = LinkedList.create(nums2);

            ListNode result = sum_lists_backward(list1, list2);
            LinkedList.print(result);
        }

        {
            int [] nums1 = { 9, 9, 9 };
            ListNode list1 = LinkedList.create(nums1);
            int [] nums2 = { 9 };
            ListNode list2 = LinkedList.create(nums2);

            ListNode result = sum_lists_backward(list1, list2);
            LinkedList.print(result);
        }

        {
            int [] nums1 = { 7, 1, 6 };
            ListNode list1 = LinkedList.create(nums1);
            int [] nums2 = { 5, 9, 2 };
            ListNode list2 = LinkedList.create(nums2);

            ListNode result = sum_lists_backward(list1, list2);
            LinkedList.print(result);
        }

        // ========================================================================
        // Test forward

        {
            int [] nums1 = { 1, 2, 3 };
            ListNode list1 = LinkedList.create(nums1);
            int [] nums2 = { 3, 2, 1 };
            ListNode list2 = LinkedList.create(nums2);

            ListNode result = sum_lists_forward(list1, list2);
            LinkedList.print(result);
        }

        {
            int [] nums1 = { 9, 9, 9 };
            ListNode list1 = LinkedList.create(nums1);
            int [] nums2 = { 9, 9, 9 };
            ListNode list2 = LinkedList.create(nums2);

            ListNode result = sum_lists_forward(list1, list2);
            LinkedList.print(result);
        }

        {
            int [] nums1 = { 9, 9, 9 };
            ListNode list1 = LinkedList.create(nums1);
            int [] nums2 = { 9 };
            ListNode list2 = LinkedList.create(nums2);

            ListNode result = sum_lists_forward(list1, list2);
            LinkedList.print(result);
        }

        {
            int [] nums1 = { 7, 1, 6 };
            ListNode list1 = LinkedList.create(nums1);
            int [] nums2 = { 5, 9, 2 };
            ListNode list2 = LinkedList.create(nums2);

            ListNode result = sum_lists_forward(list1, list2);
            LinkedList.print(result);
        }

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
