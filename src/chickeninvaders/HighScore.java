package chickeninvaders;

public class HighScore {

    private String playerName;

    private int score;

    private int levelReached;

    private String dateTime;

    public HighScore(String playerName, int score, int levelReached, String dateTime){

        this.playerName = playerName;

        this.score = score;

        this.levelReached = levelReached;

        this.dateTime = dateTime;
    }

    public String getPlayerName(){
        return playerName;
    }

    public int getScore(){
        return score;
    }

    public int getLevelReached(){
        return levelReached;
    }

    public String getDateTime(){
        return dateTime;
    }

    public String toFileString(){
        return playerName + "," + score + "," + levelReached + "," + dateTime;
    }
}
