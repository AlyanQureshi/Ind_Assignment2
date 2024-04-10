// LocationBasedReliefWorker.java

package edu.ucalgary.oop;

public interface LocationBasedReliefWorker extends ReliefWorker {
    public void orderSupplies(Location location);
    public void enterDisasterVictimInfo(Location location);
}