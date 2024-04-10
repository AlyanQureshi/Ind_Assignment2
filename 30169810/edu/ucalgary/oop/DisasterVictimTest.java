/**
 * @author Alyan Qureshi <a href="mailto:muhammad.qureshi4@ucalgary.ca">
 * muhammad.qureshi4@ucalgary.ca</a>
 * @version 3.1
 * @since 1.0
*/

package edu.ucalgary.oop;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.Vector;
import java.util.HashSet;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class DisasterVictimTest {
    private DisasterVictim victim1;
    private HashSet<Supply> personalBelongings = new HashSet<>();
    private HashSet<FamilyRelation> familyConnections = new HashSet<>();
    private Vector<MedicalRecord> medicalRecords = new Vector<>();
    private String firstName = "Freda";
    private String lastName = "Mcdonalds";
    private String ENTRY_DATE = "2002-01-18";
    private String validDate = "2019-01-15";
    private String invalidDate = "15/13/2124";
    private String gender = "man";
    private String[] genderOptions;
    private final String filename = "genderOptionsTestFile.txt";
    private String dateOfBirth = "2001-08-13";
    private String comments = "Needs medical attention and speaks 2 languages";
    private int age;
    String[] mealRestrictions = {"AVML"};

    enum DietTypes {
        AVML, DBML, GFML, LSML, MOML, PFML, VGML, VJML // These are all possible dietary restrictions
    }

    /** Creates a setup method that will run before every test to ensure consistency */
    @Before
    public void setUp() {
        victim1 = new DisasterVictim(firstName, ENTRY_DATE);
        DisasterVictim victim2 = new DisasterVictim("Bob", "Henry", "2020-02-02", "2023-02-02");
        familyConnections.add(new FamilyRelation(victim1, "child", victim2));
        medicalRecords.add(new MedicalRecord(new Location("Henry Park", "1234 Street NW"), "Got surgery for eye.", "2024-01-01"));
        personalBelongings.add(new Supply("Grapes", 3));
        age = 12;
    }

    /** Testing constructor with a valid date to see if it correctly makes an object */
    @Test
    public void testDisasterVictimConstructorWithValidDate() {
        DisasterVictim newVictim = new DisasterVictim(firstName, validDate);
        assertEquals("The constructor was not able to set a valid date for Entry_Date.", validDate, newVictim.getEntryDate());
    }

    /** Testing constructor with last name with a valid date to see if it correctly makes an object */
    @Test
    public void testDisasterVictimConstructorLastNameWithValidDate() {
        DisasterVictim newVictim = new DisasterVictim(firstName, lastName, validDate);
        assertEquals("The constructor was not able to set a valid date for Entry_Date.", validDate, newVictim.getEntryDate());
    }

    /** Testing constructor with last name and dateOfBirth with a valid date to see if it correctly makes an object */
    @Test
    public void testDisasterVictimConstructorLastNameAndDateOfBirthWithValidDate() {
        DisasterVictim newVictim = new DisasterVictim(firstName, lastName, dateOfBirth, validDate);
        assertEquals("The constructor was not able to set a valid date for Entry_Date.", validDate, newVictim.getEntryDate());
    }

    /** Testing constructor with an invalid date to see if it correctly throws an Illegal Argument Exception  */
    @Test (expected = IllegalArgumentException.class)
    public void testDisasterVictimConstructorWithInvalidDate() {
        DisasterVictim newVictim = new DisasterVictim(firstName, invalidDate);
        // Expecting to throw Illegal Argument Exception when running this test.
    }

    /** Testing constructor with last name with an invalid date to see if it correctly throws an Illegal Argument Exception */
    @Test (expected = IllegalArgumentException.class)
    public void testDisasterVictimConstructorLastNameWithInvalidDate() {
        DisasterVictim newVictim = new DisasterVictim(firstName, lastName, invalidDate);
        // Expecting to throw Illegal Argument Exception when running this test.
    }

    /** Testing constructor with last name and date of birth with an invalid date to see if it correctly throws an Illegal Argument Exception */
    @Test (expected = IllegalArgumentException.class)
    public void testDisasterVictimConstructorLastNameAndDateOfBirthWithInvalidDate() {
        DisasterVictim newVictim = new DisasterVictim(firstName, lastName, dateOfBirth, invalidDate);
        // Expecting to throw Illegal Argument Exception when running this test.
    }

    /** Testing constructor with last name and invalid date of birth with an valid date to see if it correctly throws an Illegal Argument Exception */
    @Test (expected = IllegalArgumentException.class)
    public void testDisasterVictimConstructorLastNameAndDateOfBirthWithInvalidBirthDate() {
        DisasterVictim newVictim = new DisasterVictim(firstName, lastName, invalidDate, dateOfBirth);
        // Expecting to throw Illegal Argument Exception when running this test.
    }

    /** Testing get and set first name to ensure both methods work correctly */
    @Test
    public void testGetAndSetFirstName() {
        String newFirstName = "Micheal";
        victim1.setFirstName(newFirstName);
        assertEquals("The method setFirstName did not update and getFirstName did not return the new first name", newFirstName, victim1.getFirstName());
    }

    /** Testing get and set lirst name to ensure both methods work correctly */
    @Test
    public void testGetAndSetLastName() {
        String newLastName = "Egypt";
        victim1.setLastName(newLastName);
        assertEquals("The method setLastName did not update and getLastName did not return the new last name", newLastName, victim1.getLastName());
    }

    /** Testing whether the method getDateOfBirth accurately return the date of birth  */
    @Test
    public void testGetDateOfBirth() {
        assertEquals("The method getDateOfBirth did not return the accurate date of birth.", victim1.getDateOfBirth(), dateOfBirth);
    }

    /** Testing whether setDateOfBirth updates the date with a valid date  */
    @Test 
    public void testSetDateOfBirthWithValidDate() {
        victim1.setDateOfBirth(validDate);
        assertEquals("The victim's date of birth was not able to be updated.", dateOfBirth, victim1.getDateOfBirth());
    }

    /** Testing whether setDateOfBirth accurately throws an Illegal Argument Exception */
    @Test (expected = IllegalArgumentException.class)
    public void testSetDateOfBirthWithInvalidDate() {
        victim1.setDateOfBirth(invalidDate);
        // Expecting to throw Illegal Argument Exception when running this test.
    }

    /** Testing whether the method getAge returns the correct age */
    @Test
    public void testGetAge() {
        assertEquals("The method getAge did not return the right age.", age, victim1.getAge());
    }

    /** Testing whether the methods setAge accurately updates the age of the victim */
    @Test
    public void testSetValidAge() {
        victim1.setAge(20);
        assertEquals("The method setAge did not set the new age.", 20, victim1.getAge());
    }

    /** Testing whether the method setAge accurately throws an Illegal Argument Exception when the age is a negative value. */
    @Test (expected = IllegalArgumentException.class)
    public void testSetInvalidNegativeAge() {
        victim1.setAge(-20);
        // Expecting to throw Illegal Argument Exception when running this test.
    }

    /** Testing whether the method setAge accurately throws an Illegal Argument Exception when the age is too high of a value. */
    @Test (expected = IllegalArgumentException.class)
    public void testSetInvalidHighAge() {
        victim1.setAge(200);
        // Expecting to throw Illegal Argument Exception when running this test.
    }

    /** Testing whether setting first age then date of birth will properly throw an Illegal Argument Exception like
     * it is supposed to.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testFirstAgeThenBirthDate() {
        boolean check = false;
        victim1.setAge(15);
        victim1.setDateOfBirth("2003-02-01");
        // Expecting to throw Illegal Argument Exception when running this test.
    }

    /** Testing whether setting first date of birth then age will properly throw an Illegal Argument Exception
     * like it is supposed to.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testFirstBirthDateThenAge() {
        boolean check = false;
        victim1.setDateOfBirth("2003-02-01");
        victim1.setAge(15);
        // Expecting to throw Illegal Argument Exception when running this test.
    }

    /** Testing whether the method getAssignedSocialID accurately return the right SocialID */
    @Test
    public void testGetAssignedSocialID() {
        DisasterVictim newVictim = new DisasterVictim("James", "2022-10-25");
        int socialId = newVictim.getAssignedSocialID() + 1;
        DisasterVictim newerVictim = new DisasterVictim("Bob", "2024-03-14");

        assertEquals("The method getAssignedSocialID did not return the expected social ID", socialId, newerVictim.getAssignedSocialID());
    }

    /** Testing whether the method getFamilyConnections returns the right family connections */
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

    /** Testing whether the method setFamilyConnections accurately updates Bob's familyConnections */
    @Test
    public void testSetFamilyConnectionsWithChildRelationshipBob() {
        DisasterVictim newVictim1 = new DisasterVictim("Bob", "2020-01-01");
        DisasterVictim newVictim2 = new DisasterVictim("Shake", "2022-01-01");
        
        FamilyRelation relation1 = new FamilyRelation(newVictim1, "child", newVictim2);
        
        HashSet<FamilyRelation> newFamilyConnections = new HashSet<>();
        newFamilyConnections.add(relation1);
        victim1.setFamilyConnections(newFamilyConnections);

        assertEquals("The method setFamilyConnections did not update the family connections.", newFamilyConnections, victim1.getFamilyConnections());
    }

    /** Testing whether the method setFamilyConnections accurately updates Shake's familyConnections */
    @Test
    public void testSetFamilyConnectionsWithChildRelationshipShake() {
        DisasterVictim newVictim1 = new DisasterVictim("Bob", "2020-01-01");
        DisasterVictim newVictim2 = new DisasterVictim("Shake", "2022-01-01");
        
        FamilyRelation relation1 = new FamilyRelation(newVictim1, "child", newVictim2);
        HashSet<FamilyRelation> relations = new HashSet<>();
        relations.add(relation1);
        newVictim1.setFamilyConnections(relations);
        

        assertEquals("The method setFamilyConnections did not update the family connections.", relations, newVictim2.getFamilyConnections());
    }

    /**Testing family consistency */
    @Test
    public void testOppositeFamilyConnectionWithParent() {
        DisasterVictim newVictim1 = new DisasterVictim("Bob", "2020-01-01");
        DisasterVictim newVictim2 = new DisasterVictim("Shake", "2022-01-01");
        
        FamilyRelation relation1 = new FamilyRelation(newVictim1, "parent", newVictim2);
        newVictim1.addFamilyConnection(relation1);
        FamilyRelation oppRelation = new FamilyRelation(newVictim2, "child", newVictim1);
        assertEquals("The method addFamilyConnection did not properly add the opposite relationship to the other person.", oppRelation, newVictim2.containsFamilyObject(oppRelation));
    }

    /**Testing family consistency */
    @Test
    public void testOppositeFamilyConnectionWithSpouse() {
        DisasterVictim newVictim1 = new DisasterVictim("Bob", "2020-01-01");
        DisasterVictim newVictim2 = new DisasterVictim("Shake", "2022-01-01");
        
        FamilyRelation relation1 = new FamilyRelation(newVictim1, "spouse", newVictim2);
        newVictim1.addFamilyConnection(relation1);
        FamilyRelation oppRelation = new FamilyRelation(newVictim2, "child", newVictim1);
        assertEquals("The method addFamilyConnection did not properly add the opposite relationship to the other person.", oppRelation, newVictim2.containsFamilyObject(oppRelation));
    }

    /**Testing family consistency */
    @Test
    public void testOppositeFamilyConnectionWithSibling() {
        DisasterVictim newVictim1 = new DisasterVictim("Bob", "2020-01-01");
        DisasterVictim newVictim2 = new DisasterVictim("Shake", "2022-01-01");
        
        FamilyRelation relation1 = new FamilyRelation(newVictim1, "sibling", newVictim2);
        newVictim1.addFamilyConnection(relation1);
        FamilyRelation oppRelation = new FamilyRelation(newVictim2, "sibling", newVictim1);
        assertEquals("The method addFamilyConnection did not properly add the opposite relationship to the other person.", oppRelation, newVictim2.containsFamilyObject(oppRelation));
    }

    /**Testing family consistency */
    @Test
    public void testOppositeFamilyConnectionWithChild() {
        DisasterVictim newVictim1 = new DisasterVictim("Bob", "2020-01-01");
        DisasterVictim newVictim2 = new DisasterVictim("Shake", "2022-01-01");
        
        FamilyRelation relation1 = new FamilyRelation(newVictim1, "child", newVictim2);
        newVictim1.addFamilyConnection(relation1);
        FamilyRelation oppRelation = new FamilyRelation(newVictim2, "parent", newVictim1);
        assertEquals("The method addFamilyConnection did not properly add the opposite relationship to the other person.", oppRelation, newVictim2.containsFamilyObject(oppRelation));
    }

    /**Testing family consistency */
    @Test (expected = IllegalArgumentException.class)
    public void testAddingSameFamilyConnection() {
        DisasterVictim newVictim1 = new DisasterVictim("Bob", "2020-01-01");
        DisasterVictim newVictim2 = new DisasterVictim("Shake", "2022-01-01");
        
        FamilyRelation relation1 = new FamilyRelation(newVictim1, "child", newVictim2);
        newVictim1.addFamilyConnection(relation1);
        newVictim1.addFamilyConnection(relation1);
        // Expecting to throw Illegal Argument Exception when running this test.
    }

    /**Testing family consistency */
    @Test
    public void testEnsuringFamilyConsistencyThroughAddingConnectionSiblingToSibling() {
        DisasterVictim newVictim1 = new DisasterVictim("Bob", "2020-01-01");
        DisasterVictim newVictim2 = new DisasterVictim("Shake", "2022-01-01");
        DisasterVictim newVictim3 = new DisasterVictim("Sarah", "2022-01-01");
        
        FamilyRelation relation1 = new FamilyRelation(newVictim1, "sibling", newVictim2);
        FamilyRelation relation2 = new FamilyRelation(newVictim1, "sibling", newVictim3);
        newVictim1.addFamilyConnection(relation1);
        newVictim1.addFamilyConnection(relation2);
        FamilyRelation oppRelation = new FamilyRelation(newVictim3, "sibling", newVictim2);

        assertEquals("The method addFamilyConnection did not ensure FamilyConsistency by adding the last relationship.", oppRelation, newVictim3.containsFamilyObject(oppRelation));
    }

    /**Testing family consistency */
    @Test
    public void testEnsuringFamilyConsistencyThroughAddingConnectionParentToSpouse() {
        DisasterVictim newVictim1 = new DisasterVictim("Bob", "2020-01-01");
        DisasterVictim newVictim2 = new DisasterVictim("Shake", "2022-01-01");
        DisasterVictim newVictim3 = new DisasterVictim("Sarah", "2022-01-01");
        
        FamilyRelation relation1 = new FamilyRelation(newVictim1, "parent", newVictim2);
        FamilyRelation relation2 = new FamilyRelation(newVictim1, "spouse", newVictim3);
        newVictim1.addFamilyConnection(relation1);
        newVictim1.addFamilyConnection(relation2);
        FamilyRelation oppRelation = new FamilyRelation(newVictim3, "parent", newVictim2);

        assertEquals("The method addFamilyConnection did not ensure FamilyConsistency by adding the last relationship.", oppRelation, newVictim3.containsFamilyObject(oppRelation));
    }

    /**Testing family consistency */
    @Test
    public void testEnsuringFamilyConsistencyThroughAddingConnectionSpouseToParent() {
        DisasterVictim newVictim1 = new DisasterVictim("Bob", "2020-01-01");
        DisasterVictim newVictim2 = new DisasterVictim("Shake", "2022-01-01");
        DisasterVictim newVictim3 = new DisasterVictim("Sarah", "2022-01-01");
        
        FamilyRelation relation1 = new FamilyRelation(newVictim1, "parent", newVictim2);
        FamilyRelation relation2 = new FamilyRelation(newVictim1, "spouse", newVictim3);
        newVictim1.addFamilyConnection(relation1);
        newVictim1.addFamilyConnection(relation2);
        FamilyRelation oppRelation = new FamilyRelation(newVictim3, "child", newVictim2);

        assertEquals("The method addFamilyConnection did not ensure FamilyConsistency by adding the last relationship.", oppRelation, newVictim3.containsFamilyObject(oppRelation));
    }

    /**Testing family consistency */
    @Test
    public void testEnsuringFamilyConsistencyThroughAddingConnectionChildToSibling() {
        DisasterVictim newVictim1 = new DisasterVictim("Bob", "2020-01-01");
        DisasterVictim newVictim2 = new DisasterVictim("Shake", "2022-01-01");
        DisasterVictim newVictim3 = new DisasterVictim("Sarah", "2022-01-01");
        
        FamilyRelation relation1 = new FamilyRelation(newVictim1, "child", newVictim2);
        FamilyRelation relation2 = new FamilyRelation(newVictim1, "sibling", newVictim3);
        newVictim1.addFamilyConnection(relation1);
        newVictim1.addFamilyConnection(relation2);
        FamilyRelation oppRelation = new FamilyRelation(newVictim3, "child", newVictim2);

        assertEquals("The method addFamilyConnection did not ensure FamilyConsistency by adding the last relationship.", oppRelation, newVictim3.containsFamilyObject(oppRelation));
    }

    /**Testing family consistency */
    @Test
    public void testEnsuringFamilyConsistencyThroughAddingConnectionSiblingToChild() {
        DisasterVictim newVictim1 = new DisasterVictim("Bob", "2020-01-01");
        DisasterVictim newVictim2 = new DisasterVictim("Shake", "2022-01-01");
        DisasterVictim newVictim3 = new DisasterVictim("Sarah", "2022-01-01");
        
        FamilyRelation relation1 = new FamilyRelation(newVictim1, "sibling", newVictim2);
        FamilyRelation relation2 = new FamilyRelation(newVictim1, "child", newVictim3);
        newVictim1.addFamilyConnection(relation1);
        newVictim1.addFamilyConnection(relation2);
        FamilyRelation oppRelation = new FamilyRelation(newVictim3, "parent", newVictim2);

        assertEquals("The method addFamilyConnection did not ensure FamilyConsistency by adding the last relationship.", oppRelation, newVictim3.containsFamilyObject(oppRelation));
    }

    /**Testing family consistency */
    @Test
    public void testEnsuringFamilyConsistencyThroughAddingConnectionChildToChild() {
        DisasterVictim newVictim1 = new DisasterVictim("Bob", "2020-01-01");
        DisasterVictim newVictim2 = new DisasterVictim("Shake", "2022-01-01");
        DisasterVictim newVictim3 = new DisasterVictim("Sarah", "2022-01-01");
        
        FamilyRelation relation1 = new FamilyRelation(newVictim1, "child", newVictim2);
        FamilyRelation relation2 = new FamilyRelation(newVictim1, "child", newVictim3);
        newVictim1.addFamilyConnection(relation1);
        newVictim1.addFamilyConnection(relation2);
        FamilyRelation oppRelation = new FamilyRelation(newVictim3, "spouse", newVictim2);

        assertEquals("The method addFamilyConnection did not ensure FamilyConsistency by adding the last relationship.", oppRelation, newVictim3.containsFamilyObject(oppRelation));
    }

    /**Testing family consistency */
    @Test
    public void testEnsuringFamilyConsistencyThroughAddingConnectionParentToParent() {
        DisasterVictim newVictim1 = new DisasterVictim("Bob", "2020-01-01");
        DisasterVictim newVictim2 = new DisasterVictim("Shake", "2022-01-01");
        DisasterVictim newVictim3 = new DisasterVictim("Sarah", "2022-01-01");
        
        FamilyRelation relation1 = new FamilyRelation(newVictim1, "parent", newVictim2);
        FamilyRelation relation2 = new FamilyRelation(newVictim1, "parent", newVictim3);
        newVictim1.addFamilyConnection(relation1);
        newVictim1.addFamilyConnection(relation2);
        FamilyRelation oppRelation = new FamilyRelation(newVictim3, "sibling", newVictim2);

        assertEquals("The method addFamilyConnection did not ensure FamilyConsistency by adding the last relationship.", oppRelation, newVictim3.containsFamilyObject(oppRelation));
    }

    /** Testing whether the setMedicalRecord accurately sets the medical records to the new medical record  */
    @Test
    public void testSetMedicalRecords() {
        Location newLocation = new Location("Shelter F", "9999 Shelter Ave");
        MedicalRecord newRecord = new MedicalRecord(newLocation, "Sore Throat", "2023-01-01");

        Vector<MedicalRecord> newRecords = new Vector<>();
        newRecords.add(newRecord);
        victim1.setMedicalRecords(newRecords);

        assertEquals("The method setMedicalRecords did not update the medical records.", newRecords, victim1.getMedicalRecords());
    }

    /** Testing whether setPersonalBelongings accurately updates personalBelongings */
    @Test
    public void testSetPersonalBelongings() {
        Supply item1 = new Supply("Granola Bar", 5);
        Supply item2 = new Supply("Water Bottle", 10);
        HashSet<Supply> newSupplies = new HashSet<>();
        newSupplies.add(item1);
        newSupplies.add(item2);
        Location tempLocation = new Location("Stark Hospital", "Stark street");
        tempLocation.setSupplies(newSupplies);

        victim1.setPersonalBelongings(newSupplies, tempLocation);
    
        assertEquals("The method setPersonalBelongings did correctly update the personal belongings.", newSupplies, victim1.getPersonalBelongings());
    }

    /** Testing whether the method addPersonalBelonging correctly adds a new Supply object to personalBelongings */
    @Test
    public void testAddPersonalBelongingWithEnoughSuppliesAtLocation() {
        Supply newItem = new Supply("Milk", 2);
        Location tempLocation = new Location("Gondola Reserves", "Gondal Street");
        tempLocation.addSupply(new Supply("Milk", 10));
        victim1.addPersonalBelonging(newItem, tempLocation);

        boolean check = false;
        for (Supply item : this.personalBelongings) {
            if ((item.getType().equals(newItem.getType())) && (item.getQuantity() == newItem.getQuantity())) {
                check = true;
                break;
            }
        }
        assertTrue("The method addPersonalBelonging did not add the new personal belonging.", check);
    }

    /**Testing supply consistency */
    @Test (expected = IllegalArgumentException.class)
    public void testAddPersonalBelongingWithNotEnoughSuppliesAtLocation() {
        Supply newItem = new Supply("Milk", 2);
        Location tempLocation = new Location("Gondola Reserves", "Gondal Street");
        tempLocation.addSupply(new Supply("Milk", 1));
        victim1.addPersonalBelonging(newItem, tempLocation);
        // Expecting to throw Illegal Argument Exception when running this test.
    }

    /** Testing whether the method removePersonalBelonging correctly removes the Supply object from personalBelongings */
    @Test (expected = IllegalArgumentException.class)
    public void testRemovePersonalBelongingWithNotEnoughPersonalBelonings() {
        Supply itemToAdd = new Supply("Milk", 2);
        Supply unwantedItem = new Supply("Coco Cola", 6);
        Location tempLocation = new Location("Gondola Reserves", "Gondal Street");

        victim1.addPersonalBelonging(itemToAdd, tempLocation);
        victim1.removePersonalBelonging(unwantedItem, tempLocation);
        // Expecting to throw Illegal Argument Exception when running this test.
    }
        
    /**Testing supply consistency */
    @Test 
    public void testAddPersonalBelongingWithEnoughPersonalBelongings() {
        Supply newItem = new Supply("Milk", 2);
        Location tempLocation = new Location("Gondola Reserves", "Gondal Street");
        
        victim1.addPersonalBelonging(newItem, tempLocation);
        victim1.removePersonalBelonging(newItem, tempLocation);

        boolean check = false;
        for (Supply item : this.personalBelongings) {
            if ((item.getType().equals(newItem.getType())) && (item.getQuantity() == newItem.getQuantity())) {
                check = true;
                break;
            }
        }
        assertEquals("The method removePersonalBelonging did not properly remove the Supply from the personal belongings.", false, check);
        // Expecting to throw Illegal Argument Exception when running this test.
    }

    /** Testing whether the method removeFamilyConnection correctly removes the unwanted relation from familyConnections */
    @Test
    public void testRemoveFamilyConnection() {
        DisasterVictim newVictim1 = new DisasterVictim("Bob", "2020-01-01");
        DisasterVictim newVictim2 = new DisasterVictim("Shake", "2022-01-01");
        
        FamilyRelation unwantedRelation = new FamilyRelation(newVictim1, "Father", newVictim2);
        victim1.addFamilyConnection(unwantedRelation);
        victim1.removeFamilyConnection(unwantedRelation);

        assertEquals("The method removeFamilyConnection did not remove the unwanted relation.", true, victim1.containsFamilyObject(unwantedRelation));
    }

    /** Testing whether the method addMedicalRecord correctly adds a new medical record */
    @Test
    public void testAddMedicalRecord() {
        Location newLocation = new Location("Shelter B", "8888 Shelter Ave");
        MedicalRecord newRecord = new MedicalRecord(newLocation, "Pain in Throat", "2023-01-01");

        victim1.addMedicalRecord(newRecord);
        boolean check = false;
        for (MedicalRecord record : victim1.getMedicalRecords()) {
            if (record.getLocation().equals(newLocation) && record.getTreatmentDetails().equals("Pain in Throat") && 
                record.getDateOfTreatment().equals("2023-01-01")) {
                check = true;
                break;
            }
        }

        assertTrue("The method addMedicalRecord did not add a new medical record.", check);
    }

    /** Testing whether the method getEntryDate return the return the right entry date */
    @Test
    public void testGetEntryDate() {
        assertEquals("The method getEntryDate did not return the right entry date.", ENTRY_DATE, victim1.getEntryDate());
    }

    /** Testing whether the method getComments returns the right comments */
    @Test
    public void testGetComments() {
        assertEquals("The method getComments did not return the comments.", comments, victim1.getComments());
    }

    /** Testing whether the method setComments accurately updates the comments */
    @Test
    public void testSetComments() {
        String newComment = "I am waiting for my brother to pick me up from hospital.";
        victim1.setComments(newComment);
        assertEquals("The method setComments did not set the comment correctly.", newComment, victim1.getComments());
    }

    /** Testing whether the method getGender returns the right gender. */
    @Test
    public void testGetGender() {
        assertEquals("The method getGender did not return the right gender.", gender, victim1.getGender());
    }

    /** Testing whether the method setGender correctly updates the gender */
    @Test
    public void testSetGenderWithValidInput() {
        String validGender = "woman";
        victim1.setGender(validGender);
        assertEquals("The method setGender did not update the gender correctly.", validGender, victim1.getGender());
    }

    /** Testing whether the method setGender correctly throws an Illegal Argument Exception */
    @Test (expected = IllegalArgumentException.class) 
    public void testSetGenderWithInvalidInput() {
        String invalidGender = "rhinoceros"; //Should be Invalid
        victim1.setGender(invalidGender);
        // Expecting to throw Illegal Argument Exception when running this test.
    }

    /**EDIT THIS LATER */
    @Test
    public void testGetGenderOptions() {
        String[] expectedOptions = {"Male", "Female", "Other"}; // Known values
        assertEquals("The method getGenderOptions did not accurately return the right genders.", expectedOptions, victim1.getGenderOptions());   
    }

    /** Testing whether the method populateGenderOptions correctly reads and fills out the string list of gender options */
    @Test  
    public void testSetGenderOptions() {
        String[] expectedOptions = {"Male", "Female", "Other"}; // Known values

        // Write known values to a file
        String filename = "genderOptionsTestFile.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String option : expectedOptions) {
                writer.write(option);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        // Call setGenderOptions to read values from the file
        victim1.setGenderOptions();
        

        // Deleting text file 
        try {
            Files.deleteIfExists(Paths.get(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Compare expected and actual values
        assertArrayEquals("The method setGenderOptions did not accurately update the gender options array", expectedOptions, this.genderOptions);
    }

    /** Testing whether the method getDietaryRestrictions correctly returns the right dietary restrictions */
    @Test
    public void testGetDietaryRestrictions() {
        assertEquals("The method getDietaryRestrictions did not return the correct dietary restriction.", 
            victim1.getDietaryRestrictions(), mealRestrictions);
    }

    /** Testing wheter the method setDietaryRestrictions correctly updates the mealRestrictions */
    @Test
    public void testSetDietaryRestrictionsWithValidOptions() {
        String[] newRestrictions = {"AVML", "VGML"};
        victim1.setDietaryRestrictions(newRestrictions);
        assertEquals("The method setDietaryRestrictions did not update the dietary restrictions correctly.", 
            victim1.getDietaryRestrictions(), newRestrictions);
    }

    /**Testing diet options */
    @Test (expected = IllegalArgumentException.class) 
    public void testSetDietaryRestrictionsWithInvalidOptions() {
        String[] newRestrictions = {"AVML", "VVML"};
        victim1.setDietaryRestrictions(newRestrictions);
        // Expecting IllegalArgumentException due to an invalid date format
    }
}
