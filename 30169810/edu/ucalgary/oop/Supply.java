/**
 * @author Alyan Qureshi <a href="mailto:muhammad.qureshi4@ucalgary.ca">
 * muhammad.qureshi4@ucalgary.ca</a>
 * @version 1.4
 * @since 1.0
*/

package edu.ucalgary.oop;

public class Supply {
    private String type;
    private int quantity;

    /** Default constructor */
    public Supply(String type, int quantity) {
        this.type = type;
        this.quantity = quantity;
    }

    /** Getters and setters */
    public void setType(String type) { this.type = type; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String getType() { return this.type; }
    public int getQuantity() { return this.quantity; }
}