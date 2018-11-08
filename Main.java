// Program to simulate two queues with two servers

import java.util.*;

public class Main {
    
    public static int getPoissonRandom(double mean) {
        Random r = new Random();
        double L = Math.exp(-mean);
        int k = 0;
        double p = 1.0;
        do {
            p = p * r.nextDouble();
            k++;
        } while (p > L);
        return k - 1;
    }
    
    public static void main(String[] args) {
        CircularQueue q1 = new CircularQueue(50);
        CircularQueue q2 = new CircularQueue(50);
        Server server1 = new Server(null, 0);
        Server server2 = new Server(null, 0);
        Random numberMaker = new Random();

        int clientId = 1;
   
        for (int tick = 1, serviced = 0; serviced < 5; tick++) {
			
            System.out.println("tick " + tick);
            // remove clinet who completed service
            if (server1.client != null) {
                if (server1.timeRemaining <= 0) {   // server finished
                    System.out.println("queue 1: server 1 ends service "
                                       + "on customer " + server1.client.id);
                    server1.client = null;
                } else
                    server1.timeRemaining--;    // decrease timeRemaining in each tick loop
            }
            
            if (server2.client != null) {
                if (server2.timeRemaining <= 0) {
                    System.out.println("queue 2: server 2 ends service "
                                       + "on customer " + server2.client.id);
                    server2.client = null;
                } else
                    server2.timeRemaining--;
            }
          
           // update any measurements of those in queue;
            q1.incrementTicks();
            q2.incrementTicks();
                
            // For queue 2,
            int arrivals2 = getPoissonRandom(0.25);       // get customers number who arrival queue 2
        
            for (int i = 0; i < arrivals2; i++) {
                Customer newClient = new Customer(clientId, 2);  // creat new customer for queue 2
                q2.insert( newClient );                         // insert to queue 2
                clientId++;
            }
        
            // For queue 1,
            int arrivals1 = getPoissonRandom(0.25);
        
            for (int i = 0; i < arrivals1 ; i++) {
 
                q1.insert(new Customer(clientId, 1));     // creat new customer, insert to queue 1
                
                int choice = numberMaker.nextInt(2);      // choice get 0 or 1, 50/50 customer switches queue
                if (choice == 1) {                        // choice to switch to queue 1
				//System.out.println(" id " + clientId);
                    Customer deletedCustomer = q1.deleteTail();      // delete last client from queue 1.
					deletedCustomer.currentQueue = 2;
                    deletedCustomer.isCopy = true;                  // flag last client as removed
                    q2.insert(deletedCustomer);                      // moves last client to queue 2
					Customer copiedCustomer = new Customer(clientId, 1); // make a copy of removed customer
					copiedCustomer.isCopy = true;
                    q1.insert(copiedCustomer);                      // insert copiedCustomer back to queue 1
                    System.out.println("queue 1: customer " + deletedCustomer.id + " switched to queue 2");
                }
                clientId++;				
            }
                                   
            // assign customer(s) to idle server(s);
            if (server1.client == null) {                   // if server is idle (no customer)

                // remove customer before server start, so it's waitTime not increased when server start
                Customer clientServing1 = q1.delete(); 
               			   
                    if (clientServing1 != null) {                             
                     
                        int serviceTime = numberMaker.nextInt(5) + 1;  // get random number from 1 to 5
                   
                        // begin service for queue 1
                        server1 = new Server(clientServing1, serviceTime);  // server1 begin service with service time

                        if ( !clientServing1.isCopy ) {        // client not switched to queue 2
					   
                           System.out.println("queue 1: Server 1 start service "
                                           + "on customer " + server1.client.id);
                           System.out.println("queue 1: customer " + server1.client.id + " waited "
                                           +  server1.client.waitTime + " ticks to be serviced");
                        }
                        else {            // client has moved to queue 2
                            System.out.println("queue 1: Customer " + server1.client.id
                                           + " (moved to queue 2) would have taken " + server1.client.waitTime
                                           + " ticks to be serviced if it had not switched to queue 2");
                            server1.client = null; // remove client (customer)
                        }
                    } 
            }
            
            if (server2.client == null) {                      // if server 2 is idle

                Customer clientServing2 = q2.delete();
                // begin service for queue 2
                if (clientServing2 != null) {
                    server2 = new Server(clientServing2, numberMaker.nextInt(5) + 1);
                    System.out.println("queue 2: Server 2 starts service "
                                       + "on customer " + server2.client.id);
                    System.out.println("queue 2: customer " + server2.client.id + " waited "
                                       + server2.client.waitTime + " ticks to be serviced");
                    if (clientServing2.originalQueue == 1)
                        serviced++;
                }
            }
			
			if ( serviced == 5 ) {
				if (server1.client == null) {                   // if server is idle (no customer)
				   System.out.println("\nqueue 1: server idle");
			    }
			    else {
				   System.out.println("\nqueue 1: server busy");
                }
                q1.printQueue1();  // print queue 1
				if (server2.client == null) {                  // if server is idle (no customer)
				   System.out.println("\nqueue 2: server idle");
			    }
				else {
				   System.out.println("\nqueue 2: server busy");
			    }
			    System.out.println("queue 2: " + q2.getQuereSize() + " customers in queue " );
                q2.printQueue();   // print queue 2
			}
        }
    }
}
    




