// Mains Class

package edu.ucalgary.oop;
import java.util.Scanner;

import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Mains implements ReliefWorker {
    private Connection dbConnect;
    private ResultSet results;

    public Mains() {}

    public void locationWorkerMain(HashSet<Location> locationList, Scanner scanner) {
        System.out.println("");
        System.out.println("As a Relief Worker you have three locations to choose from: ");
        System.out.println("");
        for (Location object : locationList) {
            System.out.println(object.getName() + ". Address: " + object.getAddress());
        }
    }

    public void centralWorkerMain(HashSet<Location> locationList, Scanner scanner) {
        Mains database = new Mains();
        database.createConnection();
        
        System.out.println("");
        System.out.println("As a Central Relief Worker you have two responsibilities:");
        System.out.println("");
        System.out.println("1) Log Inquirer Queries.");
        System.out.println("2) Search for Disaster Victims Using Inquiry Log.");
        System.out.println("");

        System.out.print("Enter '1' or '2' depending on what you want to do (Enter '0' to stop): ");
        String option = scanner.nextLine();
        if (option.equals("1")) {
            database.logInquirerQuery(scanner);
        } else if (option.equals("2")) {
            database.resolveQueries(scanner, locationList);
        }
        
        database.close();
    }

    public void resolveQueries(Scanner scanner, HashSet<Location> allLocations) {
        boolean done = true;
        System.out.println("");
        System.out.println("Here is the current query log with the current inquiries: ");
        System.out.println("-----------------------------");
        HashMap<Integer, String> queryLog = returnAllQueries();
        for (int key : queryLog.keySet()) {
            System.out.println(key + ": " + queryLog.get(key));
        }
        System.out.println("-----------------------------");
        System.out.println("");
        System.out.print("Please enter an inquiry number from the available options above that you want to resolve: ");
        int inquiryID = Integer.parseInt(scanner.nextLine());
        System.out.println("");
        System.out.println("You have chosen this inquiry to handle: " + queryLog.get(inquiryID));
        while (done) {
            System.out.println("");
            System.out.print("Please enter a first name(partial works as well) to search for. Enter 'done' to stop managing this query: ");
            String partialName = scanner.nextLine();
            if (partialName.equals("done")) {
                break;
            }

            ArrayList<String> searchResults = findDisasterVictims(allLocations, partialName);
            if (searchResults == null) {
                System.out.println("Your search did not return any names. Please try again.");
                continue;
            }
            
            while (true) {
                System.out.println("");
                int i = 0;
                System.out.println("Here are all the first names that match your search: ");
                for (String name : searchResults) {
                    System.out.println((i+1) + ": " + name);
                    i++;
                }
                System.out.println("");
                System.out.print("Enter a number above to see more information (Enter '0' to search with a different name): ");
                
                int element = Integer.parseInt(scanner.nextLine());
                if (element == 0) {
                    break;
                }
                int index = element - 1;
                System.out.println("");
                victimMoreInfo(searchResults.get(index), allLocations);
                System.out.println("");

                System.out.println("Was this the person you are looking for?");
                System.out.print("Enter 'yes' if it is, enter 'no' if it is not: ");
                String found = scanner.nextLine();

                if (found.equals("yes")) {
                    System.out.println("");
                    String number = getInquirerPhoneNumber(inquiryID);
                    System.out.println("Please call this inquirer's number to tell them that we have resolved their query: " + number);
                    done = false;
                    break;
                }
            }
        }
    }

    public String getInquirerPhoneNumber(int inquiryLogID) {
        String phoneNumber = null;
        try {
            String query = "SELECT INQUIRER.phoneNumber " +
                        "FROM INQUIRER " +
                        "INNER JOIN INQUIRY_LOG ON INQUIRER.id = INQUIRY_LOG.inquirer " +
                        "WHERE INQUIRY_LOG.id = ?";
            
            PreparedStatement myStmt = dbConnect.prepareStatement(query);
            myStmt.setInt(1, inquiryLogID);
            ResultSet results = myStmt.executeQuery();
            
            if (results.next()) {
                phoneNumber = results.getString("phoneNumber");
            }
            
            myStmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return phoneNumber;
    }


    public void victimMoreInfo(String fName, HashSet<Location> allLocations) {
        String lowerName = fName.toLowerCase();
        for (Location place : allLocations) {
            for (DisasterVictim occupant : place.getOccupants()) {
                String occName = occupant.getFirstName().toLowerCase();

                if (occName.equals(lowerName)) {
                    System.out.println("First Name: " + occupant.getFirstName());
                    if (occupant.getLastName() == null) {
                        System.out.println("Last Name: Not Known" );
                    } else {
                        System.out.println("Last Name: " + occupant.getLastName());
                    }
                    System.out.println("Entry Date: " + occupant.getEntryDate());
                    System.out.println("Social ID: " + occupant.getAssignedSocialID());
                    return;
                }
            }
        }
    }

    public HashMap<Integer, String> returnAllQueries() {
        HashMap<Integer, String> queries = new HashMap<>();
        try {
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM inquiry_log");
            
            while (results.next()) {
                int id = results.getInt("id");
                String detail = results.getString("details");
                queries.put(id, detail);
            }
            myStmt.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return queries;
    }

    public void enterDisasterVictimInfo() {
        return;
    }
    
    private static boolean isValidNumberFormat(String number) {
        String numberFormatPattern = "^\\d{3}-\\d{3}-\\d{4}$";
        return number.matches(numberFormatPattern);
    }

    private static boolean isValidDateFormat(String date) {
        String dateFormatPattern = "^\\d{4}-\\d{2}-\\d{2}$";
        return date.matches(dateFormatPattern);
    }

    public void logInquirerQuery(Scanner scanner) {
        System.out.println("");
        System.out.println("Please enter the inquirer: ");
        System.out.print("Enter their first name: ");
        String fName = scanner.nextLine();
        System.out.print("Enter their last name(Enter 'none' if you don't know their last name): ");
        String lName = scanner.nextLine();
        if (lName.equals("none")) {
            lName = null;
        }
        String number = "";
        while (true) {
            System.out.print("Please enter a valid phone number: ");
            String input = scanner.nextLine();
            if (!isValidNumberFormat(input)) {
                System.out.println("This number did not have a valid format!");
                continue;
            }
            number = input;
            break;
        }
        Inquirer userInquirer = null;

        if (lName == null) {
            userInquirer = new Inquirer(fName, number);
        } else {
            userInquirer = new Inquirer(fName, lName, number);
        }

        int inquirerID = inquirerAlreadyExists(userInquirer);
        System.out.println("");
        if (inquirerID != 0) {
            System.out.println("This inquirer already exists.");
            System.out.print("Please enter the details this inquirer gave: ");
            String inquiry = scanner.nextLine();

            String date = "";
            while (true) {
                System.out.print("Please enter the date this inquirer called (Enter a valid date): ");
                String input = scanner.nextLine();
                if (!isValidDateFormat(input)) {
                    System.out.println("This date does not have a valid format!");
                    continue;
                }
                date = input;
                break;
            }

            addToInquiryLog(inquirerID, date, inquiry);
            System.out.println("This inquiry has been stored into the database.");

        } else {
            addNewInquirer(userInquirer);
            System.out.println("This inquirer is new and has now been added to the database!");
            int newInquirerID = inquirerAlreadyExists(userInquirer);

            System.out.print("Please enter the details this inquirer gave: ");
            String inquiry = scanner.nextLine();

            String date = "";
            while (true) {
                System.out.print("Please enter the date this inquirer called (Enter a valid date): ");
                String input = scanner.nextLine();
                if (!isValidDateFormat(input)) {
                    System.out.println("This date does not have a valid format!");
                    continue;
                }
                date = input;
                break;
            }

            addToInquiryLog(newInquirerID, date, inquiry);
            System.out.println("This inquiry has been stored into the database.");
        }
    }
    
    public void addNewInquirer(Inquirer newInquirer) {
        int maxInquirerID = 0;
        try {
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT MAX(id) AS max_id FROM inquirer");
            if (results.next()) {
                maxInquirerID = results.getInt("max_id");
            }

            myStmt.close();
        } catch (SQLException el) {
            el.printStackTrace();
        }

        int newInquirerID = maxInquirerID + 1;
        
        if (newInquirer.getLastName() == null) {
            try {
                String query = "INSERT INTO inquirer (id, firstName, phoneNumber) VALUES (?,?,?)";
                PreparedStatement myStmt = dbConnect.prepareStatement(query);
                myStmt.setInt(1, newInquirerID);
                myStmt.setString(2, newInquirer.getFirstName());
                myStmt.setString(3, newInquirer.getServicesPhoneNum());

                int rowCount = myStmt.executeUpdate();
                myStmt.close();
            } catch (SQLException el) {
                el.printStackTrace();
            }
        }
        else {
            try {
                String query = "INSERT INTO inquirer (id, firstName, lastName, phoneNumber) VALUES (?,?,?,?)";
                PreparedStatement myStmt = dbConnect.prepareStatement(query);
                myStmt.setInt(1, newInquirerID);
                myStmt.setString(2, newInquirer.getFirstName());
                myStmt.setString(3, newInquirer.getLastName());
                myStmt.setString(4, newInquirer.getServicesPhoneNum());

                int rowCount = myStmt.executeUpdate();
                myStmt.close();
            } catch (SQLException el) {
                el.printStackTrace();
            }
        }
    }

    public void createConnection(){
        try{
            dbConnect = DriverManager.getConnection("jdbc:postgresql://localhost/ensf380project", "oop", "ucalgary");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addToInquiryLog(int inquirerID, String date, String info) {
        int maxInquiryLogID = 0;
        try {
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT MAX(id) AS max_id FROM inquiry_log");
            if (results.next()) {
                maxInquiryLogID = results.getInt("max_id");
            }
            
            myStmt.close();
        } catch (SQLException el) {
            el.printStackTrace();
        }

        int newLogID = maxInquiryLogID + 1;

        try {
            java.sql.Date dateOfInquiry = java.sql.Date.valueOf(date);

            String query = "INSERT INTO inquiry_log (id, inquirer, callDate, details) VALUES (?,?,?,?)";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);

            myStmt.setInt(1, newLogID);
            myStmt.setInt(2, inquirerID);
            myStmt.setDate(3, dateOfInquiry);
            myStmt.setString(4, info);

            int rowCount = myStmt.executeUpdate();
            myStmt.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void close() {
        try {
            results.close();
            dbConnect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int inquirerAlreadyExists(Inquirer person) {
        int id = 0;
        try {
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT * FROM inquirer");

            while (results.next()) {
                if ((results.getString("firstname").equals(person.getFirstName())) && 
                    (results.getString("phonenumber").equals(person.getServicesPhoneNum())) &&
                    (results.getString("lastname").equals(person.getLastName()))) {
                    id = results.getInt("id");
                    break;
                }
            }

            myStmt.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return id;
    }

    public ArrayList<String> findDisasterVictims(HashSet<Location> locations, String name) {
        String partialName = name.toLowerCase();
        ArrayList<String> names = new ArrayList<>();
        boolean check = false;

        for (Location place : locations) {
            for (DisasterVictim occupant : place.getOccupants()) {
                String occName = occupant.getFirstName().toLowerCase();

                if (occName.contains(partialName)) {
                    names.add(occupant.getFirstName());
                    check = true;
                }
            }
        }

        if (check) {
            return names;
        } else {
            return null;
        } 
    }

    public int orderingSupplies() {
        HashMap<String, Integer> supplyDict = new HashMap<>();
        supplyDict.put("Medkit", 70);
        supplyDict.put("Water", 2);
        supplyDict.put("Bandage", 10);
        supplyDict.put("Chips", 4);
        supplyDict.put("Juice", 5);
        supplyDict.put("Pop", 3);
        supplyDict.put("Needle", 15);
        supplyDict.put("Tylenol", 8);
        
        System.out.println("These are the supplies that you can order: ");
        System.out.println("Note: Their price/quantity is listed beside the item name.");
        System.out.println("");

        for (String key : supplyDict.keySet()) {
            int price = supplyDict.get(key);
            System.out.println(key + ": $" + price);
        }

        int totalPrice = 0;
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("");
            System.out.print("Enter an item (Enter a '1' to stop adding supplies): ");

            String item = scanner.nextLine();

            if (item.equals("1")) {
                break;
            }

            if (!supplyDict.containsKey(item)) {
                System.out.println("This item does not exist in the supplies list.");
                System.out.println("Please try again.");
                continue;
            } else {
                totalPrice += supplyDict.get(item);
                System.out.println("Item added!");
            }
        }
        
        return totalPrice;
    }
}
