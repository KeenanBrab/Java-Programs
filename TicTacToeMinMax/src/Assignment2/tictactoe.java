package Assignment2;
import java.util.Scanner;

/**Keenan Brab 5862990 Assignment 2
 * intelij
 * program that will play an “optimal” game of tic-tac-toe against a human user.
 * I build the game tree and was able to use post order and preorder although the computer does not always make optimal move
 * My recursive build tree, prune, and Wl values work although My sorting algorthm may have caused issues
 * to View the game tree through PostOrder uncomment at line 222
 */
public class tictactoe {

    private TreeNode rootOfgame;
    private int globW;
    private char updateO[][];


    public static void main(String[] args) {

        new tictactoe();
    }

    public tictactoe() {
        int level = 0;
        boolean winstate = false;
        char gameempty[][] = {{' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '}};
        char empty[][] = {{' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '}};
        rootOfgame = new TreeNode(empty);
        rootOfgame.l = 0;
        rootOfgame.w = 0;

        buildTree(rootOfgame, 'X');
        pruneTree(rootOfgame, 'O');
        postOrder(rootOfgame, level);
        System.out.println(globW);
        postOrderSort(rootOfgame, level);
        Scanner keyboard = new Scanner(System.in);
        while (!winstate){
            System.out.println("0-8");
            int myint = keyboard.nextInt();

            if(countblk(gameempty) == 0 ){
                System.out.println("Game Over");
                winstate = true;
            }else {
                switch (myint) {
                    case 0: if(gameempty[0][0] ==' '){ gameempty[0][0] = 'X';}
                        break;
                    case 1:  if(gameempty[0][1] ==' '){ gameempty[0][1] = 'X';}
                        break;
                    case 2: if (gameempty[0][2] ==' '){ gameempty[0][2] = 'X';}
                        break;
                    case 3: if (gameempty[1][0] ==' '){ gameempty[1][0] = 'X';}
                        break;
                    case 4: if (gameempty[1][1] ==' '){ gameempty[1][1] = 'X';}
                        break;
                    case 5: if (gameempty[1][2] ==' '){ gameempty[1][2] = 'X';}
                        break;
                    case 6: if (gameempty[2][0] ==' '){ gameempty[2][0] = 'X';}
                        break;
                    case 7: if (gameempty[2][1] ==' '){ gameempty[2][1] = 'X';}
                        break;
                    case 8: if (gameempty[2][2] ==' '){ gameempty[2][2] = 'X';}
                        break;

            }
            printboard(gameempty);
            winstate = check(gameempty);
            oMove(rootOfgame,gameempty);
            gameempty = updateO;
            printboard(gameempty);
            winstate = check(gameempty);

            }

        }

    }
    //sets the game board to the O move, attempts to utilize the game board tree to find best move

    private void oMove(TreeNode currentNode, char [][] game) {

        if (currentNode != null) {

            if( java.util.Arrays.deepEquals(
                    currentNode.array,
                    game
            )){

                updateO = currentNode.firstChild.array;
            }


            oMove(currentNode.firstChild, game);
            oMove(currentNode.nextSibling, game);


        }

    }
        //fheck game state during game
    private boolean check(char[][] array) {

        if ((array[0][0] == 'X' && array[0][1] == 'X' && array[0][2] == 'X') ||
                (array[1][0] == 'X' && array[1][1] == 'X' && array[1][2] == 'X') ||
                (array[2][0] == 'X' && array[2][1] == 'X' && array[2][2] == 'X') ||
                (array[0][0] == 'X' && array[1][0] == 'X' && array[2][0] == 'X') ||
                (array[0][1] == 'X' && array[1][1] == 'X' && array[2][1] == 'X') ||
                (array[0][2] == 'X' && array[1][2] == 'X' && array[2][2] == 'X') ||
                (array[0][0] == 'X' && array[1][1] == 'X' && array[2][2] == 'X') ||
                (array[2][0] == 'X' && array[1][1] == 'X' && array[0][2] == 'X')) {
                return true;
        }
        if ((array[0][0] == 'O' && array[0][1] == 'O' && array[0][2] == 'O') ||
                (array[1][0] == 'O' && array[1][1] == 'O' && array[1][2] == 'O') ||
                (array[2][0] == 'O' && array[2][1] == 'O' && array[2][2] == 'O') ||
                (array[0][0] == 'O' && array[1][0] == 'O' && array[2][0] == 'O') ||
                (array[0][1] == 'O' && array[1][1] == 'O' && array[2][1] == 'O') ||
                (array[0][2] == 'O' && array[1][2] == 'O' && array[2][2] == 'O') ||
                (array[0][0] == 'O' && array[1][1] == 'O' && array[2][2] == 'O') ||
                (array[2][0] == 'O' && array[1][1] == 'O' && array[0][2] == 'O')) {
            return true;
        }

        return false;
    }
        //print board function
    private void printboard(char[][] read){

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++) {
                System.out.print(read[i][j]);
                System.out.print(" ");



            }
            System.out.println();
        }



    }
    //post order sort
    //sorts children of node
    private void postOrderSort(TreeNode currentNode, int level){
        int count;
        if (currentNode != null) {
            postOrder(currentNode.firstChild, level);

            postOrder(currentNode.nextSibling, level);
            if (currentNode.firstChild != null) {
                count = countblk(currentNode.firstChild.array);

                sort(currentNode.firstChild, count);

            }
        }

    }
