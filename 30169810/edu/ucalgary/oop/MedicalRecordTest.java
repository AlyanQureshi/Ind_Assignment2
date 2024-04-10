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

    private MedicalRecord medicalRecord;
    private Location location;
    private String treatmentDetails;
    private String dateOfTreatment;
    private String inValidDateOfTreatment;

    @Before
    public void setUp() {
        location = new Location("Telus Spark", "102 17th Ave NW, Calgary");
        treatmentDetails = "Fixed a fracture in femur.";
        dateOfTreatment = "2024-03-13";
        inValidDateOfTreatment = "2024-2-4";
        medicalRecord = new MedicalRecord(location, treatmentDetails, dateOfTreatment);
    }

    @Test
    public void testMedicalRecordConstructorWithValidDate() {
        assertNotNull("Medical Record Constructor did not create a new object.", medicalRecord);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMedicalRecordConstructorWithInvalidDate() {
        MedicalRecord invalidMedicalRecord = new MedicalRecord(location, treatmentDetails, inValidDateOfTreatment);
        // Expecting IllegalArgumentException due to invalid date format
    }

    @Test
    public void testSetLocation() {
        Location expectedLocation = new Location("Walmart", "Centre Street NE, Calgary");
        medicalRecord.setLocation(expectedLocation);
        assertEquals("Method setLocation() did not update the location.", expectedLocation, medicalRecord.getLocation());
    }

    @Test
    public void testGetLocation() {
        assertEquals("Method getLocation did not return the right location.", location, medicalRecord.getLocation());
    }

    @Test
    public void testGetTreatmentDetails() {
        assertEquals("Method getTreatmentDetails did not return the correct treatment details.", treatmentDetails, medicalRecord.getTreatmentDetails());
    }

    @Test
    public void testSetTreatmentDetails() {
        String newTreatmentDetails = "Finished surgery of ther orthogonal surgery.";
        medicalRecord.setTreatmentDetails(newTreatmentDetails);
        assertEquals("Method setTreatmentDetails did not set the new treatment details.", newTreatmentDetails, medicalRecord.getTreatmentDetails());
    }    

    @Test
    public void testGetDateOfTreatment() {
        assertEquals("Method getDateOfTreatment did not return the right date of treatment.", dateOfTreatment, medicalRecord.getDateOfTreatment());
    }

    @Test
    public void testSetDateOfTreatmentWithValidFormat() {
        String newDate = "2023-05-19";
        medicalRecord.setDateOfTreatment(newDate);
        assertEquals("Method setDateOfTreatment did not return the right date.", newDate, medicalRecord.getDateOfTreatment());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetDateOfTreatmentWithInvalidFormat() {
        medicalRecord.setDateOfTreatment(inValidDateOfTreatment);
        // This will return an IllegalArgumentException due to invalid date format
    }

    @Test
    public void testValidDateFormatWithCorrectFormatMedicalRecord() {
        boolean expectedCheck = true;
        boolean actualCheck = medicalRecord.isValidDateFormat(dateOfTreatment);
        assertEquals("Method isValidDateFormat returned false for a date that has a correct format.", expectedCheck, actualCheck);
    }

    @Test(expected = IllegalArgumentException.class) 
    public void testValidDateFormatWithIncorrectFormatMedicalRecord() {
        medicalRecord.isValidDateFormat(inValidDateOfTreatment);
        // Expecting IllegalArgumentException due to an invalid date format
    }
}