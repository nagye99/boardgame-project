package boardgame.results;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class represents the players and there stats.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    private String name;
    private Integer plays;
    private Integer wins;
}
