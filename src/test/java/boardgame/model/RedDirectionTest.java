package boardgame.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RedDirectionTest {

    @Test
    void of() {
        assertSame(RedDirection.DOWN, RedDirection.of(1,0));
        assertSame(RedDirection.DOWN_LEFT, RedDirection.of(1,-1));
        assertSame(RedDirection.DOWN_RIGHT,RedDirection.of(1,1));
    }

    @Test
    void of_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> RedDirection.of(0, 0));
        assertThrows(IllegalArgumentException.class, () -> RedDirection.of(-1, 0));
    }
}