package chickeninvaders;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.*;
import java.util.Random;


public class GamePanel extends JPanel implements KeyListener, ActionListener {

    private GameMain gameMain;

    private Timer gameTimer;

    private int planeX;
    private int planeY;

    private int planeWidth = 70;
    private int planeHeight = 55;

    private int planeSpeed = 5;

    private boolean movingLeft;
    private boolean movingRight;
    private boolean movingUp;
    private boolean movingDown;

    private ArrayList<Bullet> bullets;

    private ArrayList<Enemy> enemies;

    private ArrayList<Egg> eggs;

    private ArrayList<PowerUp> powerUps;

    private ArrayList<Explosion> explosions;

    private ArrayList<EnemyBullet> enemyBullets;

    private Random random;

    private long lastEggTime;

    private int eggDelay = 1500;

    private int lives = 3;

    private boolean gameOver;

    private long lastShotTime;

    private int shotDelay = 300;

    private int enemySpeed = 1;

    private int enemyDirection = 1;

    private int enemyDownStep = 20;

    private int score;

    private Boss boss;

    private long lastBossShotTime;

    private int bossShotDelay = 1500;

    private boolean rapidFireActive;

    private long rapidFireEndTime;

    private boolean shieldActive;

    private long shieldEndTime;

    private boolean freezeActive;

    private long freezeEndTime;

    private int rapidShotDelay = 100;

    private long lastShooterShotTime;

    private int shooterShotDelay = 2000;

    private int level = 1;
    private int maxLevel = 8;

    private boolean win;

    private int fireLevel = 1;
    private int maxFireLevel = 5;

    private boolean scoreSaved;

    private boolean paused;

    private int enemyRows = 5;

    private int enemyCols = 8;

    private int enemyStartX = 80;

    private int enemyStartY = 80;

    private int enemyGapX = 80;

    private int enemyGapY = 55;

    private int[][] cellCounters;

    private int enemyGridOffsetX = 0;

    private int enemyGridOffsetY = 0;

    public GamePanel(GameMain gameMain){

        this.gameMain = gameMain;

        setBackground(Color.DARK_GRAY);

        setFocusable(true);

        addKeyListener(this);

        gameTimer = new Timer(16,this);

        bullets = new ArrayList<>();

        enemies = new ArrayList<>();

        eggs = new ArrayList<>();

        powerUps = new ArrayList<>();

        explosions = new ArrayList<>();

        enemyBullets = new ArrayList<>();

        random = new Random();
    }

    public void startGame(){

        planeX = getWidth() / 2 - planeWidth / 2;
        planeY = getHeight() - 100;

        movingLeft = false;
        movingRight = false;
        movingDown = false;
        movingUp = false;

        bullets.clear();

        enemies.clear();

        eggs.clear();

        powerUps.clear();

        explosions.clear();

        enemyBullets.clear();

        boss = null;

        score = 0;

        level = 1;

        win = false;

        lives = 3;

        fireLevel = 1;

        rapidFireActive = false;

        shieldActive = false;

        freezeActive = false;

        rapidFireEndTime = 0;

        shieldEndTime = 0;

        freezeEndTime = 0;

        gameOver = false;

        paused = false;

        scoreSaved = false;

        enemyDirection = 1;

        enemyGridOffsetX = 0;
        enemyGridOffsetY = 0;

        setupLevel();

        if(isBossLevel()){
            createBoss();
        }
        else{
            setupCellCounters();

            createEnemies();
        }

        lastShotTime = 0;

        lastEggTime = System.currentTimeMillis();

        lastShooterShotTime = System.currentTimeMillis();

        lastBossShotTime = System.currentTimeMillis();

        requestFocusInWindow();

        gameTimer.start();
    }

