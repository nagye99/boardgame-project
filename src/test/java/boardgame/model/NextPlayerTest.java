package boardgame.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NextPlayerTest {

    @Test
    void alter() {
        assertSame(NextPlayer.RED_PLAYER, NextPlayer.BLUE_PLAYER.alter());
        assertSame(NextPlayer.BLUE_PLAYER, NextPlayer.RED_PLAYER.alter());
    }
}