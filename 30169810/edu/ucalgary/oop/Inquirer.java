package edu.ucalgary.oop;

public class Inquirer {
    private final String FIRST_NAME;
    private final String LAST_NAME;
    private final String SERVICES_PHONE;

    public Inquirer(String firstName, String lastName, String phone) {
        this.FIRST_NAME = firstName;
        this.LAST_NAME = lastName;
        this.SERVICES_PHONE = phone;
    }

    public Inquirer(String firstName, String phone) {
        this.FIRST_NAME = firstName;
        this.LAST_NAME = null;
        this.SERVICES_PHONE = phone;
    }

    public String getFirstName() { return this.FIRST_NAME; }
    public String getLastName() { return this.LAST_NAME; }
    public String getServicesPhoneNum() { return this.SERVICES_PHONE; }

}