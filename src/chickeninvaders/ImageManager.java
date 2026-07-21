package chickeninvaders;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/*
 * Loads and stores all image assets used by the game.
 * Other classes get player, enemy, airplanes, boss, bullet, egg, background, and power-up images from here.
 */

public class ImageManager {

    private static BufferedImage backgroundImage;
    private static BufferedImage shotImage;

    private static BufferedImage eggImage;
    private static BufferedImage explosionImage;

    private static BufferedImage normalChickenImage;
    private static BufferedImage fastChickenImage;
    private static BufferedImage zigzagChickenImage;
    private static BufferedImage shooterChickenImage;

    private static BufferedImage bossLevel4Image;
    private static BufferedImage bossLevel8Image;

    private static BufferedImage planeDefaultImage;
    private static BufferedImage planeFastImage;
    private static BufferedImage planeHeavyImage;
    private static BufferedImage planeSniperImage;

    private static BufferedImage powerExtraLifeImage;
    private static BufferedImage powerAddFireImage;
    private static BufferedImage powerRapidFireImage;
    private static BufferedImage powerShieldImage;
    private static BufferedImage powerFreezeBombImage;

    public static void loadImages(){

        backgroundImage = loadImage("assets/image/background.png");

        shotImage = loadImage("assets/image/shot.png");

        eggImage = loadImage("assets/image/egg.png");

        explosionImage = loadImage("assets/image/Explosion2.png");

        normalChickenImage = loadImage("assets/image/normal_chicken.png");

        fastChickenImage = loadImage("assets/image/fast_chicken.png");

        zigzagChickenImage = loadImage("assets/image/zigzag_chicken.png");

        shooterChickenImage = loadImage("assets/image/shooter_chicken.png");

        bossLevel4Image = loadImage("assets/image/boss1.png");

        bossLevel8Image = loadImage("assets/image/boss2.png");

        planeDefaultImage = loadImage("assets/image/1.png");
        planeFastImage = loadImage("assets/image/4.png");
        planeHeavyImage = loadImage("assets/image/5.png");
        planeSniperImage = loadImage("assets/image/6.png");

        powerExtraLifeImage = loadImage("assets/image/health.png");
        powerAddFireImage = loadImage("assets/image/addfire.png");
        powerRapidFireImage = loadImage("assets/image/rapidfire.png");
        powerShieldImage = loadImage("assets/image/shield.png");
        powerFreezeBombImage = loadImage("assets/image/freeze.png");
    }

    private static BufferedImage loadImage(String filePath){

        try{
            File imageFile = new File(filePath);

            if(!imageFile.exists()){
                System.out.println("Image file not found: " + filePath );
                return null;
            }
            return ImageIO.read(imageFile);
        }
        catch (Exception e) {
            System.out.println("Error loading image: " + filePath);
            return null;
        }
    }

    public static BufferedImage getBackgroundImage(){
        return backgroundImage;
    }

    public static BufferedImage getShotImage(){
        return shotImage;
    }

    public static BufferedImage getEggImage(){
        return eggImage;
    }

    public static BufferedImage getExplosionImage(){
        return explosionImage;
    }

    public static BufferedImage getNormalChickenImage(){
        return normalChickenImage;
    }

    public static BufferedImage getFastChickenImage(){
        return fastChickenImage;
    }

    public static BufferedImage getZigzagChickenImage(){
        return zigzagChickenImage;
    }

    public static BufferedImage getShooterChickenImage(){
        return shooterChickenImage;
    }

    public static BufferedImage getBossLevel4Image(){
        return bossLevel4Image;
    }

    public static BufferedImage getBossLevel8Image(){
        return bossLevel8Image;
    }

    public static BufferedImage getPlaneDefaultImage(){
        return planeDefaultImage;
    }

    public static BufferedImage getPlaneFastImage(){
        return planeFastImage;
    }

    public static BufferedImage getPlaneHeavyImage(){
        return planeHeavyImage;
    }

    public static BufferedImage getPlaneSniperImage(){
        return planeSniperImage;
    }

    public static BufferedImage getPowerExtraLifeImage(){
        return powerExtraLifeImage;
    }

    public static BufferedImage getPowerAddFireImage(){
        return powerAddFireImage;
    }

    public static BufferedImage getPowerRapidFireImage(){
        return powerRapidFireImage;
    }

    public static BufferedImage getPowerShieldImage(){
        return powerShieldImage;
    }

    public static BufferedImage getPowerFreezeBombImage(){
        return powerFreezeBombImage;
    }


}
