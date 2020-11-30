package View;

import Model.Move;

import java.util.Scanner;

public class UIController {
    Scanner reader = new Scanner(System.in);

    /**
     * Takes an 2D array as input for the gameboard
     */
    public UIController(){
        //this.board = board;
        //printBoard();
    }

    public Move promptPlayer(){
        System.out.println("Make a move, COLUMN (0-7): ");
        int column = reader.nextInt();
        System.out.println("Make a move, ROW (0-7): ");
        int row = reader.nextInt();
        return new Move(1, column, row);
    }

    public void printBoard(int[][] board) {
        String res = "--------------------------------------------" +
                "\n  X = AI         0 = Human" +
                "\n  0   1   2   3   4   5   6   7";
        for(int i = 0; i < board.length; i++){
            res+="\n|";
            for(int j = 0; j < board[i].length; j++){
                int temp = board[j][i];                 //---> only works with [j][i]... should be [i][j]?? <---
                if(temp == 1)
                    res+=" O |";
                else if(temp == 0)
                    res+=" X |";
                else
                    res+="   |";
            }
            res+="   "+i;
        }
        res+="\n--------------------------------------------";
        System.out.println(res);
    }

}