    private void setupLevel(){

        if(level == 1){
            enemySpeed = 1;
            enemyDownStep = 20;
            eggDelay = 3000;
            shooterShotDelay = 2500;
        }

        else if(level == 2) {
            enemySpeed = 2;
            enemyDownStep = 20;
            eggDelay = 2500;
            shooterShotDelay = 2400;
        }

        else if(level == 3) {
            enemySpeed = 2;
            enemyDownStep = 25;
            eggDelay = 2200;
            shooterShotDelay = 2200;
        }

        else if(level == 4){
            enemySpeed = 2;
            enemyDownStep = 25;
            eggDelay = 2000;
            shooterShotDelay = 2000;
        }

        else if(level == 5){
            enemySpeed = 3;
            enemyDownStep = 25;
            eggDelay = 1800;
            shooterShotDelay = 1800;
        }

        else if(level == 6){
            enemySpeed = 3;
            enemyDownStep = 30;
            eggDelay = 1600;
            shooterShotDelay = 1600;
        }

        else if(level == 7){
            enemySpeed = 3;
            enemyDownStep = 30;
            eggDelay = 1400;
            shooterShotDelay = 1400;
        } else if (level == 8) {

            enemySpeed = 4;
            enemyDownStep = 30;
            eggDelay = 1200;
            shooterShotDelay = 1200;
        }
    }

    public void stopGame(){

        gameTimer.stop();
    }

    public boolean isBossLevel(){
        return level == 4 || level == 8;
    }

    private void setupCellCounters(){

        cellCounters = new int[enemyRows][enemyCols];

        int initialCounter = getInitialCellCounter();

        for(int row = 0; row < enemyRows; row++){

            for(int col = 0; col < enemyCols; col++){

                cellCounters[row][col] = initialCounter;
            }
        }
    }

    private int getInitialCellCounter(){

        if(level == 1){
            return 2;
        }
        else if(level == 2){
            return 2;
        }
        else if(level == 3){
            return 3;
        }
        else if(level == 5){
            return 3;
        }
        else if(level == 6){
            return 4;
        }
        else if(level == 7){
            return 4;
        }

        return 0;
    }

    private Enemy createEnemyForCell(int row, int col){

        int enemyX = getEnemyCellX(col);

        int enemyY = getEnemyCellY(row);

        String enemyType = chooseEnemyType(row, col);

        Enemy enemy = new Enemy(enemyX, enemyY, enemyType, level, row, col);

        return enemy;
    }

    public void createEnemies(){

        for(int row = 0; row < enemyRows; row++){

            for(int col = 0; col < enemyCols; col++){

                Enemy enemy = createEnemyForCell(row, col);

                enemies.add(enemy);
            }
        }
    }

    public void createBoss(){

        enemies.clear();

        boss = new Boss(getWidth(),level);

        if(level == 4){
            bossShotDelay = 1500;
        }
        else{
            bossShotDelay = 1000;
        }

        lastBossShotTime = System.currentTimeMillis();
    }

    private String chooseEnemyType(int row, int col) {

        if (level == 1) {

            return Enemy.NORMAL;
        }

        if (level == 2) {

            if (col % 2 == 0) {
                return Enemy.NORMAL;
            }
            else {
                return Enemy.FAST;
            }
        }

        if (level == 3) {

            if(row % 2 == 0){
                return Enemy.NORMAL;
            }
            else{
                return Enemy.ZIGZAG;
            }
        }

        if(level == 4){

            if(col % 4 == 0){
                return Enemy.SHOOTER;
            }
            else{
                return Enemy.NORMAL;
            }
        }

        if(level == 5){

            if(col % 3 == 0){
                return Enemy.SHOOTER;
            }
            else if(row % 2 == 0){
                return Enemy.FAST;
            }
            else{
                return Enemy.NORMAL;
            }
        }

        if(level == 6){

            if(col % 3 == 0){
                return Enemy.SHOOTER;
            }
            else if(row % 2 == 0) {
                return Enemy.ZIGZAG;
            }
            else{
                return Enemy.FAST;
            }
        }

        if(level == 7){

            if(col % 2 == 0){
                return Enemy.SHOOTER;
            }
            else if(row % 2 == 0){
                return Enemy.ZIGZAG;
            }
            else{
                return Enemy.FAST;
            }
        }

        if(level == 8){

            if(col % 2 ==0){
                return Enemy.SHOOTER;
            }
            else{
                return Enemy.ZIGZAG;
            }
        }

        return Enemy.NORMAL;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        updateGame();
        repaint();
    }

