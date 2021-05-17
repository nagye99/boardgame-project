package boardgame.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PieceTest {

    void assertPiece(PieceColor exceptedType, int expectedRow, int expectedCol, Piece piece) {
        assertAll("piece",
                () -> assertEquals(exceptedType, piece.getType()),
                () -> assertEquals(expectedRow, piece.getPosition().row()),
                () -> assertEquals(expectedCol, piece.getPosition().col())
        );
    }

    static Stream<Piece> redPieceProvider() {
        return Stream.of(new Piece(PieceColor.RED, new Position(0, 0)),
                new Piece(PieceColor.RED, new Position(0, 1)),
                new Piece(PieceColor.RED, new Position(0, 2)),
                new Piece(PieceColor.RED, new Position(0, 3)),
                new Piece(PieceColor.RED, new Position(0, 4)),
                new Piece(PieceColor.RED, new Position(0, 5)),
                new Piece(PieceColor.RED, new Position(0, 6)));
    }
    static Stream<Piece> bluePieceProvider() {
        return Stream.of(new Piece(PieceColor.BLUE, new Position(5,0)),
                new Piece(PieceColor.BLUE, new Position(5,1)),
                new Piece(PieceColor.BLUE, new Position(5,2)),
                new Piece(PieceColor.BLUE, new Position(5,3)),
                new Piece(PieceColor.BLUE, new Position(5,4)),
                new Piece(PieceColor.BLUE, new Position(5,5)),
                new Piece(PieceColor.BLUE, new Position(5,6)));
    }


    @ParameterizedTest
    @MethodSource("redPieceProvider")
    void moveTo_red_down(Piece piece) {
        var type = piece.getType();
        var row = piece.getPosition().row();
        var col = piece.getPosition().col();
        piece.moveTo(RedDirection.DOWN);
        assertPiece(type, row + 1, col + 0, piece);
    }

    @ParameterizedTest
    @MethodSource("redPieceProvider")
    void moveTo_red_down_right(Piece piece) {
        var type = piece.getType();
        var row = piece.getPosition().row();
        var col = piece.getPosition().col();
        piece.moveTo(RedDirection.DOWN_RIGHT);
        assertPiece(type, row + 1, col + 1, piece);
    }

    @ParameterizedTest
    @MethodSource("redPieceProvider")
    void moveTo_red_down_left(Piece piece) {
        var type = piece.getType();
        var row = piece.getPosition().row();
        var col = piece.getPosition().col();
        piece.moveTo(RedDirection.DOWN_LEFT);
        assertPiece(type, row + 1, col - 1, piece);
    }

    @ParameterizedTest
    @MethodSource("bluePieceProvider")
    void moveTo_blue_up(Piece piece) {
        var type = piece.getType();
        var row = piece.getPosition().row();
        var col = piece.getPosition().col();
        piece.moveTo(BlueDirection.UP);
        assertPiece(type, row - 1, col + 0, piece);
    }

    @ParameterizedTest
    @MethodSource("bluePieceProvider")
    void moveTo_blue_up_right(Piece piece) {
        var type = piece.getType();
        var row = piece.getPosition().row();
        var col = piece.getPosition().col();
        piece.moveTo(BlueDirection.UP_RIGHT);
        assertPiece(type, row - 1, col + 1, piece);
    }

    @ParameterizedTest
    @MethodSource("bluePieceProvider")
    void moveTo_blue_up_left(Piece piece) {
        var type = piece.getType();
        var row = piece.getPosition().row();
        var col = piece.getPosition().col();
        piece.moveTo(BlueDirection.UP_LEFT);
        assertPiece(type, row - 1, col - 1, piece);
    }

    @ParameterizedTest
    @MethodSource("bluePieceProvider")
    void testEquals_blue(Piece piece) {
        assertTrue(piece.equals(piece));
        assertTrue(piece.equals(new Piece(piece.getType(), new Position(piece.getPosition().row(), piece.getPosition().col()))));
        assertFalse(piece.equals(new Piece(piece.getType(), new Position(Integer.MIN_VALUE, piece.getPosition().col()))));
        assertFalse(piece.equals(new Piece(piece.getType(), new Position(piece.getPosition().row(), Integer.MAX_VALUE))));
        assertFalse(piece.equals(new Piece(piece.getType(), new Position(Integer.MIN_VALUE, Integer.MAX_VALUE))));
        assertFalse(piece.equals(new Piece(PieceColor.RED, new Position(piece.getPosition().row(), piece.getPosition().col()))));
        assertFalse(piece.equals(null));
        assertFalse(piece.equals("Hello, World!"));
    }

    @ParameterizedTest
    @MethodSource("redPieceProvider")
    void testEquals_red(Piece piece) {
        assertTrue(piece.equals(piece));
        assertTrue(piece.equals(new Piece(piece.getType(), new Position(piece.getPosition().row(), piece.getPosition().col()))));
        assertFalse(piece.equals(new Piece(piece.getType(), new Position(Integer.MIN_VALUE, piece.getPosition().col()))));
        assertFalse(piece.equals(new Piece(piece.getType(), new Position(piece.getPosition().row(), Integer.MAX_VALUE))));
        assertFalse(piece.equals(new Piece(piece.getType(), new Position(Integer.MIN_VALUE, Integer.MAX_VALUE))));
        assertFalse(piece.equals(new Piece(PieceColor.BLUE, new Position(piece.getPosition().row(), piece.getPosition().col()))));
        assertFalse(piece.equals(null));
        assertFalse(piece.equals("Hello, World!"));
    }

    @ParameterizedTest
    @MethodSource("bluePieceProvider")
    void testHashCode_blue(Piece piece) {
        assertTrue(piece.hashCode() == piece.hashCode());
        assertTrue(piece.hashCode() == new Piece(PieceColor.BLUE, new Position(piece.getPosition().row(), piece.getPosition().col())).hashCode());
    }

    @ParameterizedTest
    @MethodSource("redPieceProvider")
    void testHashCode_red(Piece piece) {
        assertTrue(piece.hashCode() == piece.hashCode());
        assertTrue(piece.hashCode() == new Piece(PieceColor.RED, new Position(piece.getPosition().row(), piece.getPosition().col())).hashCode());
    }

    @ParameterizedTest
    @MethodSource("bluePieceProvider")
    void testToString_blue(Piece piece) {
        assertEquals("\ncolor: " + piece.getType().toString() + " position: " + piece.getPosition().toString(), piece.toString());
    }

    @ParameterizedTest
    @MethodSource("redPieceProvider")
    void testToString_red(Piece piece) {
        assertEquals("\ncolor: " + piece.getType().toString() + " position: " + piece.getPosition().toString(), piece.toString());
    }
}