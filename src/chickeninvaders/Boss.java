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

        if(level == 4){
            width = 220;
            height = 130;

            maxHealth = 50;
            health = maxHealth;

            scoreValue = 500;

            speed = 3;
        }
        else{
            width = 260;
            height = 150;

            maxHealth = 100;
            health = maxHealth;

            scoreValue = 1000;

            speed = 4;
        }

        x = panelWidth / 2 - width / 2;
        y = 90;

        direction = 1;
    }

    public void update(int panelWidth){

        x += direction * speed;

        if(x <= 0){
            x = 0;
            direction = 1;
        }

        if(x + width >= panelWidth){
            x = panelWidth - width;
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

            drawHealthBar(g);

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

        int barX = x;
        int barY = y + height + 10;

        double healthPercent = (double) health / maxHealth;

        if(healthPercent < 0){
            healthPercent = 0;
        }

        int currentBarWidth = (int)(barWidth * healthPercent);

        g.setColor(Color.RED);
        g.fillRect(barX, barY, barWidth, barHeight);

        g.setColor(Color.GREEN);
        g.fillRect(barX, barY, currentBarWidth, barHeight);

        g.setColor(Color.WHITE);
        g.drawRect(barX, barY, barWidth, barHeight);
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
