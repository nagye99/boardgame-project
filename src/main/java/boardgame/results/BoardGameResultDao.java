package boardgame.results;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

@RegisterBeanMapper(Results.class)
public interface BoardGameResultDao {
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

    @SqlUpdate("INSERT INTO BoardGameResults (red_player, blue_player, winner, steps, duration, gameTime) VALUES (:red_player, :blue_player, :winner, :steps, :duration, :gameTime)")
    @GetGeneratedKeys
    long insertResult(@BindBean Results results);

    @SqlQuery("SELECT * FROM BoardGameResults ORDER BY gameTime DESC ")
    List<Results> listResults();

}