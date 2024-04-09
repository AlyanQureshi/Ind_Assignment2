// ReliefWorker.java

package edu.ucalgary.oop;
import java.util.ArrayList;

public interface ReliefWorker {
    public ArrayList<String> findDisasterVictims(HashSet<Location> locations, String name);
}