package View;

import java.util.Scanner;

public class UIController {
    Scanner reader = new Scanner(System.in);
    private int[][] board;

    /**
     * Takes an 2D array as input for the gameboard
     * @param board
     */
    public UIController(int[][] board){
        this.board = board;
        printBoard();
        gameThread();
    }

    public int[] promptPlayer(){
        System.out.println("Make a move, COLUMN (0-7): ");
        int column = reader.nextInt();
        System.out.println("Make a move, ROW (0-7): ");
        int row = reader.nextInt();
        return new int[]{row, column};
    }

    public void gameThread(){
        while(true){
            promptPlayer(); //prompt human
            printBoard();

            //prompt AI
//            System.out.println("Player 1 Make a move, COLUMN (0-7): ");
//            column = reader.nextInt();
//            System.out.println("Player 1 Make a move, ROW (0-7): ");
//            row = reader.nextInt();
//            updateBoard(1, new int[]{row, column});
//            printBoard();
        }
    }

    public void printBoard() {
        String res = "  0   1   2   3   4   5   6   7";
        for(int i = 0; i < board.length; i++){
            res+="\n|";
            for(int j = 0; j < board[i].length; j++){
                int temp = board[i][j];
                if(temp == 1)
                    res+=" O |";
                else if(temp == 0)
                    res+=" X |";
                else
                    res+="   |";
            }
            res+="   "+i;
        }
        System.out.println(res);
    }

    public static void main(String[] args) {
        int[][] test = new int[8][8];
        for(int i = 0; i < test.length; i++){
            for (int j = 0; j < test[i].length; j++){
                test[i][j] = -1;
            }
        }
        UIController c = new UIController(test);
//        c.updateBoard(0, new int[]{4,2});
//        c.updateBoard(1, new int[]{4,3});
//        c.updateBoard(0, new int[]{5,2});
//        c.printBoard();

    }
}