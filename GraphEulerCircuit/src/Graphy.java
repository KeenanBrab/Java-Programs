/*
Keenan Brab
5862990
Assignment 4

For this program use ass4in for Euler circuit case
Use test for Largest Euler Subset case

 */

import java.util.LinkedList;
import java.util.Stack;
import java.io.*;
import java.util.* ;

public class Graphy {

    private int V;
    private boolean[][] adjacencyMatrix;

    Queue<Integer> q = new LinkedList<>();
    Stack<Integer> stack = new Stack<>();

    private static String FILENAME = "D:\\Assignment4\\src\\assn4in.txt";
    public boolean [] visited;
    public int data = 0;

    Graphy(int V,boolean[][]matrix) {
        this.V = V;
        this.adjacencyMatrix = new boolean[V+1][V+1];

        for (int i = 1; i <= V; i++) {
            for (int j = 1; j <= V; j++) {
                if (matrix[i][j] == true) {
                    addEdge(i, j);
                }

            }

        }



    }



    private void addEdge(Integer u, Integer v) {
        adjacencyMatrix[u][v] = true;
        adjacencyMatrix[v][u] = true;



    }
    public void removeEdge (int u, int v)
    {
        adjacencyMatrix[u][v]  = false;
        adjacencyMatrix[v][u] = false;
    }



    public static void main(String[] args) throws FileNotFoundException {
        int p = 0;
        FileReader file = new FileReader(FILENAME);
        try {
            Scanner input = new Scanner(file);
            p = input.nextInt();
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        //P is number of vertices

        boolean[][] matrix = new boolean[p+1][p+1];


        int k = 0;
        FileReader full = new FileReader(FILENAME);
        try {
            Scanner input = new Scanner(full);
            p = input.nextInt();

    //place adjmatrix from text file to array
            for (int i = 1; i <= p; i++) {
                for (int j = 1; j <= p; j++) {
                    k = input.nextInt();

                    if (k == 1) {
                        matrix[i][j] = true;
                    } else if (k == 0) {
                        matrix[i][j] = false;
                    } else {

                    }
                }

            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        boolean check;

    //create graph
        Graphy g1 = new Graphy(p,matrix);
        check = rowCheck(matrix,p);
        int subset;
        if(check==true){
            //build circuit if one exists
            g1.findCircuit(1);
            g1.printstack();
        }else{
            //find subset if exists at certain cardinality
         subset = g1.largestEulerSubset(matrix,p);
            System.out.println("Largest Euler subset: "+g1.data);
        }

    }
        //Sheridans algorithm for finding largest subset
    private int largestEulerSubset(boolean[][] matrix,int p) {
        boolean success = false;
        success = rowCheck(matrix,p);

        if(success == true)data = p;
        if(success == true)return p;

        for(int i = 1; i <=V; i++){


            for(int j = 1;j<=V;j++){

            matrix[j][i] = false;
            matrix[i][j] = false;
        }
            largestEulerSubset(matrix,p-1);

        }
        return 1;
    }
    //print stack result from circuit finder
    private void printstack() {
        int startnode = (V-V)+1;
        stack.add(startnode);
        int size = stack.size();
        int circuit [] = new int[size];
        int count = size-1;
        char[] comp = {' ','A','B','C','D','E','F'};
        while(!stack.isEmpty()){
            int element = stack.pop();
            circuit[count] = element;
            count--;
        }
        System.out.print("Euler Circuit: ");
        for(int i = 0; i < size; i++){
            int temp = circuit[i];
            System.out.print(comp[temp]);
        }





    }
    //recursive  algorithm finding path
    private  void findCircuit(int vertex) {

        for(int dest = 1; dest<=V;dest++){
            //next edge finds the next available edge to traverse too
            if(!(adjacencyMatrix[vertex][dest] == false) && NextEdge(vertex, dest)){
                q.add(vertex);
                stack.push(vertex);
                //removes edges onces traversed
                removeEdge(vertex, dest);
                //recurse
                findCircuit(dest);

            }
        }



    }
        //next available edge to traverse too
    private boolean NextEdge(int s, int dest) {
        int count = 0;
        for (int vertex = 1; vertex <= V; vertex++)
        {
            if (!(adjacencyMatrix[s][vertex] == false))
            {
                count++;
            }
        }

        if (count == 1 )
        {
            return true;
        }

        int visited[] = new int[V+1];
        int count1 = DFSC(s, visited);

        removeEdge(s, dest);
        for (int vertex = 1; vertex <= V; vertex++)
        {
            visited[vertex] = 0;
        }

        int count2 = DFSC(s, visited);
        addEdge(s, dest);

        return (count1 > count2 ) ? false : true;


    }
//depth first recursive algorothm
    private int DFSC(int index, int[] visited) {
        visited[index] = 1;
        int count = 1;
        int destination = 1;

        while (destination <= V)
        {   //recurses to unvisited vertices
            if(!(adjacencyMatrix[index][destination] == false) && visited[destination] == 0)
            {
                count += DFSC(destination, visited);
            }
            destination++;
        }
        return count;

    }
    //check rows to ensure they are even

    private static boolean rowCheck(boolean[][] matrix,int p) {
        int count = 0;
        boolean testDegree = true;
        for (int i = 1; i <= p; i++) {
            for (int j = 1; j <= p; j++) {
                if(matrix[i][j]==true){
                    count++;
                }
            }
            if(count % 2 == 0){
                testDegree = true;
                count = 0;
            }else{
                testDegree = false;
                return testDegree;
            }


        }

        return testDegree;

    }
}