/**
 * @author Alyan Qureshi <a href="mailto:muhammad.qureshi4@ucalgary.ca">
 * muhammad.qureshi4@ucalgary.ca</a>
 * @version 1.2
 * @since 1.0
*/

package edu.ucalgary.oop;

public interface CentralReliefWorker extends ReliefWorker {
    /** Contract developed by the Central relief worker */
    public void logInquirerQuery(Inquirer newInquirer, String date, String infoProvided);
}