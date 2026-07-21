package chickeninvaders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/*
 * Represents normal enemy chickens in the game grid.
 * It stores enemy type, health, score value, cell position, and movement behavior.
 * It also supports respawn movement from the screen edge back to its target cell.
 */

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

    private int cellRow;

    private int cellCol;

    private int targetX;

    private int targetY;

    private boolean movingToCell;

    private int enterSpeed = 4;

    public Enemy(int x, int y, String type, int level){

        this.x = x;
        this.y = y;

        this.type = type;

        setupStats(level);

        this.cellRow = -1;
        this.cellCol = -1;
    }

    public Enemy(int x, int y, String type, int level, int cellRow, int cellCol){

        this(x, y, type, level);

        this.cellRow = cellRow;
        this.cellCol = cellCol;

        this.targetX = x;
        this.targetY = y;

        this.movingToCell = false;
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

    private BufferedImage getEnemyImage(){

        if(type.equals(NORMAL)){
            return ImageManager.getNormalChickenImage();
        }
        else if(type.equals(FAST)){
            return ImageManager.getFastChickenImage();
        }
        else if(type.equals(ZIGZAG)){
            return ImageManager.getZigzagChickenImage();
        }
        else if(type.equals(SHOOTER)){
            return ImageManager.getShooterChickenImage();
        }

        return null;
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

        BufferedImage enemyImage = getEnemyImage();

        if(enemyImage != null){

            g.drawImage(
                    enemyImage,
                    x,
                    y,
                    width,
                    height,
                    null
            );
        }

        else {
            if (type.equals(NORMAL)) {
                g.setColor(Color.WHITE);
            } else if (type.equals(FAST)) {
                g.setColor(Color.YELLOW);
            } else if (type.equals(ZIGZAG)) {
                g.setColor(Color.GREEN);
            } else if (type.equals(SHOOTER)) {
                g.setColor(Color.RED);
            }

            g.fillOval(x, y, width, height);

            g.setColor(Color.ORANGE);
            g.fillRect(x + width / 2 - 4, y + height / 2, 8, 6);

            g.setColor(Color.BLACK);
            g.drawString(getShortName(), x + 12, y + 22);
        }
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

    public int getY(){
        return y;
    }

    public int getHeight(){
        return height;
    }

    public int getCellRow(){
        return cellRow;
    }

    public int getCellCol(){
        return cellCol;
    }

    public void setTargetCellPosition(int targetX, int targetY){

        this.targetX = targetX;
        this.targetY = targetY;
    }

    public void setMovingToCell(boolean movingToCell){

        this.movingToCell = movingToCell;
    }

    public boolean isMovingToCell(){

        return movingToCell;
    }

    public void moveToTargetCell(){

        int dx = targetX - x;
        int dy = targetY - y;

        double distance = Math.sqrt(dx * dx + dy * dy);

        if(distance <= enterSpeed){

            x = targetX;
            y = targetY;

            movingToCell = false;

            return;
        }

        x += (int)Math.round(enterSpeed * dx / distance);
        y += (int)Math.round(enterSpeed * dy / distance);
    }
}
