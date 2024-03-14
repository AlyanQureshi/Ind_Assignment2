package edu.ucalgary.oop;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.Vector;
import java.util.HashSet;
import java.util.ArrayList;

public class DisasterVictimTest {
    private DisasterVictim victim1;
    private HashSet<Supply> personalBelongings; 
    private HashSet<FamilyRelation> familyConnections;
    private Vector<MedicalRecord> medicalRecords;
    private String firstName = "Freda";
    private String ENTRY_DATE = "2024-01-18";
    private String validDate = "2024-01-15";
    private String invalidDate = "15/13/2024";
    private String gender = "Male";
    private String dateOfBirth = "2001-08-13";
    private String comments = "Needs medical attention and speaks 2 languages";
    private int age;
    String[] mealRestrictions = ["Halal Meal"];
    public enum DietTypes {
        AVML, DBML, GFML, LSML, MOML, PFML, VGML, VJML // These are all possible dietary restrictions
    }

    @Before
    public void setUp() {
        victim1 = new DisasterVictim(firstName, ENTRY_DATE);
        DisasterVictim victim2 = new DisasterVictim("Bob", "2023-02-02");
        familyConnections = new HashSet<>();
        familyConnections.add(new FamilyRelation(victim1, "brother", victim2));
        medicalRecords.add(new MedicalRecord(new Location("Henry Park", "1234 Street NW"), "Got surgery for eye.", "2024-01-01"));
        personalBelongings.add(new Supply("Grapes", 3));
        age = 12;
    }

