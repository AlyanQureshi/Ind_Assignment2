/**
 * @author Alyan Qureshi <a href="mailto:muhammad.qureshi4@ucalgary.ca">
 * muhammad.qureshi4@ucalgary.ca</a>
 * @version 1.7
 * @since 1.0
*/

package edu.ucalgary.oop;

public class FamilyRelation {
    private DisasterVictim personOne;
    private String relationshipTo;
    private DisasterVictim personTwo;

    /**Constructor */
    public FamilyRelation(DisasterVictim personOne, String relationshipTo, DisasterVictim personTwo) {
        this.personOne = personOne;
        this.relationshipTo = relationshipTo;
        this.personTwo = personTwo;
    }

    /** Getter and setter for personOne */
    public DisasterVictim getPersonOne() {
        return personOne;
    }

    /** Setter for personOne */
    public void setPersonOne(DisasterVictim personOne) {
        this.personOne = personOne;
    }

    /** Getter and setter for relationshipTo */
    public String getRelationshipTo() {
        return relationshipTo;
    }

    /** Setting for relationshipTo */
    public void setRelationshipTo(String relationshipTo) {
        this.relationshipTo = relationshipTo;
    }

    /** Getter and setter for personTwo */
    public DisasterVictim getPersonTwo() {
        return personTwo;
    }

    public void setPersonTwo(DisasterVictim personTwo) {
        this.personTwo = personTwo;
    }
}