// This file defines class CircularQueue

public class CircularQueue {
    private Customer[] q;
    private int head, tail, size;
	private int currentSize;
    
    public CircularQueue() {      // default constructor
        head = -1;
		tail = -1;
        size = 10;
		currentSize = 0;
    }
    
    public CircularQueue(int qSize) { // overloaded constructor
        head = -1;
		tail = -1;
        size = qSize;
        currentSize =0;		
        q = new Customer[size];
    }
	
	int getQuereSize() {
		return currentSize;
	}
    
    public boolean isFull() {       // returns true if full
        return currentSize == q.length;
    }
    
    public boolean isEmpty() {      // returns true if empty
        return currentSize == 0;
    }
    
    public void insert(Customer client){ // inserts at tail
         
        if ( !this.isFull() ) {       // queue is not full
            
            tail = (tail + 1) % size;  // tail increase 1
            q[tail] = client;
            currentSize++;
            
            if ( head == -1 ){     // when insert first element
                head = 0;          // head and tail point first element in queue
            }
        }
        else {
			System.out.println("insert: queue is full");
		}
    }
  
    public Customer deleteTail(){
        
        if(this.isEmpty()){
            return null;
        }
        else{
            Customer deletedUser = new Customer();
            //System.out.println("tail " + tail);
            
            deletedUser = q[tail];              // keep deleted element for return
            q[tail] = null;                     // delete element from queue
            
            tail = (tail - 1) % size;           // head increase 1
            
            //System.out.println("head " + head);
            currentSize--;
            return deletedUser;
        }
    }
    
    public Customer delete(){
		
        if(this.isEmpty()){
            return null;
        }
        else{
			Customer deletedUser = new Customer();
            //System.out.println("head " + head);
			 
			deletedUser = q[head];              // keep deleted element for return
			q[head] = null;                     // delete element from queue
			
            head = (head + 1) % size;           // head increase 1
			 
			//System.out.println("head " + head);
			currentSize--;
            return deletedUser;
        }
    }
    
    public int countQueue() { // returns the number of elements in the queue        
            return currentSize;
    }
	
    public void incrementTicks() { // increments wait time of customers
        if (!isEmpty()) {
            //for (int i = head % size; i != (tail) % size; i = (i + 1) % size) {
			for ( int i = head % size; i <= tail % size; i++) {
                q[i].waitTime++;
                // System.out.print( " id " + q[i].id + ", waitTime " + q[i].waitTime +", ");
                // System.out.print( "i " + i + ",");
            }
		    //System.out.println();
        }
    }
	
	public void printQueue() { // for print queue 2
        if (!isEmpty()) {
		
		    for ( int i = head % size; i <= tail % size; i++) {
				 System.out.println(q[i] + " ");
               }
        }
        else
            System.out.println("queue 2: Empty Queue");
    }
    
	public void printQueue1() { // for print queue 1
	
	    int n = 0;
        if (!isEmpty()) {

		    for ( int i = head % size; i <= tail % size; i++) {
				
				  // find number of costomer who not moved to queue 2
				  if ( !q[i].isCopy ) {  
					 n++; 
				  }
            }
			
			System.out.println("queue 1: " + n + " customers in queue " );
			
		    for ( int i = head % size; i <= tail % size; i++) {
				  if ( !q[i].isCopy ) {
                     System.out.println(q[i] + " ");
				  }
            }
		}
        else {
			System.out.println("queue 1: " + n + " customers in queue " );
            System.out.println("queue 1: Empty Queue");
		}
    }

}
