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
    private Location currentLocation;
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
    
    public void setFamilyConnections(HashSet<FamilyRelation> connections) {
        this.familyConnections.clear();
        for (FamilyRelation newConnection : connections) {
            addFamilyConnection(newConnection);
        }
    }

    public void setMedicalRecords(Vector<MedicalRecord> records) {
        this.medicalRecords.clear();
        for (MedicalRecord newRecord : records) {
            addMedicalRecord(newRecord);
        }
    }

    public void setPersonalBelongings(HashSet<Supply> belongings) {
        this.personalBelongings.clear();
        for (Supply newSupply : belongings) {
            addPersonalBelonging(newSupply);
        }
    }

    // Add a Supply to personalBelonging
    public void addPersonalBelonging(Supply supply) {
        boolean locationCheck = false;
        boolean suppliesAlreadyUpdated = false;
        // Find out whether there is enough supply at the current location and make locationCheck = true
        for (Supply temp : this.currentLocation.getSupplies()) {
            if ((temp.getType() == supply.getType()) && ((temp.getQuantity() - supply.getQuantity()) >= 0)) {
                // Remove supply from current location
                this.currentLocation.removeSupply(supply);
                locationCheck = true;
                break;
            }
        }

        // If supply exists at the location add it to our own personal belongings
        if (locationCheck) {
            // Find out whether the we already have this item and add the supplies quantity to its previous quantity
            for (Supply temp : this.personalBelongings) {
                if (temp.getType() == supply.getType()) {
                    int newTotal = temp.getQuantity() + supply.getQuantity();
                    temp.setQuantity(newTotal);
                    suppliesAlreadyUpdated = true;
                    break;
                }
            }

            // if this item does not already exist, add the entire Supply object to our personal belongings
            if (!suppliesAlreadyUpdated) {
                this.personalBelongings.add(supply);
            }
        } 

        // Throw an error since there was not enough supply at the location.
        else {
            throw new IllegalArgumentException("These supplies cannot be given to the disaster victim as there are not enough at the location!");
        }
    }

    // Remove a Supply from personalBelongings, we assume it only appears once
    public void removePersonalBelonging(Supply unwantedSupply) {
        boolean personalBelongingUpdated = false;
        // Find out whether there is enough supplies for the disaster victim to even give out
        // If there are enough, change the quantity to its reduced quantity
        for (Supply temp : this.personalBelongings) {
            if ((temp.getType() == unwantedSupply.getType()) && ((temp.getQuantity() - unwantedSupply.getQuantity()) >= 0)) {
                int newQuantity = temp.getQuantity() - unwantedSupply.getQuantity();
                // Remove that supply, if quantity is 0.
                if (newQuantity == 0) {
                    this.personalBelongings.remove(temp);
                } else {
                    temp.setQuantity(newQuantity);
                }
                personalBelongingUpdated = true; 
                break;
            }     
        }

        // Since we removed supply from personal belonging, add that unwanted supply back into location
        if (personalBelongingUpdated) {
            this.currentLocation.addSupply(unwantedSupply);
        }
        // if disaster victim did have that many supplies to begin with, throw an IllegalArgumentException
        else {
            throw new IllegalArgumentException("The disasater victim is giving out more supplies than what he actually has!")
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
            if (temp.getPersonOne() == object.getPersonOne() && temp.getPersonTwo() == object.getPersonTwo() && 
                temp.getRelationshipTo() == object.getRelationshipTo()) {
                    this.familyConnections.remove(temp);
            }
        }
    }

    private boolean containsFamilyObject(FamilyRelation object) {
        boolean check = false;
        for (FamilyRelation temp : this.familyConnections) {
            if (temp.getPersonOne() == object.getPersonOne() && temp.getPersonTwo() == object.getPersonTwo() && 
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
            // If the other person had it, that means the first person did not have the relationship so add it to personOne's familyConnections
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
        this.medicalRecords.add(record);
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location newLocation) {
        this.currentLocation = newLocation;
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
