package boardgame.model;

public enum BlueDirection implements Direction {

    UP_LEFT(-1, -1),
    UP(-1, 0),
    UP_RIGHT(-1, 1);

    private final int rowChange;
    private final int colChange;

    BlueDirection(int rowChange, int colChange) {
        this.rowChange = rowChange;
        this.colChange = colChange;
    }

    public int getRowChange() {
        return rowChange;
    }

    public int getColChange() {
        return colChange;
    }

    public static BlueDirection of(int rowChange, int colChange) {
        for (var direction : values()) {
            if (direction.rowChange == rowChange && direction.colChange == colChange) {
                return direction;
            }
        }
        throw new IllegalArgumentException();
    }
}
