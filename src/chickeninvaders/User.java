package chickeninvaders;

public class User {

    private String username;

    private String password;

    private int highestScore;

    private int lastLevel;

    private boolean backgroundMusicOn;

    private boolean shotSoundOn;

    private boolean explosionSoundOn;

    private boolean gameResultSoundOn;

    private String selectedPlane;

    public User(String username, String password){

        this.username = username;
        this.password = password;

        highestScore = 0;

        lastLevel = 1;

        backgroundMusicOn = true;
        shotSoundOn = true;
        explosionSoundOn = true;
        gameResultSoundOn = true;

        this.selectedPlane = PlaneType.DEFAULT;
    }

    public User(String username, String password, int highestScore, int lastLevel,
                boolean backgroundMusicOn, boolean shotSoundOn,
                boolean explosionSoundOn, boolean gameResultSoundOn,
                String selectedPlane){

        this.username = username;
        this.password = password;

        this.highestScore = highestScore;
        this.lastLevel = lastLevel;

        this.backgroundMusicOn = backgroundMusicOn;
        this.shotSoundOn = shotSoundOn;
        this.explosionSoundOn = explosionSoundOn;
        this.gameResultSoundOn = gameResultSoundOn;

        if(PlaneType.isValidPlane(selectedPlane)){
            this.selectedPlane = selectedPlane;
        }
        else{
            this.selectedPlane = PlaneType.DEFAULT;
        }
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public int getHighestScore(){
        return highestScore;
    }

    public void setHighestScore(int highestScore){
        this.highestScore = highestScore;
    }

    public int getLastLevel(){
        return lastLevel;
    }

    public void setLastLevel(int lastLevel){
        this.lastLevel = lastLevel;
    }

    public boolean isBackgroundMusicOn(){
        return backgroundMusicOn;
    }

    public void setBackgroundMusicOn(boolean backgroundMusicOn){
        this.backgroundMusicOn = backgroundMusicOn;
    }

    public boolean isShotSoundOn(){
        return shotSoundOn;
    }

    public void setShotSoundOn(boolean shotSoundOn){
        this.shotSoundOn = shotSoundOn;
    }

    public boolean isExplosionSoundOn(){
        return explosionSoundOn;
    }

    public void setExplosionSoundOn(boolean explosionSoundOn){
        this.explosionSoundOn = explosionSoundOn;
    }

    public boolean isGameResultSoundOn(){
        return gameResultSoundOn;
    }

    public void setGameResultSoundOn(boolean gameResultSoundOn){
        this.gameResultSoundOn = gameResultSoundOn;
    }

    public String getSelectedPlane(){

        return selectedPlane;
    }

    public void setSelectedPlane(String selectedPlane){

        if(PlaneType.isValidPlane(selectedPlane)){
            this.selectedPlane = selectedPlane;
        }
        else{
            this.selectedPlane = PlaneType.DEFAULT;
        }
    }

    public String toFileString(){

        return username + ","
                + password + ","
                + highestScore + ","
                + lastLevel + ","
                + backgroundMusicOn + ","
                + shotSoundOn + ","
                + explosionSoundOn + ","
                + gameResultSoundOn;
    }
}