    public void updateGame(){

        if(gameOver || win){
            return;
        }

        if(paused){
            return;
        }

        updateTemporaryPowerUps();

        updatePlane();
        updateBullets();

       if(!freezeActive){

           if(boss != null){
               updateBoss();
               updateBossAttacks();
           }
           else{
               updateEnemies();

               checkEnemiesReachedBottom();

               if(gameOver){
                   return;
               }

               updateEnemyEggDropping();

               updateShooterAttacks();
           }

           updateEggs();

           updateEnemyBullets();
       }

        updatePowerUps();

        updateExplosions();

        checkBulletEnemyCollision();

        checkEggPlaneCollision();

        checkEnemyBulletPlaneCollision();

        checkPowerUpPlaneCollision();

        checkLevelFinished();

    }

    private void saveFinalScore(){

        if(scoreSaved){
            return;
        }

        int finalLevel = level;

        if(finalLevel > maxLevel){
            finalLevel = maxLevel;
        }

        User currentUser = gameMain.getCurrentUser();

        if(currentUser != null){

            String username = currentUser.getUsername();

            HighScoreManager.saveScore(username, score, finalLevel);

            if(score > currentUser.getHighestScore()){

                currentUser.setHighestScore(score);
            }

            currentUser.setLastLevel(finalLevel);

            currentUser.setBackgroundMusicOn(SoundSettings.isBackgroundMusicOn());
            currentUser.setShotSoundOn(SoundSettings.isShotSoundOn());
            currentUser.setExplosionSoundOn(SoundSettings.isExplosionSoundOn());
            currentUser.setGameResultSoundOn(SoundSettings.isGameResultSoundOn());

            UserManager.updateUser(currentUser);
        }
        else{
            HighScoreManager.saveScore("Player", score, finalLevel);
        }

        scoreSaved = true;
    }

    private void checkLevelFinished(){

        if(isBossLevel()){

            if(boss != null){
                return;
            }
        }
        else{

            if(enemies.size() > 0){
                return;
            }
        }

        score += 200;
        level++;

        if(level > maxLevel){

            win = true;

            gameTimer.stop();

            SoundManager.playWinSound();

            saveFinalScore();

            return;
        }

        bullets.clear();

        eggs.clear();

        enemyBullets.clear();

        powerUps.clear();

        boss = null;

        enemyDirection = 1;

        enemyGridOffsetX = 0;
        enemyGridOffsetY = 0;

        setupLevel();

        if(isBossLevel()){
            createBoss();
        }
        else{
            setupCellCounters();

            createEnemies();
        }

        lastEggTime = System.currentTimeMillis();

        lastShooterShotTime = System.currentTimeMillis();

        lastBossShotTime = System.currentTimeMillis();
    }

