package chickeninvaders;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageManager {

    private static BufferedImage backgroundImage;
    private static BufferedImage airplaneImage;
    private static BufferedImage shotImage;

    public static void loadImages(){

        backgroundImage = loadImage("assets/image/background.png");
        airplaneImage = loadImage("assets/image/1.png");
        shotImage = loadImage("assets/image/shot.png");
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
}
