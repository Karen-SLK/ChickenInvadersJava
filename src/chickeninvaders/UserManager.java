package chickeninvaders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UserManager {

    public static boolean registerUser(String username, String password){

        username = cleanText(username);
        password = cleanText(password);

        if(username.equals("") || password.equals("")){

            return false;
        }

        if(isUsernameTaken(username)){

            return false;
        }

        String sql =
                "INSERT INTO users ("
                        + "username, password, highest_score, last_level, "
                        + "background_music_on, shot_sound_on, explosion_sound_on, game_result_sound_on, "
                        + "selected_plane"
                        + ") VALUES (?, ?, 0, 1, 1, 1, 1, 1, ?)";
        try{

            Connection connection = DatabaseManager.getConnection();

            if(connection == null){
                return false;
            }

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, PlaneType.DEFAULT);

            statement.executeUpdate();

            statement.close();
            connection.close();

            return true;
        }
        catch(Exception e){
            System.out.println("Error registering user.");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static User loginUser(String username, String password){

        username = cleanText(username);
        password = cleanText(password);

        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try{

            Connection connection = DatabaseManager.getConnection();

            if(connection == null){
                return null;
            }

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            User user = null;

            if(resultSet.next()){

                user = createUserFromResultSet(resultSet);
            }

            resultSet.close();
            statement.close();
            connection.close();

            return user;
        }
        catch(Exception e){
            System.out.println("Error logging in user.");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static boolean isUsernameTaken(String username){

        username = cleanText(username);

        String sql = "SELECT username FROM users WHERE username = ?";

        try{

            Connection connection = DatabaseManager.getConnection();

            if(connection == null){
                return false;
            }

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();

            boolean exists = resultSet.next();

            resultSet.close();
            statement.close();
            connection.close();

            return exists;
        }
        catch(Exception e){
            System.out.println("Error checking username.");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static void updateUser(User updatedUser){

        String sql =
                "UPDATE users SET "
                        + "password = ?, "
                        + "highest_score = ?, "
                        + "last_level = ?, "
                        + "background_music_on = ?, "
                        + "shot_sound_on = ?, "
                        + "explosion_sound_on = ?, "
                        + "game_result_sound_on = ?, "
                        + "selected_plane = ? "
                        + "WHERE username = ?";

        try{

            Connection connection = DatabaseManager.getConnection();

            if(connection == null){
                return;
            }

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, updatedUser.getPassword());
            statement.setInt(2, updatedUser.getHighestScore());
            statement.setInt(3, updatedUser.getLastLevel());
            statement.setInt(4, booleanToInt(updatedUser.isBackgroundMusicOn()));
            statement.setInt(5, booleanToInt(updatedUser.isShotSoundOn()));
            statement.setInt(6, booleanToInt(updatedUser.isExplosionSoundOn()));
            statement.setInt(7, booleanToInt(updatedUser.isGameResultSoundOn()));
            statement.setString(8, updatedUser.getSelectedPlane());
            statement.setString(9, updatedUser.getUsername());

            statement.executeUpdate();

            statement.close();
            connection.close();
        }
        catch(Exception e){
            System.out.println("Error updating user.");
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<User> loadUsers(){

        ArrayList<User> users = new ArrayList<>();

        String sql = "SELECT * FROM users";

        try{

            Connection connection = DatabaseManager.getConnection();

            if(connection == null){
                return users;
            }

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){

                User user = createUserFromResultSet(resultSet);

                users.add(user);
            }

            resultSet.close();
            statement.close();
            connection.close();
        }
        catch(Exception e){
            System.out.println("Error loading users.");
            System.out.println(e.getMessage());
        }

        return users;
    }

    private static User createUserFromResultSet(ResultSet resultSet){

        try{

            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            String selectedPlane = resultSet.getString("selected_plane");

            int highestScore = resultSet.getInt("highest_score");
            int lastLevel = resultSet.getInt("last_level");

            boolean backgroundMusicOn = intToBoolean(resultSet.getInt("background_music_on"));
            boolean shotSoundOn = intToBoolean(resultSet.getInt("shot_sound_on"));
            boolean explosionSoundOn = intToBoolean(resultSet.getInt("explosion_sound_on"));
            boolean gameResultSoundOn = intToBoolean(resultSet.getInt("game_result_sound_on"));

            return new User(
                    username,
                    password,
                    highestScore,
                    lastLevel,
                    backgroundMusicOn,
                    shotSoundOn,
                    explosionSoundOn,
                    gameResultSoundOn,
                    selectedPlane
            );
        }
        catch(Exception e){
            System.out.println("Error creating user from database.");
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static int booleanToInt(boolean value){

        if(value){
            return 1;
        }

        return 0;
    }

    private static boolean intToBoolean(int value){

        return value == 1;
    }

    private static String cleanText(String text){

        if(text == null){
            return "";
        }

        text = text.trim();
        text = text.replace(",", "");

        return text;
    }


}