/**
 * @author Alyan Qureshi <a href="mailto:muhammad.qureshi4@ucalgary.ca">
 * muhammad.qureshi4@ucalgary.ca</a>
 * @version 2.9
 * @since 1.0
*/

package edu.ucalgary.oop;

import org.junit.*;
import static org.junit.Assert.*;

public class MedicalRecordTest {

    /** this method runs before every test file */
    @Before
    public void setUp() {
        Location location = new Location("Telus Spark", "102 17th Ave NW, Calgary");
        String treatmentDetails = "Fixed a fracture in femur.";
        String dateOfTreatment = "2024-03-13";
        String inValidDateOfTreatment = "2024-2-4";
        MedicalRecord medicalRecord = new MedicalRecord(location, treatmentDetails, dateOfTreatment);
    }

    /** Testing constructor with a valid date format */
    @Test
    public void testMedicalRecordConstructorWithValidDate() {
        assertNotNull("Medical Record Constructor did not create a new object.", medicalRecord);
    }

    /** Testing constructor with an invalid date format */
    @Test(expected = IllegalArgumentException.class)
    public void testMedicalRecordConstructorWithInvalidDate() {
        MedicalRecord invalidMedicalRecord = new MedicalRecord(location, treatmentDetails, inValidDateOfTreatment);
        // Expecting IllegalArgumentException due to invalid date format
    }

    /** Testing set location with a new location */
    @Test
    public void testSetLocation() {
        Location expectedLocation = new Location("Walmart", "Centre Street NE, Calgary");
        medicalRecord.setLocation(newLocation);
        assertEquals("Method setLocation() did not update the location.", medicalRecord.getLocation().getName(), expectedLocation.getName());
    }

    /** Testing get location */
    @Test
    public void testGetLocation() {
        assertEquals("Method getLocation did not return the right location.", location, medicalRecord.getLocation());
    }

    /**  Testing get treatment details */
    @Test
    public void testGetTreatmentDetails() {
        assertEquals("Method getTreatmentDetails did not return the correct treatment details.", treatmentDetails, medicalRecord.getTreatmentDetails());
    }

    /** Testing set Treatment Details */
    @Test
    public void testSetTreatmentDetails() {
        String newTreatmentDetails = "Finished surgery of ther orthogonal surgery.";
        medicalRecord.setTreatmentDetails(newTreatmentDetails);
        assertEquals("Method setTreatmentDetails did not set the new treatment details.", newTreatmentDetails, medicalRecord.getTreatmentDetails());
    }    

    /**  Testing get Date of treatment  */
    @Test
    public void testGetDateOfTreatment() {
        assertEquals("Method getDateOfTreatment did not return the right date of treatment.", dateOfTreatment, medicalRecord.getDateOfTreatment());
    }

    /** Testing set date of treatment with a valid date format */
    @Test
    public void testSetDateOfTreatmentWithValidFormat() {
        String newDate = "2023-05-19";
        medicalRecord.setDateOfTreatment(newDate);
        assertEquals("Method setDateOfTreatment did not return the right date.", newDate, medicalRecord.getDateOfTreatment());
    }

    /** Testing set date of treatment with an invalid date format */
    @Test(expected = IllegalArgumentException.class)
    public void testSetDateOfTreatmentWithInvalidFormat() {
        medicalRecord.setDateOfTreatment(inValidDateOfTreatment);
        // This will return an IllegalArgumentException due to invalid date format
    }

    /**  Testing validateDateFormat with a correct date format */
    @Test
    public void testValidDateFormatWithCorrectFormatMedicalRecord() {
        boolean expectedCheck = true;
        boolean actualCheck = medicalRecord.validateDateFormat(dateOfTreatment);
        assertEquals("Method validateDateFormat returned false for a date that has a correct format.", expectedCheck, actualCheck);
    }

    /**  Testing validateDateFormat with an incorrect date format */
    @Test(expected = IllegalArgumentException.class) 
    public void testValidDateFormatWithIncorrectFormatMedicalRecord() {
        medicalRecord.validateDateFormat(inValidDateOfTreatment);
        // Expecting IllegalArgumentException due to an invalid date format
    }
}