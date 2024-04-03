// DisasterVictim Class

package edu.ucalgary.oop;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.Vector;
import java.util.HashSet;
import java.util.ArrayList;
import java.time.LocalDate;


public class DisasterVictim {
    private static int counter = 0;

    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private final int ASSIGNED_SOCIAL_ID;
    private HashSet<FamilyRelation> familyConnections = new HashSet<>();
    private Vector<MedicalRecord> medicalRecords = new Vector<>();
    private HashSet<Supply> personalBelongings = new HashSet<>();
    private final String ENTRY_DATE;
    private String gender;
    private String[] genderOptions;
    private String comments;
    private int age;
    private String [] mealRestrictions;

    public DisasterVictim(String firstName, String ENTRY_DATE) {
        this.firstName = firstName;
        if (!isValidDateFormat(ENTRY_DATE)) {
            throw new IllegalArgumentException("Invalid date format for entry date. Expected format: YYYY-MM-DD");
        }
        this.ENTRY_DATE = ENTRY_DATE;
        this.ASSIGNED_SOCIAL_ID = generateSocialID();
        
    }

    public DisasterVictim(String firstName, String lastName, String ENTRY_DATE) {
        this.firstName = firstName;
        this.lastName = lastName;
        if (!isValidDateFormat(ENTRY_DATE)) {
            throw new IllegalArgumentException("Invalid date format for entry date. Expected format: YYYY-MM-DD");
        }
        this.ENTRY_DATE = ENTRY_DATE;
        this.ASSIGNED_SOCIAL_ID = generateSocialID();
        
    }

    public DisasterVictim(String firstName, String lastName, String dateOfBirth, String ENTRY_DATE) {
        this.firstName = firstName;
        this.lastName = lastName;
        if (!isValidDateFormat(ENTRY_DATE)) {
            throw new IllegalArgumentException("Invalid date format for entry date. Expected format: YYYY-MM-DD");
        }
        this.ENTRY_DATE = ENTRY_DATE;
        if (!isValidDateFormat(dataOfBirth)) {
            throw new IllegalArgumentException("Invalid date format for date of birth. Expected format: YYYY-MM-DD");
        }
        this.dataOfBirth = dataOfBirth;
        this.ASSIGNED_SOCIAL_ID = generateSocialID();
        
    }

    private static int generateSocialID() {
        counter++;
        return counter;
    }

