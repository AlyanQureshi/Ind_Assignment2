package edu.ucalgary.oop;

import org.junit.*;
import static org.junit.Assert.*;

public class SupplyTest  {

    public SupplyTest() {}

    // Testing Constructor for Supply
    @Test
    public void testSupplyConstructor () {
        Supply item =  new Supply("Orange", 4);
        assertNotNull("Supply Constructor did not create a new object for 4 oranges.", item);
    }

    // Testing setType 
    @Test
    public void testSupplySetType() {
        Supply item =  new Supply("Orange", 4);
        item.setType("Apple");
        String actualType = item.getType();
        String expectedType = "Apple";
        assertEquals("Method setType did not return the expected type.", expectedType, actualType);
    }

    // Testing getType
    @Test
    public void testSupplyGetType() {
        Supply item = new Supply("Pear", 5);
        String actualType = item.getType()
        String expectedType = "Pear";
        assertEquals("Method getType did not return the expected type.", expectedType, actualType);
    }

    // testing setQuantity
    @Test
    public void testSupplySetQuantity() {
        Supply item =  new Supply("Orange", 4);
        item.setQuantity(2);
        String actualQuantity = item.getQuantity();
        String expectedQuantity = 2;
        assertEquals("Method setQuantity did not return the expected quantity.", expectedQuantity, actualQuantity);
    }

    // Testing getQuantity
    @Test
    public void testSupplyGetQuantity() {
        Supply item =  new Supply("Orange", 4);
        String actualQuantity = item.getQuantity();
        String expectedQuantity = 4;
        assertEquals("Method getQuantity did not return the expected quantity.", expectedQuantity, actualQuantity);
    }
}