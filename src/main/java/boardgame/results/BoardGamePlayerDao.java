package boardgame.results;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

/**
 * The Dao for the BoardGamePlayerResults table in the database.
 */
@RegisterBeanMapper(Player.class)
public interface BoardGamePlayerDao {

    /**
     * Creates the table if it is not exists before.
     * The table has 4 rows: id, name, plays, wins and its rows represent {@code Player} objects.
     */
    @SqlUpdate("""
        CREATE TABLE IF NOT EXISTS BoardGamePlayerResults (
            id IDENTITY PRIMARY KEY,
            name VARCHAR NOT NULL,
            plays NUMBER DEFAULT 0,
            wins NUMBER DEFAULT 0
        )
        """
    )
    void createTable();

    /**
     * Inserts the player to the table.
     * It has automaticaly generated id in the table.
     *
     * @param name the player who is inserted
     * @return the generated id
     */
    @SqlUpdate("INSERT INTO BoardGamePlayerResults (name) VALUES (:name)")
    @GetGeneratedKeys
    long insertPlayer(@Bind("name") String name);

    /**
     * Increases an existing player's number of plays with 1.
     *
     * @param name the player's name whose plays is increase
     */
    @SqlUpdate("""
               update BoardGamePlayerResults
               set plays = plays+1
               where name = :name;
""")
    void updatePlayedGames(@Bind("name") String name);

    /**
     * Increases by an existing player's number of wins with 1.
     *
     * @param name  the player's name whose wins is increase
     */
    @SqlUpdate("""
               update BoardGamePlayerResults
               set wins = wins+1
               where name = :name;
""")
    void updateWinedGames(@Bind("name") String name);

    /**
     * Lists the names from the table.
     *
     * @return a {@code List} of {@code String} objects which are the names of players
     */
    @SqlQuery("SELECT name FROM BoardGamePlayerResults")
    List<String> listPlayersNames();

    /**
     * Lists the players results.
     * The list is ordered by the number of wins.
     *
     * @return a {@code List} of {@code Player} objects from the database
     */
    @SqlQuery("SELECT * FROM BoardGamePlayerResults ORDER BY wins DESC")
    List<Player> listPlayers();

}