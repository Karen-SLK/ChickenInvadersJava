package chickeninvaders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

/*
 * Represents collectible power-ups that fall during gameplay.
 * Each power-up has a type and changes player abilities such as life, fire level, shield, rapid fire, or freeze.
 */

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

        BufferedImage powerUpImage = getPowerUpImage();

        if(powerUpImage != null){

            Graphics2D g2 = (Graphics2D) g.create();

            Ellipse2D.Double circleClip = new Ellipse2D.Double(x, y, width, height);

            g2.setClip(circleClip);

            g2.drawImage(
                    powerUpImage,
                    x,
                    y,
                    width,
                    height,
                    null
            );

            g2.dispose();

            return;
        }


        if(type.equals(EXTRA_LIFE)){
            g.setColor(Color.PINK);
        }
        else if(type.equals(ADD_FIRE)){
            g.setColor(Color.ORANGE);
        }
        else if(type.equals(RAPID_FIRE)){
            g.setColor(Color.YELLOW);
        }
        else if(type.equals(SHIELD)){
            g.setColor(Color.CYAN);
        }
        else if(type.equals(FREEZE_BOMB)){
            g.setColor(Color.BLUE);
        }
        else{
            g.setColor(Color.WHITE);
        }

        g.fillOval(x, y, width, height);

        g.setColor(Color.BLACK);
        g.drawString(getPowerUpLetter(), x + width / 2 - 4, y + height / 2 + 5);
    }

    private BufferedImage getPowerUpImage(){

        if(type.equals(EXTRA_LIFE)){
            return ImageManager.getPowerExtraLifeImage();
        }
        else if(type.equals(ADD_FIRE)){
            return ImageManager.getPowerAddFireImage();
        }
        else if(type.equals(RAPID_FIRE)){
            return ImageManager.getPowerRapidFireImage();
        }
        else if(type.equals(SHIELD)){
            return ImageManager.getPowerShieldImage();
        }
        else if(type.equals(FREEZE_BOMB)){
            return ImageManager.getPowerFreezeBombImage();
        }

        return null;
    }

    private String getPowerUpLetter(){

        if(type.equals(EXTRA_LIFE)){
            return "+";
        }
        else if(type.equals(ADD_FIRE)){
            return "F";
        }
        else if(type.equals(RAPID_FIRE)){
            return "R";
        }
        else if(type.equals(SHIELD)){
            return "S";
        }
        else if(type.equals(FREEZE_BOMB)){
            return "*";
        }

        return "?";
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
