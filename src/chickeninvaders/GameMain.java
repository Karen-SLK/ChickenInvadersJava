package chickeninvaders;

import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Dimension;


public class GameMain {

    public static final int width = 800;
    public static final int height = 600;

    private static final String MENU_CARD = "menu";
    private static final String GAME_CARD = "game";

    private JFrame frame;

    private CardLayout cardLayout;

    private JPanel mainPanel;

    private MainMenu mainMenu;
    private GamePanel gamePanel;

    private User currentUser;

    public GameMain(){

        frame = new JFrame("Chicken Invaders");

        cardLayout = new CardLayout();

        ImageManager.loadImages();

        mainPanel = new JPanel(cardLayout);

        mainMenu = new MainMenu(this);

        gamePanel = new GamePanel(this);

        mainPanel.add(mainMenu,MENU_CARD);
        mainPanel.add(gamePanel,GAME_CARD);

        frame.setContentPane(mainPanel);
        mainPanel.setPreferredSize(new Dimension(width,height));
        frame.pack();
        frame.setResizable(false);

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);

        SoundManager.playBackgroundMusic();
    }

    public void showMainMenu(){

        gamePanel.stopGame();
        cardLayout.show(mainPanel,MENU_CARD);
    }

    public void startNewGame(){

        cardLayout.show(mainPanel,GAME_CARD);
        gamePanel.startGame();
    }

    public User getCurrentUser(){
        return currentUser;
    }

    public void setCurrentUser(User currentUser){

        this.currentUser = currentUser;

        if(currentUser != null){

            applyUserSoundSettings(currentUser);
        }
    }

    private void applyUserSoundSettings(User user){

        SoundSettings.setBackgroundMusicOn(user.isBackgroundMusicOn());
        SoundSettings.setShotSoundOn(user.isShotSoundOn());
        SoundSettings.setExplosionSoundOn(user.isExplosionSoundOn());
        SoundSettings.setGameResultSoundOn(user.isGameResultSoundOn());

        if(SoundSettings.isBackgroundMusicOn()){

            SoundManager.playBackgroundMusic();
        }
        else{

            SoundManager.stopBackgroundMusic();
        }
    }


    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                new GameMain();
            }
        });
    }
}
