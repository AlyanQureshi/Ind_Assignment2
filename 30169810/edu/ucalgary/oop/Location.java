/**
 * @author Alyan Qureshi <a href="mailto:muhammad.qureshi4@ucalgary.ca">
 * muhammad.qureshi4@ucalgary.ca</a>
 * @version 1.3
 * @since 1.0
*/

package edu.ucalgary.oop;

import java.util.HashSet;

public class Location {
    private String name;
    private String address;
    private HashSet<DisasterVictim> occupants = new HashSet<>(); // Initialized
    private HashSet<Supply> supplies = new HashSet<>(); // Initialized

    // Constructor
    public Location(String name, String address) {
        this.name = name;
        this.address = address;
    }

    // Getter and setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and setter for address
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Getter for occupants
    public HashSet<DisasterVictim> getOccupants() {
        return new HashSet<>(occupants); // Return a copy to maintain encapsulation
    }

    // Setter for occupants
    public void setOccupants(HashSet<DisasterVictim> occupants) {
        this.occupants = new HashSet<>(occupants); // Clear and addAll in one step, maintains encapsulation
    }

    // Getter for supplies
    public HashSet<Supply> getSupplies() {
        return new HashSet<>(supplies); // Return a copy to maintain encapsulation
    }

    // Setter for supplies
    public void setSupplies(HashSet<Supply> supplies) {
        this.supplies = new HashSet<>(supplies); // Clear and addAll in one step, maintains encapsulation
    }

    // Add an occupant to occupants
    public void addOccupant(DisasterVictim occupant) {
        boolean check = false;
        for (DisasterVictim object : occupants) {
            if (object.getFirstName().equals(occupant.getFirstName()) && 
                object.getEntryDate().equals(occupant.getEntryDate())) {
                check = true;
                break;
            }
        }

        if (check) {
            throw new IllegalArgumentException("This occupant already exists at this location and so it cannot be added again!");
        }
        else {
            occupants.add(occupant);
        }
    }

    // Remove an occupant from occupants
    public void removeOccupant(DisasterVictim occupant) {
        boolean check = false;
        for (DisasterVictim object : occupants) {
            if (object.getFirstName().equals(occupant.getFirstName()) && 
                object.getEntryDate().equals(occupant.getEntryDate())) {
                check = true;
                break;
            }
        }

        if (!check) {
            throw new IllegalArgumentException("This occupant did not exist at this location in the first place so they cannot be removed!");
        }
    }

    // Add a supply to supplies
    public void addSupply(Supply supply) {
        boolean check = false;
        for (Supply object : supplies) {
            if (object.getType().equals(supply.getType())) {
                int newQuantity = object.getQuantity() + supply.getQuantity();
                object.setQuantity(newQuantity);
                check = true;
                break;
            }
        }

        if (!check) {
            supplies.add(supply);
        }
    }

    // Remove a supply from supplies
    public void removeSupply(Supply supply) {
        boolean check = false;
        for (Supply object : supplies) {
            if ((object.getType().equals(supply.getType())) && ((object.getQuantity() - supply.getQuantity()) >= 0)) {
                int newQuantity = object.getQuantity() - supply.getQuantity();
                if (newQuantity == 0) {
                    supplies.remove(object);
                }
                else {
                    object.setQuantity(newQuantity);
                }
                check = true;
                break;
            }
        }

        if (!check) {
            throw new IllegalArgumentException("Cannot remove more supplies than what are already at this location!");
        }
    }
}