/**
 * @author Alyan Qureshi <a href="mailto:muhammad.qureshi4@ucalgary.ca">
 * muhammad.qureshi4@ucalgary.ca</a>
 * @version 1.5
 * @since 1.0
*/


package edu.ucalgary.oop;

import org.junit.*;
import static org.junit.Assert.*;

public class FamilyRelationTest {
    
    /** This setup will run before every other test */ 
    @Before
    public void setUp() {
        private DisasterVictim personOne = new DisasterVictim("John Dalan", "2024-01-19");
        private DisasterVictim personTwo = new DisasterVictim("Jane Dalan", "2024-02-20");
        private String relationshipTo = "sibling";
        private FamilyRelation familyRelationObject = new FamilyRelation(personOne, relationshipTo, personTwo);
    }

    /**  Default constructor */
    public FamilyRelationTest() {}

    /** Testing constructor for Family Relation */
    @Test
    public void testFamilyRelationConstructor() {
        assertNotNull("Family Relation class was not able to make a new object.", familyRelationObject);
    }

    /** Testing method getPersonOne */
    @Test
    public void testGetPersonOne() {
        assertEquals("Method getPersonOne did not return personOne", personOne, familyRelationObject.getPersonOne());
    }

    /**  Testing setPersonOne */
    @Test
    public void testSetPersonOne() {
        DisasterVictim newPersonOne = new DisasterVictim("John Wick", "2024-03-21");
        familyRelationObject.setPersonOne(newPersonOne);
        assertEquals("Method setPersonOne did not update personOne", newPersonOne, familyRelationObject.getPersonOne());
    }

    /** Testing method getRelationshipTo */
    @Test
    public void testGetRelationshipTo() {
        assertEquals("Method getRelationshipTo did not return the correct relationship.", relationshipTo, familyRelationObject.getRelationshipTo());
    }

    /**  Testing setRelationshipTo */
    @Test
    public void testSetRelationshipTo() {
        String newRelationship = "Father";
        familyRelationObject.setRelationshipTo(newRelationship);
        assertEquals("Method setRelationshipTo did not update the relationship properly.", newRelationship, familyRelationObject.getRelationshipTo());
    }

    /** Testing method getPersonTwo */
    @Test
    public void testGetPersonTwo() {
        assertEquals("Method getPersonTwo did not return personTwo", personTwo, familyRelationObject.getPersonTwo());
    }

    /** Testing method setPersonTwo */
    @Test 
    public void testSetPersonTwo() {
        DisasterVictim newPersonTwo = new DisasterVictim("Jason Bourne", "2024-03-21");
        familyRelationObject.setPersonTwo(newPersonTwo);
        assertEquals("Method setPersonTwo did not update personTwo", newPersonTwo, familyRelationObject.getPersonTwo());
    }
}