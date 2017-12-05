import java.sql.*;

public class Main {
    // Please change dbmslab into the appropriate name of the schema
    private static String connectionUrl = "jdbc:mysql://localhost:3306/"
            + "dbmslab?user=root&password=";
    private static Connection CONN;
    
    public static void connectToDatabase() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            CONN = DriverManager.getConnection(connectionUrl);
        } catch (ClassNotFoundException cne) {
            cne.printStackTrace();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
    
    public static void showMenu() {
        System.out.println("The Vet Clinic");
    }
    
    public static void main(String[] args) {
        // connectToDatabase();
        showMenu();
    }
}
