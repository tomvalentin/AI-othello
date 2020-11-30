package Controller;

import Model.Move;
import View.UIController;

import java.util.ArrayList;

public class GameController {
    UIController controller;
    AIPlayer ai;

    //States of the squares
    private final static int EMPTY = -1;
    private final static int WHITE = 0; //AI
    private final static int BLACK = 1; //HUMAN

    //Directions {x,y}
    private final static int[] UP = {0, -1};
    private final static int[] UPRIGHT = {1, -1};
    private final static int[] RIGHT = {1, 0};
    private final static int[] DOWNRIGHT = {1, 1};
    private final static int[] DOWN = {0, 1};
    private final static int[] DOWNLEFT = {-1, 1};
    private final static int[] LEFT = {-1, 0};
    private final static int[] UPLEFT = {-1, -1};

    private int[][] board = new int[8][8];

    private ArrayList<int[]> directions = new ArrayList<>();

    public GameController() {
        initialize();

    }

    /**
     * Initializes game and sets starting condition of the board
     */
    public void initialize() {
        controller = new UIController();
        ai = new AIPlayer(this);

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = EMPTY;

            }
        }

        board[3][3] = WHITE;
        board[3][4] = BLACK;
        board[4][3] = BLACK;
        board[4][4] = WHITE;

        directions.add(UP);
        directions.add(UPRIGHT);
        directions.add(RIGHT);
        directions.add(DOWNRIGHT);
        directions.add(DOWN);
        directions.add(DOWNLEFT);
        directions.add(LEFT);
        directions.add(UPLEFT);

        gameThread();

    }

    public void gameThread(){
        boolean validMove;
        while(true){
            //Human player
            validMove = false;
            while(validMove == false) { //prompt human and checks if move is valid
                controller.printBoard(board);
                Move humanMove = controller.promptPlayer();
                if (makeMove(humanMove)){
                    validMove = true;
                }else{
                    System.out.println("Move cannot be done, try again");
                }

            }

            controller.printBoard(board);


            //AI
            ai.makeMove();

        }
    }

    /**
     * Method will be used by AI to evaluate the state of the board
     *
     * @param player current player
     * @return number of discs on the board with the players color
     */
    public int countNbrOfDiscs(int player) {
        int count = 0;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {

                if (board[i][j] == player) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * Checks if a move is valid or not
     *
     * @param move current player and x,y position of the new disc
     * @return true if move is valid
     */
    public boolean makeMove(Move move) {

        if (move.getX() > board.length || move.getY() > board.length) {
            System.out.println("Outside board");
            return false;
        } else if (move.getX() < 0 || move.getY() < 0) {
            System.out.println("Outside board");
            return false;
        } else if (board[move.getX()][move.getY()] != EMPTY) {
            return false;
        }

        return checkIfAnyDirectionIsValid(move, true);

    }

    /**
     * Checks if any direction to flip discs is valid
     *
     * @param makeMove boolean indicating whether to carry out the move or not
     * @param move current player and x,y position of the new disc
     * @return true if the player can flip discs in at least one direction
     */
    private boolean checkIfAnyDirectionIsValid(Move move, boolean makeMove) {
        //System.out.println("Has called checkIfAnyDirectionIsValid with X: " + move.getX() + " Y: " + move.getY());
        boolean hasFoundValidDirection = false;
        for (int[] direction : directions) {
            if (checkIfDirectionIsValid(move, direction, makeMove)) {
                hasFoundValidDirection = true;
            }
        }

        return hasFoundValidDirection;

    }

    /**
     * checks if the current player can flip discs in a certain direction
     *
     *
     * @param move current player and x,y position of the new disc
     * @param direction an array {x,y} where x,y is a number between -1 and 1 indicating the direction in x,y axis respectively
     * @param makeMove boolean indicating whether to carry out the move or not
     * @return true if its possible to flip discs in this direction
     */
    private boolean checkIfDirectionIsValid(Move move, int[] direction, boolean makeMove) {
        //System.out.println("Has called checkIfDirectionIsValid with X: " + move.getX() + " Y: " + move.getY());

        boolean hasFoundOpposite = false;
        int nextX = move.getX(), nextY = move.getY();

        for (int i = 0; i < board.length; i++) {

            nextX += direction[0];
            nextY += direction[1];

            if (nextX >= board.length || nextY >= board.length) {
                return false;
            } else if (nextX < 0 || nextY < 0) {
                return false;
            } else if (board[nextX][nextY] == EMPTY) {
                return false;
            }


            if (board[nextX][nextY] == getOpposite(move.getPlayer())) {
                hasFoundOpposite = true;

            } else if (board[nextX][nextY] == move.getPlayer()) {

                if (hasFoundOpposite) {
                    if(makeMove) {
                        flipDiscs(move, direction);     //Probably needs to be moved somehow when AI is implemented
                    }

                    return true;

                } else {
                    return false;
                }

            }

        }

        return false;
    }

    /**
     * Flips discs on the board in a given direction
     * @param move current player and x,y position of the new disc
     * @param direction an array {x,y} where x,y is a number between -1 and 1 indicating the direction in x,y axis respectively
     */
    public void flipDiscs(Move move, int[] direction) {
        //System.out.println("Has called flipDiscs with X: " + move.getX() + " Y: " + move.getY() + " Direction: " + direction[0] +  ", " + direction[1]);

        int newX = move.getX(), newY = move.getY();
        board[newX][newY] = move.getPlayer();

        newX += direction[0];
        newY += direction[1];

        while (board[newX][newY] == getOpposite(move.getPlayer())) {
            board[newX][newY] = move.getPlayer();

            //System.out.println("Flipped disc on row: " + newX + " column: " + newY);

            newX += direction[0];
            newY += direction[1];

        }
    }

    /**
     *
     * returns list of all moves available to the player based on a new disc position
     * intended to be used at some point in the AI decision process
     *
     * @param player
     * @return arrayList containing all moves available to the specific player
     */
    public ArrayList<Move> getAllAvailableMoves(int player) {
        ArrayList<Move> availableMoves = new ArrayList<>();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != EMPTY) {
                    continue;
                }

                Move tempMove = new Move(player, i, j);

                if (checkIfAnyDirectionIsValid(tempMove, false)) {
                    availableMoves.add(tempMove);
                }
            }
        }

        return availableMoves;
    }

    /**
     *
     * @return a copy of the board
     */
    public int[][] copyBoard() {
        int[][] boardCopy = new int[8][8];

        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length; j++) {
                boardCopy[i][j] = board[i][j];
            }
        }

        return boardCopy;
    }

    /**
     *
     * @param boardCopy the board that will overwrite the current board
     */
    public void pasteBoard(int[][] boardCopy) {
        for(int i = 0; i < boardCopy.length; i++) {
            for(int j = 0; j < boardCopy.length; j++) {
                board[i][j] = boardCopy[i][j];
            }
        }
    }

    /**
     * @param player current player
     * @return opposite players colorcode
     */
    private int getOpposite(int player) {

        if (player == WHITE) {
            return BLACK;
        } else {
            return WHITE;
        }

    }

    public static void main(String[] args) {
        GameController gc = new GameController();
        gc.initialize();
    }
}
