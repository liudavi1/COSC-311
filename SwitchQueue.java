// this is the main driver to run the simlate servers

import java.util.Random;

public class SwitchQueue{
	
	private static int getPoissonRandom(double mean) {
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

	// get total service time for a queue
    private static int getTotalServiceTime(Cqueue cq) {
		int totalServiceTime = 0;
		
		if ( !cq.isEmpty() ) {
		   for (int i=cq.getHead(); i<=cq.getTail(); i++) {
		      totalServiceTime = totalServiceTime + cq.getQueue()[i].getServiceTime();
		   }
		}
		return totalServiceTime;
	}

	// get wait time for last customer in queue
    private static int getWaitTime(Cqueue cq, int tail) {
		int waitTime = 0;
		
		if ( !cq.isEmpty() ) {
		   for (int i=cq.getHead(); i<=tail; i++) {
		      waitTime = waitTime + cq.getQueue()[i].getServiceTime();
		   }
		}
		return waitTime;
	}
	
	// update wait time for each customer in queue
	private static void updateWaitTime(Cqueue cq) {
		
        int waitTime = 0;
		
		if ( !cq.isEmpty() ) {
		   for (int i=cq.getHead(); i<=cq.getTail(); i++) {
			  cq.getQueue()[i].setWaitTime(waitTime);
		      waitTime = waitTime + cq.getQueue()[i].getServiceTime();
		   }
		}

	}

    public static void main (String [] args) {
        int size = 2000000;
        
        boolean done = false;
		
        Cqueue cq1 = new Cqueue(size);
        Cqueue cq2 = new Cqueue(size);
        
		int queue1WaitTime = 0;
		int queue2WaitTime = 0;
		
        int user_id = 1;
        int tick = 1;
		int clock1 = 0;
		int clock2 = 0;
		boolean server1Begin = false;
		boolean server2Begin = false;
		int numberOfSwitch = 0;
		
        while ( !done ) {
			
            if ( !cq1.isEmpty() ) {
                
              //  int head = cq1.getHead();
              //  int sTime = cq1.getQueue()[head].getServiceTime();
                
                // System.out.println("clock1: " + clock1 + ", tick: " + tick );
                if ( server1Begin && clock1 == tick) {
                    //System.out.print("before: "); cq1.displayQueue();
                    Customer deletedUser1 = cq1.deleteHead();               // remove completed customer
                    updateWaitTime(cq1);
                    server1Begin = false;
                    // System.out.print("after: "); cq1.displayQueue();
                }
            }

            if ( !cq2.isEmpty() ) {
                
               // int head = cq2.getHead();
               // int sTime = cq2.getQueue()[head].getServiceTime();
                
                //System.out.println("clock2: " + clock2 + ", tick: " + tick );
                if ( server2Begin && clock2 == tick) {
                    //System.out.print("before: "); cq2.displayQueue();
                    Customer deletedUser2 = cq2.deleteHead();          // remove completed customer
                    updateWaitTime(cq2);
                    server2Begin = false;
                    
                    if ( deletedUser2.getOriginalQueue() == 1 ) {
                        numberOfSwitch++;     // number of customer switched and finished
                    }
                }
            }
			int customerToQueue1 = getPoissonRandom(0.25);
            
			//System.out.println("queue 1, arrival: " + customerToQueue1);
			
			for ( int q1=0; q1<customerToQueue1; q1++) {
				queue1WaitTime = getWaitTime(cq1, cq1.getTail());
				Customer user1 = new Customer(user_id,queue1WaitTime,1,1); // creat a customer object for queue 1
				user_id++;
				cq1.insert(user1);   // add new customer to queue 1
			}
			//cq1.displayQueue();
			int customerToQueue2 = getPoissonRandom(0.25);
			
			//System.out.println("queue 2, arrival: " + customerToQueue2);
			
			for ( int q2=0; q2<customerToQueue2; q2++) {
				queue2WaitTime = getWaitTime(cq2, cq2.getTail());
				Customer user2 = new Customer(user_id,queue2WaitTime,2,2); // creat a customer object for queue 2
				user_id++;
				cq2.insert(user2);  // add new customer to queue 2
			}
            			
            if ( !cq1.isEmpty() ) {				
               int totalServiceTime = 0;  // total time for queue 2
					
               for (int h = cq1.getHead(); h < cq1.getTail(); h++ ) {
                  totalServiceTime = getTotalServiceTime(cq2);
					    
                  if ( cq1.getQueue()[h].getWaitTime() > totalServiceTime  ) {
                     System.out.print("*** Customer id " + cq1.getQueue()[h].getId() + ": ");
					 System.out.print("need wait " + cq1.getQueue()[h].getWaitTime() + " tick without switch,");
					 System.out.println(" need wait " + totalServiceTime + " tick with switch");
                              
                     Customer deletedUser1 = cq1.deleteElement(h);      // delete a customer from queue 1
					 deletedUser1.setCurrentQueue(2);          // set current queue to queue 2
					 cq2.insert(deletedUser1);                 // insert customer to queue 2
					 h--;   // after delete element at index h, recheck element at h in new queue
					 updateWaitTime(cq1);   // after delele a customer, update waiting time in queue
                  }
               }
            }
			
            
            //// section AA: begin to move last customer in queue 1 to queue 2 (50% possibility)
            
			if ( !cq1.isEmpty() && cq1.getTail() != 0 ) {        // customer in end of the queue 1
               int totalServiceTime = getTotalServiceTime(cq2);
			   
                if (Math.random() < 0.5) {                       // 50% possibility
                   System.out.print("*** last customer id " + cq1.getQueue()[cq1.getTail()].getId() + ": ");
                   System.out.print("need wait " + cq1.getQueue()[cq1.getTail()].getWaitTime() + " tick without switch,");
                   System.out.println(" need wait " + totalServiceTime + " tick with switch");
                   
                   Customer deletedUser1 = cq1.deleteTail();   // delete last customer from queue 1
				   cq2.insert(deletedUser1);                   // insert deleted customer to queue 2
			   }
			}
            //// end of section AA
            
        
			
			// begin server 1
            if ( cq1.isEmpty() ) {
				System.out.println("server 1 is idle");
			}
			else {				

				if ( !server1Begin ) {
                  int head = cq1.getHead();
                  int sTime = cq1.getQueue()[head].getServiceTime();

				   clock1 = tick + sTime;
				   System.out.println("tick " + tick +": server 1 start service on customery " + cq1.getQueue()[head].getId());
				   server1Begin = true;
				}
				 
            }

			// begin server 2
            if ( cq2.isEmpty() ) {
				System.out.println("server 2 is idle");
			}
			else {

				if ( !server2Begin ) {
                    
                  int head = cq2.getHead();
                  int sTime = cq2.getQueue()[head].getServiceTime();

                   clock2 = tick + sTime;
				   System.out.println("tick " + tick +": server 2 start service on customery " + cq2.getQueue()[head].getId());
				   server2Begin = true;
				}
            }				
			tick++;			
			
            // 5 customers switch from queue 1 to queue 2 and finish the service will end the loop
        
			if (numberOfSwitch == 5) {
				done = true;
			}
		///////////////////// section for display demonstration ///////////////////////	
		
			System.out.println("tick " + tick);
			if ( server1Begin )
				System.out.println("queue 1: server busy");
			else
				System.out.println("queue 1: server idle");
			System.out.println("queue 1: " + cq1.getCurrentSize() + " customer in queue");
			System.out.print("queue 1: ");
			cq1.displayQueue();
				
			if ( server2Begin )
				System.out.println("queue 2: server busy");
			else
				System.out.println("queue 2: server idle");
			System.out.println("queue 2: " + cq2.getCurrentSize() + " customer in queue");
			System.out.print("queue 2: ");
			cq2.displayQueue();
		
	    ///////////////////////// end of display section ///////////////////////
				
		}  // end of while loop
    }
}