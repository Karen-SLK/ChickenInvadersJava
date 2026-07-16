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

        int size = (maxLife - life) * 3 + 25;

        if(ImageManager.getExplosionImage() != null){

            g.drawImage(
                    ImageManager.getExplosionImage(),
                    x - size / 2,
                    y - size / 2,
                    size,
                    size,
                    null
            );
        }
        else{

            g.setColor(Color.ORANGE);
            g.fillOval(x - size / 2, y - size / 2, size, size);

            g.setColor(Color.RED);
            g.drawOval(x - size / 2, y - size / 2, size, size);
        }
    }

    public boolean isFinished(){
        return life <= 0;
    }
}