    @Test
    public void testDisasterVictimConstructorWithValidDate() {
        DisasterVictim newVictim = new DisasterVictim(firstName, validDate);
        assertEquals("The constructor was not able to set a valid date for Entry_Date.", validDate, newVictim.getEntryDate());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testDisasterVictimConstructorWithInvalidDate() {
        DisasterVictim newVictim = new DisasterVictim(firstName, invalidDate);
        // Expecting to throw Illegal Argument Exception when running this test.
    }

    @Test
    public void testGetAndSetFirstName() {
        String newFirstName = "Micheal";
        victim1.setFirstName(newFirstName);
        assertEquals("The method setFirstName did not update and getFirstName did not return the new first name", newFirstName, victim1.getFirstName());
    }

    @Test
    public void testGetAndSetLastName() {
        String newLastName = "Egypt";
        victim1.setLastName(newLastName);
        assertEquals("The method setLastName did not update and getLastName did not return the new last name", newLastName, victim1.getLastName());
    }

    @Test
    public void testGetDateOfBirth() {
        assertEquals("The method getDateOfBirth did not return the accurate date of birth.", victim1.getDateOfBirth(), dateOfBirth);
    }

    @Test 
    public void testSetDateOfBirthWithValidDate() {
        victim1.setDateOfBirth(validDate);
        assertEquals("The victim's date of birth was not able to be updated.", dateOfBirth, victim1.getDateOfBirth());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testSetDateOfBirthWithInvalidDate() {
        victim1.setDateOfBirth(invalidDate);
        // Expecting to throw Illegal Argument Exception when running this test.
    }

    @Test
    public void testGetAge() {
        assertEquals("The method getAge did not return the right age.", age, victim1.getAge());
    }

    @Test
    public void testSetValidAge() {
        victim1.setAge(20);
        assertEquals("The method setAge did not set the new age.", 20, victim1.getAge());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testSetInvalidAge() {
        victim1.setAge(-20);
        // Expecting to throw Illegal Argument Exception when running this test.
    }

    @Test
    public void testSetAgeWithValidDateOfBirthAndEntryDate() {
        victim1.setAge(dateOfBirth, ENTRY_DATE);
        assertEquals("The method setAge did not correctly set the new age with dateOfBirth and EntryDate.", age, victim1.getAge());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testSetAgeWithInvalidDateOfBirthAndEntryDate() {
        victim1.setAge("2030-01-01", "2028-01-01");
        // Expecting to throw Illegal Argument Exception when running this test.
    }

    @Test
    public void testSettingBothAgeAndBirthDate() {
        boolean check = false;
        
        if (victim1.setAge(15) && victim1.setDateOfBirth("2003-02-01"))
            check = true;
        
        assertFalse("Both methods setAge and setDateOfBirth should not be able to run.", check)
    }

    @Test
    public void testGetAssignedSocialID() {
        DisasterVictim newVictim = new DisasterVictim("James", "2022-10-25");
        int socialId = newVictim.getAssignedSocialID() + 1;
        DisasterVictim newerVictim = new DisasterVictim("Bob", "2024-03-14");

        assertEquals("The method getAssignedSocialID did not return the expected social ID", socialId, newerVictim.getAssignedSocialID());
    }

    @Test
    public void testGetFamilyConnections() {
        assertEquals("The method getFamilyConnections did not return the right Family Connections.", familyConnections, victim1.getFamilyConnections());
    }

    @Test
    public void testGetMedicalRecords() {
        assertEquals("The method getMedicalRecords did not return the right medical records.", medicalRecords, victim1.getMedicalRecords());
    }

    @Test
    public void testGetPersonalBelongings() {
        assertEquals("The method getPersonalBelongings did not return the right personal belongings.", personalBelongings, victim1.getPersonalBelongings());
    }

    @Test
    public void testSetFamilyConnections() {
        DisasterVictim newVictim1 = new DisasterVictim("Bob", "2020-01-01");
        DisasterVictim newVictim2 = new DisasterVictim("Shake", "2022-01-01");
        
        FamilyRelation relation1 = new FamilyRelation(newVictim1, "Father", newVictim2);
        
        HashSet<FamilyRelation> newFamilyConnections = new HashSet<>();
        newFamilyConnections.add(relation1);
        victim1.setFamilyConnections(newFamilyConnections);

        assertEquals("The method setFamilyConnections did not update the family connections.", newFamilyConnections, victim1.getFamilyConnections());
    }

    @Test
    public void testSetMedicalRecords() {
        Location newLocation = new Location("Shelter F", "9999 Shelter Ave");
        MedicalRecord newRecord = new MedicalRecord(newLocation, "Sore Throat", "2023-01-01");

        Vector<MedicalRecord> newRecords = new Vector<>();
        newRecords.add(newRecord);
        victim1.setMedicalRecords(newRecords);

        assertEquals("The method setMedicalRecords did not update the medical records.", newRecords, victim1.getMedicalRecords());
    }

    @Test
    public void testSetPersonalBelongings() {
        Supply item1 = new Supply("Granola Bar", 5);
        Supply item2 = new Supply("Water Bottle", 10);
        HashSet<Supply> newSupplies = new HashSet<>();
        newSupplies.add(item1);
        newSupplies.add(item2);

        victim1.setPersonalBelongings(newSupplies);
    
        assertEquals("The method setPersonalBelongings did correctly update the personal belongings.", newSupplies, victim1.getPersonalBelongings());
    }

    @Test
    public void testAddPersonalBelonging() {
        Supply newItem = new Supply("Milk", 2);
        victim1.addPersonalBelonging(newItem);
        assertTrue("The method addPersonalBelonging did not add the new personal belonging.", newItem, personalBelongings.contains(newItem));
    }

    @Test
    public void testRemovePersonalBelonging() {
        Supply itemToAdd = new Supply("Milk", 2);
        Supply unwantedItem = new Supply("Coco Cola", 6);

        victim1.addPersonalBelonging(itemToAdd);
        victim1.addPersonalBelonging(unwantedItem);

        victim1.removePersonalBelonging(unwantedItem);
        assertFalse("The method removePersonalBelonging did not remove the unwanted supply.", unwantedItem, personalBelongings.contains(unwantedItem));
    }

    @Test
    public void testRemoveFamilyConnection() {
        DisasterVictim newVictim1 = new DisasterVictim("Bob", "2020-01-01");
        DisasterVictim newVictim2 = new DisasterVictim("Shake", "2022-01-01");
        
        FamilyRelation unwantedRelation = new FamilyRelation(newVictim1, "Father", newVictim2);
        victim1.addFamilyConnection(unwantedRelation);
        victim1.removeFamilyConnection(unwantedRelation);

        assertFalse("The method removeFamilyConnection did not remove the unwanted relation.", unwantedRelation, familyConnections.contains(unwantedRelation));
    }

    @Test
    public void testAddFamilyConnection() {
        DisasterVictim newVictim1 = new DisasterVictim("Bob", "2020-01-01");
        DisasterVictim newVictim2 = new DisasterVictim("Shake", "2022-01-01");
        
        FamilyRelation newRelation = new FamilyRelation(newVictim1, "Father", newVictim2);
        victim1.addFamilyConnection(newRelation);

        assertTrue("The method addFamilyConnection did not add a new family connection.", newRelation, familyConnections.contains(newRelation));
    }

    @Test
    public void testAddMedicalRecord() {
        Location newLocation = new Location("Shelter B", "8888 Shelter Ave");
        MedicalRecord newRecord = new MedicalRecord(newLocation, "Pain in Throat", "2023-01-01");

        victim1.addMedicalRecord(newRecord);

        assertTrue("The method addMedicalRecord did not add a new medical record.", newRecord, medicalRecords.contains(newRecord));
    }

    @Test
    public void testGetEntryDate() {
        assertEquals("The method getEntryDate did not return the right entry date.", ENTRY_DATE, victim1.getEntryDate());
    }

    @Test
    public void testGetComments() {
        assertEquals("The method getComments did not return the comments.", comments, victim1.getComments());
    }

    @Test
    public void testSetComments() {
        String newComment = "I am waiting for my brother to pick me up from hospital.";
        victim1.setComments(newComment);
        assertEquals("The method setComments did not set the comment correctly.", newComment, victim1.getComments());
    }

    @Test
    public void testGetGender() {
        assertEquals("The method getGender did not return the right gender.", gender, victim1.getGender());
    }

    @Test
    public void testSetGenderWithValidInput() {
        String validGender = "Female";
        victim1.setGender(validGender);
        assertEquals("The method setGender did not update the gender correctly.", validGender, victim1.getGender());
    }

    @Test (expected = IllegalArgumentException.class) 
    public void testSetGenderWithInvalidInput() {
        String invalidGender = "Rhinosaurus"; //Should be Invalid
        victim1.setGender(invalidGender);
        // Expecting to throw Illegal Argument Exception when running this test.
    }

    @Test
    public void testGetDietaryRestrictions() {
        assertEquals("The method getDietaryRestrictions did not return the correct dietary restriction.", 
            victim1.getDietaryRestrictions(), mealRestrictions);
    }

    @Test
    public void testSetDietaryRestrictions() {
        DietRestrictions newRestriction = {DietTypes.VGML};
        victim1.setDietaryRestrictions(newRestriction.toString());
        assertEquals("The method setDietaryRestrictions did not update the dietary restrictions correctly.", 
            victim1.getDietaryRestrictions(), newRestriction.toString());
    }

    // Testing validateDateFormat with a correct date format
    @Test
    public void testValidDateFormatWithCorrectFormatDisasterVictim() {
        boolean expectedCheck = true;
        boolean actualCheck = medicalRecord.validateDateFormat("2023-01-01");
        assertEquals("Method validateDateFormat returned false for a date that has a correct format.", expectedCheck, actualCheck);
    }

    // Testing validateDateFormat with an incorrect date format
    @Test(expected = IllegalArgumentException.class) 
    public void testValidDateFormatWithIncorrectFormatDisasterVictim() {
        medicalRecord.validateDateFormat("2090-01-01");
        // Expecting IllegalArgumentException due to an invalid date format
    }

}
