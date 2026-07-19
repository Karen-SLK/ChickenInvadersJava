package chickeninvaders;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageManager {

    private static BufferedImage backgroundImage;
    private static BufferedImage airplaneImage;
    private static BufferedImage shotImage;

    private static BufferedImage eggImage;
    private static BufferedImage explosionImage;

    private static BufferedImage normalChickenImage;
    private static BufferedImage fastChickenImage;
    private static BufferedImage zigzagChickenImage;
    private static BufferedImage shooterChickenImage;

    private static BufferedImage bossLevel4Image;
    private static BufferedImage bossLevel8Image;

    public static void loadImages(){

        backgroundImage = loadImage("assets/image/background.png");

        airplaneImage = loadImage("assets/image/1.png");

        shotImage = loadImage("assets/image/shot.png");

        eggImage = loadImage("assets/image/egg.png");

        explosionImage = loadImage("assets/image/Explosion2.png");

        normalChickenImage = loadImage("assets/image/normal_chicken.png");

        fastChickenImage = loadImage("assets/image/fast_chicken.png");

        zigzagChickenImage = loadImage("assets/image/zigzag_chicken.png");

        shooterChickenImage = loadImage("assets/image/shooter_chicken.png");

        bossLevel4Image = loadImage("assets/image/boss1.png");

        bossLevel8Image = loadImage("assets/image/boss2.png");
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

    public static BufferedImage getAirplaneImage(){
        return airplaneImage;
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


}
