import java.sql.*;
import java.util.Scanner;

public class Main {
    // Please change dbmslab into the appropriate name of the schema
    private static String connectionUrl = "jdbc:mysql://localhost:3306/"
            + "dbmslab?user=root&password=";
    private static Connection CONN;
    private static Scanner console = new Scanner(System.in); 
    
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
        System.out.println("[1] Display all the appointments");
        System.out.println("[2] Add a new customer");
        System.out.println("[3] Update a veterinarian's type");
        System.out.println("[4] Delete a veterinarian");
        System.out.println("[5] Create new appointment for a veterinarian");
        System.out.println("[6] Display veterinarians and their appointments");
        System.out.println("[7] Update a vet's appointment time");
        System.out.println("[8] Delete a veterinarian's appointment");
        System.out.println("[9] Register a new pet patient");
        System.out.println("[10] Delete a pet patient in record");
        System.out.println("[11] Display the total number of appointments of a "
                + "certain pet");
        System.out.println("[12] Update a pet's appointment");
        System.out.println("[13] Delete a pet's appointment");
        System.out.println("[14] Display all owners who has a specific type of a"
                + " species as a pet");
        System.out.println("[15] Change owner of pet");
        System.out.println("[16] Update owner's id");
        System.out.println("[17] Add a new pet of an existing owner");
        System.out.println("[18] Quit");
    }
    
    public static void main(String[] args) {
        // connectToDatabase();
        
        int choice = 18;
        do {
            showMenu();
            System.out.print("\nEnter choice: ");
            choice = Integer.parseInt(console.nextLine());
        } while (choice != 18);
    }
}
