package chickeninvaders;

import java.io.*;
import java.util.ArrayList;

public class HighScoreManager {

    private static final String FILE_NAME = "highscores.txt";

    public static void saveScore(String playerName, int score){

        try{

            FileWriter fileWriter = new FileWriter(FILE_NAME,true);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            HighScore highScore = new HighScore(playerName,score);

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

                if(parts.length == 2){

                    String name = parts[0];

                    int score = Integer.parseInt(parts[1]);

                    scores.add(new HighScore(name, score));
                }
            }

            bufferedReader.close();
        }
        catch (IOException e){
            System.out.println("Error loading high scores");
        }

        sortScores(scores);

        return scores;
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
