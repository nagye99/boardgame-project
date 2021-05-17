package boardgame.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlueDirectionTest {

    @Test
    void of() {
        assertSame(BlueDirection.UP, BlueDirection.of(-1,0));
        assertSame(BlueDirection.UP_LEFT, BlueDirection.of(-1,-1));
        assertSame(BlueDirection.UP_RIGHT, BlueDirection.of(-1,1));
    }

    @Test
    void of_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> BlueDirection.of(0, 0));
        assertThrows(IllegalArgumentException.class, () -> BlueDirection.of(1,0));
    }
}