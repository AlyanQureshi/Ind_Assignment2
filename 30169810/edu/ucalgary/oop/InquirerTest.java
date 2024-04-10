/**
 * @author Alyan Qureshi <a href="mailto:muhammad.qureshi4@ucalgary.ca">
 * muhammad.qureshi4@ucalgary.ca</a>
 * @version 1.1
 * @since 1.0
*/

package edu.ucalgary.oop;

import org.junit.*;
import static org.junit.Assert.*;

public class InquirerTest {

    private String expectedFirstName = "Joseph";
    private String expectedLastName = "Bouillon";
    private String servicesPhone = "+1-123-456-7890";
    private Inquirer inquirer;

    /**
     * Default constructor
     */
    public InquirerTest() {}

    /**
     *  Testing Inquirer Constructor
     * */
    @Test
    public void testInquirerConstructor() {
        inquirer = new Inquirer(expectedFirstName, expectedLastName, servicesPhone);
        assertNotNull("Inquirer Constructor was not able to make a new Inquirer object.", inquirer);
    }

    /** Testing method getFirstName() */
    @Test
    public void testGetFirstName() {
        inquirer = new Inquirer(expectedFirstName, servicesPhone);
        assertEquals("Method getFirstName() did not return the inquirer's first name", expectedFirstName, inquirer.getFirstName());
    }

    /** Testing method getLastName() */
    @Test
    public void testGetLastName() {
        inquirer = new Inquirer(expectedFirstName, expectedLastName, servicesPhone);
        assertEquals("Method getLastName() did not return the inquirer's last name", expectedLastName, inquirer.getLastName());
    }

    /**Testing method getServicesPhoneNum() */
    @Test
    public void testGetServicesPhoneNum() {
        inquirer = new Inquirer(expectedFirstName, expectedLastName, servicesPhone);
        assertEquals("Method getServicesPhoneNum() did not return the correct Services Phone Number", servicesPhone, inquirer.getServicesPhoneNum());
    }
}