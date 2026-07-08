package chickeninvaders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


public class Enemy {

    public static final String NORMAL = "NORMAL";
    public static final String FAST = "FAST";
    public static final String ZIGZAG = "ZIGZAG";
    public static final  String SHOOTER = "SHOOTER";

    private int x;
    private int y;

    private int width = 40;
    private int height = 35;

    private int health;

    private int scoreValue;

    private String type;

    private int zigzagCounter = 0;



    public Enemy(int x, int y, String type, int level){

        this.x = x;
        this.y = y;

        this.type = type;

        setupStats(level);
    }

    private void setupStats(int level){

        if(type.equals(NORMAL)){
            health = 2;
            scoreValue = 10;
        }
        else if(type.equals(FAST)) {
            health = 1;
            scoreValue = 15;
        }
        else if(type.equals(ZIGZAG)) {
            health = 2;
            scoreValue = 20;
        }
        else if(type.equals(SHOOTER)){
            health = 2;
            scoreValue = 25;
        }

        if(level >= 5){
            health++;
        }
    }

    public void update(int direction, int speed){

        int currentSpeed = speed;

        if(type.equals(FAST)){
            currentSpeed = speed * 2;
        }

        x += direction * currentSpeed;

        if(type.equals(ZIGZAG)){

            zigzagCounter++;

            if(zigzagCounter % 40 < 20){
                y += 1;
            }
            else{
                y -= 1;
            }
        }
    }

    public void moveDown(int amount){
        y += amount;
    }

    public void draw(Graphics g){

        if (type.equals(NORMAL)) {
            g.setColor(Color.WHITE);
        }
        else if (type.equals(FAST)) {
            g.setColor(Color.YELLOW);
        }
        else if (type.equals(ZIGZAG)) {
            g.setColor(Color.GREEN);
        }
        else if (type.equals(SHOOTER)) {
            g.setColor(Color.RED);
        }

        g.fillOval(x, y, width, height);

        g.setColor(Color.ORANGE);
        g.fillRect(x + width / 2 - 4, y + height / 2, 8, 6);

        g.setColor(Color.BLACK);
        g.drawString(getShortName(), x + 12, y + 22);
    }

    private String getShortName() {

        if (type.equals(NORMAL)) {
            return "N";
        } else if (type.equals(FAST)) {
            return "F";
        } else if (type.equals(ZIGZAG)) {
            return "Z";
        } else {
            return "S";
        }
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

    public String getType(){
        return type;
    }
}
