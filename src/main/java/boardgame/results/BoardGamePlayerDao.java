package boardgame.results;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

@RegisterBeanMapper(Player.class)
public interface BoardGamePlayerDao {
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

    @SqlUpdate("INSERT INTO BoardGamePlayerResults (name) VALUES (:name)")
    @GetGeneratedKeys
    long insertPlayer(@Bind("name") String name);

    @SqlUpdate("""
               update BoardGamePlayerResults
               set plays = plays+1
               where name = :name;
""")
    void updatePlayedGames(@Bind("name") String name);

    @SqlUpdate("""
               update BoardGamePlayerResults
               set wins = wins+1
               where name = :name;
""")
    void updateWinedGames(@Bind("name") String name);

    @SqlQuery("SELECT name FROM BoardGamePlayerResults")
    List<String> listPlayersNames();

    @SqlQuery("SELECT * FROM BoardGamePlayerResults ORDER BY wins DESC")
    List<Player> listPlayers();

}