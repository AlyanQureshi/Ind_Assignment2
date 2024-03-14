package edu.ucalgary.oop;

import org.junit.*;
import static org.junit.Assert.*;

public class MedicalRecordTest {

    Location location = new Location("Telus Spark", "102 17th Ave NW, Calgary");
    private String treatmentDetails = "Fixed a fracture in femur.";
    private String dateOfTreatment = "2024-03-13";
    private String inValidDateOfTreatment = "2024-2-4";
    MedicalRecord medicalRecord = new MedicalRecord(location, treatmentDetails, dateOfTreatment);

    public MedicalRecordTest() {} 

    // Testing constructor with a valid date format
    @BeforeClass
    public void testMedicalRecordConstructorWithValidDate() {
        assertNotNull("Medical Record Constructor did not create a new object.", medicalRecord);
    }

    // Testing constructor with an invalid date format
    @Test(expected = IllegalArgumentException.class)
    public void testMedicalRecordConstructorWithInvalidDate() {
        MedicalRecord invalidMedicalRecord = new MedicalRecord(location, treatmentDetails, inValidDateOfTreatment);
        // Expecting IllegalArgumentException due to invalid date format
    }

    // Testing set location with a new location
    @Test
    public void testSetLocation() {
        Location expectedLocation = new Location("Walmart", "Centre Street NE, Calgary");
        medicalRecord.setLocation(newLocation);
        assertEquals("Method setLocation() did not update the location.", medicalRecord.getLocation().getName(), expectedLocation.getName());
    }

    // Testing get location
    @Before
    public void testGetLocation() {
        assertEquals("Method getLocation did not return the right location.", location, medicalRecord.getLocation());
    }

    // Testing get treatment details
    @Before
    public void testGetTreatmentDetails() {
        assertEquals("Method getTreatmentDetails did not return the correct treatment details.", treatmentDetails, medicalRecord.getTreatmentDetails());
    }

    // Testing set Treatment Details
    @Test
    public void testSetTreatmentDetails() {
        String newTreatmentDetails = "Finished surgery of ther orthogonal surgery.";
        medicalRecord.setTreatmentDetails(newTreatmentDetails);
        assertEquals("Method setTreatmentDetails did not set the new treatment details.", newTreatmentDetails, medicalRecord.getTreatmentDetails());
    }    

    // Testing get Date of treatment 
    @Before
    public void testGetDateOfTreatment() {
        assertEquals("Method getDateOfTreatment did not return the right date of treatment.", dateOfTreatment, medicalRecord.getDateOfTreatment());
    }

    // Testing set date of treatment with a valid date format
    @Test
    public void testSetDateOfTreatmentWithValidFormat() {
        String newDate = "2023-05-19";
        medicalRecord.setDateOfTreatment(newDate);
        assertEquals("Method setDateOfTreatment did not return the right date.", newDate, medicalRecord.getDateOfTreatment());
    }

    // Testing set date of treatment with an invalid date format
    @Test(expected = IllegalArgumentException.class)
    public void testSetDateOfTreatmentWithInvalidFormat() {
        medicalRecord.setDateOfTreatment(inValidDateOfTreatment);
        // This will return an IllegalArgumentException due to invalid date format
    }
}