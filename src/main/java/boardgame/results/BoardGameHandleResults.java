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


public class BoardGameHandleResults {

    private static void MakeResultsDir() {
        try{
        String homeDirectory = System.getProperty("user.home");
        Path resultsDirectory = Path.of(homeDirectory + File.separator +".results");
        if (Files.notExists(resultsDirectory)){
            Files.createDirectory(resultsDirectory);}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Jdbi CreateConnection(){
        MakeResultsDir();
        String databaseURL = "jdbc:h2:file:"+System.getProperty("user.home") + File.separator+ ".results" + File.separator + "result";
        Jdbi jdbi = Jdbi.create(databaseURL, "sa", "");
        Logger.error("jdbc:h2:file:"+"user.home" + File.separator +".results" + File.separator + "result");
        jdbi.installPlugin(new SqlObjectPlugin());
        return jdbi;
    }

    public static void SaveResult(Results results){
        Jdbi jdbi = CreateConnection();
        try(Handle handle = jdbi.open()) {
            BoardGameResultDao dao = handle.attach(BoardGameResultDao.class);
            dao.createTable();
            dao.insertResult(results);
        }
    }

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

    public static List<Results> GetResults(){
        Jdbi jdbi = CreateConnection();
        try(Handle handle = jdbi.open()) {
            BoardGameResultDao dao = handle.attach(BoardGameResultDao.class);
            dao.createTable();
            return dao.listResults();
        }
    }

    public static List<Player> GetPlayer(){
        Jdbi jdbi = CreateConnection();
        try(Handle handle = jdbi.open()) {
            BoardGamePlayerDao dao = handle.attach(BoardGamePlayerDao.class);
            dao.createTable();
            return dao.listPlayers();
        }
    }


    public static void main(String[] args) {
        String name = "Gizi";
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
}
