package boardgame.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.*;

/**
 * Represents the rules of the abstract game.
 */
public class BoardGameModel {

    /**
     * The width of the board.
     */
    public static int BOARD_WIDTH = 7;

    /**
     * The height of the board.
     */
    public static int BOARD_HEIGHT = 6;

    private final ArrayList<Piece> pieces;
    private final ArrayList<Position> blocks;
    private final HashSet<Position> redPiecesPosition = new HashSet<>();
    private final HashSet<Position> bluePiecesPosition = new HashSet<>();

    /**
     * Represents who is the next player.
     */
    public ObjectProperty<NextPlayer> nextPlayer;

    /**
     * Creates a {@code BoardGameModel} object which represents the initial state of the original game.
     */
    public BoardGameModel() {
        this(NextPlayer.RED_PLAYER, new ArrayList<Position>(Arrays.asList(new Position(2, 4), new Position(3, 2))),
                new Piece(PieceColor.RED, new Position(0, 0)),
                new Piece(PieceColor.RED, new Position(0, 1)),
                new Piece(PieceColor.RED, new Position(0, 2)),
                new Piece(PieceColor.RED, new Position(0, 3)),
                new Piece(PieceColor.RED, new Position(0, 4)),
                new Piece(PieceColor.RED, new Position(0, 5)),
                new Piece(PieceColor.RED, new Position(0, 6)),
                new Piece(PieceColor.BLUE, new Position(5, 0)),
                new Piece(PieceColor.BLUE, new Position(5, 1)),
                new Piece(PieceColor.BLUE, new Position(5, 2)),
                new Piece(PieceColor.BLUE, new Position(5, 3)),
                new Piece(PieceColor.BLUE, new Position(5, 4)),
                new Piece(PieceColor.BLUE, new Position(5, 5)),
                new Piece(PieceColor.BLUE, new Position(5, 6)));
    }

    /**
     * Creates a {@code BoardGameModel} object initialzing the next player the position of the blocks and the position of the pieces with the parameters specified.
     * The constructor expects a {@code NextPlayer} object an {@code ArrayList} of {@code Position} objects and an array of {@code Piece} objects.
     *
     * @param startPlayer the initial starter player of game
     * @param blocks the initial positions of the blocks
     * @param pieces the initial pieces in the gametable
     */
    public BoardGameModel(NextPlayer startPlayer, ArrayList<Position> blocks, Piece... pieces) {
        checkItems(pieces, blocks);
        this.pieces = new ArrayList<Piece>(Arrays.asList(pieces));
        this.blocks = blocks;
        for (var piece : pieces) {
            if (piece.getType() == PieceColor.RED) {
                redPiecesPosition.add(piece.getPosition());
            } else {
                bluePiecesPosition.add(piece.getPosition());
            }
        }
        this.nextPlayer = new SimpleObjectProperty<>(startPlayer);
    }

    private void checkItems(Piece[] pieces, ArrayList<Position> blocks) {
        var seen = new HashSet<Position>();
        for (var piece : pieces) {
            if (!isOnBoard(piece.getPosition()) || seen.contains(piece.getPosition())) {
                throw new IllegalArgumentException();
            }
            seen.add(piece.getPosition());
        }
        for (var block : blocks) {
            if (!isOnBoard(block) || seen.contains(block)) {
                throw new IllegalArgumentException();
            }
            seen.add(block);
        }
    }

    private static boolean isOnBoard(Position position) {
        return 0 <= position.row() && position.row() < BOARD_HEIGHT
                && 0 <= position.col() && position.col() < BOARD_WIDTH;
    }

    /**
     * It counts the pieces  number in the table.
     *
     * @return the number of the pieces
     */
    public int getPieceCount() {
        return pieces.size();
    }

    /**
     * It counts the blocks number in the table.
     *
     * @return the number of blocks
     */
    public int getBlockCount() {
        return blocks.size();
    }

    /**
     * This represents the nex player of the game.
     * It return an {@code ObjectProperty} of {@code NextPlayer}.
     *
     * @return the {@code nextPlayer} property
     */
    public ObjectProperty<NextPlayer> nextPlayerProperty() {
        return nextPlayer;
    }

