package boardgame.results;

import boardgame.controller.BoardGameController;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;

@RegisterBeanMapper(Results.class)
public interface BoardGameDao {
    @SqlUpdate("""
        CREATE TABLE IF NOT EXISTS BoardGameResults (
            id IDENTITY PRIMARY KEY,
            red_player VARCHAR NOT NULL,
            blue_player VARCHAR NOT NULL,
            winner VARCHAR NOT NULL,
            steps NUMBER NOT NULL,
            duration VARCHAR NOT NULL
        )
        """
    )
    void createTable();

    @SqlUpdate("INSERT INTO BoardGameResults (red_player, blue_player, winner, steps, duration) VALUES (:red_player, :blue_player, :winner, :steps, :duration)")
    @GetGeneratedKeys
    long insertResult(@BindBean Results results);

    @SqlQuery("SELECT * FROM BoardGameResults")
    List<Results> listResults();

}