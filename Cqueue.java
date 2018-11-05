// this is the file for define the class Cqueue (circular queue),
 
public class Cqueue {

    private Customer[] q;
    private int head, tail, size;
	private int currentSize;
	private Customer user;
   // private static int arrSize = 20;
    
    public Cqueue(){
        head = -1;
        tail = -1;
        size = 100;
        currentSize = 0;
    }

    Cqueue(int qSize){
        head = -1;
        tail = -1;
        size = qSize;
		currentSize = 0;
        q = new Customer[size];
    }

    public int getHead() {
        return head;
    }

    public int getTail() {
        return tail;
    }
    
	public int getCurrentSize() {
		return this.currentSize;
	}
	
    public Customer[] getQueue() {
        return q;
    }

    boolean isFull(){
        return currentSize == q.length;
    }

    boolean isEmpty(){       
        return currentSize ==0;
    }

    void insert(Customer user){
         
         if ( !this.isFull() ) {     // queue is not full
            
            tail = (tail + 1) % size;  // tail increase 1
            q[tail] = user;
            currentSize++;
            
            if ( head == -1 ){     // when insert first element
                head = 0;          // head and tail point first element in queue
            }
        }
        
    }
    
    
    Customer deleteElement(int index){
        
        if(this.isEmpty()){
            return null;
        }
        else{
            Customer deletedUser = new Customer();
            
            deletedUser = q[index];       // keep deleted element for return
            
            if ( index != tail ) {    // not last element in queue
               for (int i = index; i < tail; i++) {
                  q[i]= q[i+1];             // element replace by next element in queue
                  //System.out.println("index: " + index + ", " + q[index].getId() + ", ");
               }
            }
            
           // System.out.println( "1 tail: "+ tail + "id: " + q[tail].getId());
            q[tail]= null;
            this.tail = this.tail - 1;
           // System.out.println("head " + head);
            //System.out.println( "2 tail: "+ tail + "id: " + q[tail].getId());

            currentSize--;
            return deletedUser;
        }
    }
    
    Customer deleteTail(){
        
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
    
    Customer deleteHead(){
		
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
	
	void displayQueue(){
		
        if(this.isEmpty()){
            System.out.println("Empty queue");
        }
        else{
            
            for (int i = head; i <= tail; i++) { // print queue from head to tail
			    System.out.print( "id:" + q[i].getId() + "," +
				                  "wait time: " + q[i].getWaitTime() + "," +
								  "origianl queue:" + q[i].getOriginalQueue() + "," +
								  "current queue:" +q[i].getCurrentQueue() );
              //  System.out.print( "id:" + q[i].getId() + ", queue:" +q[i].getOriginalQueue() + ",  ");
			}
			System.out.println();
        }
    }
}