    private void checkEnemiesReachedBottom(){

        if(boss != null){
            return;
        }

        for(int i = 0; i < enemies.size(); i++){

            Enemy enemy = enemies.get(i);

            if(enemy.isMovingToCell()){

                continue;
            }

            if(enemy.getY() + enemy.getHeight() >= getHeight()){

                gameOver = true;

                gameTimer.stop();

                SoundManager.playGameOverSound();

                saveFinalScore();

                return;
            }
        }
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

    private void updateShooterAttacks(){

        long currentTime = System.currentTimeMillis();

        if(currentTime - lastShooterShotTime < shooterShotDelay){
            return;
        }

        ArrayList<Enemy> shooters = new ArrayList<>();

        for(int i = 0; i < enemies.size(); i++){

            Enemy enemy = enemies.get(i);

            if(enemy.isMovingToCell()){

                continue;
            }

            if(enemy.getType().equals(Enemy.SHOOTER)){
                shooters.add(enemy);
            }
        }

        if(shooters.size() == 0){
            return;
        }

        int shooterIndex = random.nextInt(shooters.size());

        Enemy shooter = shooters.get(shooterIndex);

        Rectangle shooterBounds = shooter.getBounds();

        int shooterCenterX = shooterBounds.x + shooterBounds.width / 2;

        int planeCenterX = planeX + planeWidth / 2;

        int speedX = 0;

        if(planeCenterX < shooterCenterX - 20) {
            speedX = -3;
        }
        else if(planeCenterX > shooterCenterX + 20) {
            speedX = 3;
        }

        int speedY = 4;

        EnemyBullet enemyBullet = new EnemyBullet(
                shooterCenterX - 5,
                shooterBounds.y + shooterBounds.height,
                speedX,
                speedY
        );

        enemyBullets.add(enemyBullet);

        lastShooterShotTime = currentTime;
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

    private void updateEnemyBullets(){

        for(int i = enemyBullets.size() - 1; i>=0; --i){

            EnemyBullet enemyBullet = enemyBullets.get(i);

            enemyBullet.update();

            if(enemyBullet.isOutOfScreen(getWidth(), getHeight())){
                enemyBullets.remove(i);
            }
        }
    }

    private void updateEnemies(){

        boolean hitEdge = false;
        boolean normalEnemyMoved = false;

        for(int i = 0; i < enemies.size(); i++){

            Enemy enemy = enemies.get(i);

            if(enemy.isMovingToCell()){

                int targetX = getEnemyCellX(enemy.getCellCol());
                int targetY = getEnemyCellY(enemy.getCellRow());

                enemy.setTargetCellPosition(targetX, targetY);

                enemy.moveToTargetCell();

                continue;
            }

            enemy.update(enemyDirection, enemySpeed);
            normalEnemyMoved = true;

            if(enemy.getX() < 0 || enemy.getX() + enemy.getWidth() > getWidth()){

                hitEdge = true;
            }
        }

        if(normalEnemyMoved){
            enemyGridOffsetX += enemyDirection * enemySpeed;
        }

        if(hitEdge){

            enemyDirection = enemyDirection * -1;

            for(int i = 0; i < enemies.size(); i++){

                Enemy enemy = enemies.get(i);

                if(enemy.isMovingToCell()){

                    continue;
                }

                enemy.moveDown(enemyDownStep);
            }

            enemyGridOffsetY += enemyDownStep;
        }
    }
    private void updateEggs(){

        for(int i = eggs.size() - 1; i>=0; i--){

            Egg egg = eggs.get(i);

            egg.update();

            if(egg.isOutOfScreen(getHeight())){
                eggs.remove(i);
            }
        }
    }

    private void updateEnemyEggDropping(){

        if(enemies.size() == 0){
            return;
        }

        long currentTime = System.currentTimeMillis();

        if(currentTime - lastEggTime < eggDelay){
            return;
        }

        ArrayList<Enemy> readyEnemies = new ArrayList<>();

        for(int i = 0; i < enemies.size(); i++){

            Enemy enemy = enemies.get(i);

            if(enemy.isMovingToCell()){

                continue;
            }

            readyEnemies.add(enemy);
        }

        if(readyEnemies.size() == 0){
            return;
        }

        int enemyIndex = random.nextInt(readyEnemies.size());

        Enemy enemy = readyEnemies.get(enemyIndex);

        Rectangle enemyBounds = enemy.getBounds();

        int eggX = enemyBounds.x + enemyBounds.width / 2 - 5;
        int eggY = enemyBounds.y + enemyBounds.height;

        Egg egg = new Egg(eggX, eggY);

        eggs.add(egg);

        lastEggTime = currentTime;
    }

    private void updatePowerUps(){

        for(int i = powerUps.size() - 1; i>=0; --i){

            PowerUp powerUp =   powerUps.get(i);

            powerUp.update();

            if(powerUp.isOutOfScreen(getHeight())){
                powerUps.remove(i);
            }
        }
    }

    private void updateExplosions(){

        for(int i = explosions.size() - 1; i >= 0; --i){

            Explosion explosion = explosions.get(i);

            explosion.update();

            if(explosion.isFinished()){
                explosions.remove(i);
            }
        }
    }

    private void updateTemporaryPowerUps(){

        long currentTime = System.currentTimeMillis();

        if(rapidFireActive && currentTime >= rapidFireEndTime){

            rapidFireActive = false;
        }

        if(shieldActive && currentTime >= shieldEndTime){

            shieldActive = false;
        }

        if(freezeActive && currentTime >= freezeEndTime){

            freezeActive = false;
        }
    }

    private void updateBoss(){
        if(boss != null){
            boss.update(getWidth());
        }
    }

    private void updateBossAttacks(){

       if (boss == null){
           return;
       }

       long currentTime = System.currentTimeMillis();

       if(currentTime - lastBossShotTime < bossShotDelay){
           return;
       }

       Rectangle bossBounds = boss.getBounds();

       int bossCenterX = bossBounds.x + bossBounds.width / 2;
       int bossCenterY = bossBounds.y + bossBounds.height / 2;

       if(level == 4){

           addBossBullet(bossCenterX, bossCenterY, 0, -4);

           addBossBullet(bossCenterX, bossCenterY, 0, 4);

           addBossBullet(bossCenterX, bossCenterY, -4, 0);

           addBossBullet(bossCenterX, bossCenterY, 4, 0);
       }

       else if(level == 8){

           addBossBullet(bossCenterX, bossCenterY, 0, -5);

           addBossBullet(bossCenterX, bossCenterY, 4, -4);

           addBossBullet(bossCenterX, bossCenterY, 5, 0);

           addBossBullet(bossCenterX, bossCenterY, 4, 4);

           addBossBullet(bossCenterX, bossCenterY, 0, 5);

           addBossBullet(bossCenterX, bossCenterY, -4, 4);

           addBossBullet(bossCenterX, bossCenterY, -5, 0);

           addBossBullet(bossCenterX, bossCenterY, -4, -4);
       }

       lastBossShotTime = currentTime;
    }

    private void addBossBullet(int x, int y, int speedX, int speedY){

        EnemyBullet bullet = new EnemyBullet(
                x-5,
                y-5,
                speedX,
                speedY
        );

        enemyBullets.add(bullet);
    }

    private Enemy createRespawnEnemyForCell(int row, int col){

        int targetX = getEnemyCellX(col);
        int targetY = getEnemyCellY(row);

        String enemyType = chooseEnemyType(row, col);

        int panelWidth = getWidth();

        if(panelWidth <= 0){
            panelWidth = 800;
        }

        int startX;

        if(col < enemyCols / 2){

            startX = -80;
        }
        else{

            startX = panelWidth + 80;
        }

        int startY = 40;

        Enemy enemy = new Enemy(startX, startY, enemyType, level, row, col);

        enemy.setTargetCellPosition(targetX, targetY);

        enemy.setMovingToCell(true);

        return enemy;
    }

    private void spawnPowerUp(int x, int y){

        int chance = random.nextInt(100);

        if(chance >= 20){
            return;
        }

        String type;

        int randomType = random.nextInt(5);

        if(randomType == 0){
            type = PowerUp.EXTRA_LIFE;
        }
        else if(randomType == 1){
            type = PowerUp.ADD_FIRE;
        }
        else if(randomType == 2){
            type = PowerUp.RAPID_FIRE;
        }
        else if(randomType == 3){
            type = PowerUp.SHIELD;
        }
        else{
            type = PowerUp.FREEZE_BOMB;
        }

        PowerUp powerUp = new PowerUp(x, y, type);

        powerUps.add(powerUp);
    }

    private void checkBulletEnemyCollision(){

        for(int i = bullets.size() - 1; i>=0; --i){

            Bullet bullet = bullets.get(i);

            Rectangle bulletBounds = bullet.getBounds();

            if(boss != null && bulletBounds.intersects(boss.getBounds())){

                bullets.remove(i);

                boolean bossDead = boss.hit();

                if(bossDead){

                    Rectangle bossBounds = boss.getBounds();

                    int explosionX = bossBounds.x + bossBounds.width / 2;
                    int explosionY = bossBounds.y + bossBounds.height / 2;

                    Explosion explosion = new Explosion(explosionX, explosionY);

                    explosions.add(explosion);

                    SoundManager.playBossDeadSound();

                    score += boss.getScoreValue();

                    boss = null;
                }

                continue;
            }

            for(int j = enemies.size() - 1; j>=0; --j){

                Enemy enemy = enemies.get(j);

                Rectangle enemyBounds = enemy.getBounds();

                if(bulletBounds.intersects(enemyBounds)){

                    bullets.remove(i);

                    boolean enemyDead = enemy.hit();

                    if(enemyDead){

                        score += enemy.getScoreValue();

                        Rectangle enemyBoundsForPowerUp = enemy.getBounds();

                        int explosionX = enemyBoundsForPowerUp.x + enemyBoundsForPowerUp.width / 2;

                        int explosionY = enemyBoundsForPowerUp.y + enemyBoundsForPowerUp.height / 2;

                        Explosion explosion = new Explosion(explosionX, explosionY);

                        explosions.add(explosion);

                        SoundManager.playChickenDeadSound();

                        spawnPowerUp(enemyBoundsForPowerUp.x, enemyBoundsForPowerUp.y);

                        int cellRow = enemy.getCellRow();

                        int cellCol = enemy.getCellCol();

                        enemies.remove(j);

                        if(cellRow >= 0 && cellRow < enemyRows && cellCol >= 0 && cellCol < enemyCols){

                            cellCounters[cellRow][cellCol]--;

                            if(cellCounters[cellRow][cellCol] > 0){

                                Enemy newEnemy = createRespawnEnemyForCell(cellRow, cellCol);

                                enemies.add(newEnemy);
                            }
                        }
                    }

                    break;
                }
            }
        }
    }

    private void checkEggPlaneCollision(){

        Rectangle planeBounds = new Rectangle(planeX, planeY, planeWidth, planeHeight);

        for(int i = eggs.size() -1; i>=0; --i){

            Egg egg = eggs.get(i);

            Rectangle eggBounds = egg.getBounds();

            if(eggBounds.intersects(planeBounds)){

                eggs.remove(i);

                int explosionX = planeX + planeWidth / 2;
                int explosionY = planeY + planeHeight / 2;

                Explosion explosion = new Explosion(explosionX, explosionY);

                explosions.add(explosion);

                SoundManager.playExplosionSound();

                if(!shieldActive){

                    lives--;

                    if (lives <= 0) {
                        gameOver = true;
                        gameTimer.stop();
                        SoundManager.playGameOverSound();
                        saveFinalScore();
                    }
                }
            }
        }
    }

    private void checkEnemyBulletPlaneCollision(){

        Rectangle planeBounds = new Rectangle(planeX, planeY, planeWidth, planeHeight);

        for(int i = enemyBullets.size() - 1; i>=0; i--){

            EnemyBullet enemyBullet = enemyBullets.get(i);

            Rectangle bulletBounds = enemyBullet.getBounds();

            if(bulletBounds.intersects(planeBounds)){

                enemyBullets.remove(i);

                int explosionX = planeX + planeWidth / 2;
                int explosionY = planeY + planeHeight / 2;

                Explosion explosion = new Explosion(explosionX, explosionY);

                explosions.add(explosion);

                SoundManager.playExplosionSound();

                if(!shieldActive) {

                    lives--;

                    if (lives <= 0) {
                        gameOver = true;
                        gameTimer.stop();
                        SoundManager.playGameOverSound();
                        saveFinalScore();
                    }
                }
            }
        }
    }

    private void checkPowerUpPlaneCollision(){

        Rectangle planeBounds = new Rectangle(planeX, planeY, planeWidth, planeHeight);

        for(int i = powerUps.size() - 1; i >= 0; --i){

            PowerUp powerUp = powerUps.get(i);

            Rectangle powerUpBounds = powerUp.getBounds();

            if(powerUpBounds.intersects(planeBounds)){

                applyPowerUp(powerUp);

                powerUps.remove(i);
            }
        }
    }

    private void applyPowerUp(PowerUp powerUp){

        String type = powerUp.getType();

        long currentTime = System.currentTimeMillis();

        if(type.equals(PowerUp.EXTRA_LIFE)){

            if(lives < 5){
                lives++;
            }
        }

        else if(type.equals(PowerUp.ADD_FIRE)){

            if(fireLevel < maxFireLevel){
                fireLevel++;
            }
        }

        else if(type.equals(PowerUp.RAPID_FIRE)){

            rapidFireActive = true;

            rapidFireEndTime = currentTime + 8000;
        }

        else if(type.equals(PowerUp.SHIELD)){

            shieldActive = true;

            shieldEndTime = currentTime + 10000;
        }

        else if(type.equals(PowerUp.FREEZE_BOMB)){

            freezeActive = true;

            freezeEndTime = currentTime + 3000;
        }
    }

    private void shootBullet(){

        long currentTime = System.currentTimeMillis();

        int currentShotDelay = shotDelay;

        if(rapidFireActive){
            currentShotDelay = rapidShotDelay;
        }

        if(currentTime - lastShotTime < currentShotDelay){
            return;
        }

        int bulletY = planeY;

        int spacing = 12;

        int startX = planeX + planeWidth / 2 - ((fireLevel - 1) * spacing) / 2;

        for(int i = 0; i < fireLevel; i++){

            int bulletX = startX + i * spacing - 3;

            Bullet bullet = new Bullet(bulletX, bulletY);

            bullets.add(bullet);
        }

        SoundManager.playShotSound();

        lastShotTime = currentTime;
    }

    private int getEnemyCellX(int col){

        return enemyStartX + col * enemyGapX + enemyGridOffsetX;
    }

    private int getEnemyCellY(int row){

        return enemyStartY + row * enemyGapY + enemyGridOffsetY;
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

        drawBackground(g);

        drawHud(g);
        drawEnemies(g);
        drawBoss(g);
        drawEggs(g);
        drawEnemyBullets(g);
        drawPowerUps(g);
        drawExplosions(g);
        drawBullets(g);
        drawPlane(g);
        drawShield(g);

        if(gameOver){
            drawGameOver(g);
        }

        if(win){
            drawWin(g);
        }

        if(paused){
            drawPaused(g);
        }
    }

    private void drawHud(Graphics g){

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial",Font.BOLD,18));

        String username = "Guest";

        if(gameMain.getCurrentUser() != null){

            username = gameMain.getCurrentUser().getUsername();
        }

        g.drawString("User: " + username, 20, 25);

        g.drawString("Level: " + level, 20, 50);

        g.drawString("Score: " + score, 130, 50);

        g.drawString("Lives: " + lives, 250, 50);

        g.drawString("Fire: " + fireLevel, 360, 50);

        g.drawString("SPACE: shoot", 470, 50);

        g.drawString("P: pause", 630, 50);

        g.drawString("ESC: menu", 630, 75);

        String status = "";

        if(rapidFireActive){
            status += "Rapid ";
        }

        if(shieldActive){
            status += "Shield ";
        }

        if(freezeActive){
            status += "Freeze ";
        }

        if(!status.equals("")){
            g.drawString(status, 20, 100);
        }
    }

    private void drawEnemies(Graphics g){

        for(int i=0; i<enemies.size(); ++i){

            Enemy enemy = enemies.get(i);

            enemy.draw(g);
        }
    }

    private void drawBullets(Graphics g){

        for(int i=0; i < bullets.size(); ++i){

            Bullet bullet = bullets.get(i);
            bullet.draw(g);
        }
    }

    private void drawEnemyBullets(Graphics g){

        for(int i = 0; i<enemyBullets.size(); ++i){

            EnemyBullet enemyBullet = enemyBullets.get(i);

            enemyBullet.draw(g);
        }
    }

    private void drawPlane(Graphics g){

        if(ImageManager.getAirplaneImage() != null){

            g.drawImage(
                    ImageManager.getAirplaneImage(),
                    planeX,
                    planeY,
                    planeWidth,
                    planeHeight,
                    null
            );
        }
        else{
            g.setColor(Color.CYAN);

            int[] xPoints = {planeX + planeWidth / 2, planeX, planeX + planeWidth};
            int[] yPoints = {planeY, planeY + planeHeight, planeY + planeHeight};

            g.fillPolygon(xPoints, yPoints, 3);
        }
    }

    private void drawBackground(Graphics g){

        if(ImageManager.getBackgroundImage() != null){

            g.drawImage(
                    ImageManager.getBackgroundImage(),
                    0,
                    0,
                    getWidth(),
                    getHeight(),
                    null
            );
        }
        else{
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    private void drawEggs(Graphics g){

        for(int i = 0; i < eggs.size(); ++i){

            Egg egg = eggs.get(i);

            egg.draw(g);
        }
    }

    private void drawBoss(Graphics g){

        if(boss != null){
            boss.draw(g);
        }
    }

    private void drawGameOver(Graphics g){

        g.setColor(Color.RED);

        g.setFont(new Font("Arial", Font.BOLD, 50));

        g.drawString("GAME OVER", 250, 280);

        g.setFont(new Font("Arial", Font.BOLD, 20));

        g.drawString("Press ESC to return menu", 280, 320);
    }

    private void drawWin(Graphics g){

        g.setColor(Color.GREEN);

        g.setFont(new Font("Arial", Font.BOLD, 50));

        g.drawString("YOU WIN!", 270, 280);

        g.setFont(new Font("Arial", Font.BOLD, 20));

        g.drawString("Press ESC to return menu", 280, 320);
    }

    private void drawPowerUps(Graphics g){

        for(int i = 0; i < powerUps.size(); ++i){

            PowerUp powerUp = powerUps.get(i);

            powerUp.draw(g);
        }
    }

    private void drawPaused(Graphics g){

        g.setColor(Color.YELLOW);

        g.setFont(new Font("Arial",Font.BOLD,50));

        g.drawString("PAUSED",300,280);

        g.setFont(new Font("Arial",Font.BOLD,20));

        g.drawString("Press P to resume",310,320);
    }

    private void drawExplosions(Graphics g){

        for(int i = 0; i < explosions.size(); i++){

            Explosion explosion = explosions.get(i);

            explosion.draw(g);
        }
    }

    private void drawShield(Graphics g){

        if(!shieldActive){
            return;
        }

        g.setColor(Color.CYAN);

        g.drawOval(planeX - 8, planeY - 8, planeWidth + 16, planeHeight + 16);
    }


    @Override
    public void keyPressed(KeyEvent e){

        int key = e.getKeyCode();

        if(key == KeyEvent.VK_P && !gameOver && !win){
            paused = !paused;
            repaint();
            return;
        }

        if(paused){
            return;
        }

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
        if(key == KeyEvent.VK_SPACE && !gameOver && !win && !paused){
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
