package chickeninvaders;

import java.sql.Connection;
import java.sql.DriverManager;

/*
 * Manages the SQLite database connection for the whole project.
 * Other classes use this class to connect to game.db and run SQL queries.
 */

public class DatabaseManager {

    private static final String DB_URL = "jdbc:sqlite:identifier.db";

    public static Connection getConnection(){

        try{

            Connection connection = DriverManager.getConnection(DB_URL);

            return connection;
        }
        catch(Exception e){

            System.out.println("Database connection error.");
            System.out.println(e.getMessage());

            return null;
        }
    }

    public static void testConnection(){

        Connection connection = getConnection();

        if(connection != null){

            System.out.println("Database connected successfully.");

            try{
                connection.close();
            }
            catch(Exception e){
                System.out.println("Error closing database connection.");
            }
        }
        else{

            System.out.println("Database connection failed.");
        }
    }
}