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
    private String invalidDateOfInquiry = "20/02/10";
    private String infoProvided = "Looking for family members";
    private String expectedLogDetails = "Inquirer: John, Missing Person: Jane Alex, Date of Inquiry: 2024-02-10, Info Provided: Looking for family members, Last Known Location: University of Calgary"; 

    public ReliefServiceTest() {}

    @Before
    public void setUp() {
        inquirer = new Inquirer("John", "Alex", "123-456-7890");
        missingPerson = new DisasterVictim("Jane Alex", "2024-01-25");
        lastKnownLocation = new Location("University of Calgary", "2500 University Dr NW");
        reliefService = new ReliefService(inquirer, missingPerson, validDateOfInquiry, infoProvided, lastKnownLocation);
    }

    @Test
    public void testReliefServiceConstructorWithValidDate() {
        assertNotNull("The Relief Service constructor was not able to make a Relief Service object.", reliefService);
        assertEquals("Inquirer does not match the one set in setup.", inquirer, reliefService.getInquirer());
        assertEquals("Missing person does not match the one set in setup.", missingPerson, reliefService.getMissingPerson());
        assertEquals("Date of inquiry does not match the one set in setup.", validDateOfInquiry, reliefService.getDateOfInquiry());
        assertEquals("Info provided does not match the one set in setup.", infoProvided, reliefService.getInfoProvided());
        assertEquals("Last known location does not match the one set in setup.", lastKnownLocation, reliefService.getLastKnownLocation());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReliefServiceConstructorWithInvalidDate() {
        ReliefService newReliefService = new ReliefService(inquirer, missingPerson, invalidDateOfInquiry, infoProvided, lastKnownLocation);
    }

    @Test
    public void testGetInquirer() {
        assertEquals("Method getInquirer was not able to retrieve Inquirer properly.", inquirer, reliefService.getInquirer());
    }

    @Test
    public void testSetInquirer() {
        Inquirer newInquirer = new Inquirer("Jonny", "Wick", "123-456-7899");
        reliefService.setInquirer(newInquirer);
        assertEquals("The method setInquirer was not able to update inquirer.", newInquirer, reliefService.getInquirer());
    }

    @Test
    public void testGetMissingPerson() {
        assertEquals("Missing person does not match the one set in setup", missingPerson, reliefService.getMissingPerson());
    }

    @Test
    public void testSetMissingPerson() {
        DisasterVictim newMissingPerson = new DisasterVictim("John Carly", "2023-01-05");
        reliefService.setMissingPerson(newMissingPerson);
        assertEquals("Method setMissingPerson did not update missingPerson.", newMissingPerson, reliefService.getMissingPerson());
    }

    @Test
    public void testGetDateOfInquiry() {
        assertEquals("Date of inquiry does not match the one set in setup", validDateOfInquiry, reliefService.getDateOfInquiry());
    }

    @Test
    public void testSetDateOfInquiryWithValidDate() {
        reliefService.setDateOfInquiry(validDateOfInquiry);
        assertEquals("Setting a valid date did not update the date of inquiry", validDateOfInquiry, reliefService.getDateOfInquiry());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetDateOfInquiryWithInvalidDate() {
        reliefService.setDateOfInquiry(invalidDateOfInquiry);
    }

    @Test
    public void testGetInfoProvided() {
        assertEquals("Info provided did not match the one set in setup.", infoProvided, reliefService.getInfoProvided());
    }

    @Test
    public void testSetInfoProvided() {
        String newInfoProvided = "I am trying to find my two lost sons.";
        reliefService.setInfoProvided(newInfoProvided);
        assertEquals("Info provided was not able to get updated with new information.", newInfoProvided, reliefService.getInfoProvided());
    }

    @Test
    public void testGetLastKnownLocation() {
        assertEquals("Last known location did not match the one set in setup", lastKnownLocation, reliefService.getLastKnownLocation());
    }

    @Test
    public void testSetLastKnownLocation() {
        Location newLocation = new Location("NASA Help Centre", "1202 Carlington Drive, CA");
        reliefService.setLastKnownLocation(newLocation);
        assertEquals("The location was not able to be updated with a new location.", newLocation, reliefService.getLastKnownLocation());
    }

    @Test
    public void testGetLogDetails() {
        assertEquals("Log details do not match the expected format.", expectedLogDetails, reliefService.getLogDetails());
    }

    @Test
    public void testValidDateFormatWithCorrectFormatReliefService() {
        boolean expectedCheck = true;
        boolean actualCheck = reliefService.isValidDateFormat("2023-01-01");
        assertEquals("Method validateDateFormat returned false for a date that has a correct format.", expectedCheck, actualCheck);
    }

    @Test(expected = IllegalArgumentException.class) 
    public void testValidDateFormatWithIncorrectFormatReliefService() {
        reliefService.isValidDateFormat("209-01-01");
    }
}