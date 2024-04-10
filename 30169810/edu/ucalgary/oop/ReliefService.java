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
        Location carnival = new Location("Carnival Hospital", "20 Carnival Street");
        DisasterVictim alyan = new DisasterVictim("alyan", "2022-01-03");
        DisasterVictim balyan = new DisasterVictim("balyan", "2019-01-03");
        DisasterVictim dalyan = new DisasterVictim("ALyAn", "2021-01-03");
        DisasterVictim libby = new DisasterVictim("foxxxxxx", "2024-01-03");
        telusSpark.addOccupant(alyan);
        carnival.addOccupant(balyan);
        childHospital.addOccupant(dalyan);
        telusSpark.addOccupant(libby);

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