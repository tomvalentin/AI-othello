package Controller;

import Model.Move;

import java.util.ArrayList;

public class AIPlayer {
    private final static int DEPTH_LIMIT = 5;
    private GameController controller;
    private Move bestMove;

    public AIPlayer(GameController controller) {
        this.controller = controller;
    }

    public void makeMove() {
        maximizer(DEPTH_LIMIT, Integer.MIN_VALUE, Integer.MAX_VALUE);

        controller.makeMove(bestMove);
    }

    private int maximizer(int depth, int alpha, int beta) {

        if (depth == 0) {
            return controller.countNbrOfDiscs(0);
        }

        ArrayList<Move> availableMoves = controller.getAllAvailableMoves(0);

        for(Move move : availableMoves) {

            int[][] initialBoardState = controller.copyBoard();

            controller.makeMove(move); //needs to made in temporary board or needs to be unmade

            int rating = minimizer(depth -1, alpha, beta);

            controller.setBoard(initialBoardState);

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

    private int minimizer(int depth, int alpha, int beta) {

        if (depth == 0) {
            return controller.countNbrOfDiscs(0);
        }

        ArrayList<Move> availableMoves = controller.getAllAvailableMoves(1);

        for(Move move : availableMoves) {

            int[][] initialBoardState = controller.copyBoard();

            controller.makeMove(move); //needs to made in temporary board or needs to be unmade

            int rating = maximizer(depth -1, alpha, beta);

            controller.setBoard(initialBoardState);

            if(rating <= beta) {
                beta = rating;
            }

            if(alpha >= beta) {
                return beta;
            }

        }

        return beta;

    }


}
