package chickeninvaders;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class UserManager {

    private static final String FILE_NAME = "users.txt";

    public static boolean registerUser(String username, String password){

        username = cleanText(username);
        password = cleanText(password);

        if(username.equals("") || password.equals("")){
            return false;
        }

        if(isUsernameTaken(username)){
            return false;
        }

        User user = new User(username, password);

        saveNewUser(user);

        return true;
    }

    public static User loginUser(String username, String password){

        username = cleanText(username);
        password = cleanText(password);

        ArrayList<User> users = loadUsers();

        for(int i = 0; i < users.size(); i++){

            User user = users.get(i);

            if(user.getUsername().equals(username)
                    && user.getPassword().equals(password)){

                return user;
            }
        }

        return null;
    }

    public static boolean isUsernameTaken(String username){

        username = cleanText(username);

        ArrayList<User> users = loadUsers();

        for(int i = 0; i < users.size(); i++){

            User user = users.get(i);

            if(user.getUsername().equals(username)){
                return true;
            }
        }

        return false;
    }

    public static ArrayList<User> loadUsers(){

        ArrayList<User> users = new ArrayList<>();

        File file = new File(FILE_NAME);

        if(!file.exists()){
            return users;
        }

        try{
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;

            while((line = bufferedReader.readLine()) != null){

                String[] parts = line.split(",");

                if(parts.length >= 8){

                    String username = parts[0];
                    String password = parts[1];

                    int highestScore = Integer.parseInt(parts[2]);
                    int lastLevel = Integer.parseInt(parts[3]);

                    boolean backgroundMusicOn = Boolean.parseBoolean(parts[4]);
                    boolean shotSoundOn = Boolean.parseBoolean(parts[5]);
                    boolean explosionSoundOn = Boolean.parseBoolean(parts[6]);
                    boolean gameResultSoundOn = Boolean.parseBoolean(parts[7]);

                    User user = new User(
                            username,
                            password,
                            highestScore,
                            lastLevel,
                            backgroundMusicOn,
                            shotSoundOn,
                            explosionSoundOn,
                            gameResultSoundOn
                    );

                    users.add(user);
                }
            }

            bufferedReader.close();
        }
        catch (Exception e) {
            System.out.println("Error loading users");
        }

        return users;
    }

    private static void saveNewUser(User user){

        try{

            FileWriter fileWriter = new FileWriter(FILE_NAME, true);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(user.toFileString());

            bufferedWriter.newLine();

            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Error saving user");
        }
    }

    public static void updateUser(User updatedUser){

        ArrayList<User> users = loadUsers();

        for(int i = 0; i < users.size(); i++){

            User user = users.get(i);

            if(user.getUsername().equals(updatedUser.getUsername())){

                users.set(i, updatedUser);

                break;
            }
        }

        saveAllUsers(users);
    }

    private static void saveAllUsers(ArrayList<User> users){

        try{

            FileWriter fileWriter = new FileWriter(FILE_NAME, false);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for(int i = 0; i < users.size(); i++){

                User user = users.get(i);

                bufferedWriter.write(user.toFileString());
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
        }
        catch(Exception e){
            System.out.println("Error updating users.");
        }
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
