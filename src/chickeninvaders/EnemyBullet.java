package chickeninvaders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class EnemyBullet {

    private int x;
    private int y;

    private int width =10;
    private int height =10;

    private int speedX;
    private int speedY;

    public EnemyBullet(int x, int y, int speedX, int speedY){

        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    public void update(){

        x += speedX;
        y += speedY;
    }

    public void draw(Graphics g){

        g.setColor(Color.RED);
        g.fillOval(x, y, width, height);
    }

    public boolean isOutOfScreen(int panelWidth, int panelHeight){
        return x + width < 0 || x > panelWidth || y > panelHeight;
    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, width, height);
    }
}
