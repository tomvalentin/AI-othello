package Controller;

import Model.Move;

import java.util.ArrayList;

public class AIPlayer {
    private final static int DEPTH_LIMIT = 5;
    private GameController controller;
    private Move bestMove;
    private int nbrOfNodes;

    /**
     *
     * @param controller the GameController
     */
    public AIPlayer(GameController controller) {
        this.controller = controller;
    }

    /**
     * initiates the ai decision making process and calls the makeMove method in the controller when that is finished.
     */
    public void makeMove() {
        nbrOfNodes = 0;
        max(DEPTH_LIMIT, Integer.MIN_VALUE, Integer.MAX_VALUE);

        controller.makeMove(bestMove);
        System.out.println("AI has visited " + nbrOfNodes + " nodes");
        System.out.println("AI searched with depth " + DEPTH_LIMIT);
        System.out.println("AI made move: " + bestMove.getX() + ", " + bestMove.getY());

    }

    /**
     *
     *
     *
     * @param depth depth of the nodes still to be visited. Is decreased by one for each method invocation
     * @param alpha the current highest rating alpha rating
     * @param beta the current lowest beta rating
     * @return rating
     */
    private int max(int depth, int alpha, int beta) {
        nbrOfNodes++;

        if (depth == 0) {
            return controller.countNbrOfDiscs(0);
        }

        ArrayList<Move> availableMoves = controller.getAllAvailableMoves(0);

        for(Move move : availableMoves) {

            int[][] initialBoardState = controller.copyBoard();

            controller.makeMove(move);

            int rating = min(depth -1, alpha, beta);

            controller.pasteBoard(initialBoardState);

            if(rating > alpha) {
                alpha = rating;

                if(depth == DEPTH_LIMIT) {
                    bestMove = move;
                }

            }

            if(alpha >= beta) {
                return alpha;
            }

        }

        return alpha;

    }

    /**
     *
     * @param depth depth of the nodes still to be visited. Is decreased by one for each method invocation
     * @param alpha the current highest rating alpha rating
     * @param beta the current lowest beta rating
     * @return rating
     */
    private int min(int depth, int alpha, int beta) {
        nbrOfNodes++;

        if (depth == 0) {
            return controller.countNbrOfDiscs(0);
        }

        ArrayList<Move> availableMoves = controller.getAllAvailableMoves(1);

        for(Move move : availableMoves) {

            int[][] initialBoardState = controller.copyBoard();

            controller.makeMove(move);

            int rating = max(depth -1, alpha, beta);

            controller.pasteBoard(initialBoardState);

            if(rating < beta) {
                beta = rating;
            }

            if(alpha >= beta) {
                return beta;
            }

        }

        return beta;

    }


}
