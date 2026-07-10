package chickeninvaders;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.ArrayList;

public class MainMenu extends JPanel{

    private GameMain gameMain;

    public MainMenu(GameMain gameMain){

        this.gameMain = gameMain;

        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);

        createMenu();
    }

    private void createMenu(){

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0 ;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10,10,10,10);

        JLabel titleLabel = new JLabel("Chicken Invaders",SwingConstants.CENTER);

        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial",Font.BOLD,36));

        gbc.gridy = 0 ;

        add(titleLabel,gbc);

        JButton newGameButton = createMenuButton("New Game");

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                gameMain.startNewGame();
            }
        });

        gbc.gridy = 1;

        add(newGameButton,gbc);

        JButton highScoresButton = createMenuButton("High Scores");

        gbc.gridy = 2;

        add(highScoresButton,gbc);

        JButton setSettingsButton = createMenuButton("Settings");

        gbc.gridy = 3;

        add(setSettingsButton,gbc);

        setSettingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSettings();
            }
        });

        JButton howToPlayButton = createMenuButton("How to Play");

        gbc.gridy = 4;

        add(howToPlayButton,gbc);

        howToPlayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showHowToPlay();
            }
        });

        JButton exitButton = createMenuButton("Exit");

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        highScoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showHighScores();
            }
        });

        gbc.gridy = 5;

        add(exitButton,gbc);
    }

    private void showHighScores(){

        ArrayList<HighScore> scores = HighScoreManager.loadScores();

        if(scores.size() == 0){
            JOptionPane.showMessageDialog(this, "No high scores saved yet");
            return;
        }

        String text = "";

        int count = scores.size();

        if(count > 10){
            count = 10;
        }

        for(int i = 0; i < count; i++){

            HighScore highScore = scores.get(i);

            text += (i + 1) + ". " + highScore.getPlayerName()
                    + " - " + highScore.getScore() + "\n";
        }

        JOptionPane.showMessageDialog(this, text, "High Scores", JOptionPane.INFORMATION_MESSAGE);
    }

    private JButton createMenuButton(String text){

        JButton button = new JButton(text);

        button.setFont(new Font("Arial",Font.BOLD,20));

        button.setFocusPainted(false);

        return button;
    }

    private void showHowToPlay(){

        String text = "";

        text += "How to Play\n\n";
        text += "Move Left: LEFT or A\n";
        text += "Move Right: RIGHT or D\n";
        text += "Move Up: UP or W\n";
        text += "Move Down: DOWN or S\n";
        text += "Shoot: SPACE\n";
        text += "Return to Menu: ESC\n\n";
        text += "Destroy enemies to get score.\n";
        text += "Avoid eggs and enemy bullets.\n";
        text += "Collect power ups to get extra abilities.\n";
        text += "Defeat bosses in level 4 and level 8.";

        JOptionPane.showMessageDialog(this, text, "How to Play", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showSettings(){

        String text = "";

        text += "Settings\n\n";
        text += "Current version uses default game settings.\n";
        text += "Sound and difficulty settings can be added later.";

        JOptionPane.showMessageDialog(this, text, "Settings", JOptionPane.INFORMATION_MESSAGE);
    }
}
