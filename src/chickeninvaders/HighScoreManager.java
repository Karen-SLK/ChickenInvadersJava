package chickeninvaders;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class HighScoreManager {

    private static final String FILE_NAME = "highscores.txt";

    public static void saveScore(String playerName, int score, int levelReached){

        try{

            FileWriter fileWriter = new FileWriter(FILE_NAME,true);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            String dateTime = LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            );

            HighScore highScore = new HighScore(
                    playerName,
                    score,
                    levelReached,
                    dateTime
            );

            bufferedWriter.write(highScore.toFileString());

            bufferedWriter.newLine();

            bufferedWriter.close();
        }
        catch (IOException e) {
            System.out.println("Error saving high score");

        }
    }

    public static  ArrayList<HighScore> loadScores(){

        ArrayList<HighScore> scores = new ArrayList<>();

        File file = new File(FILE_NAME);

        if(!file.exists()){
            return scores;
        }

        try{
            FileReader fileReader = new FileReader(FILE_NAME);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;

            while ((line = bufferedReader.readLine()) != null){

                String[] parts = line.split(",");

                if(parts.length >= 4){

                    String playerName = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    int levelReached = Integer.parseInt(parts[2]);
                    String dateTime = parts[3];

                    HighScore highScore = new HighScore(
                            playerName,
                            score,
                            levelReached,
                            dateTime
                    );

                    addOrUpdateBestScore(scores, highScore);
                }

                else if(parts.length >= 2){

                    String playerName = parts[0];
                    int score = Integer.parseInt(parts[1]);

                    HighScore highScore = new HighScore(
                            playerName,
                            score,
                            1,
                            "Unknown"
                    );

                    addOrUpdateBestScore(scores, highScore);
                }
            }

            bufferedReader.close();
        }
        catch (Exception e){
            System.out.println("Error loading high scores");
        }

        sortScores(scores);

        return scores;
    }

    private static void addOrUpdateBestScore(ArrayList<HighScore> scores, HighScore newScore){

        for(int i = 0; i<scores.size(); ++i){

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

        for(int i = 0; i<scores.size(); ++i){
            for(int j = 0; j<scores.size()-i-1; ++j){

                HighScore firstScore = scores.get(j);
                HighScore secondScore = scores.get(j+1);

                if(firstScore.getScore() < secondScore.getScore()){

                    scores.set(j, secondScore);
                    scores.set(j+1, firstScore);
                }
            }
        }
    }
}
