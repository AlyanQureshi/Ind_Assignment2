/**
 * @author Alyan Qureshi <a href="mailto:muhammad.qureshi4@ucalgary.ca">
 * muhammad.qureshi4@ucalgary.ca</a>
 * @version 1.5
 * @since 1.0
*/

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

    /**Constructor */
    public ReliefService(Inquirer inquirer, DisasterVictim missingPerson, String dateOfInquiry, String infoProvided, Location lastKnownLocation) {
        this.inquirer = inquirer;
        this.missingPerson = missingPerson;
        this.setDateOfInquiry(dateOfInquiry);
        this.infoProvided = infoProvided;
        this.lastKnownLocation = lastKnownLocation;
    }

    /** Getter and setter for inquirer */
    public Inquirer getInquirer() {
        return inquirer;
    }

    /** Setter for inquirer */
    public void setInquirer(Inquirer inquirer) {
        this.inquirer = inquirer;
    }

    /** Getter and setter for missingPerson */
    public DisasterVictim getMissingPerson() {
        return missingPerson;
    }

    /** setting for missing person */
    public void setMissingPerson(DisasterVictim missingPerson) {
        this.missingPerson = missingPerson;
    }

    /** Getter and setter for dateOfInquiry */
    public String getDateOfInquiry() {
        return dateOfInquiry;
    }

    /**setting for date of inquiry */
    public void setDateOfInquiry(String dateOfInquiry) {
        // Check if the dateOfInquiry string matches the expected date format
        if (!isValidDateFormat(dateOfInquiry)) {
            throw new IllegalArgumentException("Invalid date format for date of inquiry. Expected format: YYYY-MM-DD");
        }
        this.dateOfInquiry = dateOfInquiry;
    }

    /** Getter and setter for infoProvided */
    public String getInfoProvided() {
        return infoProvided;
    }

    /** Setting for info provided */
    public void setInfoProvided(String infoProvided) {
        this.infoProvided = infoProvided;
    }

    /** Getter and setter for lastKnownLocation */
    public Location getLastKnownLocation() {
        return lastKnownLocation;
    }

    /** Setting for location */
    public void setLastKnownLocation(Location lastKnownLocation) {
        this.lastKnownLocation = lastKnownLocation;
    }

    /** Helper method to check if a string matches the YYYY-MM-DD date format */
    public boolean isValidDateFormat(String date) {
        try {
            LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
            return true;
        } catch (Exception e) {
            throw new IllegalArgumentException("Wrong Date Format. Must be YYYY-MM-DD. ");
        }
    }
    
    /** Returns log details */
    public String getLogDetails() {
       return "Inquirer: " + inquirer.getFirstName() + 
           ", Missing Person: " + missingPerson.getFirstName() + 
           ", Date of Inquiry: " + dateOfInquiry + 
           ", Info Provided: " + infoProvided + 
           ", Last Known Location: " + lastKnownLocation.getName();
    }

    /** Main */
    public static void main(String[] args) {
        HashSet<Location> locationList = new HashSet<>();
        Location telusSpark = new Location("Telus Spark", "32 Spark Street");
        Location childHospital = new Location("Children's Hospital", "102 Child West");
        Location carnival = new Location("Carnival Hospital", "20 Carnival Street");

        locationList.add(telusSpark);
        locationList.add(childHospital);
        locationList.add(carnival);
        System.out.println("");

        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("");
        System.out.println("Welcome to Los Pollos Hermanos!");
        
        
        int i = 0;
        while (true) {
            if (i != 0) {
                System.out.println("");
                System.out.println("You have been logged out!");
            }
            System.out.println("");
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please login: ");
            System.out.println("1: Central Relief Worker.");  
            System.out.println("2: Location-based Relief Worker");
            System.out.println("");
            System.out.print("Enter one of the options above (Enter any character to exit this application): ");
            
            String input = scanner.nextLine();

            Mains main = new Mains();
            if (input.equals("1")) {
                main.centralWorkerMain(locationList, scanner);
            } else if (input.equals("2")) {
                main.locationWorkerMain(locationList, scanner);
            } else {
                break;
            }
            i++;
        }
        System.out.println("");
        System.out.println("PROGRAM HAS FINISHED");
        System.out.println("--------------------------------------------------------------------------------");
        
    }    
}