    /**
     * It represents the type of the given {@code Piece}.
     *
     * @param pieceNumber the number of the actual {@code Piece} in the array of pieces
     * @return a {@code PieceColor} object which represent the color of the actual piece
     */
    public PieceColor getPieceType(int pieceNumber) {
        return pieces.get(pieceNumber).getType();
    }

    /**
     * It represents the position of the given {@code Piece}.
     *
     * @param pieceNumber the number of the actual {@code Piece} in the array of pieces
     * @return a {@code Position} object which represent the position of the actual piece
     */
    public Position getPiecePosition(int pieceNumber) {
        return pieces.get(pieceNumber).getPosition();
    }

    /**
     * It represents the position of the given block.
     *
     * @param blockNumber the number of the actual block in the array of blocks
     * @return a {@code Position} object which represent the position of the actual block
     */
    public Position getBlockPosition(int blockNumber) {
        return blocks.get(blockNumber);
    }

    /**
     * It gives the {@code ObjectProperty} of {@code Position} for the actual {@code Piece}.
     *
     * @param pieceNumber the number of the actual piece in the array of pieces
     * @return a Property of {@code Position} object which represent the position of the actual piece
     */
    public ObjectProperty<Position> positionProperty(int pieceNumber) {
        return pieces.get(pieceNumber).positionProperty();
    }

    /**
     * It represents the place of the piece which in the actual position in the pieces of array.
     *
     * @param position the position of the sought piece
     * @return the number of the piece in the array
     */
    public OptionalInt getPieceNumber(Position position) {
        for (int i = 0; i < pieces.size(); i++) {
            if (pieces.get(i).getPosition().equals(position)) {
                return OptionalInt.of(i);
            }
        }
        return OptionalInt.empty();
    }

    /**
     * It gives the positions where the pieces are red and they can move.
     *
     * @return a {@code List} object of {@code Position} objects which represents the movable red pieces
     */
    public List<Position> getRedPositions() {
        List<Position> redPositions = new ArrayList<>();
        for (var position : redPiecesPosition) {
            if (!getValidRedMoves(position).isEmpty()) {
                redPositions.add(position);
            }
        }
        return redPositions;
    }

    /**
     *  It gives the positions where the pieces are blue and they can move.
     *
     * @return a {@code List} object of {@code Position} objects which represents the movable blue pieces
     */
    public List<Position> getBluePositions() {
        List<Position> bluePositions = new ArrayList<>();
        for (var position : bluePiecesPosition) {
            if (!getValidBlueMoves(position).isEmpty()) {
                bluePositions.add(position);
            }
        }
        return bluePositions;
    }

