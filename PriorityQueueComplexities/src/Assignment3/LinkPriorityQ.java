package Assignment3;


public class LinkPriorityQ {

  public static class PqNode{
        int val;
        int pri;

        PqNode next;
    }


  public static PqNode newNode(int c, int p){

       PqNode temp = new PqNode();
       temp.val = c;
       temp.pri = p;
       temp.next = null;
       return temp;

   }

  public static PqNode popy(PqNode head){
       PqNode temp = head;
       head = head.next;
       return head;
   }

  public static PqNode pushy(PqNode head, int c, int p){

       PqNode first = head;

       PqNode temp = newNode(c,p);

       if(head.pri > p){
           temp.next = head;
           head = temp;
       }else{
           while(first.next != null && first.next.pri < p){
               first = first.next;
           }
           temp.next = first.next;
           first.next = temp;
       }
        return head;

   }

   public static boolean isEmpty(PqNode head){

       if(head == null){
           return true;
       }else{
           return false;
       }

   }

}
