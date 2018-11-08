// This file defines class Server

public class Server {
    
    Customer client;
    int timeRemaining;   // remaining time that server need to work for client
    
    public Server() {
        client = null;      // clinet=null means server idle 
        timeRemaining = 0;
    }
    
    public Server(Customer newClient, int timeRemaining) {
        this.client = newClient;    // means server is servring a customer (newClient)
        this.timeRemaining = timeRemaining;
    }
}