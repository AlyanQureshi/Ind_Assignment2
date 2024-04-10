/**
 * @author Alyan Qureshi <a href="mailto:muhammad.qureshi4@ucalgary.ca">
 * muhammad.qureshi4@ucalgary.ca</a>
 * @version 2.2
 * @since 1.0
*/

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

    /** The method setUp runs before test method to ensure consistency */
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

    /** This test checks whether the Location Constructor is able to make a new Location object */
    @Test
    public void testLocationConstructor() {
        assertNotNull("The Location Constructor was not able to make an object.", location);
    }

    /** Testing whether the method getName returns the right name */
    @Test
    public void testGetName() {
        assertEquals("The name is not the same as the name that the object location had in setup.", name, location.getName());
    }

    /** Testing whether the method setName correcly updates the value of name */
    @Test
    public void testSetName() {
        String newName = "Rumble Town Rodeo";
        location.setName(newName);
        assertEquals("The method setName did not correctly update the name of the location.", newName, location.getName());
    }

    /** Tests whether the method getAddress correctly returns the right address */
    @Test
    public void testGetAddress() {
        assertEquals("The method getAddress did not retrieve the address accurately.", address, location.getAddress());
    }

    /** Tests whether the method setAddress correctly updates the address */
    @Test
    public void testSetAddress() {
        String newAddress = "Old Town Road";
        location.setAddress(newAddress);
        assertEquals("The method setAddress did not appropriately update the location's address.", newAddress, location.getAddress());
    }

    /** Testing whether the method getOccupants returns the right occupants */
    @Test
    public void testGetOccupants() {
        HashSet<DisasterVictim> expectedOccupants = new HashSet<>();
        expectedOccupants.add(victim1);
        expectedOccupants.add(victim2);

        assertEquals("The method getOccupants was not able to retrieve the hashset of occupants for a location.", expectedOccupants, location.getOccupants());
    }

    /** Testing whether the method setOccupants correctly updates the occupants */
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

    /** Testing whether the method getSupplies correctly returns the right supplies */
    @Test
    public void testGetSupplies() {
        HashSet<Supply> expectedSupplies = new HashSet<>();
        expectedSupplies.add(supply1);
        expectedSupplies.add(supply2);

        // Convert location.getSupplies() to a HashSet to match expectedSupplies
        assertEquals("The method getSupplies did not retrieve the vector of supplies for this location.", expectedSupplies, new HashSet<>(location.getSupplies()));
    }

    /** Testing whether the method setSupplies correctly updates the right supplies */
    @Test
    public void testSetSupplies() {
        Supply newSupply1 = new Supply("Lays", 20);
        Supply newSupply2 = new Supply("Pepsi", 4);

        HashSet<Supply> newSupplies = new HashSet<>();
        newSupplies.add(newSupply1);
        newSupplies.add(newSupply2);

        location.setSupplies(newSupplies);
        assertEquals("The method setSupplies was not able to update the value of the location's supplies.", newSupplies, location.getSupplies());
    }

    /** Testing whether the method addOccupant correctly adds a new DisasterVictim object */
    @Test
    public void testAddOccupant() {
        DisasterVictim victim3 = new DisasterVictim("Henry Cavil", "2021-09-07");
        location.addOccupant(victim3);
        
        assertTrue("The method addOccupant did not correctly add the new victim to this location.", location.getOccupants().contains(victim3));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddOccupantButOccupantAlreadyExists() {
        DisasterVictim victim3 = new DisasterVictim("Henry Cavil", "2021-09-07");
        location.addOccupant(victim3);
        location.addOccupant(victim3);
        // Expecting to throw Illegal Argument Exception when running this test.
    }

    /** Testing whether the method removeOccupant correctly removes a DisasterVictim object */
    @Test
    public void testRemoveOccupant() {
        location.removeOccupant(victim2);
        
        assertFalse("The method removeOccupant did not properly delete the victim.", location.getOccupants().contains(victim2));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testRemoveOccupantButNoOccupantExists() {
        location.removeOccupant(new DisasterVictim("sara", "2020-02-02"));
        // Expecting to throw Illegal Argument Exception when running this test.
    }

    /** Testing whether the method addSupply adds a new Supply object to the location */
    @Test 
    public void testAddSupply() {
        Supply supply3 = new Supply("Banana", 15);
        location.addSupply(supply3);
        assertTrue("The method addSupply did not properly add the supplies to the location.", location.getSupplies().contains(supply3));
    }

    /** Testing whether the method removeSupply correctly removes a supply object. */
    @Test
    public void testRemoveSupplyWithEnoughSupply() {
        Supply newSupply = new Supply("Pop", 4);
        location.addSupply(newSupply);
        location.removeSupply(newSupply);
        boolean check = false;
        for (Supply item : location.getSupplies()) {
            if ((item.getType().equals("Pop")) && (item.getQuantity() == 4)) {
                check = true;
            }
        }
        assertFalse("The method removeSupply was not able to properly delete this supply from the location.", check);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testRemoveSupplyWithNotEnoughSupply() {
        Supply newSupply = new Supply("Pop", 4);
        Supply unwantedItem = new Supply("Pop", 10);
        location.addSupply(newSupply);
        location.removeSupply(unwantedItem);
        // Expecting to throw Illegal Argument Exception when running this test.
    }
}