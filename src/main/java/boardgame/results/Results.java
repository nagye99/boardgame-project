package boardgame.results;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Class represents the played games data.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Results {
    private String red_player;
    private String blue_player;
    private String winner;
    private Integer steps;
    private String duration;
    private LocalDateTime gameTime;
}
