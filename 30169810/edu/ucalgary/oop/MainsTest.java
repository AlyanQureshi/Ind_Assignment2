/**
 * @author Alyan Qureshi <a href="mailto:muhammad.qureshi4@ucalgary.ca">
 * muhammad.qureshi4@ucalgary.ca</a>
 * @version 3.2
 * @since 1.0
*/

package edu.ucalgary.oop;
import java.sql.*;
import org.junit.*;
import static org.junit.Assert.*;

public class MainsTest {
    private Connection dbConnect;
    private ResultSet results;

    public MainsTest() {}

    /**Testing for whether an inquirer already exists (Requirement 5) */
    @Test
    public void testExistingInquirer() {
        Mains database = new Mains();
        database.createConnection();

        Inquirer person = new Inquirer("Dominik", "Pflug", "123-456-9831");
        
        int inquirerID = 0;
        try {
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM inquirer");

            while (results.next()) {
                if ((results.getString("firstname").equals(person.getFirstName())) && 
                    (results.getString("phonenumber").equals(person.getServicesPhoneNum())) &&
                    (results.getString("lastname").equals(person.getLastName()))) {
                    inquirerID = results.getInt("id");
                    break;
                }
            }

            myStmt.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        database.close();
        assertEquals("The id should be equal to 1 as the inquirer exists but it is not 1.", 1, inquirerID);
    }

    /** Testing to see whether an inquirer does not exist*/
    @Test
    public void testInquirerThatDoesNotExist() {
        Mains database = new Mains();
        database.createConnection();

        Inquirer person = new Inquirer("Harry", "Poor", "111-222-1111");
        
        int inquirerID = 0;
        try {
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM inquirer");

            while (results.next()) {
                if ((results.getString("firstname").equals(person.getFirstName())) && 
                    (results.getString("phonenumber").equals(person.getServicesPhoneNum())) &&
                    (results.getString("lastname").equals(person.getLastName()))) {
                    inquirerID = results.getInt("id");
                    break;
                }
            }

            myStmt.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        database.close();
        assertEquals("The id should be equal to 0 as the inquirer did not exist", 0, inquirerID);
    }

    /** Testing to see whether a new inquiry is added when an inquirer exists.*/
    @Test
    public void testMaintainingMultipleInteractionsWithTheSameInquirer() {
        Mains database = new Mains();
        database.createConnection();

        Inquirer person = new Inquirer("Dominik", "Pflug", "123-456-9831");
        
        int inquirerID = 0;
        try {
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM inquirer");

            while (results.next()) {
                if ((results.getString("firstname").equals(person.getFirstName())) && 
                    (results.getString("phonenumber").equals(person.getServicesPhoneNum())) &&
                    (results.getString("lastname").equals(person.getLastName()))) {
                    inquirerID = results.getInt("id");
                    break;
                }
            }
            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        int maxInquiryLogID = 0;
        try {
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT MAX(id) AS max_id FROM inquiry_log");
            if (results.next()) {
                maxInquiryLogID = results.getInt("max_id");
            }
            
            myStmt.close();
        } catch (SQLException el) {
            el.printStackTrace();
        }

        int newLogID = maxInquiryLogID + 1;
        String date = "2020-02-02";
        String info = "Hello My name is Alyan.";
        try {
            java.sql.Date dateOfInquiry = java.sql.Date.valueOf(date);

            String query = "INSERT INTO inquiry_log (id, inquirer, callDate, details) VALUES (?,?,?,?)";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);

            myStmt.setInt(1, newLogID);
            myStmt.setInt(2, inquirerID);
            myStmt.setDate(3, dateOfInquiry);
            myStmt.setString(4, info);

            int rowCount = myStmt.executeUpdate();
            myStmt.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        int newMaxInquiryLogID = 0;
        try {
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT MAX(id) AS max_id FROM inquiry_log");
            if (results.next()) {
                newMaxInquiryLogID = results.getInt("max_id");
            }
            
            myStmt.close();
        } catch (SQLException el) {
            el.printStackTrace();
        }

        database.close();
        assertEquals("The id should be equal to 10 as that is the new id of the the new inquiry that was supposed to be inserted into the database.", 10, newMaxInquiryLogID);
    }
}