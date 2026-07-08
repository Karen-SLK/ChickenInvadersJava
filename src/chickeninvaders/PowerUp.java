package chickeninvaders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class PowerUp {

    public static final String EXTRA_LIFE = "EXTRA_LIFE";
    public static final String ADD_FIRE = "ADD_FIRE";

    public static final String RAPID_FIRE = "RAPID_FIRE";
    public static final String  SHIELD = "SHIELD";
    public static final String FREEZE_BOMB = "FREEZE_BOMB";

    private int x;
    private int y;

    private int width = 25;
    private int height = 25;

    private int speed = 2;

    private String type;

    public PowerUp(int x, int y, String type){

        this.x = x;
        this.y = y;
        this.type = type;
    }

    public void update(){
        y += speed;
    }

    public void draw(Graphics g){

        if(type.equals(EXTRA_LIFE)){
            g.setColor(Color.PINK);
        }
        else if(type.equals(ADD_FIRE)) {
            g.setColor(Color.MAGENTA);
        }
        else if(type.equals(RAPID_FIRE)){
            g.setColor(Color.YELLOW);
        }
        else if(type.equals(SHIELD)) {
            g.setColor(Color.CYAN);
        }
        else if(type.equals(FREEZE_BOMB)){
            g.setColor(Color.BLUE);
        }

        g.fillOval(x, y, width, height);

        g.setColor(Color.WHITE);

        if (type.equals(EXTRA_LIFE)) {

            g.drawString("+", x + 8, y + 18);
        }
        else if (type.equals(ADD_FIRE)) {

            g.drawString("F", x + 8, y + 18);
        }
        else if (type.equals(RAPID_FIRE)) {
            g.drawString("R", x + 8, y + 18);
        }
        else if (type.equals(SHIELD)) {
            g.drawString("S", x + 8, y + 18);
        }
        else if (type.equals(FREEZE_BOMB)) {
            g.drawString("Z", x + 8, y + 18);
        }
    }

    public boolean isOutOfScreen(int panelHeight){
        return y > panelHeight;
    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, width, height);
    }

    public String getType(){
        return type;
    }
}
