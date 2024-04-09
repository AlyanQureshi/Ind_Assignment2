// CentralReliefWorker.java

package edu.ucalgary.oop;

public interface CentralReliefWorker extends ReliefWorker {
    public void logInquirerQuery(Inquirer newInquirer, String date, String infoProvided);
}

/*SELECT MAX(id) from inquirer;*/
/*
INSERT INTO inquirer (firstname, lastname, phonenumber) VALUES 
('bob', 'builder', '221-121-8772');
*/

//SELECT * FROM inquirer;