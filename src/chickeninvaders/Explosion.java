package chickeninvaders;

import java.awt.Color;
import java.awt.Graphics;

public class Explosion {

    private int x;
    private int y;

    private int life;
    private int maxLife = 20;

    public Explosion(int x, int y){

        this.x = x;
        this.y = y;

        this.life = maxLife;
    }

    public void update(){
        life--;
    }

    public void draw(Graphics g){

        int radius = (maxLife - life) * 2 +10;

        int drawX = x - radius / 2;
        int drawY = y - radius / 2;

        g.setColor(Color.ORANGE);

        g.fillOval(drawX, drawY, radius, radius);

        g.setColor(Color.RED);

        g.fillOval(drawX + radius / 4, drawY + radius / 4, radius / 2, radius / 2);
    }

    public boolean isFinished(){
        return life <= 0;
    }
}
