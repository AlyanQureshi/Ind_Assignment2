package edu.ucalgary.oop;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.Vector;
import java.util.HashSet;
import java.util.ArrayList;


public class LocationTest {
    private Location location;
    private DisasterVictim victim1;
    private DisasterVictim victim2;
    private Supply supply1;
    private Supply supply2;
    private String name;
    private String address;

    @Before
    public void setUp() {
        // Initializing test objects before each test method
        name = "Shelter Alcatraz";
        address = "738 Street SE, Calgary";
        location = new Location(name, address);
        victim1 = new DisasterVictim("John Doe", "2024-01-01");
        victim2 = new DisasterVictim("Tom Hank", "2023-06-09");
        supply1 = new Supply("Water Bottle", 10);
        supply2 = new Supply("Special Medkit", 2);

        HashSet<DisasterVictim> occupants = new HashSet<>();
        occupants.add(victim1);
        occupants.add(victim2);

        Vector<Supply> supplies = new Vector<>();
        supplies.add(supply1);
        supplies.add(supply2);
    }

    @Test
    public void testLocationConstructor() {
        assertNotNull("The Location Constructor was not able to make an object.", location);
    }

    @Test
    public void testGetName() {
        assertEquals("The name is not the same as the name that the object location had in setup.", name, location.getName());
    }

    @Test
    public void testSetName() {
        String newName = "Rumble Town Rodeo";
        location.setName(newName);
        assertEquals("The method setName did not correctly update the name of the location.", newName, location.getName());
    }

    @Test
    public void testGetAddress() {
        assertEquals("The method getAddress did not retrieve the address accurately.", address, location.getAddress());
    }

    @Test
    public void testSetAddress() {
        String newAddress = "Old Town Road";
        location.setAddress(newAddress);
        assertEquals("The method setAddress did not appropriately update the location's address.", newAddress, location.getAddress());
    }

    @Test
    public void testGetOccupants() {
        assertEquals("The method getOccupants was not able to retrieve the hashset of occupants for a location.", occupants, location.getOccupants());
    }

    @Test
    public void testSetOccupants() {
        DisasterVictim newVictim1 = new DisasterVictim("John Sock", "2021-09-01");
        DisasterVictim newVictim2 = new DisasterVictim("Dom Lank", "2023-10-28");

        HashSet<DisasterVictim> newOccupants = new HashSet<>();
        newOccupants.add(newVictim1);
        newOccupants.add(newVictim2);

        location.setOccupants(newOccupants);
        assertEquals("The method setOccupants was not able to update the value of the location's occupants.", newOccupants, location.getOccupants());
    }

    @Test
    public void testGetSupplies() {
        assertEquals("The method getSupplies did not retrieve the vector of supplies for this location.", supplies, location.getSupplies());
    }

    @Test 
    public void testSetSupplies() {
        Supply newSupply1 = new Supply("Lays", 20);
        Supply newSupply2 = new Supply("Pepsi", 4);

        Vector<Supply> newSupplies = new Vector<>();
        newSupplies.add(newSupply1);
        newSupplies.add(newSupply2);

        location.setSupplies(newSupplies);
        assertEquals("The method setSupplies was not able to update the value of the location's supplies.", newSupplies, location.getSupplies());
    }

    @Test
    public void testAddOccupant() {
        DisasterVictim victim3 = new DisasterVictim("Henry Cavil", "2021-09-07");
        location.addOccupant(victim3);
        assertTrue("The method addOccupant did not correctly add the new victim to this location.", location.getOccupants().contains(victim3));
    }

    @Test
    public void testRemoveOccupant() {
        location.removeOccupant(victim2);
        assertFalse("The method removeOccupant did not properly delete the victim.", location.getOccupants().contains(victim2));
    }

    @Test 
    public void testAddSupply() {
        Supply supply3 = new Supply("Banana", 15);
        location.addSupply(supply3);
        assertTrue("The method addSupply did not properly add the supplies to the location.", location.getSupplies().contains(supply3));
    }

    @Test
    public void testRemoveSupply() {
        location.removeSupply(supply2);
        assertFalse("The method removeSupply was not able to properly delete this supply from the location.", location.getSupplies().contains(supply2));
    }
}