import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    // Make sure that the URL is correct
    private static String connectionUrl = "jdbc:mysql://localhost:3306/"
                                          + "vetclinic?user=root&password=chelseafc";
    private static Connection CONN;
    private static Scanner console = new Scanner(System.in);

    public static void connectToDatabase() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            CONN = DriverManager.getConnection(connectionUrl);
            System.out.println("Connection successful!");
            System.out.print("Press any key to continue...");
            console.nextLine();
            System.out.println("");
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
        System.out.println("[5] Book a new appointment");
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
    
    public static void displayAppointments() throws SQLException {
        Date today = new Date();
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        DateFormat scheduleFormat = new SimpleDateFormat("HH:mm");
        String query = "SELECT apptid, schedule, room_no, "
                       + "CONCAT(vet_first_name, ' ', vet_last_name) vetname, "
                       + "pet_name, status FROM appointment NATURAL JOIN pet "
                       + "NATURAL JOIN veterinarian WHERE DATE(schedule) = CURDATE();";

        PreparedStatement preparedStatement = CONN.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        
        System.out.println("Appointments scheduled " + dateFormat.format(today) + "\n");
        System.out.printf("%-5s%-8s%-8s%-20s%-15s%-10s%n",
                          "ID", "Time", "Room", 
                          "Veterinarian", "Pet", "Status");
        while (resultSet.next()) {
            int appointmentId = resultSet.getInt("apptid");
            String schedule = scheduleFormat.format(resultSet.getTimestamp("schedule"));
            String room = resultSet.getString("room_no");
            String veterinarianName = resultSet.getString("vetname");
            String petName = resultSet.getString("pet_name");
            String status = resultSet.getString("status");
            
            System.out.printf("%-5d%-8s%-8s%-20s%-15s%-10s%n",
                              appointmentId, schedule, room, 
                              veterinarianName, petName, status);
        }
        
        preparedStatement.close();
        resultSet.close();
        System.out.print("\nPress any key to continue...");
        console.nextLine();
    }
    
    public static void addNewCustomer() throws SQLException {  
        System.out.println("Add new Customer\n");
        System.out.print("Enter first name: ");
        String lastName = console.nextLine();
        System.out.print("Enter last name: ");
        String firstName = console.nextLine();
        String gender = "";
        
        do {
            System.out.print("Enter gender <M/F>: ");
            gender = console.nextLine().toUpperCase();
            
            if (!"MF".contains(gender)) {
                System.out.println("Invalid input! Please enter M or F only.");
                System.out.println("Please try again...");
            }
        } while (!"MF".contains(gender));
        
        System.out.print("Enter contact number: ");
        String contactNumber = console.nextLine();
        
        String sql = "INSERT INTO owner(owner_last_name, owner_first_name, "
             + "owner_gender, owner_contact_no) VALUES(?, ?, ?, ?);";
        PreparedStatement preparedStatement = CONN.prepareStatement(sql);
        
        preparedStatement.setString(1, lastName);
        preparedStatement.setString(2, firstName);
        preparedStatement.setString(3, gender);
        preparedStatement.setString(4, contactNumber);
        
        int rowsInserted = preparedStatement.executeUpdate();
        System.out.println((rowsInserted > 0)
                            ? "A new customer was successfully added!"
                            : "Nothing was inserted...");
        System.out.println("Press any key to continue...");
        console.nextLine();
        
        preparedStatement.close();
    }
    
    public static void updateVetType() throws SQLException {
        System.out.println("\nUpdate Vet Type");
        
        String choice = "N";
        String vetId = "";
        String sql = "SELECT CONCAT(vet_first_name, ' ', vet_last_name), vet_type"
                     + " FROM veterinarian WHERE vetid = ?;";
        PreparedStatement preparedStatement = CONN.prepareStatement(sql);
        
        do {
            System.out.print("Enter veterinarian id: ");
            vetId = console.nextLine();
            preparedStatement.setString(1, vetId);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next() && choice.equals("N")) {
                String name = resultSet.getString(1);
                String type = resultSet.getString(2);
                
                System.out.printf("%n%-15s%-10s%n", name, type);
                System.out.print("Is this the vet you want to edit <Y/N>? ");
                choice = console.nextLine().toUpperCase();
                
                if (choice.equals("Y")) {
                    sql = "UPDATE veterinarian SET vet_type = ?;";
                    preparedStatement = CONN.prepareStatement(sql);
                    System.out.print("Enter new type <Permanent/Visiting>: ");
                    String newType = console.nextLine().toLowerCase();
                    newType = newType.substring(0, 1) + newType.substring(1);
                    
                    preparedStatement.setString(1, newType);
                    
                    int rowsUpdated = preparedStatement.executeUpdate();
                    System.out.println((rowsUpdated > 0)
                                        ? "Type was successfully updated!"
                                        : "Nothing was updated...");
                    
                    System.out.print("Press any key to continue...");
                    console.nextLine();
                    
                    preparedStatement.close();
                } else {
                    System.out.println("Try again");
                }
            }
        } while (choice.equals("N"));
    }
    
    public static void deleteVet() throws SQLException {
        System.out.println("\nDelete a Veterinarian");
        String sql = "SELECT CONCAT(vet_first_name, ' ', vet_last_name) "
                     + "FROM veterinarian WHERE vetid = ?;";
        PreparedStatement preparedStatement = CONN.prepareStatement(sql);
        String choice = "N";
        
        System.out.print("Enter vet id: ");
        String vetId = console.nextLine();
        preparedStatement.setString(1, vetId);
        ResultSet resultSet = preparedStatement.executeQuery();
        
        if (resultSet.next()) {
            String name = resultSet.getString(1);
            System.out.print("Are you sure you want to delete " + name 
                               + "<Y/N>? ");
            choice = console.nextLine().toUpperCase();
            
            if (choice.equals("Y")) {
                sql = "DELETE FROM veterinarian WHERE vetid = ?;";
                preparedStatement = CONN.prepareStatement(sql);
                preparedStatement.setString(1, vetId);
                try {
                    int rowsDeleted = preparedStatement.executeUpdate();
                    System.out.println((rowsDeleted > 0) ? "Successfully deleted vet!"
                                                         : "Nothing was deleted...");
                    
                } catch (MySQLIntegrityConstraintViolationException sqe) {
                    sqe.printStackTrace();
                }
                
            } else {
                System.out.println("Deletion cancelled...");
            }
            
            resultSet.close();
            preparedStatement.close();
            System.out.print("Press any key to continue...");
            console.nextLine();
        }
    }
    
    public static void bookAppointment() throws SQLException {
        System.out.print("Enter appointment date (yyyy-MM-dd): ");
        String date = console.nextLine();
        System.out.print("Enter appointment time (HH:mm): ");
        String time = console.nextLine();
        System.out.println("Enter room: ");
        String room = console.nextLine();
        System.out.println("Enter vet id: ");
        String vetId = console.nextLine();
        System.out.println("Enter pet id: ");
        String petId = console.nextLine();
        
        String sql = "SELECT petid, ownerid FROM pet WHERE petid = ?;";
        PreparedStatement preparedStatement = CONN.prepareStatement(sql);
        preparedStatement.executeQuery();
        
        
    }
    //Cancel an appointment
    public static void cancelVetAppointment() throws SQLException {
        System.out.print("Enter appointment id: ");
        int apptid = Integer.parseInt(console.nextLine());
        
        PreparedStatement psu = CONN.prepareStatement("DELETE FROM appointment "
                                                      + "WHERE apptid="+apptid);
        psu.executeUpdate();
        
        String stSel = "SELECT * FROM appointment";
        Statement stmt = null;
        try {
            stmt = CONN.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
        } catch (SQLException ex) {
            ex.printStackTrace();
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
        
        PreparedStatement psu = CONN.prepareStatement("UPDATE appointment "
                                                      + "SET date ='"+date+"', "
                                                      + "time ='"+time+"' "
                                                      + "WHERE apptid="+apptid);
        psu.executeUpdate();
        
        String stSel = "SELECT * FROM appointment";
        Statement stmt = null;
        try {
            stmt = CONN.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        ResultSet rs = stmt.executeQuery(stSel);
        
        rs.beforeFirst();
        while (rs.next()) {
            int ad = rs.getInt("apptid");
            String d = rs.getString("date");
            String t = rs.getString("time");
            String rm = rs.getString("room_no");
            String status = rs.getString("status");
          
            System.out.printf("%3d %-15s %-15s %-15s %-15s\n", 
                              ad, d, t, rm, status);
        }

    }
    
    //display owners who have this certain type of pet
    public static void displayPetSpecies() throws SQLException {
        System.out.print("Enter pet species: ");
        String input = console.nextLine();
      
        String query = "SELECT owner_last_name, owner_first_name, pet_name "
                       + "FROM pet NATURAL JOIN owner "
                       + "WHERE species = '" + input + "';";
      
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
       
            System.out.println("Owner: " + ownerfname + " " + ownerlname);
            System.out.println("Pet: " + petname);
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
        
        PreparedStatement psu = CONN.prepareStatement("UPDATE owner SET "
                                                      + "owner_contact_no ='"
                                                      + contact + "' "
                                                      + "WHERE owner_last_name='"
                                                      + lname 
                                                      + "' AND owner_first_name='"
                                                      +fname+"'");
        psu.executeUpdate();
        
        String stSel = "SELECT * from owner";
        Statement stmt = null;
        try {
            stmt = CONN.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        ResultSet rs = stmt.executeQuery(stSel);

        rs.beforeFirst();
        while (rs.next()) {
            int oid = rs.getInt("ownerid");
            String ln = rs.getString("owner_last_name");
            String fn = rs.getString("owner_first_name");
            String g = rs.getString("owner_gender");
            String cn = rs.getString("owner_contact_no");
            
            System.out.printf("%3d %-15s %-15s %-15s %-15s\n", 
                              oid, ln, fn, g, cn);
        }
    }

    public static void changeOwner() throws SQLException {
        System.out.print("Enter petid: ");
        int petid = Integer.parseInt(console.nextLine());
        System.out.println("Enter new ownerid: ");
        int ownerid = Integer.parseInt(console.nextLine());

        PreparedStatement psu = CONN.prepareStatement("UPDATE pet SET ownerid='"+ownerid+"' WHERE petid="+petid);
        psu.executeUpdate();

        String stSel = "SELECT * FROM pet";
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
            int pet_id = rs.getInt("petid");
            String pet_name = rs.getString("pet_name");
            String species = rs.getString("species");
            String gender = rs.getString("gender");
            String bdate = rs.getString("bdate");
            int owner_id = rs.getInt("ownerid");

            System.out.printf("%3d %-15s %-15s %-15s %-15s\n", petid, pet_name, species, gender, bdate,ownerid);

        }

    }
    
    public static void main(String[] args) throws SQLException {
        
        connectToDatabase();
        
        int choice = 19;
        do {
            showMenu();
            System.out.print("\nEnter choice: ");
            choice = Integer.parseInt(console.nextLine());
            System.out.println();
            
            switch (choice) {
                case 1:
                    displayAppointments();
                    break;
                case 2:
                    addNewCustomer();
                    break;
                case 3:
                    updateVetType();
                    break;
                case 4:
                    deleteVet();
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
                    changeOwner();
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
                    System.out.println("Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
                    
            }
            
        } while (choice != 19);
    }
}
