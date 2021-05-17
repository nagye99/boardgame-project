package boardgame.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    void assertPosition(int expectedRow, int expectedCol, Position position) {
        assertAll("position",
                () -> assertEquals(expectedRow, position.row()),
                () -> assertEquals(expectedCol, position.col())
        );
    }

    static Stream<Position> positionProvider() {
        return Stream.of(new Position(0, 0),
                new Position(0, 1),
                new Position(0, 2),
                new Position(0, 3),
                new Position(0, 4),
                new Position(0, 5),
                new Position(0, 6),
                new Position(1, 0),
                new Position(1, 1),
                new Position(1, 2),
                new Position(1, 3),
                new Position(1, 4),
                new Position(1, 5),
                new Position(1, 6),
                new Position(2, 0),
                new Position(2, 1),
                new Position(2, 2),
                new Position(2, 3),
                new Position(2, 4),
                new Position(2, 5),
                new Position(2, 6),
                new Position(3, 0),
                new Position(3, 1),
                new Position(3, 2),
                new Position(3, 3),
                new Position(3, 4),
                new Position(3, 5),
                new Position(3, 6),
                new Position(4, 0),
                new Position(4, 1),
                new Position(4, 2),
                new Position(4, 3),
                new Position(4, 4),
                new Position(4, 5),
                new Position(4, 6),
                new Position(5, 0),
                new Position(5, 1),
                new Position(5, 2),
                new Position(5, 3),
                new Position(5, 4),
                new Position(5, 5),
                new Position(5, 6));
    }

    @ParameterizedTest
    @MethodSource("positionProvider")
    void moveTo(Position position) {
        assertPosition(position.row() + 1, position.col() + 0, position.moveTo(RedDirection.DOWN));
        assertPosition(position.row() + 1, position.col() - 1, position.moveTo(RedDirection.DOWN_LEFT));
        assertPosition(position.row() + 1, position.col() + 1, position.moveTo(RedDirection.DOWN_RIGHT));
        assertPosition(position.row() - 1, position.col() + 0, position.moveTo(BlueDirection.UP));
        assertPosition(position.row() - 1, position.col() - 1, position.moveTo(BlueDirection.UP_LEFT));
        assertPosition(position.row() - 1, position.col() + 1, position.moveTo(BlueDirection.UP_RIGHT));
    }

    @ParameterizedTest
    @MethodSource("positionProvider")
    void testEquals(Position position) {
        assertTrue(position.equals(position));
        assertTrue(position.equals(new Position(position.row(), position.col())));
        assertFalse(position.equals(new Position(Integer.MIN_VALUE, position.col())));
        assertFalse(position.equals(new Position(position.row(), Integer.MAX_VALUE)));
        assertFalse(position.equals(new Position(Integer.MIN_VALUE, Integer.MAX_VALUE)));
        assertFalse(position.equals(null));
        assertFalse(position.equals("Hello, World!"));
    }

    @ParameterizedTest
    @MethodSource("positionProvider")
    void testHashCode(Position position) {
        assertTrue(position.hashCode() == position.hashCode());
        assertTrue(position.hashCode() == new Position(position.row(), position.col()).hashCode());
    }

    @ParameterizedTest
    @MethodSource("positionProvider")
    void testToString(Position position) {
        assertEquals(String.format("(%d,%d)", position.row(), position.col()), position.toString());
    }
}