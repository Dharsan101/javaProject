import java.sql.*;
import java.util.Scanner;

public class MedicalEquipmentSystem {

    static final String URL = "jdbc:mysql://localhost:3306/medical_db";
    static final String USER = "root";
    static final String PASSWORD = "root"; // change if needed

    static Connection con;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USER, PASSWORD);

            int choice;
            do {
                System.out.println("\n--- Medical Equipment Tracking System ---");
                System.out.println("1. Add Equipment");
                System.out.println("2. View Equipment");
                System.out.println("3. Update Equipment");
                System.out.println("4. Delete Equipment");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");
                choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        addEquipment();
                        break;
                    case 2:
                        viewEquipment();
                        break;
                    case 3:
                        updateEquipment();
                        break;
                    case 4:
                        deleteEquipment();
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice!");
                }

            } while (choice != 5);

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Add Equipment
    static void addEquipment() throws SQLException {
        sc.nextLine();

        System.out.print("Enter Equipment Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Type: ");
        String type = sc.nextLine();

        System.out.print("Enter Quantity: ");
        int quantity = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Status (Available/In Use): ");
        String status = sc.nextLine();

        String query = "INSERT INTO equipment(name, type, quantity, status) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(query);

        ps.setString(1, name);
        ps.setString(2, type);
        ps.setInt(3, quantity);
        ps.setString(4, status);

        ps.executeUpdate();
        System.out.println("Equipment Added Successfully!");
    }

    // View Equipment
    static void viewEquipment() throws SQLException {
        String query = "SELECT * FROM equipment";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);

        System.out.println("\nID\tName\tType\tQuantity\tStatus");

        while (rs.next()) {
            System.out.println(
                    rs.getInt("id") + "\t" +
                    rs.getString("name") + "\t" +
                    rs.getString("type") + "\t" +
                    rs.getInt("quantity") + "\t\t" +
                    rs.getString("status"));
        }
    }

    // Update Equipment
    static void updateEquipment() throws SQLException {
        System.out.print("Enter Equipment ID to Update: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter New Quantity: ");
        int quantity = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter New Status: ");
        String status = sc.nextLine();

        String query = "UPDATE equipment SET quantity=?, status=? WHERE id=?";
        PreparedStatement ps = con.prepareStatement(query);

        ps.setInt(1, quantity);
        ps.setString(2, status);
        ps.setInt(3, id);

        int rows = ps.executeUpdate();

        if (rows > 0)
            System.out.println("Updated Successfully!");
        else
            System.out.println("Equipment not found!");
    }

    // Delete Equipment
    static void deleteEquipment() throws SQLException {
        System.out.print("Enter Equipment ID to Delete: ");
        int id = sc.nextInt();

        String query = "DELETE FROM equipment WHERE id=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, id);

        int rows = ps.executeUpdate();

        if (rows > 0)
            System.out.println("Deleted Successfully!");
        else
            System.out.println("Equipment not found!");
    }
}