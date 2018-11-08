// This file defines the class Customer,
// which includes a customer's information

public class Customer {
    int id;           // customer id
    boolean isCopy;   // flag for identifying if the customer is a copy
    int waitTime;
    int originalQueue;
    int currentQueue;
    int timeRemaining;
    
    
    public Customer() {
        id = -1;
        isCopy = false;
        waitTime = 0;
        originalQueue = -1;
        currentQueue = originalQueue;
        timeRemaining = 0;
    }
	
    public Customer(int id, int originalQueue) {
        this.id = id;
        this.originalQueue = originalQueue;
        this.currentQueue = originalQueue;
        isCopy = false;
        waitTime = 0;
        timeRemaining = 0;
    }
	
    public String toString() { // toString for customer data
        
        String str = "Customer: " + id +
		             "\nWait Time: " + waitTime + " ticks" +
                     "\nOrigianl Queue: " + originalQueue +
                     "\nCurrent  Queue: " + currentQueue;
		return str;
    }
    
}
