/**Keenan Brab 5862990 Assignment 2
 * intelij
 * program that will play an “optimal” game of tic-tac-toe against a human user.
 * I build the game tree and was able to use post order and preorder although the computer does not always make optimal move
 * My recursive build tree, prune, and Wl values work although My sorting algorthm may have caused issues
 * to View the game tree through PostOrder uncomment at line 222
 */
package Assignment2;


public class TreeNode {
    int w;
    int l;

    TreeNode firstChild;
    TreeNode nextSibling;
    char[][] array;

    public TreeNode(char[][] arr) {
        array = arr;
        w = 0;
        l = 0;
        firstChild = null;
        nextSibling = null;
    }
}
    