package boardgame.model;

public enum NextPlayer {
    RED_PLAYER,
    BLUE_PLAYER;

    public NextPlayer alter() {
        return switch (this) {
            case RED_PLAYER -> BLUE_PLAYER;
            case BLUE_PLAYER -> RED_PLAYER;
        };
    }
}