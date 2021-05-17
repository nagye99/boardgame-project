package boardgame.results;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

/**
 * The Dao for the BoardGameResults table in the database.
 */
@RegisterBeanMapper(Results.class)
public interface BoardGameResultDao {

    /**
     * Creating the table if it is not exists before.
     * The table has 7 rows: id, red_player, blue_player, winnew, steps, duration, gameTime and its rows represent {@code Results} objects.
     */
    @SqlUpdate("""
        CREATE TABLE IF NOT EXISTS BoardGameResults (
            id IDENTITY PRIMARY KEY,
            red_player VARCHAR NOT NULL,
            blue_player VARCHAR NOT NULL,
            winner VARCHAR NOT NULL,
            steps NUMBER NOT NULL,
            duration VARCHAR NOT NULL,
            gameTime TIMESTAMP NOT NULL
        )
        """
    )
    void createTable();

    /**
     * Insert the data of the given {@code Result} to the table.
     * It has automaticaly generated id in the table.
     *
     * @param results is the {@code Results} object which is inserted
     * @return the generated id
     */
    @SqlUpdate("INSERT INTO BoardGameResults (red_player, blue_player, winner, steps, duration, gameTime) VALUES (:red_player, :blue_player, :winner, :steps, :duration, :gameTime)")
    @GetGeneratedKeys
    long insertResult(@BindBean Results results);

    /**
     * Listing the played games data.
     * The list is ordered by the game's date.
     *
     * @return a {@code List} of {@code Results} objects from the database
     */
    @SqlQuery("SELECT * FROM BoardGameResults ORDER BY gameTime DESC ")
    List<Results> listResults();

}