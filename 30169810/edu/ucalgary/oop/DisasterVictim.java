// DisasterVictim Class

package edu.ucalgary.oop;

import java.util.Vector;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.time.LocalDate;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;


public class DisasterVictim {
    private static int counter = 0;

    private String firstName;
    private String lastName;
    private String dateOfBirth = "";
    private final int ASSIGNED_SOCIAL_ID;
    private HashSet<FamilyRelation> familyConnections = new HashSet<>();
    private Vector<MedicalRecord> medicalRecords = new Vector<>();
    private HashSet<Supply> personalBelongings = new HashSet<>();
    private final String ENTRY_DATE;
    private String gender;
    private static String[] genderOptions;
    private String comments;
    private int age = 0;
    private String [] mealRestrictions;
    enum DietRestrictions {
        AVML,
        DBML,
        GFML,
        KSML,
        LSML,
        MOML,
        PFML,
        VGML,
        VJML
    }

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
        if (!isValidDateFormat(dateOfBirth)) {
            throw new IllegalArgumentException("Invalid date format for date of birth. Expected format: YYYY-MM-DD");
        }
        this.dateOfBirth = dateOfBirth;
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
        else if (age != 0) {
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
        else if (dateOfBirth != "") {
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

    public void setPersonalBelongings(HashSet<Supply> belongings, Location currLocation) {
        this.personalBelongings.clear();
        for (Supply newSupply : belongings) {
            addPersonalBelonging(newSupply, currLocation);
        }
    }

    // Add a Supply to personalBelonging
    public void addPersonalBelonging(Supply supply, Location currentLocation) {
        boolean locationCheck = false;
        boolean suppliesAlreadyUpdated = false;
        // Find out whether there is enough supply at the current location and make locationCheck = true
        for (Supply temp : currentLocation.getSupplies()) {
            if ((temp.getType() == supply.getType()) && ((temp.getQuantity() - supply.getQuantity()) >= 0)) {
                // Remove supply from current location
                currentLocation.removeSupply(supply);
                locationCheck = true;
                break;
            }
        }

        // If supply exists at the location add it to our own personal belongings
        if (locationCheck) {
            // Find out whether we already have this item and add the supplies quantity to its previous quantity
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
    public void removePersonalBelonging(Supply unwantedSupply, Location currentLocation) {
        boolean personalBelongingUpdated = false;
        // Find out whether there is enough supplies for the disaster victim to even give out
        // If there are enough, change the quantity to its reduced quantity
        for (Supply temp : this.personalBelongings) {
            if ((temp.getType() == unwantedSupply.getType()) && ((temp.getQuantity() - unwantedSupply.getQuantity()) >= 0)) {
                int newQuantity = temp.getQuantity() - unwantedSupply.getQuantity();
                // Remove that supply, if quantity is 0
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
            currentLocation.addSupply(unwantedSupply);
        }
        // if disaster victim did have that many supplies to begin with, throw an IllegalArgumentException
        else {
            throw new IllegalArgumentException("The disasater victim is giving out more supplies than what he actually has!");
        }
    }

    public void removeFamilyConnection(FamilyRelation exRelation) {
        // Making a oppositeRelationship FamilyRelation object where personOne and personTwo are switched for checking purposes.  
        FamilyRelation oppositeRelationship = reverseFamilyRelation(relation);

        DisasterVictim otherPerson = oppositeRelationship.getPersonOne();

        // Checking whether the original exRelation exists in personOne's familyConnections
        if (this.containsFamilyObject(exRelation)) {
            this.removeFamilyObject(exRelation);
        
            // Checking whether the opposite relation exists in personTwo's familyConnections
            if (otherPerson.containsFamilyObject(oppositeRelationship)) {
                otherPerson.removeFamilyObject(oppositeRelationship);
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
        // Making a opposite relation object where personOne and 
        // personTwo are switched as well as relationship is switched    
        FamilyRelation oppositeRelationship = reverseFamilyRelation(relation);

        DisasterVictim otherPerson = oppositeRelationship.getPersonOne();

        // Check whether this relation already exists in familyConnections.
        if (this.containsFamilyObject(relation)) {
            return;
        } 
        // Check whether the other person also has the opposite relation
        else if (otherPerson.containsFamilyObject(oppositeRelationship)) {
            // If the other person had it, that means the first person did not have the relationship so add it to personOne's familyConnections
            this.familyConnections.add(relation);
        }
        // If both people did not have the relatioships, then add it to both personOne's familyConnections and 
        // personTwo's familyConnections
        else {
            this.familyConnections.add(relation);
            otherPerson.getFamilyConnections().add(oppositeRelationship);
        }

        // Check whether family consistency is needed
        if ((this.connectionRepeats(familyConnections) == 2) && (otherPerson.connectionRepeats(otherPerson.getFamilyConnections()) == 1)) {
            FamilyRelation newFamilyConnection = ensureFamilyConsistency(familyConnections);
            FamilyRelation newOppositeConnection = reverseFamilyRelation(newFamilyConnection);

            DisasterVictim newPerson1 = newFamilyConnection.getPersonOne();
            DisasterVictim newPerson2 = newFamilyConnection.getPersonTwo();

            newPerson1.getFamilyConnections().add(newFamilyConnection);
            newPerson2.getFamilyConnections().add(newOppositeConnection);
        }
    }

    public FamilyRelation ensureFamilyConsistency(HashSet<FamilyRelation> familyConnections) {
        FamilyRelation relation1;
        FamilyRelation relation2;
        
        int i = 0;
        for (FamilyRelation object : familyConnections) {
            if (i == 0) {
                relation1 = object;
            }
            else if (i == 1) {
                relation2 = object;
            }
            else {
                break;
            }
            i++;
        }

        DisasterVictim newPerson1;
        DisasterVictim newPerson2;
        String finalRelationship;
        String relationshipOne = relation1.getRelationshipTo();
        String relationshipTwo = relation2.getRelationshipTo();

        if ((relationshipOne == "sibling") && (relationshipTwo == "sibling")) {
            newPerson1 = relation1.getPersonTwo();
            finalRelationship = "sibling";
            newPerson2 = relation2.getPersonTwo();
        } // Second if
        else if ((relationshipOne == "parent") && (relationshipTwo == "spouse")) {
            newPerson1 = relation2.getPersonTwo();
            finalRelationship = "parent";
            newPerson2 = relation1.getPersonTwo();
        } // third if
        else if ((relationshipOne == "spouse") && (relationshipTwo == "parent")) {
            newPerson1 = relation1.getPersonTwo();
            finalRelationship = "parent";
            newPerson2 = relation2.getPersonTwo();
        } // fourth if
        else if ((relationshipOne == "child") && (relationshipTwo == "sibling")) {
            newPerson1 = relation2.getPersonTwo();
            finalRelationship = "child";
            newPerson2 = relation1.getPersonTwo();
        } // fifth if
        else if ((relationshipOne == "sibling") && (relationshipTwo == "child")) {
            newPerson1 = relation1.getPersonTwo();
            finalRelationship = "child";
            newPerson2 = relation2.getPersonTwo();
        } // sixth if
        else if ((relationshipOne == "child") && (relationshipTwo == "child")) {
            newPerson1 = relation1.getPersonTwo();
            finalRelationship = "spouse";
            newPerson2 = relation2.getPersonTwo();
        } // seventh if
        else if ((relationshipOne == "parent") && (relationshipTwo == "parent")) {
            newPerson1 = relation1.getPersonTwo();
            finalRelationship = "sibling";
            newPerson2 = relation2.getPersonTwo();
        }
        FamilyRelation newConnection = new FamilyRelation(newPerson1, finalRelationship, newPerson2);

        return newConnection;
    }

    private FamilyRelation reverseFamilyRelation(FamilyRelation connection) {
        DisasterVictim tempPerson1 = connection.getPersonTwo();
        DisasterVictim tempPerson2 = connection.getPersonOne();
        String oppositeRelationship;

        if (connection.getRelationshipTo() == "parent") {
            oppositeRelationship = "child";
        }
        else if (connection.getRelationshipTo() == "child") {
            oppositeRelationship = "parent";
        }
        else {
            oppositeRelationship = connection.getRelationshipTo();
        }
        FamilyRelation oppRelation = new FamilyRelation(tempPerson1, oppositeRelationship, tempPerson2);
        return oppRelation;
    }

    private boolean connectionRepeats(HashSet<FamilyRelation> familyConnections) {
        int count = 0;
        for (FamilyRelation temp : familyConnections) {
            count++;
        }
        return count;
    }

    // Add a MedicalRecord to medicalRecords
    public void addMedicalRecord(MedicalRecord record) {
        this.medicalRecords.add(record);
    }

    public String getEntryDate() {
        return ENTRY_DATE;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
    
        List<String> genderOptionsList = Arrays.asList(genderOptions);

        if (!genderOptionsList.contains(gender.toLowerCase())) {
            throw new IllegalArgumentException("Invalid gender. Acceptable values are: " + Arrays.toString(genderOptions));
        }
        
        this.gender = gender.toLowerCase(); // Store in a consistent format
    }

    public void getGenderOptions() {
        return genderOptions;
    }

    public static void setGenderOptions() {
        try {
            // Read all lines from the file into a List<String>
            List<String> lines = Files.readAllLines(Paths.get("GenderOptions.txt"));
            
            // Convert the list to an array
            genderOptions = lines.toArray(new String[0]);
            
            // Print the contents of the array (for verification)
            for (String option : genderOptions) {
                System.out.println(option);
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
            // Optionally, you can log the stack trace for debugging purposes
            e.printStackTrace();
        }
    }

    public String[] getDietaryRestrictions() {
        return mealRestrictions;
    }

    public void setDietaryRestrictions(String[] mealRestrictions) {
        Set<String> validRestrictions = new HashSet<>();
        for (DietRestrictions restriction : DietRestrictions.values()) {
            validRestrictions.add(restriction.toString());
        }

        for (String option : mealRestrictions) {
            if (!validRestrictions.contains(option)) {
                throw new IllegalArgumentException("This dietary restriction does not exist: " + option);
            }
        }

        this.mealRestrictions = mealRestrictions;
    }

    public static void readDietOptions() {
        // Iterate over mealRestrictions and call commentOnMeal for each restriction
        for (String restriction : mealRestrictions) {
            commentOnMeal(DietRestrictions.valueOf(restriction));
        }
    }

    public static void commentOnMeal(DietRestrictions meal) {
        switch(meal) {
            case AVML:
                System.out.println(meal + ": Asian vegetarian meal.");
                break;
            case DBML:
                System.out.println(meal + ": Diabetic meal.");
                break;
            case GFML:
                System.out.println(meal + ": Gluten intolerant meal.");
                break;
            case KSML:
                System.out.println(meal + ": Kosher meal.");
                break;
            case LSML:
                System.out.println(meal + ": Low salt meal.");
                break;
            case MOML:
                System.out.println(meal + ": Muslim meal.");
                break;
            case PFML:
                System.out.println(meal + ": Peanut-free meal.");
                break;
            case VGML:
                System.out.println(meal + ": Vegan meal.");
                break;
            case VJML:
                System.out.println(meal + ": Vegetarian Jain meal.");
                break;
        }
    }
   
}
