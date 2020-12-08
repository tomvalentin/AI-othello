package View;

import Model.Move;

import java.util.Scanner;

public class UIController {
    Scanner reader = new Scanner(System.in);

    public Move promptPlayer(){
        System.out.println("Make a move, COLUMN (0-7): ");
        int column = reader.nextInt();
        System.out.println("Make a move, ROW (0-7): ");
        int row = reader.nextInt();
        return new Move(1, column, row);
    }

    public void printGameBoard(int[][] board, int aiScore, int humanScore) {
        String res = "--------------------------------------------" +
                "\n*                                           |" +
                "\n*  X = AI         0 = Human                 |" +
                "\n*  Discs: "+aiScore+"        Discs: "+humanScore+"                 |"+
                " \n*                                           |" +
                "\n  0   1   2   3   4   5   6   7             |";
        for(int i = 0; i < board.length; i++){
            res+="\n|";
            for(int j = 0; j < board[i].length; j++){
                int temp = board[j][i];
                if(temp == 1)
                    res+=" O |";
                else if(temp == 0)
                    res+=" X |";
                else
                    res+="   |";
            }
            res+="   "+i+"       |";
        }
        res+="\n--------------------------------------------";
        System.out.println(res);
    }

    public void printGameAlert(String message){
        String res =
                "************ GAME ALERT ************\n" +
                        "       " + message +"\n" +
                        "************************************";
    }

    public void printCasualGameAlert(String message){
        System.out.println("* INFO: "+message);
    }

    public void printGameOver(String player){
        System.out.println(
                "************** GAME OVER **************\n" +
                        "           "+player+" player won! \n" +
                        "***************************************"

        );
    }

}
