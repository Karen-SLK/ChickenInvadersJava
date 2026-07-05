package chickeninvaders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Bullet {

    private int x;
    private int y;

    private int width = 6;
    private int height = 15;

    private int speed = 8;

    public Bullet(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void update(){
        y -= speed;
    }

    public void draw(Graphics g){
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, width, height);
    }

    public boolean isOutOfScreen(){
        return y + height < 0;
    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, width, height);
    }
}
