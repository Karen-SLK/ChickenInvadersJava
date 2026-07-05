package chickeninvaders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


public class Enemy {

    private int x;
    private int y;

    private int width = 40;
    private int height = 35;

    private int health = 1;
    private int scoreValue = 10;

    public Enemy(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void update(int direction, int speed){
        x += direction * speed;
    }

    public void moveDown(int amount){
        y += amount;
    }

    public void draw(Graphics g){

        g.setColor(Color.WHITE);
        g.fillOval(x, y, width, height);

        g.setColor(Color.RED);
        g.fillOval(x+12, y-8, 16, 12);

        g.setColor(Color.ORANGE);
        g.fillRect(x + width / 2 - 4, y + height / 2, 8, 6);
    }

    public boolean hit(){

        health--;
        return health <= 0;
    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, width, height);
    }

    public int getX() {
        return x;
    }

    public int getWidth() {
        return width;
    }

    public int getScoreValue() {
        return scoreValue;
    }
}
