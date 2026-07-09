package chickeninvaders;

public class HighScore {

    private String playerName;

    private int score;

    public HighScore(String playerName, int score){

        this.playerName = playerName;
        this.score = score;
    }

    public String getPlayerName(){
        return playerName;
    }

    public int getScore(){
        return score;
    }

    public String toFileString(){
        return playerName + "," + score;
    }
}
