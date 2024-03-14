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

    @BeforeClass
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
        medicalRecord.setLocation(newLocation);
        assertEquals("Method setLocation() did not update the location.", medicalRecord.getLocation().getName(), expectedLocation.getName());
    }

    @Before
    public void testGetLocation() {
        assertEquals("Method getLocation did not return the right location.", location, medicalRecord.getLocation());
    }

    @Before
    public void testGetTreatmentDetails() {
        assertEquals("Method getTreatmentDetails did not return the correct treatment details.", treatmentDetails, medicalRecord.getTreatmentDetails());
    }

    @Test
    public void testSetTreatmentDetails() {
        String newTreatmentDetails = "Finished surgery of ther orthogonal surgery.";
        medicalRecord.setTreatmentDetails(newTreatmentDetails);
        assertEquals("Method setTreatmentDetails did not set the new treatment details.", newTreatmentDetails, medicalRecord.getTreatmentDetails());
    }    

    @Before
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
}