//counts blank lines
    private int countblk(char[][] array) {
    int count = 0;
            for (int i = 0; i < 3; i++){
                for (int j = 0; j < 3; j++){
                   if( array[i][j] == ' '){

                       count++;
                   }



                }
        }

    return count;
    }
        //sort algorithm to make w to far left and l to far right decreasing order
    private  void sort(TreeNode cNode, int count){

            int tempw = 0;
            int templ = 0;
            char[][] temparray;
                    


                for ( int j = 0; j < count; j++){
                for ( int i = 0; i < count; i++) {
                    if (cNode.nextSibling != null) {
                        if (cNode.w - cNode.l < cNode.nextSibling.w - cNode.nextSibling.l) {
                            //instead of manipulating pointers just move data inside node around
                            tempw = cNode.w;
                            templ = cNode.l;
                            temparray = cNode.array;

                            cNode.w = cNode.nextSibling.w;
                            cNode.l = cNode.nextSibling.l;
                            cNode.array = cNode.nextSibling.array;

                            cNode.nextSibling.w = tempw;
                            cNode.nextSibling.l = templ;
                            cNode.nextSibling.array = temparray;

                            cNode = cNode.nextSibling;
                        }
                    }

                    }
                }

    }
    //this post order traversal calculates the w and l values for each node
    private void postOrder(TreeNode currentNode,int level){


        if (currentNode != null) {

            postOrder(currentNode.firstChild, level);

            postOrder(currentNode.nextSibling, level);
            wl(currentNode);
            //to post order print entire tree un comment
            //displayChild(currentNode, level);

        }


    }
    //sum of w
    private int sumOfWchildren(TreeNode cNode){
        int tempw = 0;

        while(cNode != null){

            tempw = tempw + cNode.w;
            cNode = cNode.nextSibling;

        }


        return tempw;
    }
        //sum of l
    private int sumOfLchildren(TreeNode cNode){
        int templ = 0;

        while(cNode != null){

            templ = templ + cNode.l;
           cNode = cNode.nextSibling;

        }



        return templ;
    }
    //find win loss values for each child then adds the sum of w and l to parent
    private void wl(TreeNode currentNode){
        int tempw = 0;
        int templ = 0;
        if(currentNode.firstChild != null){

            tempw =  sumOfWchildren(currentNode.firstChild);
            templ =  sumOfLchildren(currentNode.firstChild);
            currentNode.w = currentNode.w + tempw;
            currentNode.l = currentNode.l + templ;

        }
        if ((currentNode.array[0][0] == 'X' && currentNode.array[0][1] == 'X' && currentNode.array[0][2] == 'X') ||
                (currentNode.array[1][0] == 'X' && currentNode.array[1][1] == 'X' && currentNode.array[1][2] == 'X') ||
                (currentNode.array[2][0] == 'X' && currentNode.array[2][1] == 'X' && currentNode.array[2][2] == 'X') ||
                (currentNode.array[0][0] == 'X' && currentNode.array[1][0] == 'X' && currentNode.array[2][0] == 'X') ||
                (currentNode.array[0][1] == 'X' && currentNode.array[1][1] == 'X' && currentNode.array[2][1] == 'X') ||
                (currentNode.array[0][2] == 'X' && currentNode.array[1][2] == 'X' && currentNode.array[2][2] == 'X') ||
                (currentNode.array[0][0] == 'X' && currentNode.array[1][1] == 'X' && currentNode.array[2][2] == 'X') ||
                (currentNode.array[2][0] == 'X' && currentNode.array[1][1] == 'X' && currentNode.array[0][2] == 'X')) {
            currentNode.l = currentNode.l + 1 ;
            globW++;

        }
        if ((currentNode.array[0][0] == 'O' && currentNode.array[0][1] == 'O' && currentNode.array[0][2] == 'O') ||
                (currentNode.array[1][0] == 'O' && currentNode.array[1][1] == 'O' && currentNode.array[1][2] == 'O') ||
                (currentNode.array[2][0] == 'O' && currentNode.array[2][1] == 'X' && currentNode.array[2][2] == 'O') ||
                (currentNode.array[0][0] == 'O' && currentNode.array[1][0] == 'X' && currentNode.array[2][0] == 'O') ||
                (currentNode.array[0][1] == 'O' && currentNode.array[1][1] == 'X' && currentNode.array[2][1] == 'O') ||
                (currentNode.array[0][2] == 'O' && currentNode.array[1][2] == 'X' && currentNode.array[2][2] == 'O') ||
                (currentNode.array[0][0] == 'O' && currentNode.array[1][1] == 'X' && currentNode.array[2][2] == 'O') ||
                (currentNode.array[2][0] == 'O' && currentNode.array[1][1] == 'X' && currentNode.array[0][2] == 'O')) {
            currentNode.w = currentNode.w + 1 ;
            globW++;
        }

    }

    //preorder recursive function to print tree
    private void buildTree(TreeNode currentNode, char move) {
        char nextmove;

        if (move == 'X') {
            nextmove = 'O';
        } else {

            nextmove = 'X';
        }
        if (currentNode != null) {
            buildChildren(currentNode, move);
            buildTree(currentNode.firstChild, nextmove);
            buildTree(currentNode.nextSibling, move);
        }

    }
    //un comment this out to post order print tree
    private void displayChild(TreeNode cnode, int level) {


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(cnode.array[i][j]);
                System.out.print(" ");


            }
            System.out.println();
        }
        System.out.println("w " + cnode.w);
        System.out.println("l "+ cnode.l);
        System.out.println();


    }

        //prune tree function uses preorder recursion to find value
    private void pruneTree(TreeNode currentNode, char move) {
        char nextmove;

        if (move == 'X') {
            nextmove = 'O';
        } else {

            nextmove = 'X';
        }
        if (currentNode != null) {
            if (checkForwin(currentNode, move)) {
                //once value is found destroy sub tree
                currentNode.firstChild = null;
            }
            pruneTree(currentNode.firstChild, nextmove);

            pruneTree(currentNode.nextSibling, move);
        }


    }
    //returns true if win
    private boolean checkForwin(TreeNode cnode, char move) {
        if ((cnode.array[0][0] == move && cnode.array[0][1] == move && cnode.array[0][2] == move) ||
                (cnode.array[1][0] == move && cnode.array[1][1] == move && cnode.array[1][2] == move) ||
                (cnode.array[2][0] == move && cnode.array[2][1] == move && cnode.array[2][2] == move) ||
                (cnode.array[0][0] == move && cnode.array[1][0] == move && cnode.array[2][0] == move) ||
                (cnode.array[0][1] == move && cnode.array[1][1] == move && cnode.array[2][1] == move) ||
                (cnode.array[0][2] == move && cnode.array[1][2] == move && cnode.array[2][2] == move) ||
                (cnode.array[0][0] == move && cnode.array[1][1] == move && cnode.array[2][2] == move) ||
                (cnode.array[2][0] == move && cnode.array[1][1] == move && cnode.array[0][2] == move)) {
            return true;
        } else {
            return false;
        }
    }
        //builds all children of game board
    private void buildChildren(TreeNode parent, char move) {


        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                    //enters value in empty space
                if (parent.array[x][y] == ' ') {
                    parent.array[x][y] = move;

                    char newMove[][] = new char[3][3];

                    for (int j = 0; j < 3; j++) {
                        for (int k = 0; k < 3; k++) {

                            newMove[j][k] = parent.array[j][k];

                        }
                    }

                    parent.array[x][y] = ' ';
                    insertChild(newMove, parent);
                }

            }
        }
    }
        //inserts a Child into the tree
    private void insertChild(char[][] move, TreeNode parent) {
        TreeNode temp = new TreeNode(move);
        temp.l = 0;
        temp.w = 0;
        //makes sure new insert is the new child of parent
        temp.nextSibling = parent.firstChild;

        parent.firstChild = temp;
    }
}
    
        
    
    
    