    /**
     * It tests that the any of the blue pieces are movable.
     *
     * @return a {@code boolean} which represents can any of blue piece move
     */
    public boolean canBlueMove() {
        for (var position : bluePiecesPosition) {
            if (!getValidBlueMoves(position).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * It tests that the any of the red pieces are movable.
     *
     * @return a {@code boolean} which represents can any of red piece move
     */
    public boolean canRedMove() {
        for (var position : redPiecesPosition) {
            if (!getValidRedMoves(position).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * It represents the {@code RedDirection} objects which the piece from the given position can apply.
     *
     * @param position the position of the piece
     * @return a {@code Set} of {@code RedDirection} objects which are the valid directions of the given position
     */
    public Set<RedDirection> getValidRedMoves(Position position) {
        EnumSet<RedDirection> validMoves = EnumSet.noneOf(RedDirection.class);
        for (var direction : RedDirection.values()) {
            if (isValidRedMove(position, direction)) {
                validMoves.add(direction);
            }
        }
        return validMoves;
    }

    /**
     * It represents the {@code BlueDirection} objects which the piece from the given position can apply.
     *
     * @param position the position of the piece
     * @return a {@code Set} of {@code BlueDirection} objects which are the valid directions of the given position
     */
    public Set<BlueDirection> getValidBlueMoves(Position position) {
        EnumSet<BlueDirection> validMoves = EnumSet.noneOf(BlueDirection.class);
        for (var direction : BlueDirection.values()) {
            if (isValidBlueMove(position, direction)) {
                validMoves.add(direction);
            }
        }
        return validMoves;
    }

    private boolean isValidRedMove(Position position, RedDirection direction) {
        if (!isOnBoard(position)) {
            throw new IllegalArgumentException();
        }
        if (!redPiecesPosition.contains(position)) {
            return false;
        }
        Position newPosition = position.moveTo(direction);
        if (!isValidMove(newPosition)) {
            return false;
        }
        for (var pos : redPiecesPosition) {
            if (pos.equals(newPosition)) {
                return false;
            }
        }
        if (direction == RedDirection.DOWN) {
            for (var pos : bluePiecesPosition) {
                if (pos.equals(newPosition)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValidBlueMove(Position position, BlueDirection direction) {
        if (!isOnBoard(position)) {
            throw new IllegalArgumentException();
        }
        if (!bluePiecesPosition.contains(position)) {
            return false;
        }
        Position newPosition = position.moveTo(direction);
        if (!isValidMove(newPosition)) {
            return false;
        }
        for (var pos : bluePiecesPosition) {
            if (pos.equals(newPosition)) {
                return false;
            }
        }
        if (direction == BlueDirection.UP) {
            for (var pos : redPiecesPosition) {
                if (pos.equals(newPosition)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValidMove(Position newPosition) {
        if (!isOnBoard(newPosition)) {
            return false;
        }
        for (var block : blocks) {
            if (block.equals(newPosition)) {
                return false;
            }
        }
        return true;
    }

    /**
     * It calculates about the old and new position of the piece the direction where it moves.
     *
     * @param selected the old {@code Position} of the {@code Piece}
     * @param position the new {@code Position} of the {@code Piece}
     * @return the {@code Direction} of the move
     */
    public Direction getDirection(Position selected, Position position) {
        Direction direction;
        switch (this.nextPlayer.get()) {
            case RED_PLAYER -> {
                direction = RedDirection.of(position.row() - selected.row(), position.col() - selected.col());
            }
            case BLUE_PLAYER -> {
                direction = BlueDirection.of(position.row() - selected.row(), position.col() - selected.col());
            }
            default -> throw new IllegalStateException("Unexpected value: " + nextPlayer.get());
        }
        return direction;
    }

    /**
     * This method represents the move in the table.
     * It get the piece which will move and the {@code Direction} where it will move.
     *
     * @param pieceNumber the number of {@code Piece} in the array of pieces which move
     * @param direction the {@code Direction} where the given piece move
     */
    public void move(int pieceNumber, Direction direction) {
        var piece = pieces.get(pieceNumber);
        var position = piece.getPosition();
        var newPosition = position.moveTo(direction);
        if (piece.getType() == PieceColor.RED) {
            if (isValidRedMove(position, (RedDirection) direction)) {
                moveRedPiece(piece, newPosition);
            }
        } else {
            if (isValidBlueMove(position, (BlueDirection) direction)) {
                moveBluePiece(piece, newPosition);
            }
        }
        nextPlayer.set(nextPlayer.get().alter());
        piece.moveTo(direction);
    }

    private void moveRedPiece(Piece piece, Position newPosition) {
        redPiecesPosition.remove(piece.getPosition());
        redPiecesPosition.add(newPosition);
        if (bluePiecesPosition.contains(newPosition)) {
            bluePiecesPosition.remove(newPosition);
            var a = pieces.remove(new Piece(PieceColor.BLUE, newPosition));
        }
    }

    private void moveBluePiece(Piece piece, Position newPosition) {
        bluePiecesPosition.remove(piece.getPosition());
        bluePiecesPosition.add(newPosition);
        if (redPiecesPosition.contains(newPosition)) {
            redPiecesPosition.remove(newPosition);
            var a = pieces.remove(new Piece(PieceColor.RED, newPosition));
        }
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(",", "[", "]");
        for (var piece : pieces) {
            joiner.add(piece.toString());
        }
        return joiner.toString();
    }
}