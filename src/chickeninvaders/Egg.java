package chickeninvaders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Egg {

    private int x;
    private int y;

    private int width = 18;
    private int height = 24;

    private int speed = 4;

    public Egg(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void update(){
        y += speed;
    }

    public void draw(Graphics g){

        if(ImageManager.getEggImage() != null){

            g.drawImage(ImageManager.getEggImage(),
                    x,
                    y,
                    width,
                    height,
                    null);
        }

        else{
            g.setColor(Color.WHITE);
            g.fillOval(x, y, width, height);
        }


    }

    public boolean isOutOfScreen(int panelHeight){
        return y > panelHeight;
    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, width, height);
    }
}
