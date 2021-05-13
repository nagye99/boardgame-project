package boardgame.results;

import javafx.beans.property.StringProperty;
import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.time.DurationFormatUtils;

import java.time.Duration;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Results {
    private String red_player;
    private String blue_player;
    private String winner;
    private Integer steps;
    private String duration;

    public static void main(String[] args) {

    }
}
