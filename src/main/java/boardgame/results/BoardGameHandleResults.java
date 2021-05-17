package boardgame.results;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.tinylog.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * This class responds the database connections and its IO operations.
 */
public class BoardGameHandleResults {

    private static String databaseDirectory = System.getProperty("user.home") + File.separator+ ".results";

    /**
     * This method makes directory where the database will be stored.
     */
    private static void MakeResultsDir() {
        try{
        Path databaseDirectoryPath = Path.of(databaseDirectory);
        if (Files.notExists(databaseDirectoryPath)){
            Files.createDirectory(databaseDirectoryPath);}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method creates the connection to the {@code Jdbc} database.
     *
     * @return the created {@code Jdbi} connection
     */
    public static Jdbi CreateConnection(){
        MakeResultsDir();
        String databaseURL = databaseDirectory + File.separator + "result";
        Jdbi jdbi = Jdbi.create("jdbc:h2:file:"+ databaseURL);
        Logger.info("Connected to jdbc:h2:file:"+"user.home" + File.separator +".results" + File.separator + "result");
        jdbi.installPlugin(new SqlObjectPlugin());
        return jdbi;
    }

    /**
     * This method saves a {@code Results} object to the given {@code Jdbi} database.
     *
     * @param results the {@code Results} object which the method save
     */
    public static void SaveResult(Results results){
        Jdbi jdbi = CreateConnection();
        try(Handle handle = jdbi.open()) {
            BoardGameResultDao dao = handle.attach(BoardGameResultDao.class);
            dao.createTable();
            dao.insertResult(results);
        }
    }

    /**
     * This method saves the winner of the game.
     * The winner of the game name is searched in the database and increase the number of the plays and the number of the wins for him/her.
     * If it was the first play of the player then we make the player with 1 play and 1 win.
     *
     * @param name the name of the winner
     */
    public static void SaveWinnerPlayer(String name){
        Jdbi jdbi = CreateConnection();
        try(Handle handle = jdbi.open()) {
            BoardGamePlayerDao dao = handle.attach(BoardGamePlayerDao.class);
            dao.createTable();
            List<String> players = dao.listPlayersNames();
            if (!players.contains(name)){
                dao.insertPlayer(name);
            }
            dao.updateWinedGames(name);
            dao.updatePlayedGames(name);
        }
    }

    /**
     * This method saves the loser of the game.
     * The loser of the game name is searched in the database and increase the number of the plays for him/her.
     * If it was the first play of the player then we make the player with 1 plays and 0 wins.
     *
     * @param name the name of the loser
     */
    public static void SaveLoserPlayer(String name){
        Jdbi jdbi = CreateConnection();
        try(Handle handle = jdbi.open()) {
            BoardGamePlayerDao dao = handle.attach(BoardGamePlayerDao.class);
            dao.createTable();
            List<String> players = dao.listPlayersNames();
            if (!players.contains(name)){
                dao.insertPlayer(name);
            }
            dao.updatePlayedGames(name);
        }
    }

    /**
     * This method lists out the results of the database.
     *
     * @return a {@code List} of {@code Result} objects which represents the previous game results
     */
    public static List<Results> GetResults(){
        Jdbi jdbi = CreateConnection();
        try(Handle handle = jdbi.open()) {
            BoardGameResultDao dao = handle.attach(BoardGameResultDao.class);
            dao.createTable();
            return dao.listResults();
        }
    }

    /**
     * This method list out the players and there results from the database.
     *
     * @return a {@code List} of {@code Player} objects which represents the players name and their played and winned games
     */
    public static List<Player> GetPlayer(){
        Jdbi jdbi = CreateConnection();
        try(Handle handle = jdbi.open()) {
            BoardGamePlayerDao dao = handle.attach(BoardGamePlayerDao.class);
            dao.createTable();
            return dao.listPlayers();
        }
    }
}
