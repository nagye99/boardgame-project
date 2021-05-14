package boardgame.results;

import org.jdbi.v3.core.ConnectionException;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.tinylog.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
//import org.h2.jdbcx.JdbcDataSource;


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
        String databaseURL = "jdbc:h2:file:"+System.getProperty("user.home") + File.separator +".results"+File.separator+"result";
        Jdbi jdbi = Jdbi.create(databaseURL,"sa","");
        Logger.error("jdbc:h2:file:"+"user.home" + File.separator +".results"+File.separator+"result");
        jdbi.installPlugin(new SqlObjectPlugin());
        return jdbi;
    }

    /*public void SaveResults(String red_player, String blue_player, String winner, Integer steps, String duration){
        Handle handle = CreateConnection();
        BoardGameDao dao = handle.attach(BoardGameDao.class);
                dao.createTable();
            dao.insertResult(red_player, blue_player, winner, steps, duration);
        }*/

    public static void SaveResult(Results results){
        Jdbi jdbi = CreateConnection();
        try(Handle handle = jdbi.open()) {
            BoardGameDao dao = handle.attach(BoardGameDao.class);
            dao.createTable();
            dao.insertResult(results);
            /*BoardGamePlayerDao dao2 = handle.attach(BoardGamePlayerDao.class);
            List<String> players = dao2.listPlayersNames();
            if (!players.contains(results.getRed_player())){
                dao2.insertPlayer(results.getRed_player());
            }
            dao2.updatePlayedGames(results.getRed_player());
            if (!players.contains(results.getBlue_player())){
                dao2.insertPlayer(results.getBlue_player());
            }
            dao2.updatePlayedGames(results.getWinner());
            dao2.updateWinedGames(results.getWinner());*/
        }
    }

    public static List<Results> GetResults(){
        Jdbi jdbi = CreateConnection();
        try(Handle handle = jdbi.open()) {
            BoardGameDao dao = handle.attach(BoardGameDao.class);
            dao.createTable();
            return dao.listResults();
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

    public static List<Player> GetPlayer(){
        Jdbi jdbi = CreateConnection();
        try(Handle handle = jdbi.open()) {
            BoardGamePlayerDao dao = handle.attach(BoardGamePlayerDao.class);
            dao.createTable();
            return dao.listPlayers();
        }
    }


    public static void main(String[] args) {

    }
}
