import java.sql.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    // Please change dbmslab into the appropriate name of the schema
    private static String connectionUrl = "jdbc:mysql://localhost:8889/"
            + "vetclinic?user=root&password=root";
    private static Connection CONN;
    private static Scanner console = new Scanner(System.in); 
    
    public static void connectToDatabase() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            CONN = DriverManager.getConnection(connectionUrl);
            System.out.println("connection done");
        } catch (ClassNotFoundException cne) {
            cne.printStackTrace();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
    
    public static void displayPetSpecies() throws SQLException {
        System.out.println("What species? ");
        String input = console.nextLine();
        String stSel = "SELECT * FROM pet WHERE species ='"+input+"'";

        Statement stmt = null;
        try {
            stmt = CONN.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResultSet rs = stmt.executeQuery(stSel);

        rs.beforeFirst();
        while (rs.next()) {
            int petid = rs.getInt(1);
            String petname = rs.getString("pet_name");
            String species = rs.getString("species");
            String gender = rs.getString("gender");
            String birthdate = rs.getString("birthdate");

            System.out.printf("%3d %-25s %-25s %-25s %-25s\n", petid, petname, species, gender, birthdate);
        }
    }
    
    public static void showMenu() {
        System.out.println("The Vet Clinic");
        System.out.println("[1] Display all the appointments of the day");
        System.out.println("[2] Add a new customer");
        System.out.println("[3] Update a veterinarian's type");
        System.out.println("[4] Delete a veterinarian record");
        System.out.println("[5] Book a new appointment for a veterinarian");
        System.out.println("[6] Display veterinarians and their appointments");
        System.out.println("[7] Update a vet's appointment time");
        System.out.println("[8] Cancel a veterinarian's appointment");
        System.out.println("[9] Register a new pet patient");
        System.out.println("[10] Delete a pet patient in record");
        System.out.println("[11] Display the total number of appointments of a "
                + "certain pet");
        System.out.println("[12] Update a pet's appointment");
        System.out.println("[13] Cancel a pet's appointment");
        System.out.println("[14] Display all owners who has a specific type of a"
                + " species as a pet");
        System.out.println("[15] Change owner of pet");
        System.out.println("[16] Update owner's id");
        System.out.println("[17] Add a new pet of an existing owner");
        System.out.println("[18] View appointment information");
        System.out.println("[19] Quit");
    }
    
    public static void main(String[] args) throws SQLException {
        connectToDatabase();
        int choice = 18;
        do {
            showMenu();
            System.out.print("\nEnter choice: ");
            choice = Integer.parseInt(console.nextLine());
            
            switch(choice) {
                case 1:
                    
                    break;
                case 2:
                    
                    break;
                    
                case 3:
                    
                    break;
                    
                case 4:
                    
                    break;
                    
                case 5:
                    
                    break;
                case 6:
                    
                    break;
                case 7:
                    
                    break;
                case 8:
                    
                    break;
                case 9:
                    
                    break;
                case 10:
                    
                    break;
                case 11:
                    
                    break;
                case 12:
                    
                    break;
                case 13:
                    
                    break;
                case 14:
                    displayPetSpecies();
                    break;
                case 15:
                    
                    break;
                case 16:
                    
                    break;
                case 17:
                    
                    break;
                case 18:
                    
                    break;
                case 19:
                    System.exit(0);
                    break;   
            }
        } while (choice != 19);
    }
}
