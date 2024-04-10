// ReliefWorker.java

package edu.ucalgary.oop;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public interface ReliefWorker {
    public ArrayList<String> findDisasterVictims(HashSet<Location> locations, String name);
    public boolean findLocationVictim(Location location, Scanner scanner);
}