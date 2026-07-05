package chickeninvaders;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.*;


public class GamePanel extends JPanel implements KeyListener, ActionListener {

    private GameMain gameMain;

    private Timer gameTimer;

    private int planeX;
    private int planeY;

    private int planeWidth = 50;
    private int planeHeight = 40;

    private int planeSpeed = 5;

    private boolean movingLeft;
    private boolean movingRight;
    private boolean movingUp;
    private boolean movingDown;

    private ArrayList<Bullet> bullets;

    private long lastShotTime;
    private int shotDelay = 300;

    public GamePanel(GameMain gameMain){

        this.gameMain = gameMain;

        setBackground(Color.DARK_GRAY);

        setFocusable(true);

        addKeyListener(this);

        gameTimer = new Timer(16,this);

        bullets = new ArrayList<>();
    }

    public void startGame(){

        planeX = getWidth() / 2 - planeWidth / 2;
        planeY = getHeight() - 100;

        movingLeft = false;
        movingRight = false;
        movingDown = false;
        movingUp = false;

        bullets.clear();

        lastShotTime = 0;

        requestFocusInWindow();

        gameTimer.start();
    }

    public void stopGame(){

        gameTimer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        updateGame();
        repaint();
    }

    public void updateGame(){

        updatePlane();
        updateBullets();
    }

    private void updatePlane(){

        if (movingLeft) {
            planeX -= planeSpeed;
        }

        if (movingRight) {
            planeX += planeSpeed;
        }

        if (movingDown) {
            planeY += planeSpeed;
        }

        if (movingUp) {
            planeY -= planeSpeed;
        }

        keepPlaneInsideWindow();
    }

    private void updateBullets(){

        for(int i = bullets.size() - 1; i>=0; --i){

            Bullet bullet = bullets.get(i);

            bullet.update();

            if(bullet.isOutOfScreen()){
                bullets.remove(i);
            }
        }
    }

    private void shootBullet(){

        long currentTime = System.currentTimeMillis();

        if(currentTime - lastShotTime < shotDelay){
            return;
        }

        int bulletX = planeX + planeWidth / 2 - 3;
        int bulletY = planeY;

        Bullet bullet = new Bullet(bulletX, bulletY);

        bullets.add(bullet);

        lastShotTime = currentTime;
    }

    private void keepPlaneInsideWindow(){

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        if(planeX < 0){
            planeX = 0;
        }
        if(planeX + planeWidth > panelWidth){
            planeX = panelWidth - planeWidth;
        }
        if(planeY < 0){
            planeY = 0;
        }
        if(planeY + planeHeight > panelHeight){
            planeY = panelHeight - planeHeight;
        }
    }

    @Override
    protected void paintComponent(Graphics g){

        super.paintComponent(g);

        drawHud(g);
        drawBullets(g);
        drawPlane(g);
    }

    private void drawHud(Graphics g){

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial",Font.BOLD,18));

        g.drawString("Level: 1",20,30);
        g.drawString("Score: 0",140,30);
        g.drawString("Lives: 3",270,30);
        g.drawString("Press SPACE to shoot", 410, 30);

        g.drawString("ESC: menu",620,30);
    }

    private void drawBullets(Graphics g){

        for(int i=0; i < bullets.size(); ++i){

            Bullet bullet = bullets.get(i);
            bullet.draw(g);
        }
    }

    private void drawPlane(Graphics g){

        g.setColor(Color.CYAN);

        int[] xPoints = {planeX + planeWidth/2,planeX,planeX + planeWidth};
        int[] yPoints ={planeY,planeY + planeHeight,planeY + planeHeight};

        g.fillPolygon(xPoints,yPoints,3);
    }

    @Override
    public void keyPressed(KeyEvent e){

        int key = e.getKeyCode();

        if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A){
            movingLeft = true;
        }
        if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D){
            movingRight = true;
        }
        if(key == KeyEvent.VK_UP || key == KeyEvent.VK_W){
            movingUp = true;
        }
        if(key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S){
            movingDown = true;
        }

        if(key == KeyEvent.VK_SPACE){
            shootBullet();
        }

        if(key == KeyEvent.VK_ESCAPE){
            gameMain.showMainMenu();
        }
    }

    @Override
    public void keyReleased(KeyEvent e){

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            movingLeft = false;
        }

        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            movingRight = false;
        }

        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
            movingUp = false;
        }

        if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
            movingDown = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }


}
