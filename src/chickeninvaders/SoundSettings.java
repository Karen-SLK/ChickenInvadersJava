package chickeninvaders;

public class SoundSettings {

    private static boolean backgroundMusicOn = true;

    private static boolean shotSoundOn = true;

    private static boolean explosionSoundOn = true;

    private static boolean gameResultSoundOn = true;

    public static boolean isBackgroundMusicOn(){
        return backgroundMusicOn;
    }

    public static void setBackgroundMusicOn(boolean value){
        backgroundMusicOn = value;
    }

    public static boolean isShotSoundOn(){
        return shotSoundOn;
    }

    public static void setShotSoundOn(boolean value){
        shotSoundOn = value;
    }

    public static boolean isExplosionSoundOn(){
        return explosionSoundOn;
    }

    public static void setExplosionSoundOn(boolean value){
        explosionSoundOn = value;
    }

    public static boolean isGameResultSoundOn(){
        return gameResultSoundOn;
    }

    public static void setGameResultSoundOn(boolean value){
        gameResultSoundOn = value;
    }
}
