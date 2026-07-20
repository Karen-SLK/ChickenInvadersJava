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

                if(gameMain.getCurrentUser() == null){

                    showLoginRegisterDialog();
                }
                else{

                    gameMain.startNewGame();
                }
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

        JButton storeButton = createMenuButton("Store");

        gbc.gridy = 6;

        add(storeButton,gbc);

        storeButton.addActionListener(e -> showStore());
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

            text += (i + 1) + ". "
                    + highScore.getPlayerName()
                    + " | Score: " + highScore.getScore()
                    + " | Level: " + highScore.getLevelReached()
                    + " | Date: " + highScore.getDateTime()
                    + "\n";
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

        JCheckBox backgroundMusicCheckBox = new JCheckBox(
                "Background Music",
                SoundSettings.isBackgroundMusicOn()
        );

        JCheckBox shotSoundCheckBox = new JCheckBox(
                "Shot Sound",
                SoundSettings.isShotSoundOn()
        );

        JCheckBox explosionSoundCheckBox = new JCheckBox(
                "Crash / Explosion Sound",
                SoundSettings.isExplosionSoundOn()
        );

        JCheckBox gameResultSoundCheckBox = new JCheckBox(
                "Game Over / Win Sound",
                SoundSettings.isGameResultSoundOn()
        );

        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(4, 1));

        panel.add(backgroundMusicCheckBox);
        panel.add(shotSoundCheckBox);
        panel.add(explosionSoundCheckBox);
        panel.add(gameResultSoundCheckBox);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Sound Settings",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if(result == JOptionPane.OK_OPTION){

            SoundSettings.setBackgroundMusicOn(backgroundMusicCheckBox.isSelected());
            SoundSettings.setShotSoundOn(shotSoundCheckBox.isSelected());
            SoundSettings.setExplosionSoundOn(explosionSoundCheckBox.isSelected());
            SoundSettings.setGameResultSoundOn(gameResultSoundCheckBox.isSelected());

            if(SoundSettings.isBackgroundMusicOn()){

                SoundManager.playBackgroundMusic();
            }
            else{

                SoundManager.stopBackgroundMusic();
            }

            saveSoundSettingsForCurrentUser();

            JOptionPane.showMessageDialog(this, "Sound settings saved.");

        }
    }

    private void saveSoundSettingsForCurrentUser(){

        User currentUser = gameMain.getCurrentUser();

        if(currentUser == null){

            return;
        }

        currentUser.setBackgroundMusicOn(SoundSettings.isBackgroundMusicOn());

        currentUser.setShotSoundOn(SoundSettings.isShotSoundOn());

        currentUser.setExplosionSoundOn(SoundSettings.isExplosionSoundOn());

        currentUser.setGameResultSoundOn(SoundSettings.isGameResultSoundOn());

        UserManager.updateUser(currentUser);
    }

    private void showLoginRegisterDialog(){

        String[] options = {"Login", "Register", "Cancel"};

        int result = JOptionPane.showOptionDialog(
                this,
                "You need to login or register before starting the game.",
                "User Account",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        if(result == 0){

            showLoginDialog();
        }
        else if(result == 1){

            showRegisterDialog();
        }
    }

    private void showLoginDialog(){

        JTextField usernameField = new JTextField();

        JPasswordField passwordField = new JPasswordField();

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Login",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if(result == JOptionPane.OK_OPTION){

            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            User user = UserManager.loginUser(username, password);

            if(user == null){

                JOptionPane.showMessageDialog(this,
                        "Invalid username or password.");
            }
            else{

                gameMain.setCurrentUser(user);

                JOptionPane.showMessageDialog(this,
                        "Login successful. Welcome " + user.getUsername() + ".");

                gameMain.startNewGame();
            }
        }
    }

    private void showRegisterDialog(){

        JTextField usernameField = new JTextField();

        JPasswordField passwordField = new JPasswordField();

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Register",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if(result == JOptionPane.OK_OPTION){

            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            boolean registered = UserManager.registerUser(username, password);

            if(!registered){

                JOptionPane.showMessageDialog(this,
                        "Register failed. Username may be empty or already taken.");
            }
            else{

                User user = UserManager.loginUser(username, password);

                gameMain.setCurrentUser(user);

                JOptionPane.showMessageDialog(this,
                        "Register successful. Welcome " + user.getUsername() + ".");

                gameMain.startNewGame();
            }
        }
    }

    private void showStore(){

        User currentUser = gameMain.getCurrentUser();

        if(currentUser == null){

            JOptionPane.showMessageDialog(
                    this,
                    "Please login or register first."
            );

            return;
        }

        String[] planes = PlaneType.getAllPlanes();

        StringBuilder message = new StringBuilder();

        message.append("Your highest score: ")
                .append(currentUser.getHighestScore())
                .append("\n");

        message.append("Current plane: ")
                .append(currentUser.getSelectedPlane())
                .append("\n\n");

        message.append("Available planes:\n");

        for(int i = 0; i < planes.length; i++){
            message.append(PlaneType.getInfoText(planes[i]))
                    .append("\n");
        }

        String selectedPlane = (String) JOptionPane.showInputDialog(
                this,
                message.toString(),
                "Store",
                JOptionPane.PLAIN_MESSAGE,
                null,
                planes,
                currentUser.getSelectedPlane()
        );

        if(selectedPlane == null){

            return;
        }

        int cost = PlaneType.getCost(selectedPlane);

        if(currentUser.getHighestScore() < cost){

            JOptionPane.showMessageDialog(
                    this,
                    "You need at least " + cost + " points to unlock " + selectedPlane + "."
            );

            return;
        }

        currentUser.setSelectedPlane(selectedPlane);

        UserManager.updateUser(currentUser);

        JOptionPane.showMessageDialog(
                this,
                selectedPlane + " plane selected for next game."
        );
    }
}
