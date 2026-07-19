package chickeninvaders;

import javax.sound.sampled.*;
import java.io.File;

public class SoundManager {

    private static Clip backgroundClip;

    private static Clip loadClip(String filePath){

        try{

            File soundFile = new File(filePath);

            if(!soundFile.exists()){
                System.out.println("Sound file not found: " + filePath);
                return null;
            }

            AudioInputStream originalStream = AudioSystem.getAudioInputStream(soundFile);
            AudioFormat originalFormat = originalStream.getFormat();

            AudioFormat decodedFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    originalFormat.getSampleRate(),
                    16,
                    originalFormat.getChannels(),
                    originalFormat.getChannels() * 2,
                    originalFormat.getSampleRate(),
                    false
            );

            AudioInputStream decodedStream = AudioSystem.getAudioInputStream(
                    decodedFormat,
                    originalStream
            );

            DataLine.Info info = new DataLine.Info(Clip.class, decodedFormat);

            Clip clip = (Clip) AudioSystem.getLine(info);

            clip.open(decodedStream);

            decodedStream.close();
            originalStream.close();

            return clip;
        }
        catch(Exception e){
            System.out.println("Error loading sound: " + filePath);
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static void playSound(String filePath){

        Clip clip = loadClip(filePath);

        if(clip != null){
            clip.start();
        }
    }

    public static void playBackgroundMusic(){

        if(!SoundSettings.isBackgroundMusicOn()){
            return;
        }

        if(backgroundClip != null && backgroundClip.isRunning()){
            return;
        }

        backgroundClip = loadClip("assets/sounds/background.wav");

        if(backgroundClip != null){
            backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public static void stopBackgroundMusic(){

        if(backgroundClip != null){
            backgroundClip.stop();
            backgroundClip.close();
            backgroundClip = null;
        }
    }

    public static void playShotSound(){

        if(SoundSettings.isShotSoundOn()){
            playSound("assets/sounds/defaultweapon.wav");
        }
    }

    public static void playChickenDeadSound(){

        if(SoundSettings.isExplosionSoundOn()){
            playSound("assets/sounds/chicken1.wav");
        }
    }

    public static void playBossDeadSound(){

        if(SoundSettings.isExplosionSoundOn()){
            playSound("assets/sounds/chickbossDie.wav");
        }
    }

    public static void playExplosionSound(){

        if(SoundSettings.isExplosionSoundOn()){
            playSound("assets/sounds/explosion.wav");
        }
    }

    public static void playGameOverSound(){

        if(SoundSettings.isGameResultSoundOn()){
            playSound("assets/sounds/gameover.wav");
        }
    }

    public static void playWinSound(){

        if(SoundSettings.isGameResultSoundOn()){
            playSound("assets/sounds/win.wav");
        }
    }
}