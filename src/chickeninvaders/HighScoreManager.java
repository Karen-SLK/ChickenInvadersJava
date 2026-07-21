package chickeninvaders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/*
 * Handles saving and loading game records from the SQLite database.
 * It is used to store final game scores and return the best scores for the High Scores screen.
 */

public class HighScoreManager {

    public static void saveScore(String playerName, int score, int levelReached){

        String sql =
                "INSERT INTO game_records ("
                        + "username, score, level_reached, "
                        + "background_music_on, shot_sound_on, explosion_sound_on, game_result_sound_on"
                        + ") VALUES (?, ?, ?, ?, ?, ?, ?)";

        try{

            Connection connection = DatabaseManager.getConnection();

            if(connection == null){

                return;
            }

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, playerName);

            statement.setInt(2, score);

            statement.setInt(3, levelReached);

            statement.setInt(4, booleanToInt(SoundSettings.isBackgroundMusicOn()));

            statement.setInt(5, booleanToInt(SoundSettings.isShotSoundOn()));

            statement.setInt(6, booleanToInt(SoundSettings.isExplosionSoundOn()));

            statement.setInt(7, booleanToInt(SoundSettings.isGameResultSoundOn()));

            statement.executeUpdate();

            statement.close();
            connection.close();
        }
        catch(Exception e){
            System.out.println("Error saving game record.");
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<HighScore> loadScores(){

        ArrayList<HighScore> scores = new ArrayList<>();

        String sql =
                "SELECT username, score, level_reached, played_at "
                        + "FROM game_records "
                        + "ORDER BY score DESC";

        try{

            Connection connection = DatabaseManager.getConnection();

            if(connection == null){
                return scores;
            }

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){

                String playerName = resultSet.getString("username");
                int score = resultSet.getInt("score");
                int levelReached = resultSet.getInt("level_reached");
                String dateTime = resultSet.getString("played_at");

                HighScore highScore = new HighScore(
                        playerName,
                        score,
                        levelReached,
                        dateTime
                );

                addOrUpdateBestScore(scores, highScore);
            }

            resultSet.close();
            statement.close();
            connection.close();
        }
        catch(Exception e){
            System.out.println("Error loading high scores from database.");
            System.out.println(e.getMessage());
        }

        sortScores(scores);

        return scores;
    }

    private static void addOrUpdateBestScore(ArrayList<HighScore> scores, HighScore newScore){

        for(int i = 0; i < scores.size(); i++){

            HighScore currentScore = scores.get(i);

            if(currentScore.getPlayerName().equals(newScore.getPlayerName())){

                if(newScore.getScore() > currentScore.getScore()){

                    scores.set(i, newScore);
                }

                return;
            }
        }

        scores.add(newScore);
    }

    private static void sortScores(ArrayList<HighScore> scores){

        for(int i = 0; i < scores.size() - 1; i++){

            for(int j = 0; j < scores.size() - i - 1; j++){

                HighScore firstScore = scores.get(j);
                HighScore secondScore = scores.get(j + 1);

                if(firstScore.getScore() < secondScore.getScore()){

                    scores.set(j, secondScore);
                    scores.set(j + 1, firstScore);
                }
            }
        }
    }

    private static int booleanToInt(boolean value){

        if(value){
            return 1;
        }

        return 0;
    }
}