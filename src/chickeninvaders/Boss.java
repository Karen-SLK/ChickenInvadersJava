package chickeninvaders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Boss {

    private int x;
    private int y;

    private int width = 160;
    private int height = 90;

    private int health;
    private int maxHealth;

    private int speed;

    private int direction = 1;

    private int scoreValue;

    private int level;

    public Boss(int panelWidth, int level){

        this.level = level;

        x = panelWidth / 2 - width / 2;
        y = 60;

        if(level == 4){
            maxHealth = 50;
            speed = 2;
            scoreValue = 500;
        }
        else{
            maxHealth = 100;
            speed = 3;
            scoreValue = 1000;
        }

        health = maxHealth;
    }

    public void update(int panelwidth){

        x += direction * speed;

        if(x < 0){
            x = 0;
            direction = 1;
        }

        if(x + width > panelwidth){
            x = panelwidth - width;
            direction = -1;
        }
    }

    public void draw(Graphics g){

        BufferedImage bossImage = null;

        if(level == 4){
            bossImage = ImageManager.getBossLevel4Image();
        }
        else if(level == 8){
            bossImage = ImageManager.getBossLevel8Image();
        }

        if(bossImage != null){

            g.drawImage(
                    bossImage,
                    x,
                    y,
                    width,
                    height,
                    null
            );

            return;
        }

        if(level == 4){
            g.setColor(Color.ORANGE);
        }
        else{
            g.setColor(Color.RED);
        }

        g.fillRoundRect(x, y, width, height, 30, 30);

        g.setColor(Color.BLACK);
        g.drawString("BOSS", x + 60, y + 50);

        drawHealthBar(g);
    }

    private void drawHealthBar(Graphics g){

        int barWidth = width;
        int barHeight = 10;

        int healthWidth = (int)((double)health / maxHealth *barWidth);

        g.setColor(Color.GRAY);
        g.fillRect(x, y-18, barWidth, barHeight);

        g.setColor(Color.GREEN);
        g.fillRect(x, y-18, healthWidth, barHeight);

        g.setColor(Color.WHITE);
        g.drawRect(x, y - 18, barWidth, barHeight);
    }

    public boolean hit(){

        health--;

        return health <= 0;
    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, width, height);
    }

    public int getScoreValue(){
        return scoreValue;
    }
}
