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
        
        int i = 1;
        for (Location object : locationList) {
            System.out.println(i + ": Location Name: " + object.getName() + ". Address: " + object.getAddress());
            i++;
        }

        System.out.println("");
        System.out.print("Please choose a number corresponding to your location: ");
        int userLocation = Integer.parseInt(scanner.nextLine());
        
        Location workerLocation = null;
        int j = 1;
        for (Location object : locationList) {
            if (j == userLocation) {
                workerLocation = object;
                break;
            }
            j++;
        }
        while (true) {
            System.out.println("");
            System.out.println("You are signed in as a Relief Worker for this Location: " + workerLocation.getName());
            System.out.println("");
            System.out.println("As a Location-Based Relief Worker for this location, You have can do 5 tasks: ");
            System.out.println("");
            System.out.println("1: Ordering Supplies for this location from Amazon.");
            System.out.println("2: Look at current inventory");
            System.out.println("3: Helping People Find Disaster Victims at this location.");
            System.out.println("4: Adding Disaster Victims to this location.");
            System.out.println("5: Look at current Occupants.");
            System.out.println("");

            System.out.print("Enter a number from the above options based on what you want to do (Enter '0' to stop): ");
            String option = scanner.nextLine();
            if (option.equals("1")) {
                orderSupplies(workerLocation);
            } else if (option.equals("3")) {
                boolean check = findLocationVictim(workerLocation, scanner);
                if (check == true) {
                    return;
                }
            } else if (option.equals("2")) {
                currentInventory(workerLocation);
            } else if (option.equals("4")) {
                enterDisasterVictimInfo(workerLocation);
            } else if (option.equals("5")) {
                currentOccupants(workerLocation);
            }
            else {
                break;
            }
        }
    }

    public void currentOccupants(Location location) {
        Scanner scanner = new Scanner(System.in);
        boolean check = false;
        System.out.println("");
        System.out.println("-----------------------------------------");
        for (DisasterVictim person : location.getOccupants()) {
            if (person.getFirstName() != null) {
                System.out.println("Occupant First Name: " + person.getFirstName());
                System.out.println("Entry Date: " + person.getEntryDate());
                check = true;
            }
            
        }
        if (!check) {
            System.out.println("No Current Occupants!");
        }
        System.out.println("-----------------------------------------");
        System.out.println("");
        System.out.print("Enter any character to go back to your responsibilities: ");
        String use = scanner.nextLine();
    }

    public void enterDisasterVictimInfo(Location location) {
        System.out.println("");
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.print("Enter any character if you want to add a disaster victim to this location (Enter 'none' if you want to stop): ");
            String disasterCheck = scanner.nextLine();
            if (disasterCheck.equals("none")) {
                break;
            }
            System.out.println("");
            DisasterVictim newPerson = newDisasterVictim(scanner);
            System.out.println("");
            boolean familyCheck = true;
            System.out.println("Does this disaster victim have any family connections? ");
            while (true) {
                
                String relationship = "";
                while (true) {
                    System.out.print("Please enter one of these four options exactly: [sibling, child, parent, spouse] (Enter 'none' if there are no family connections): ");
                    String input = scanner.nextLine();
                    if (input.equals("none")) {
                        familyCheck = false;
                        break;
                    } else if ((input.equals("sibling")) || (input.equals("child")) || (input.equals("parent")) || (input.equals("spouse"))) {
                        familyCheck = true;
                        relationship = input;
                        break;
                    } else {
                        System.out.println("Please enter a valid input");
                        familyCheck = true;
                    }
                }

                if (!familyCheck) {
                    break;
                }
                
                System.out.println("Enter the person this disaster victim is connected to.");
                System.out.println("");
                DisasterVictim connectionTo = newDisasterVictim(scanner);
                FamilyRelation newRelation = new FamilyRelation(newPerson, relationship, connectionTo);
                newPerson.addFamilyConnection(newRelation);
                System.out.println("");
                System.out.println("This family connection has been added to this victim's family connections!");
                System.out.println("");
                System.out.println(newPerson.getFirstName() + "'s Family Connections: ");
                for (FamilyRelation relation : newPerson.getFamilyConnections()) {
                    System.out.println(relation.getPersonOne() + " is the " + relation.getRelationshipTo() + " of " + relation.getPersonTwo());
                }
                System.out.println("");
                System.out.println(connectionTo.getFirstName() + "'s Family Connections: ");
                for (FamilyRelation relation : connectionTo.getFamilyConnections()) {
                    System.out.println(relation.getPersonOne() + " is the " + relation.getRelationshipTo() + " of " + relation.getPersonTwo());
                }
                System.out.println("Please enter another connection.");
            }

            while (true) {
                System.out.println("");
                System.out.print("Enter 'yes' if you wish to add a medical record for this victim, enter 'no' if you do not: ");
                String medicalCheck = scanner.nextLine();
                if (medicalCheck.equals("no")) {
                    break;
                }
                System.out.println("");
                System.out.print("Enter this victim's treatment details: ");
                String details = scanner.nextLine();
                String treatmentDate = "";
                while (true) {
                    System.out.print("Please enter the treatment date of this victim (Enter a valid date): ");
                    String input = scanner.nextLine();
                    if (!isValidDateFormat(input)) {
                        System.out.println("This date does not have a valid format!");
                        continue;
                    }
                    treatmentDate = input;
                    break;
                }
                MedicalRecord newRecord = new MedicalRecord(location, details, treatmentDate);
                newPerson.addMedicalRecord(newRecord);
                System.out.println("");
                System.out.println("This medical record was added!");
                System.out.println("Please enter another Medical Record.");
            }

            while (true) {
                System.out.println("");
                System.out.print("Please enter 'add' to add any personal belongings(Supplies) this disaster victim has (Enter 'done' to stop): ");
                String supplyCheck = scanner.nextLine();
                if (supplyCheck.equals("done")) {
                    break;
                }
                System.out.println("");
                System.out.print("Enter the supply name (Enter 'done' if you do not want to add any more supplies): ");
                String item = scanner.nextLine();
                System.out.print("Enter the quantity of this supply: ");
                int quant = Integer.parseInt(scanner.nextLine());

                Supply newSupply = new Supply(item, quant);
                newPerson.addPersonalBelonging(newSupply, location);
                System.out.println("This supply has been added to the disaster victim's personal belongings!");
                System.out.println("Please enter any more belongings this person might have.");
            }
            location.addOccupant(newPerson);
        }
    }

    public DisasterVictim newDisasterVictim(Scanner scanner) {
        DisasterVictim newPerson = null;
        System.out.print("Please enter the person's first name: ");
        String firstName = scanner.nextLine();
        String date = "";
        while (true) {
            System.out.print("Please enter the entry date of this person (Enter a valid date): ");
            String input = scanner.nextLine();
            if (!isValidDateFormat(input)) {
                System.out.println("This date does not have a valid format!");
                continue;
            }
            date = input;
            break;
        }
        System.out.print("Enter their last name (Enter 'none' if you don't know their last name): ");
        String lastName = scanner.nextLine();
        if (lastName.equals("none")) {
            lastName = null;
        }
        String birthDate = "";
        while (true) {
            System.out.print("Please enter the birth date of this person (Enter a valid date or enter 'none' if you don't know it): ");
            String input = scanner.nextLine();
            if (input.equals("none")) {
                birthDate = null;
                break;
            }
            else if (!isValidDateFormat(input)) {
                System.out.println("This date does not have a valid format!");
                continue;
            }
            birthDate = input;
            break;
        }

        if ((lastName == null) && (birthDate == null)) {
            newPerson = new DisasterVictim(firstName, date);
        } else if (birthDate == null) {
            newPerson = new DisasterVictim(firstName, lastName, date);
        } else {
            newPerson = new DisasterVictim(firstName, lastName, birthDate, date);
        }

        System.out.print("Please enter the person's gender: ");
        String gender = scanner.nextLine();
        newPerson.setGender(gender);

        System.out.print("Please enter the person's age (if birthdate was not known). Enter 'done' if birthdate is already filled: ");
        String ageCheck = scanner.nextLine();
        if (!ageCheck.equals("done")) {
            int age = Integer.parseInt(ageCheck);
            newPerson.setAge(age);
        }

        System.out.print("Please provide any comments about this person: ");
        String comments = scanner.nextLine();
        newPerson.setComments(comments);
        System.out.println("");
        System.out.println("Options for Special Meals:");
        System.out.println("AVML - Asian vegetarian meal");
        System.out.println("DBML - Diabetic meal");
        System.out.println("GFML - Gluten intolerant meal");
        System.out.println("KSML - Kosher meal");
        System.out.println("LSML - Low salt meal");
        System.out.println("MOML - Muslim meal");
        System.out.println("PFML - Peanut-free meal");
        System.out.println("VGML - Vegan meal");
        System.out.println("VJML - Vegetarian Jain meal");
        ArrayList<String> restrictionsList = new ArrayList<>();
        while (true) {
            System.out.print("Enter the code exactly as seen above (All Caps) for each restriction you might have (or type 'done' to finish): ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("done")) {
                break; 
            }

            restrictionsList.add(input);
        }
        String[] restrictions = restrictionsList.toArray(new String[0]);
        newPerson.setDietaryRestrictions(restrictions);

        return newPerson;
    }

    public void currentInventory(Location location) {
        Scanner scanner = new Scanner(System.in);
        boolean check = false;
        System.out.println("------------------------");
        for (Supply item : location.getSupplies()) {
            if (item.getType() != null ) {
                System.out.println("Item: " + item.getType() + ". Quantity: " + item.getQuantity());
                check = true;
            }
                
        }
        if (!check) {
            System.out.println("No Current Supplies!");
        }
        System.out.println("------------------------");
        System.out.print("Enter any character to go back to your responsibilities: ");
        String use = scanner.nextLine();
    }

    public boolean findLocationVictim(Location location, Scanner scanner) {
        Mains database = new Mains();
        database.createConnection();
        
        boolean check = false;
        System.out.println("");
        HashMap<Integer, String> queryLog = database.returnAllQueries();
        System.out.println("This is the current query log: ");
        System.out.println("--------------------------");
        for (int key : queryLog.keySet()) {
            System.out.println(key + ": " + queryLog.get(key));
        }
        System.out.println("--------------------------");
        System.out.println("");
        System.out.println("--------------------------");
        System.out.println("These are the current occupants at this location!");
        int i = 1;
        for (DisasterVictim person : location.getOccupants()) {
            System.out.println("Occupant" + i + ": ");
            System.out.println("First Name: " + person.getFirstName());
            System.out.println("Last Name: " + person.getLastName());
            System.out.println("");
            i++;
        }
        System.out.println("--------------------------");

        System.out.println("Do you notice any similarities between the current inquiry log and the Victims currently at this location?");
        System.out.println("");
        System.out.println("If there any similarities please type '1' to log out and sign back in as a Central Relief Worker so you can resolve this query.");
        System.out.print("Otherwise, please enter any character to go back to see your responsibilities as a Location Based Relief Worker: ");
        String input = scanner.nextLine();
        if (input.equals("1")) {
            check = true;
        }
        database.close();
        return check;
    }

    public void orderSupplies(Location location) {
        System.out.println("By ordering supplies, you are adding them to this location's inventory!");
        System.out.println("");
        int totalPrice = 0;
        while (true) {
            totalPrice = orderingSupplies(location);
            if (totalPrice == 0) {
                System.out.println("You did not order any supplies!");
                System.out.println("Please try again.");
                continue;
            }
            break;
        }
        System.out.println("");
        System.out.println("You ordered $" + totalPrice + " worth of supplies to " + location.getName());
    }

    public void centralWorkerMain(HashSet<Location> locationList, Scanner scanner) {
        System.out.println("");
        System.out.println("As a Central Relief Worker you have two responsibilities:");
        System.out.println("");
        System.out.println("1) Log Inquirer Queries.");
        System.out.println("2) Search for Disaster Victims Using Inquiry Log.");
        System.out.println("");

        System.out.print("Enter '1' or '2' depending on what you want to do (Enter '0' to stop): ");
        String option = scanner.nextLine();
        if (option.equals("1")) {
            logInquirerQuery(scanner);
        } else if (option.equals("2")) {
            resolveQueries(scanner, locationList);
        }
    }

    public void resolveQueries(Scanner scanner, HashSet<Location> allLocations) {
        Mains database = new Mains();
        database.createConnection();

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
        database.close();
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
    
    private static boolean isValidNumberFormat(String number) {
        String numberFormatPattern = "^\\d{3}-\\d{3}-\\d{4}$";
        return number.matches(numberFormatPattern);
    }

    private static boolean isValidDateFormat(String date) {
        String dateFormatPattern = "^\\d{4}-\\d{2}-\\d{2}$";
        return date.matches(dateFormatPattern);
    }

    public void logInquirerQuery(Scanner scanner) {
        Mains database = new Mains();
        database.createConnection();

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

        database.close();
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

    public int orderingSupplies(Location location) {
        HashMap<String, Integer> supplyDict = new HashMap<>();
        supplyDict.put("Medkit", 70);
        supplyDict.put("Water", 2);
        supplyDict.put("Bandage", 10);
        supplyDict.put("Chips Bag", 4);
        supplyDict.put("Juice", 5);
        supplyDict.put("Pop", 3);
        supplyDict.put("Needle", 15);
        supplyDict.put("Tylenol", 8);
        
        System.out.println("These are the supplies that you can order: ");
        System.out.println("Note: Their price per quantity is listed beside the item name.");
        System.out.println("");
        System.out.println("------------------------------");
        for (String key : supplyDict.keySet()) {
            int price = supplyDict.get(key);
            System.out.println(key + ": $" + price);
        }
        System.out.println("------------------------------");

        int totalPrice = 0;
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("");
            System.out.print("Enter an item exactly how it is shown above (Enter a '1' to stop adding supplies): ");
            String item = scanner.nextLine();

            if (item.equals("1")) {
                break;
            }

            if (!supplyDict.containsKey(item)) {
                System.out.println("This item does not exist in the supplies list.");
                System.out.println("Please try again.");
                continue;
            } else {
                System.out.print("How many " + item + "s do you want (Enter an integer): ");
                int quantity = Integer.parseInt(scanner.nextLine());
                Supply newSupply = new Supply(item, quantity);
                location.addSupply(newSupply);
                int totalSupplyPrice = supplyDict.get(item) * quantity;
                totalPrice += totalSupplyPrice;
                System.out.println("Item added to total!");
                System.out.println("Current Total: " + totalPrice);
            }
        }
        
        return totalPrice;
    }
}
