// Mains Class

package edu.ucalgary.oop;

import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Mains implements VictimInterface, ReliefWorker{
    private Connection dbConnect;
    private ResultSet results;

    public void enterDisasterVictim() {
        return;
    }
    
    public void logInquirerQuery(Inquirer newInquirer, String date, String infoProvided) {
        int inquirerID = inquirerAlreadyExists(newInquirer);
        if (inquirerID != 0) {
            addToInquiryLog(inquirerID, date, infoProvided);
        }
        else {
            addNewInquirer(newInquirer);
            int newID = inquirerAlreadyExists(newInquirer);
            addToInquiryLog(newID, date, infoProvided);
        }
    }
    
    public void addNewInquirer(Inquirer newInquirer) {
        int maxInquirerID = 0;
        try {
            Statement myStmt = dbConnect.createStatement();
            results = myStmt.executeQuery("SELECT MAX(id) FROM inquirer");
            maxInquirerID = results.getInt("id");

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
            results = myStmt.executeQuery("SELECT MAX(id) FROM inquiry_log");
            maxInquiryLogID = results.getInt("id");

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
                    (results.getString("phonenumber").equals(person.getServicesPhoneNum()))) {
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

        for (Location place : locations) {
            for (DisasterVictim occupant : place.getOccupants()) {
                String occName = occupant.getFirstName().toLowerCase();

                if (occName.contains(partialName)) {
                    names.add(occupant.getFirstName());
                }
            }
        }

        return names;
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
                System.out.println("This item does not exist in the supplies list.")
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
