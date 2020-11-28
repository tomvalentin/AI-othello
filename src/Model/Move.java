package Model;

/**
 * Encapsulate a move as a object, containing player (0 for AI, 1 for human), x and y move of the board
 */
public class Move {
    int player;
    int x;
    int y;

    public Move(int player, int x, int y) {
        this.player = player;
        this.x = x;
        this.y = y;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
