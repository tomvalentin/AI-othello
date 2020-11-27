package Controller;

import java.util.ArrayList;

public class GameController {

    //States of the squares
    private final static int EMPTY = -1;
    private final static int WHITE = 0;
    private final static int BLACK = 1;

    //Directions {x,y}
    private final static int[] UP = {0,-1};
    private final static int[] UPRIGHT = {1,-1};
    private final static int[] RIGHT = {1,0};
    private final static int[] DOWNRIGHT = {1,1};
    private final static int[] DOWN = {0,1};
    private final static int[] DOWNLEFT = {-1,1};
    private final static int[] LEFT = {-1,0};
    private final static int[] UPLEFT = {-1,-1};

    private int[][] board = new int[8][8];

    private ArrayList<int[]> directions = new ArrayList<>();

    public GameController() {
      initialize();

    }

    public void initialize() {
        for(int i=0; i<board.length; i++) {
            for(int j=0; j<board[i].length; i++) {
                board[i][j]=EMPTY;

            }
        }

        board[3][3]=WHITE;
        board[3][4]=BLACK;
        board[4][3]=BLACK;
        board[4][4]=WHITE;

        directions.add(UP);
        directions.add(UPRIGHT);
        directions.add(RIGHT);
        directions.add(DOWNRIGHT);
        directions.add(DOWN);
        directions.add(DOWNLEFT);
        directions.add(LEFT);
        directions.add(UPLEFT);

    }

    public int countNbrOfDiscs(int player) {
        int count = 0;

        for(int i = 0; i<board.length; i++) {
            for(int j = 0; j<board.length; j++) {

                if(board[i][j] == player) {
                    count++;
                }
            }
        }

        return count;
    }

    public boolean checkValidMove(int player, int x, int y) {

      if(x > board.length || y > board.length) {
        return false;
      } else if(x < 0 || y < 0) {
        return false;
      } else if(board[x][y] != EMPTY) {
        return false;
      } else if(board[x][y] == getOpposite(player)) {
        return false;
      }

      return checkIfAnyDirectionIsValid(player, x, y);

    }

    private boolean checkIfAnyDirectionIsValid(int player, int x, int y) {

      for(int[] direction : directions) {
        if(checkIfDirectionIsValid(player, x, y, direction)) {
          return true;
        }
      }

      return false;

    }

    //checks if the current player can flip discs in a certain direction
    private boolean checkIfDirectionIsValid(int player, int positionX, int positionY, int[] direction) {

      boolean hasFoundOpposite = false;
      int x = positionX, y = positionY;

      for(int i = 0; i<board.length; i++) {

        x += direction[0];
        y += direction[1];

        if(x > board.length || y > board.length) {
          return false;
        } else if(x < 0 || y < 0) {
          return false;
        } else if(board[x][y] == EMPTY) {
          return false;
        }


        if(board[x][y] == getOpposite(player)){
          hasFoundOpposite = true;

        } else if(board[x][y] == player) {

          if(hasFoundOpposite) {
            makeMoves(player, positionX, positionY, direction);
            return true;

          } else {
            return false;
          }

        }

      }

      return false;
    }

    //Flips discs on the board
    public void makeMoves(int player, int x, int y, int[] direction) {

        while(board[x][x] == getOpposite(player)) {
          board[x][y] = player;

          x += direction[0];
          y += direction[1];

        }
    }

    //returns list of all moves available to the player
    public ArrayList<int[]> getAllAvailableMoves(int player) {
      ArrayList<int[]> availableMoves = new ArrayList<>();

      for(int i = 0; i < board.length; i++) {
        for(int j = 0; j < board[i].length; j++) {
          if(board[i][j] != player) {
            continue;
          }

          if(checkIfAnyDirectionIsValid(player, i, j)) {
              int[] validDirection = {i, j};
              availableMoves.add(validDirection);
          }
        }
      }

      return availableMoves;
    }

    private int getOpposite(int player) {

      if(player == WHITE) {
        return BLACK;
      } else {
        return WHITE;
      }

    }
}
