/*
5862990
Keenan Brab
Intellij
 */

//This program tests a linked pq, array heap and tree heap
package Assignment3;
import java.io.*;

import java.util.* ;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Stack;

import static Assignment3.LinkPriorityQ.*;


public class Main {
    //make sure file location is set to root directory of this project with name
    //This one is for the random generated file
    private static String FILENAME = "D:\\Assignment3\\src\\Assignment3\\RanNums.txt";
    private static TreeNode root;



    public static void main(String[] args) throws FileNotFoundException {
        int n = 10000;
        int num = 0;
        int arr[];
        long tree1,tree2,array1,array2,pq1,pq2;

        System.out.println("0 to load assn3in.txt, 1 to create new random file");
        Scanner sc = new Scanner(System.in);
        int d = sc.nextInt();

        if(d==1) {

            try (BufferedWriter input = new BufferedWriter(new FileWriter(FILENAME))) {
                String Nstring = Integer.toString(n);

                input.write(Nstring);
                input.newLine();


                for (int i = 0; i < n; i++) {
                    num = (int) randInt(0, 1000);
                    String numberAsString = Integer.toString(num);

                    input.write(numberAsString);
                    input.newLine();
                }

            } catch (IOException e) {

                e.printStackTrace();

            }
        }else{
            //this is the provided example text file make sure set to correct directory
          FILENAME = "D:\\Assignment3\\src\\Assignment3\\assn3in.txt";
        }

        int p = 0;
        FileReader file = new FileReader(FILENAME);
        try {
            Scanner input = new Scanner(file);
            p = input.nextInt();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        arr = new int[p];


        int k = 0;

        int count = 0;

        FileReader full = new FileReader(FILENAME);
        try {
            Scanner input = new Scanner(full);
            p = input.nextInt();


            while(input.hasNext())
            {

                    k = input.nextInt();

                        arr[count] = k;

                        count++;


            }
            input.close();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


        pq1 = System.nanoTime();
        /*simple PQ*/
        LinkPriorityQ.PqNode pq =  newNode(arr[0], arr[0]);
        runPQL(pq,arr);
        pq2 = System.nanoTime();

        array1 =System.nanoTime();
        /*Binary Heap Array Implementation*/
        BinaryHeap<Integer> h = new BinaryHeap<>( );
        /*STILL HAVE TO ADD PREORDER TRAVERSAL*/
        runMHA(h,arr);
        array2 = System.nanoTime();

        tree1 =System.nanoTime();
        /*Binary Tree Heap*/
        /*Tree is build successfully and complete, when item is removed it is not perfect for large sets*/
        root = new TreeNode(arr[0]);
        int county =2;
        int pounty = 2;
        for(int i = 0; i < arr.length-1;i++){
            insert(arr[i+1],county, root, pounty);
            pounty++;
            county++;
        }

        //post order traversal
        postordertraverse(root);
        System.out.println();

        //removal of all items. Empties the tree in decresaing order
        for(int i = arr.length; i > 0; i-- ){
            System.out.println(removemin(root, i));
        }
        tree2 =System.nanoTime();


        System.out.println("linked pq start: "+pq1);
        System.out.println("linked pq end: "+pq2);
        System.out.println(pq2-pq1);
        System.out.println("Array heap  start: "+array1);
        System.out.println("Array heap end: "+array2);
        System.out.println(array2-array1);
        System.out.println("Tree heap start: "+tree1);
        System.out.println("Tree heap end: "+tree2);
        System.out.println(tree2-tree1);
    }
    //remove min function, takes from root then swaps with  right most value in tree swaps with
    private static int removemin(TreeNode current, int i) {
        int temp = current.data;
        Stack<Integer> traverse = new Stack<>();
        Stack<Integer> traversetwo = new Stack<>();
        int s = i;
        int wow = 0;
        int update = 0;


        //generates traversal path
        while (s >= 1) {
            if(s==2){
                traverse.push(0);
                traversetwo.push(0);

                wow++;
                break;
            }else if(s==3){
                traverse.push(1);
                traversetwo.push(1);

                wow++;
                break;
            }else {



                if (s % 2 == 1) {
                    traverse.push(1);
                    traversetwo.push(1);

                    wow++;
                } else {
                    traverse.push(0);
                    traversetwo.push(0);

                    wow++;
                }

                s = s / 2;
            }

        }
        int[] arr;
        arr = new int [wow];
        int f = 0;
        for(int d = 0; d <wow; d++){
            f = traversetwo.pop();
            arr[d] = f;
        }
        //swaps values
        update = swap(traverse,arr, current);

        current.data = update;
        //sift down to balance and keep trees heap properties
        perdown(current,traverse);

        return temp;
    }
    //sifts down by swaping improper values, or folowing traversal path
    private static void perdown(TreeNode current, Stack<Integer> traverse) {

        while(current.left != null && current.right !=null){
            if(current.left.data < current.data){
                int tmp = current.data;
                current.data = current.left.data;
                current.left.data = tmp;

                current = current.left;
                traverse.pop();
            }
            else if(current.right.data < current.data){
                int tmp = current.data;
                current.data = current.right.data;
                current.right.data = tmp;

                current = current.right;
                traverse.pop();
            }else{
                int s = traverse.pop();
                if(s ==1){
                    current = current.right;
                    System.out.println("right");
                }else{
                    current = current.left;
                    System.out.println("left");
                }


            }




        }

    }
        //swaps root with right most value at bottom level
    private static int swap(Stack<Integer> traverse, int[] arr, TreeNode current) {

        int dat = 0;
        for(int i = 0; i < arr.length; i++){
            int s = arr[i];
            if(i+1 != arr.length) {
                if (s == 1) {
                   if(current.right!=null)current = current.right;

                } else {
                    if(current.left!=null)current = current.left;

                }
            }
            if(i+1 == arr.length){
                if(s==1){

                    if(current.right!=null)dat = current.right.data;

                    current.right= null;

                }else{
                    if(current.left!=null)dat = current.left.data;

                    current.left = null;

                }

            }
        }




        return dat;

    }
    //recursive post order traversal
    private static void postordertraverse(TreeNode node) {

        if (node == null)
            return;

        postordertraverse(node.left);
        postordertraverse(node.right);
        System.out.print(node.data + " ");
}
        //recursively inserts values into tree to the bottom right most value to keep tree compelete
    private static void insert(int d,int n, TreeNode current, int s) {
        Stack<Integer> traverse = new Stack<>();
        Stack<Integer> traversetwo = new Stack<>();
        int wow = 0;



        while (s >= 1) {
        if(s==2){
            traverse.push(0);
            traversetwo.push(0);

            wow++;
            break;
        }else if(s==3){
            traverse.push(1);
            traversetwo.push(1);

            wow++;
            break;
        }else {



                if (s % 2 == 1) {
                    traverse.push(1);
                    traversetwo.push(1);

                    wow++;
                } else {
                    traverse.push(0);
                    traversetwo.push(0);

                    wow++;
                }

                s = s / 2;
            }

        }


        //once path is found recursive calls start
        addNodey(d,n,current,traverse);
        //bubble or sift up using the recusive calls after node is added works and orders the values correctly to keep heap property
        percup(current,traversetwo,wow);

    }

    private static void addNodey(int d, int n, TreeNode current, Stack<Integer> traverse) {
        int s = traverse.pop();
        if(n==2||n==3||n==1){
            TreeNode tmp = new TreeNode(d);
            if(s==1){
               current.right = tmp;
               // System.out.println(tmp.data);
                return;
            }else{
                current.left = tmp;
               // System.out.println(tmp.data);
                return;
            }

        }
            //recursive calls
            if(s%2==1){

                addNodey(d,n/2 ,current.right, traverse);
            }else {

                addNodey(d,n/2 ,current.left, traverse);
            }

            }




        //used to sort and ensure heap values are correct
    private static void percup(TreeNode current, Stack<Integer> traversetwo, int size) {
        int s;
        int[] arr;
        TreeNode temp;
        TreeNode head = current;

        arr = new int[size];
        for(int i = 0; i < size;i++){
               s = traversetwo.pop();
               arr[i] = s;
        }

        for(int i = 0; i < size;i++){
            for(int j = i; j < size;j++){

                temp = current;

                int tmp;
                s = arr[j];
                if(s==1){
                   if(current.right!= null){
                       if(current.data>current.right.data){
                           System.out.println("right");
                           System.out.println(current.data+"<->"+current.right.data);
                           tmp = current.data;
                           current.data = current.right.data;
                           current.right.data = tmp;


                       }
                       current = current.right;
                   }
                }else{
                    if(current.left!= null){
                        if(current.data>current.left.data){
                            System.out.println("left");
                            System.out.println(current.data+"<->"+current.left.data);

                            tmp = current.data;
                            current.data = current.left.data;
                            current.left.data = tmp;



                        }
                        current = current.left;
                    }

                }


            }
            current = head;
        }

    }

    //runs array heap insert and deletes and traverse
    private static void runMHA(BinaryHeap<Integer> h, int[] arr) {
        for(int i = 0; i < arr.length;i++){

            h.insert(arr[i]);
        }
        System.out.println("Array Min Heap");
        for(int i = 0; i < arr.length;i++){
           int s = h.deleteMin( );
           System.out.println("Data: "+s+" Priority: "+ s);

        }



    }
    //runs linked priority queue insert and dequeue
    private static void runPQL(LinkPriorityQ.PqNode pq, int[] arr) {


      for(int i = 1; i < arr.length; i++) {

            pq = pushy(pq, arr[i], arr[i]);
        }
        System.out.println("Linked Priority Queue");
        while (isEmpty(pq)==false) {
            System.out.print("Data: " + pq.val+" Priority: " + pq.pri);
            System.out.println();
            pq=popy(pq);
        }


    }

    public static int randInt(int min , int max) {
        int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
        return randomNum;

    }








}
