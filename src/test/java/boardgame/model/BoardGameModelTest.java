package boardgame.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BoardGameModelTest {

    BoardGameModel model;

    @BeforeEach
    void init() {model = new BoardGameModel();}

    @Test
    void testThreeConstructor_invalid(){
        assertThrows(IllegalArgumentException.class, () -> {new BoardGameModel(NextPlayer.RED_PLAYER,
                new ArrayList<Position>(Arrays.asList(new Position(2, 7), new Position(3, 2))),
                new Piece(PieceColor.RED, new Position(0, 0)),
                new Piece(PieceColor.BLUE, new Position(5, 5)),
                new Piece(PieceColor.BLUE, new Position(5, 6)));});
        assertThrows(IllegalArgumentException.class, () -> {new BoardGameModel(NextPlayer.RED_PLAYER,
                new ArrayList<Position>(Arrays.asList(new Position(6, 4), new Position(3, 2))),
                new Piece(PieceColor.RED, new Position(0, 0)),
                new Piece(PieceColor.RED, new Position(0, 1)),
                new Piece(PieceColor.RED, new Position(0, 2)),
                new Piece(PieceColor.BLUE, new Position(5, 6)));});
        assertThrows(IllegalArgumentException.class, () -> {new BoardGameModel(NextPlayer.RED_PLAYER,
                new ArrayList<Position>(Arrays.asList(new Position(2, 4), new Position(-1, 2))),
                new Piece(PieceColor.BLUE, new Position(5, 3)),
                new Piece(PieceColor.BLUE, new Position(5, 4)),
                new Piece(PieceColor.BLUE, new Position(5, 5)),
                new Piece(PieceColor.BLUE, new Position(5, 6)));});
        assertThrows(IllegalArgumentException.class, () -> {new BoardGameModel(NextPlayer.RED_PLAYER,
                new ArrayList<Position>(Arrays.asList(new Position(2, 4), new Position(3, -1))),
                new Piece(PieceColor.RED, new Position(0, 0)),
                new Piece(PieceColor.BLUE, new Position(5, 5)),
                new Piece(PieceColor.BLUE, new Position(5, 6)));});
        assertThrows(IllegalArgumentException.class, () -> {new BoardGameModel(NextPlayer.BLUE_PLAYER,
                new ArrayList<Position>(Arrays.asList(new Position(2, 4), new Position(3, 2))),
                new Piece(PieceColor.RED, new Position(0, 0)),
                new Piece(PieceColor.RED, new Position(0, 1)),
                new Piece(PieceColor.RED, new Position(0, 2)),
                new Piece(PieceColor.RED, new Position(0, 3)),
                new Piece(PieceColor.RED, new Position(2, 4)),
                new Piece(PieceColor.RED, new Position(0, 5)),
                new Piece(PieceColor.RED, new Position(0, 6)),
                new Piece(PieceColor.BLUE, new Position(5, 0)),
                new Piece(PieceColor.BLUE, new Position(5, 5)),
                new Piece(PieceColor.BLUE, new Position(5, 6)));});
        assertThrows(IllegalArgumentException.class, () -> {new BoardGameModel(NextPlayer.RED_PLAYER,
                new ArrayList<Position>(Arrays.asList(new Position(2, 4), new Position(3, 2))),
                new Piece(PieceColor.RED, new Position(0, 0)),
                new Piece(PieceColor.RED, new Position(0, 1)),
                new Piece(PieceColor.RED, new Position(0, 2)),
                new Piece(PieceColor.RED, new Position(0, 3)),
                new Piece(PieceColor.RED, new Position(0, 3)),
                new Piece(PieceColor.BLUE, new Position(5, 6)));});
        assertThrows(IllegalArgumentException.class, () -> {new BoardGameModel(NextPlayer.BLUE_PLAYER,
                new ArrayList<Position>(Arrays.asList(new Position(2, 4), new Position(3, 2))),
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
                new Piece(PieceColor.BLUE, new Position(5, 7)));});
        assertThrows(IllegalArgumentException.class, () -> {new BoardGameModel(NextPlayer.RED_PLAYER,
                new ArrayList<Position>(Arrays.asList(new Position(2, 4))),
                new Piece(PieceColor.RED, new Position(6, 3)),
                new Piece(PieceColor.RED, new Position(0, 4)),
                new Piece(PieceColor.RED, new Position(0, 5)),
                new Piece(PieceColor.RED, new Position(0, 6)),
                new Piece(PieceColor.BLUE, new Position(5, 0)),
                new Piece(PieceColor.BLUE, new Position(5, 6)));});
        assertThrows(IllegalArgumentException.class, () -> {new BoardGameModel(NextPlayer.RED_PLAYER,
                new ArrayList<Position>(Arrays.asList(new Position(2, 4), new Position(3, 2))),
                new Piece(PieceColor.RED, new Position(0, 3)),
                new Piece(PieceColor.RED, new Position(0, 4)),
                new Piece(PieceColor.RED, new Position(5, 5)),
                new Piece(PieceColor.BLUE, new Position(5, 4)),
                new Piece(PieceColor.BLUE, new Position(5, 5)),
                new Piece(PieceColor.BLUE, new Position(5, 6)));});
    }

    @Test
    void getPieceCount() {
        assertEquals(14, model.getPieceCount());
        var model2 = new BoardGameModel(NextPlayer.BLUE_PLAYER, new ArrayList<Position>(Arrays.asList(new Position(2, 4), new Position(3, 2))),
                new Piece(PieceColor.RED, new Position(1,1)),  new Piece(PieceColor.BLUE, new Position(5,6)));
        assertEquals(2, model2.getPieceCount());
    }

    @Test
    void getBlockCount() {
        assertEquals(2, model.getBlockCount());
        var model2 = new BoardGameModel(NextPlayer.BLUE_PLAYER, new ArrayList<Position>(Arrays.asList()),
                new Piece(PieceColor.RED, new Position(1,1)),  new Piece(PieceColor.BLUE, new Position(5,6)));
        assertEquals(0, model2.getBlockCount());
        var model3 = new BoardGameModel(NextPlayer.BLUE_PLAYER, new ArrayList<Position>(Arrays.asList(new Position(2,2), new Position(5,5), new Position(3,4), new Position(2,5))),
                new Piece(PieceColor.RED, new Position(1,1)),  new Piece(PieceColor.BLUE, new Position(5,6)));
        assertEquals(4, model3.getBlockCount());
    }

    @Test
    void nextPlayerProperty() {
        assertSame(NextPlayer.RED_PLAYER, model.nextPlayerProperty().get());
        var model2 = new BoardGameModel(NextPlayer.BLUE_PLAYER, new ArrayList<Position>(Arrays.asList(new Position(1,1))), new Piece(PieceColor.RED, new Position(2,6)));
        assertSame(NextPlayer.BLUE_PLAYER, model2.nextPlayerProperty().get());
    }

    @Test
    void getPieceType() {
        assertSame(PieceColor.RED, model.getPieceType(0));
        assertSame(PieceColor.RED, model.getPieceType(3));
        assertSame(PieceColor.RED, model.getPieceType(6));
        assertSame(PieceColor.BLUE, model.getPieceType(7));
        assertSame(PieceColor.BLUE, model.getPieceType(10));
        assertSame(PieceColor.BLUE, model.getPieceType(13));
    }

    @Test
    void getPiecePosition() {
        assertEquals(new Position(0,0), model.getPiecePosition(0));
        assertEquals(new Position(0,6), model.getPiecePosition(6));
        assertEquals(new Position(5,3), model.getPiecePosition(10));
        assertEquals(new Position(5,6), model.getPiecePosition(13));
    }

    @Test
    void getBlockPosition() {
        assertEquals(new Position(2,4), model.getBlockPosition(0));
        assertEquals(new Position(3,2), model.getBlockPosition(1));
    }

    @Test
    void getPieceNumber() {
        assertEquals(OptionalInt.empty(), model.getPieceNumber(new Position(2,4)));
        assertEquals(OptionalInt.of(0), model.getPieceNumber(new Position(0,0)));
        assertEquals(OptionalInt.of(6), model.getPieceNumber(new Position(0,6)));
        assertEquals(OptionalInt.of(7), model.getPieceNumber(new Position(5,0)));
        assertEquals(OptionalInt.of(13), model.getPieceNumber(new Position(5,6)));
    }

    @Test
    void canBlueMove() {
        assertTrue(model.canBlueMove());
        var model2 = new BoardGameModel(NextPlayer.RED_PLAYER, new ArrayList<Position>(Arrays.asList(new Position(1,1))), new Piece(PieceColor.RED, new Position(5,2)));
        assertFalse(model2.canBlueMove());
    }

    @Test
    void canRedMove() {
        assertTrue(model.canRedMove());
        var model2 = new BoardGameModel(NextPlayer.BLUE_PLAYER, new ArrayList<Position>(Arrays.asList(new Position(1,1))), new Piece(PieceColor.BLUE, new Position(2,2)));
        assertFalse(model2.canRedMove());
    }

    @Test
    void getValidRedMoves() {
        assertEquals(EnumSet.of(RedDirection.DOWN, RedDirection.DOWN_RIGHT), model.getValidRedMoves(new Position(0,0)));
        assertEquals(EnumSet.noneOf(RedDirection.class), model.getValidRedMoves(new Position(5,0)));
        var model2 = new BoardGameModel(NextPlayer.RED_PLAYER, new ArrayList<Position>(Arrays.asList(new Position(2, 4), new Position(3, 2))),new Piece(PieceColor.RED, new Position(1,4)));
        assertEquals(EnumSet.of(RedDirection.DOWN_RIGHT, RedDirection.DOWN_LEFT), model2.getValidRedMoves(new Position(1,4)));
        var model3 = new BoardGameModel(NextPlayer.RED_PLAYER, new ArrayList<Position>(Arrays.asList(new Position(2, 4), new Position(3, 2))),new Piece(PieceColor.RED, new Position(4,4)), new Piece(PieceColor.BLUE, new Position(5,4 )));
        assertEquals(EnumSet.of(RedDirection.DOWN_RIGHT, RedDirection.DOWN_LEFT), model3.getValidRedMoves(new Position(4,4)));
    }

    @Test
    void getValidBlueMoves() {
        assertEquals(EnumSet.of(BlueDirection.UP, BlueDirection.UP_RIGHT), model.getValidBlueMoves(new Position(5,0)));
        assertEquals(EnumSet.noneOf(BlueDirection.class), model.getValidBlueMoves(new Position(0,0)));
        var model2 = new BoardGameModel(NextPlayer.BLUE_PLAYER, new ArrayList<Position>(Arrays.asList(new Position(2, 4), new Position(3, 2))),new Piece(PieceColor.BLUE, new Position(3,4)));
        assertEquals(EnumSet.of(BlueDirection.UP_RIGHT, BlueDirection.UP_LEFT), model2.getValidBlueMoves(new Position(3,4)));
        var model3 = new BoardGameModel(NextPlayer.RED_PLAYER, new ArrayList<Position>(Arrays.asList(new Position(2, 4), new Position(3, 2))),new Piece(PieceColor.RED, new Position(4,4)), new Piece(PieceColor.BLUE, new Position(5,4 )));
        assertEquals(EnumSet.of(BlueDirection.UP_RIGHT, BlueDirection.UP_LEFT), model3.getValidBlueMoves(new Position(5,4)));
    }

    @Test
    void getDirection() {
        assertEquals(RedDirection.DOWN, model.getDirection(new Position(0,0), new Position(1,0)));
        assertEquals(RedDirection.DOWN_LEFT, model.getDirection(new Position(0,3), new Position(1,2)));
        assertEquals(RedDirection.DOWN_RIGHT, model.getDirection(new Position(0,4), new Position(1,5)));
        var model2 = new BoardGameModel(NextPlayer.BLUE_PLAYER,  new ArrayList<Position>(Arrays.asList(new Position(2, 4), new Position(3, 2))), new Piece(PieceColor.BLUE, new Position(5,5)));
        assertEquals(BlueDirection.UP, model2.getDirection(new Position(5,5), new Position(4,5)));
        assertEquals(BlueDirection.UP_RIGHT, model2.getDirection(new Position(5,5), new Position(4,6)));
        assertEquals(BlueDirection.UP_LEFT, model2.getDirection(new Position(5,5), new Position(4,4)));
    }

    @Test
    void move_red() {
        model.move(1,RedDirection.DOWN);
        assertEquals(new Position(1,1), model.getPiecePosition(1));
    }

    @Test
    void move_blue() {
        model.move(8, BlueDirection.UP);
        assertEquals(new Position(4,1), model.getPiecePosition(8));
    }

    @Test
    void testToString() {
        assertEquals("[\ncolor: RED position: (0,0),\n" +
                "color: RED position: (0,1),\n" +
                "color: RED position: (0,2),\n" +
                "color: RED position: (0,3),\n" +
                "color: RED position: (0,4),\n" +
                "color: RED position: (0,5),\n" +
                "color: RED position: (0,6),\n" +
                "color: BLUE position: (5,0),\n" +
                "color: BLUE position: (5,1),\n" +
                "color: BLUE position: (5,2),\n" +
                "color: BLUE position: (5,3),\n" +
                "color: BLUE position: (5,4),\n" +
                "color: BLUE position: (5,5),\n" +
                "color: BLUE position: (5,6)]", model.toString());
    }
}