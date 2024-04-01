/**
 * @author Alyan Qureshi <a href="mailto:muhammad.qureshi4@ucalgary.ca">
 * muhammad.qureshi4@ucalgary.ca</a>
 * @version 1.8
 * @since 1.0
*/

package edu.ucalgary.oop;

import org.junit.*;
import static org.junit.Assert.*;

public class ReliefServiceTest {

    private ReliefService reliefService;
    private Inquirer inquirer;
    private DisasterVictim missingPerson;
    private Location lastKnownLocation;
    private String validDateOfInquiry = "2024-02-10";
    private String invalidDateOfInquiry = "2024/02/10";
    private String infoProvided = "Looking for family members";
    private String expectedLogDetails = "Inquirer: John, Missing Person: Jane Alex, Date of Inquiry: 2024-02-10, Info Provided: Looking for family member, Last Known Location: University of Calgary"; 

    /** 
     * Note: pastQueries: String[] and pastInquirers: HashSet<Inquirer> is used for the Relief Service user interface. The methods 
     for this interface are appendQuery and searchPastQuery. Thus, I will not be testing these in this file since it said on the 
     class anouncement board that we do need to test the user interface which these methods will be used for.  
     */

    /** Default Constructor */ 
    public ReliefServiceTest() {}

    /** Set Up code to set up each variable such as inquirer and missingPerson. */ 
    @Before
    public void setUp() {
        inquirer = new Inquirer("John", "Alex", "1234567890", "Looking for family member");
        missingPerson = new DisasterVictim("Jane Alex", "2024-01-25");
        lastKnownLocation = new Location("University of Calgary", "2500 University Dr NW");
        reliefService = new ReliefService(inquirer, missingPerson, validDateOfInquiry, infoProvided, lastKnownLocation);
    }

    /** Testing Relief Service Constructor with a valid date format */ 
    @Test
    public void testReliefServiceConstructorWithValidDate() {
        assertNotNull("The Relief Service constuctor was not able to make a Relief Service object.", reliefService);
    }

    /** Testing Relief Service Constructor with an invalid date format */
    @Test(expected = IllegalArgumentException.class)
    public void testReliefServiceConstructorWithInvalidDate() {
        String newReliefService = new ReliefService(inquirer, missingPerson, invalidDateOfInquiry, infoProvided, lastKnownLocation);
        // Expecting IllegalArgumentException due to invalid date format
    }

    /** Testing method getInqirer */
    @Test
    public void testGetInquirer() {
        assertEquals("Method getInquirer was not able to retrieve Inquirer properly.", inquirer, reliefService.getInquirer());
    }

    /**  Testing method setInquirer */
    @Test
    public void testSetInquirer() {
        Inquirer newInquirer = new Inquirer("Jonny", "Wick", "1234567899", "Waiting for family members");
        reliefService.testSetInquirer(newInquirer);
        assertEquals("The method setInquirer was not able to update inquirer.", newInquirer, reliefService.getInquirer());
    }

    /** Testing method getMissingPerson  */
    @Test
    public void testGetMissingPerson() {
        assertEquals("Missing person does not match the one set in setup", missingPerson, reliefService.getMissingPerson());
    }

    /** Testing method setMissingPerson */
    @Test
    public void testSetMissingPerson() {
        DisasterVictim newMissingPerson = new DisasterVictim("John Carly", "2023-01-05");
        reliefService.setMissingPerson(newMissingPerson);
        assertEquals("Method setMissingPerson did not update missingPerson.", newMissingPerson, reliefService.getMissingPerson());
    }

    /** Testing getDateOfInquiry */
    @Test
    public void testGetDateOfInquiry() {
        assertEquals("Date of inquiry does not match the one set in setup", validDateOfInquiry, reliefService.getDateOfInquiry());
    }

    /** Testing method setDateOfInquiry with a valid date format */
    @Test
    public void testSetDateOfInquiryWithValidDate() {
        reliefService.setDateOfInquiry(validDateOfInquiry);
        assertEquals("Setting a valid date did not update the date of inquiry", validDateOfInquiry, reliefService.getDateOfInquiry());
    }

    /** Testing method setDateOfInquiry with an invalid date format */
    @Test(expected = IllegalArgumentException.class)
    public void testSetDateOfInquiryWithInvalidDate() {
        reliefService.setDateOfInquiry(invalidDateOfInquiry); // This should throw IllegalArgumentException due to invalid format
    }

    /** Testing method getInfoProvided */
    @Test
    public void testGetInfoProvided() {
        assertEquals("Info provided did not match the one set in setup.", infoProvided, reliefService.getInfoProvided());
    }

    /** Testing method setInfoProvided */
    @Test
    public void testSetInfoProvided() {
        String newInfoProvided = "I am trying to find my two lost sons.";
        reliefService.setInfoProvided(newInfoProvided);
        assertEquals("Info provided was not able to get updated with new information.", newInfoProvided, reliefService.getInfoProvided());
    }

    /** Testing method getLastKnownLocation */
    @Test
    public void testGetLastKnownLocation() {
        assertEquals("Last known location did not match the one set in setup", lastKnownLocation, reliefService.getLastKnownLocation());
    }

    /** Testing method setLastKnownLocation */
    @Test
    public void testSetLastKnownLocation() {
        Location newLocation = new Location("NASA Help Centre", "1202 Carlington Drive, CA");
        reliefService.setLastKnownLocation(newLocation);
        assertEquals("The location was not able to be updated with a new location.", newLocation, reliefService.getLastKnownLocation());
    }

    /** Testing method getLogDetails */
    @Test
    public void testGetLogDetails() {
        assertEquals("Log details do not match the expected format.", expectedLogDetails, reliefService.getLogDetails());
    }

    /** Testing validateDateFormat with an correct date format */
    @Test
    public void testValidDateFormatWithCorrectFormatReliefService() {
        boolean expectedCheck = true;
        boolean actualCheck = medicalRecord.validateDateFormat("2023-01-01");
        assertEquals("Method validateDateFormat returned false for a date that has a correct format.", expectedCheck, actualCheck);
    }

    /**  Testing validateDateFormat with an incorrect date format */
    @Test(expected = IllegalArgumentException.class) 
    public void testValidDateFormatWithIncorrectFormatReliefService() {
        medicalRecord.validateDateFormat("2090-01-01");
        // Expecting IllegalArgumentException due to an invalid date format
    }
}