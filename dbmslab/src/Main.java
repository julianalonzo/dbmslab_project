import java.sql.*;
import java.util.Scanner;

public class Main {
    // Make sure that the URL is correct
    private static String connectionUrl = "jdbc:mysql://localhost:8889/"
                                          + "vetclinic?user=root&password=root";
    private static Connection CONN;
    private static Scanner console = new Scanner(System.in);

    public static void connectToDatabase() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            CONN = DriverManager.getConnection(connectionUrl);
            System.out.println("Connected to the database...");
        } catch (ClassNotFoundException cne) {
            cne.printStackTrace();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
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
        System.out.println("[8] Cancel an appointment");
        System.out.println("[9] Register a new pet patient");
        System.out.println("[10] Delete a pet patient in record");
        System.out.println("[11] Display the total number of appointments of a "
                            + "certain pet");
        System.out.println("[12] Update a pet's appointment");
        System.out.println("[13] Display all owners who has a specific type of a"
                            + " species as a pet");
        System.out.println("[14] Change owner of pet");
        System.out.println("[15] Update owner's id");
        System.out.println("[16] Add a new pet of an existing owner");
        System.out.println("[17] View appointment information");
        System.out.println("[18] Finish an appointment");
        System.out.println("[19] Quit");
    }
    
    //Cancel an appointment
    public static void cancelVetAppointment() throws SQLException {
        System.out.print("Enter appointment id: ");
        int apptid = Integer.parseInt(console.nextLine());
        
        PreparedStatement psu = CONN.prepareStatement("DELETE FROM appointment WHERE apptid="+apptid);
        psu.executeUpdate();
        
        String stSel = "SELECT * FROM appointment";
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
            int ad = rs.getInt("apptid");
            String d = rs.getString("date");
            String t = rs.getString("time");
            String rm = rs.getString("room_no");
            String status = rs.getString("status");
            
            System.out.printf("%3d %-15s %-15s %-15s %-15s\n", ad, d, t, rm, status);
        }
    }
    
    //Update an appointment's date and time
    public static void updateAppointment() throws SQLException {
        System.out.print("Enter appointment id to be updated: ");
        int apptid = Integer.parseInt(console.nextLine());
        System.out.print("Enter new date(MM-DD-YYYY): ");
        String date = console.nextLine();
        System.out.print("Enter new time(HH:MM): ");
        String time = console.nextLine();
        
        PreparedStatement psu = CONN.prepareStatement("UPDATE appointment SET date ='"+date+"',time ='"+time+"' WHERE apptid="+apptid);
        psu.executeUpdate();
        
        String stSel = "SELECT * FROM appointment";
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
            int ad = rs.getInt("apptid");
            String d = rs.getString("date");
            String t = rs.getString("time");
            String rm = rs.getString("room_no");
            String status = rs.getString("status");
            
            System.out.printf("%3d %-15s %-15s %-15s %-15s\n", ad, d, t, rm, status);
        }

    }
    
    //display owners who have this certain type of pet
    public static void displayPetSpecies() throws SQLException {
        System.out.println("Enter pet species: ");
        String input = console.nextLine();
        String stSel = "SELECT owner_last_name, owner_first_name, pet_name FROM pet NATURAL JOIN owner WHERE species ='"+input+"'";
      
        Statement statement = null;
        try {
            statement = CONN.createStatement();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        ResultSet rs = statement.executeQuery(query);

        rs.beforeFirst();
        while (rs.next()) {
            String ownerlname = rs.getString("owner_last_name");
            String ownerfname = rs.getString("owner_first_name");
            String petname = rs.getString("pet_name");
          
            System.out.println("Owner: "+ownerfname+" "+ownerlname);
            System.out.println("Pet: "+petname);
        }
    }
    
    //update owner's contact no
    public static void updateOwnerContactNo() throws SQLException {
        System.out.print("Enter owner's first name: ");
        String fname = console.nextLine();
        System.out.print("Enter owner's last name: ");
        String lname = console.nextLine();
        System.out.print("Enter new contact number: ");
        String contact = console.nextLine();
        
        PreparedStatement psu = CONN.prepareStatement("UPDATE owner SET owner_contact_no ='"+contact+"' WHERE owner_last_name='"+lname+"' AND owner_first_name='"+fname+"'");
        psu.executeUpdate();
        
        String stSel = "SELECT * from owner";
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
            int oid = rs.getInt("ownerid");
            String ln = rs.getString("owner_last_name");
            String fn = rs.getString("owner_first_name");
            String g = rs.getString("owner_gender");
            String cn = rs.getString("owner_contact_no");
            
            System.out.printf("%3d %-15s %-15s %-15s %-15s\n", oid, ln, fn, g, cn);
        }
    }
    
    public static void main(String[] args) throws SQLException {
        
        connectToDatabase();
        
        int choice = 19;
        do {
            showMenu();
            System.out.print("\nEnter choice: ");
            choice = Integer.parseInt(console.nextLine());
            
            switch (choice) {
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
                    cancelVetAppointment();
                    break;
                case 9:
                    
                    break;
                case 10:
                    
                    break;
                case 11:
                    
                    break;
                case 12:
                    updateAppointment();
                    break;
                case 13:
                    displayPetSpecies();
                    break;
                case 14:
                    
                    break;
                case 15:
                    updateOwnerContactNo();
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
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
                    
            }
            
        } while (choice != 19);
    }
}