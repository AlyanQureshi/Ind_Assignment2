// CentralReliefWorker.java

package edu.ucalgary.oop;

public interface CentralReliefWorker extends ReliefWorker {
    public void logInquirerQuery(Inquirer newInquirer, String date, String infoProvided);
}