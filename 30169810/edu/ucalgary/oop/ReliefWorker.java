/**
 * @author Alyan Qureshi <a href="mailto:muhammad.qureshi4@ucalgary.ca">
 * muhammad.qureshi4@ucalgary.ca</a>
 * @version 2.5
 * @since 1.0
*/

package edu.ucalgary.oop;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public interface ReliefWorker {
    /** Contracts given by a general Relief Worker as a whole */
    public ArrayList<String> findDisasterVictims(HashSet<Location> locations, String name);
    public boolean findLocationVictim(Location location, Scanner scanner);
}