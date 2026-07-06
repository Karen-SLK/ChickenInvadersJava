package chickeninvaders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Egg {

    private int x;
    private int y;

    private int width = 10;
    private int height = 14;

    private int speed = 4;

    public Egg(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void update(){
        y += speed;
    }

    public void draw(Graphics g){

        g.setColor(Color.WHITE);
        g.fillOval(x, y, width, height);
    }

    public boolean isOutOfScreen(int panelHeight){
        return y > panelHeight;
    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, width, height);
    }
}
