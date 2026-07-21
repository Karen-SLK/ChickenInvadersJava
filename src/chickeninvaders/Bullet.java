package chickeninvaders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/*
 * Represents a bullet fired by the player.
 * It moves upward each frame and provides bounds for collision detection.
 */

public class Bullet {

    private int x;
    private int y;

    private int width = 18;
    private int height = 32;

    private int speed = 8;

    public Bullet(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void update(){
        y -= speed;
    }

    public void draw(Graphics g){

        if(ImageManager.getShotImage() != null){

            g.drawImage(
                    ImageManager.getShotImage(),
                    x,
                    y,
                    width,
                    height,
                    null
            );
        }
        else{
            g.setColor(Color.YELLOW);
            g.fillRect(x, y, width, height);
        }
    }

    public boolean isOutOfScreen(){
        return y + height < 0;
    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, width, height);
    }
}
