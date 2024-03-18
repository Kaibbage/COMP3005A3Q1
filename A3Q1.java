import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class A3Q1 {

    public static Connection conn;

    public static void main(String[] args) {

        int option = 100;
        Scanner scanner = new Scanner(System.in);

        // JDBC & Database credentials
        String url = "jdbc:postgresql://localhost:5432/Assignment3Question1";
        String user = "postgres";
        String password = "Cmilk333";

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                System.out.println("Connected to PostgreSQL successfully!");
                while(option!=0){
                    System.out.println("Select an option:");
                    System.out.println("1. See students");
                    System.out.println("2. Add a student");
                    System.out.println("3. Update a student email");
                    System.out.println("4. Delete a student");


                     option = Integer.parseInt(scanner.nextLine().replaceAll("\\s", ""));


                    switch (option) {
                        case 0:
                            break;
                        case 1:
                            getAllStudents();
                            break;
                        case 2:
                            System.out.print("first_name: ");
                            String first_name = scanner.nextLine().replace("\n", "");

                            System.out.print("last_name: ");
                            String last_name = scanner.nextLine().replace("\n", "");

                            System.out.print("email: ");
                            String email = scanner.nextLine().replace("\n", "");

                            System.out.print("date (yyyy-MM-dd): ");
                            String enrollment_date = scanner.nextLine().replace("\n", "");

                            addStudent(first_name, last_name, email, enrollment_date);
                            getAllStudents();
                            break;
                        case 3:
                            System.out.print("id: ");
                            int id = Integer.parseInt(scanner.nextLine().replaceAll("\\s", ""));

                            System.out.print("new email: ");
                            String newEmail =scanner.nextLine().replaceAll("\n", "");

                            updateStudentEmail(id, newEmail);
                            getAllStudents();
                            break;
                        case 4:
                            System.out.print("id: ");
                            int idDelete = Integer.parseInt(scanner.nextLine().replaceAll("\\s", ""));

                            deleteStudent(idDelete);
                            getAllStudents();
                            break;
                        default:
                            System.out.println("Invalid option. Please choose an option between 1 and 4.");
                            break;
                    }

                }




                scanner.close();
            } else {
                System.out.println("Failed to establish connection.");
            } // Close the connection (in a real scenario, do this in a finally
            conn.close();
        }
        catch (ClassNotFoundException | SQLException | ParseException e) {
            e.printStackTrace();
        }
    }


    public static void getAllStudents() throws SQLException {
        Statement stmt = conn.createStatement();
        String SQL = "SELECT * FROM students";
        ResultSet rs = stmt.executeQuery(SQL);
        System.out.println(String.format("|%20s |%20s |%20s |%30s |%20s|", "student_id", "first_name", "last_name", "email", "enrollment_date"));
        while(rs.next()){
            int student_id = rs.getInt("student_id");
            String first_name = rs.getString("first_name");
            String last_name = rs.getString("last_name");
            String email = rs.getString("email");
            String enrollment_date = rs.getString("enrollment_date");
            System.out.println(String.format("|%20s |%20s |%20s |%30s |%20s|", student_id, first_name, last_name, email, enrollment_date));
        }
        rs.close();
        stmt.close();

    }

    public static void addStudent(String first_name, String last_name, String email, String enrollment_date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date e_date = simpleDateFormat.parse(enrollment_date);
        java.sql.Date e_date_sql = new java.sql.Date(e_date.getTime());

        String insertSQL = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, first_name);
            pstmt.setString(2, last_name);
            pstmt.setString(3, email);
            pstmt.setDate(4, e_date_sql);
            pstmt.executeUpdate();
            System.out.println("Data inserted using PreparedStatement.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void updateStudentEmail(int student_id, String new_email) {
        String updateSQL = "UPDATE students SET email = ? WHERE student_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
            pstmt.setString(1, new_email);
            pstmt.setInt(2, student_id);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Email address updated for student with student_id: " + student_id);
            } else {
                System.out.println("No student found with student_id: " + student_id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteStudent(int student_id) {
        String deleteSQL = "DELETE FROM students WHERE student_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
            pstmt.setInt(1, student_id);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Student with student_id " + student_id + " deleted successfully.");
            } else {
                System.out.println("No student found with student_id: " + student_id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
