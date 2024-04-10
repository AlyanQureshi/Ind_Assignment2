/**
 * @author Alyan Qureshi <a href="mailto:muhammad.qureshi4@ucalgary.ca">
 * muhammad.qureshi4@ucalgary.ca</a>
 * @version 2.2
 * @since 1.0
*/

package edu.ucalgary.oop;

public interface LocationBasedReliefWorker extends ReliefWorker {
    /** Contracts developed by the location-based relief worker */
    public void orderSupplies(Location location);
    public void enterDisasterVictimInfo(Location location);
}