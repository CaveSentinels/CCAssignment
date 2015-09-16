/**
 *  @assumptions:
 *  1). The given list is definitely a circular list.
 */


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


public class Solution08 {

    /**
     *  @brief: Find the node that begins the loop in a circular linked list.
     *  @param: [in] list: The linked list.
     *  @return: ListNode
     *      - null: When the list itself is null.
     *      - not null: When list is circular.
     */
    private static ListNode find_loop_position(ListNode list) {
        if (list == null) {
            return null;
        }

        ListNode fast = list;
        ListNode slow = list;

        do {
            fast = fast.getNext().getNext();
            slow = slow.getNext();
        } while (fast != slow);

        ListNode curr = list;
        while (curr != slow) {
            curr = curr.getNext();
            slow = slow.getNext();
        }

        return curr;
    }

    public static void main(String []args) {
        boolean pass = true;

        {
            int [] nums = { 1, 2, 3, 4, 5 };
            ListNode list = LinkedList.create(nums);
            ListNode node1 = list;
            ListNode node5 = list.getNext().getNext().getNext().getNext();
            node5.setNext(node1);
            ListNode result = find_loop_position(list);
            pass = pass && (result == node1);
            node5.setNext(null);
        }

        {
            int [] nums = { 1, 2, 3, 4, 5 };
            ListNode list = LinkedList.create(nums);
            ListNode node3 = list.getNext().getNext();
            ListNode node5 = list.getNext().getNext().getNext().getNext();
            node5.setNext(node3);
            ListNode result = find_loop_position(list);
            pass = pass && (result == node3);
            node5.setNext(null);
        }

        {
            int [] nums = { 1, 2, 3, 4, 5 };
            ListNode list = LinkedList.create(nums);
            ListNode node5 = list.getNext().getNext().getNext().getNext();
            node5.setNext(node5);
            ListNode result = find_loop_position(list);
            pass = pass && (result == node5);
            node5.setNext(null);
        }

        System.out.println("Result: " + (pass ? "All passed" : "Some failed"));
    }
}
