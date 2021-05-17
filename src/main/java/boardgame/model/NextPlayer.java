package boardgame.model;

/**
 * This class represents the players.
 */
public enum NextPlayer {

    /**
     * The red player.
     */
    RED_PLAYER,

    /**
     * The blue player.
     */
    BLUE_PLAYER;

    /**
     * This method change the actual player to the other player.
     *
     * @return the next player
     */
    public NextPlayer alter() {
        return switch (this) {
            case RED_PLAYER -> BLUE_PLAYER;
            case BLUE_PLAYER -> RED_PLAYER;
        };
    }
}