    private static boolean isValidDateFormat(String date) {
        String dateFormatPattern = "^\\d{4}-\\d{2}-\\d{2}$";
        return date.matches(dateFormatPattern);
    }

  
    // Getters and setters

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        if (!isValidDateFormat(dateOfBirth)) {
            throw new IllegalArgumentException("Invalid date format for date of birth. Expected format: YYYY-MM-DD");
        }
        // Making sure that age is NULL when we set dateOfBirth
        else if (age) {
            throw new IllegalArgumentException("Cannot set dateOfBirth since age already has a value!");
        }
        // If date has a valid format and age does not have a value, set dateOfBirth
        else {
            this.dateOfBirth = dateOfBirth;
        }
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age <= 0 || age >= 120) {
            throw new IllegalArgumentException("Invalid age, please put a valid integer for age!");
        }
        // Making sure that dateOfBirth is NULL when we set age
        else if (dateOfBirth) {
            throw new IllegalArgumentException("Cannot set age since dateOfBirth already has a value!");
        }
        // If age has a valid number and dateOfBirth does not have a value, set age
        else {
            this.age = age;
        }
    }

    public int getAssignedSocialID() {
        return ASSIGNED_SOCIAL_ID;
    }

    public HashSet<FamilyRelation> getFamilyConnections() {
        return familyConnections;
    }

    public Vector<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }

    public HashSet<Supply> getPersonalBelongings() {
        return personalBelongings;
    }
    
    // Correct the setters to accept Lists instead of arrays
    public void setFamilyConnections(HashSet<FamilyRelation> connections) {
        this.familyConnections.clear();
        for (FamilyRelation newConnection : connections) {
            addFamilyConnection(newConnection);
        }
    }

    public void setMedicalRecords(MedicalRecord[] records) {
        this.medicalRecords.clear();
        for (MedicalRecord newRecord : records) {
            addMedicalRecord(newRecord);
        }
    }

    public void setPersonalBelongings(Supply[] belongings) {
        this.personalBelongings = belongings;
    }

    // Add a Supply to personalBelonging
    public void addPersonalBelonging(Supply supply) {

        if (this.personalBelongings == null) {
            Supply tmpSupply[] = { supply };
            this.setPersonalBelongings(tmpSupply);
            return;
        }

        // Create an array one larger than the previous array
        int newLength = this.personalBelongings.length + 1;
        Supply tmpPersonalBelongings[] = new Supply[newLength];

        // Copy all the items in the current array to the new array
        int i;
        for (i=0; i < personalBelongings.length; i++) {
            tmpPersonalBelongings[i] = this.personalBelongings[i];
        }

        // Add the new element at the end of the new array
        tmpPersonalBelongings[i] = supply;

        // Replace the original array with the new array
        this.personalBelongings = tmpPersonalBelongings;
    }

    // Remove a Supply from personalBelongings, we assume it only appears once
    public void removePersonalBelonging(Supply unwantedSupply) {
        Supply[] updatedBelongings = new Supply[personalBelongings.length-1];
        int index = 0;
        int newIndex = index;
        for (Supply supply : personalBelongings) {
            if (!supply.equals(unwantedSupply)) {
                updatedBelongings[newIndex] = supply;
                newIndex++;
            }
            index++;
        }
    }

    public void removeFamilyConnection(FamilyRelation exRelation) {
        // Making a temp FamilyRelation object where personOne and personTwo are switched for checking purposes.  
        DisasterVictim tempPerson1 = exRelation.getPersonTwo();
        DisasterVictim tempPerson2 = exRelation.getPersonOne();
        String tempRelationship = exRelation.getRelationshipTo();
        FamilyRelation tempRelation = new FamilyRelation(tempPerson1, tempRelationship, tempPerson2);

        // Checking whether the original exRelation exists in personOne's familyConnections
        if (this.familyConnections.containsFamilyObject(exRelation)) {
            this.familyConnections.removeFamilyObject(exRelation);
            
            // Checking whether the original exRelation exists in personTwo's familyConnections
            if (tempPerson1.getFamilyConnections().containsFamilyObject(exRelation)) {
                tempPerson1.getFamilyConnections().removeFamilyObject(exRelation);
            }
            // Checking whether the temp exRelation exists in personTwo's familyConnections
            else if (tempPerson1.getFamilyConnections().containsFamilyObject(tempRelation)) {
                tempPerson1.getFamilyConnections().removeFamilyObject(tempRelation);
            }
        }

        // Checking whether the temp exRelation exists in personOne's familyConnections
        else if (this.familyConnections.containsFamilyObject(tempRelation)) {
            this.familyConnections.removeFamilyObject(tempRelation);

            // Checking whether the original exRelation exists in personTwo's familyConnections
            if (tempPerson1.getFamilyConnections().containsFamilyObject(exRelation)) {
                tempPerson1.getFamilyConnections().removeFamilyObject(exRelation);
            }
            // Checking whether the temp exRelation exists in personTwo's familyConnections
            else if (tempPerson1.getFamilyConnections().containsFamilyObject(tempRelation)) {
                tempPerson1.getFamilyConnections().removeFamilyObject(tempRelation);
            }
        }

        // Throw an exception if the exRelation or tempRelation did not even exist
        else {
            throw new IllegalArgumentException("A relation that did not exist cannot be deleted in the first place!");
        }
    }

    private void removeFamilyObject(FamilyRelation object) {
        for (FamilyRelation temp : this.familyConnections) {
            if (temp.getPersonOne() == object.getPersonOne() && temp.getPersonOne() == object.getPersonOne() && 
                temp.getRelationshipTo() == object.getRelationshipTo()) {
                    this.familyConnections.remove(temp);
            }
        }
    }

    private boolean containsFamilyObject(FamilyRelation object) {
        boolean check = false;
        for (FamilyRelation temp : this.familyConnections) {
            if (temp.getPersonOne() == object.getPersonOne() && temp.getPersonOne() == object.getPersonOne() && 
                temp.getRelationshipTo() == object.getRelationshipTo()) {
                    check = true;
            }
        }
        return check;
    }

    public void addFamilyConnection(FamilyRelation relation) { 
        // Making a temp FamilyRelation object where personOne and personTwo are switched for checking purposes.    
        DisasterVictim tempPerson1 = relation.getPersonTwo();
        DisasterVictim tempPerson2 = relation.getPersonOne();
        String tempRelationship = relation.getRelationshipTo();
        FamilyRelation tempRelation = new FamilyRelation(tempPerson1, tempRelationship, tempPerson2);

        // NOTE: The following if statements also handle the case where there is a series of relationships
        // For example, if Peace and Sam also have a sibling Diamond, we 
        // will have a situation where Peace and Sam's, and Peace
        // and Diamond's, and Diamond and Sam's relationship are all present 
        // in each of their respective familyConnections variable.

        // Check whether this relation already exists in familyConnections.
        // One is the original relation and the other is with personOne and personTwo switched.
        if (this.familyConnections.containsFamilyObject(relation) || this.familyConnections.containsFamilyObject(tempRelation)) {
            return;
        } 
        // Check whether the other person also has the relation with both types of relationship 
        // with personOne switched with personTwo in another relationship
        else if (tempPerson1.getFamilyConnections().containsFamilyObject(relation) || 
                tempPerson1.getFamilyConnections().containsFamilyObject(tempRelation)) {
            // If it had it, that means the other person did not have the relationship so add it to personOne's familyConnections
            this.familyConnections.add(relation);
            return;
        }
        // If both people did not have the relatioships, then add it to both personOne's familyConnections and 
        // personTwo's familyConnections
        else {
            this.familyConnections.add(relation);
            tempPerson1.getFamilyConnections().add(tempRelation);
        }
    }


    // Add a MedicalRecord to medicalRecords
    public void addMedicalRecord(MedicalRecord record) {
        medicalRecords.add(record);
    }

    public String getEntryDate() {
        return ENTRY_DATE;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments =  comments;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        if (!gender.matches("(?i)^(male|female|other)$")) {
            throw new IllegalArgumentException("Invalid gender. Acceptable values are male, female, or other.");
        }
        this.gender = gender.toLowerCase(); // Store in a consistent format
    }

   
}
