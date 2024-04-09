// Relief Service Class

package edu.ucalgary.oop;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Scanner;

public class ReliefService {
    private Inquirer inquirer;
    private DisasterVictim missingPerson;
    private String dateOfInquiry;
    private String infoProvided;
    private Location lastKnownLocation;

    // Constructor
    public ReliefService(Inquirer inquirer, DisasterVictim missingPerson, String dateOfInquiry, String infoProvided, Location lastKnownLocation) {
        this.inquirer = inquirer;
        this.missingPerson = missingPerson;
        setDateOfInquiry(dateOfInquiry);
        this.infoProvided = infoProvided;
        this.lastKnownLocation = lastKnownLocation;
    }

    // Getter and setter for inquirer
    public Inquirer getInquirer() {
        return inquirer;
    }

    public void setInquirer(Inquirer inquirer) {
        this.inquirer = inquirer;
    }

    // Getter and setter for missingPerson
    public DisasterVictim getMissingPerson() {
        return missingPerson;
    }

    public void setMissingPerson(DisasterVictim missingPerson) {
        this.missingPerson = missingPerson;
    }

    // Getter and setter for dateOfInquiry
    public String getDateOfInquiry() {
        return dateOfInquiry;
    }

    public void setDateOfInquiry(String dateOfInquiry) {
        // Check if the dateOfInquiry string matches the expected date format
        if (!isValidDateFormat(dateOfInquiry)) {
            throw new IllegalArgumentException("Invalid date format for date of inquiry. Expected format: YYYY-MM-DD");
        }
        this.dateOfInquiry = dateOfInquiry;
    }

    // Getter and setter for infoProvided
    public String getInfoProvided() {
        return infoProvided;
    }

    public void setInfoProvided(String infoProvided) {
        this.infoProvided = infoProvided;
    }

    // Getter and setter for lastKnownLocation
    public Location getLastKnownLocation() {
        return lastKnownLocation;
    }

    public void setLastKnownLocation(Location lastKnownLocation) {
        this.lastKnownLocation = lastKnownLocation;
    }

    // Helper method to check if a string matches the YYYY-MM-DD date format
    private boolean isValidDateFormat(String date) {
        try {
            LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public String getLogDetails() {
       return "Inquirer: " + inquirer.getFirstName() + 
           ", Missing Person: " + missingPerson.getFirstName() + 
           ", Date of Inquiry: " + dateOfInquiry + 
           ", Info Provided: " + infoProvided + 
           ", Last Known Location: " + lastKnownLocation.getName();
    }

    public static void main(String[] args) {
        HashSet<Location> locationList = new HashSet<>();
        Location telusSpark = new Location("Telus Spark", "32 Spark Street");
        Location childHospital = new Location("Children's Hospital", "102 Child West");
        Location carnival = new Location("Helper Carnival", "20 Carnival Street");

        locationList.add(telusSpark);
        locationList.add(childHospital);
        locationList.add(carnival);

        System.out.println("----------------------------------------");
        System.out.println("");
        System.out.println("Welcome to Los Pollos Hermanos!");
        System.out.println("");
        System.out.println("As a Relief Worker you have three locations to choose from: ");
        System.out.println("");
        for (Location object : locationList) {
            System.out.println(object.getName() + ". Address: " + object.getAddress());
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("");
        System.out.println("Are you a Location-based Relief Worker? Or a Central Relief Worker?");
        System.out.println("Enter '1' to be a Location-based Relief Worker. ");  
        System.out.print("Enter '0' to be Central Relief Worker: ");
        
        String input = scanner.nextLine();
        if (input.equals("0")) {
            System.out.println("CENTRAL");
            //locationWorkerMain(locationList)
        } else {
            System.out.println("LOCATION!");
        }
        

        
        /*
        Mains myJDBC = new Mains();
        myJDBC.createConnection();
        myJDBC.addToInquiryLog(4, "2024-02-01", "YAYYYY!!!!");
        myJDBC.close();
        */
    }    
}