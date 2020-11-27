package Controller;

import java.util.ArrayList;

public class GameController {

    //States of the squares
    private final static int EMPTY = -1;
    private final static int WHITE = 0;
    private final static int BLACK = 1;

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
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; i++) {
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
     * @param player current player
     * @param x      x position of the new disc
     * @param y      y position of the new disc
     * @return true if move is valid
     */
    public boolean makeMove(int player, int x, int y) {

        if (x > board.length || y > board.length) {
            return false;
        } else if (x < 0 || y < 0) {
            return false;
        } else if (board[x][y] != EMPTY) {
            return false;
        } else if (board[x][y] == getOpposite(player)) {
            return false;
        }

        return checkIfAnyDirectionIsValid(player, x, y);

    }

    /**
     * Checks if any direction to flip discs is valid
     *
     * @param player current player
     * @param x      x position of the new disc
     * @param y      y position of the new disc
     * @return true if the player can flip discs in at least one direction
     */
    private boolean checkIfAnyDirectionIsValid(int player, int x, int y) {
        boolean hasFoundValidDirection = false;

        for (int[] direction : directions) {
            if (checkIfDirectionIsValid(player, x, y, direction)) {
                hasFoundValidDirection = true;
            }
        }

        return hasFoundValidDirection;

    }

    /**
     * checks if the current player can flip discs in a certain direction
     *
     * @param player    current player
     * @param x         x position of the new disc
     * @param y         y position of the new disc
     * @param direction an array {x,y} where x,y is a number between -1 and 1 indicating the direction in x,y axis respectively
     * @return true if its possible to flip discs in this direction
     */
    private boolean checkIfDirectionIsValid(int player, int x, int y, int[] direction) {

        boolean hasFoundOpposite = false;
        int nextX = x, nextY = y;

        for (int i = 0; i < board.length; i++) {

            nextX += direction[0];
            nextY += direction[1];

            if (nextX > board.length || nextY > board.length) {
                return false;
            } else if (nextX < 0 || nextY < 0) {
                return false;
            } else if (board[nextX][nextY] == EMPTY) {
                return false;
            }


            if (board[nextX][nextY] == getOpposite(player)) {
                hasFoundOpposite = true;

            } else if (board[nextX][nextY] == player) {

                if (hasFoundOpposite) {
                    flipDiscs(player, x, y, direction);     //Probably needs to be moved somehow when AI is implemented
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
     *
     * @param player    current player
     * @param x         x position of the new disc
     * @param y         y position of the new disc
     * @param direction an array {x,y} where x,y is a number between -1 and 1 indicating the direction in x,y axis respectively
     */
    public void flipDiscs(int player, int x, int y, int[] direction) {
        int newX = x, newY = y;

        while (board[newX][newY] == getOpposite(player)) {
            board[newX][newY] = player;

            newX += direction[0];
            newY += direction[1];

        }
    }

    /**
     *
     * ---needs to be changed---
     *
     * returns list of all moves available to the player based on a new disc position
     * intended to be used at some point in the AI decision process
     *
     * @param player
     * @return
     */
    public ArrayList<int[]> getAllAvailableMoves(int player) {
        ArrayList<int[]> availableMoves = new ArrayList<>();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != player) {
                    continue;
                }

                if (checkIfAnyDirectionIsValid(player, i, j)) {
                    int[] validMove = {i, j};
                    availableMoves.add(validMove);
                }
            }
        }

        return availableMoves;
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
}
