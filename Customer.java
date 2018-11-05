// this file defines the class Customer

import java.util.Random;

public class Customer {

    private int id;
	private int originalQueue;
	private int currentQueue;
	private int waitTime;
	private int serviceTime;
	
    public Customer(){
		
        this.id = 0;
        this.waitTime = 0;
		this.originalQueue = 0;
		this.currentQueue = 0;
		setServiceTime();
		
    }
	
    public Customer(int newId, int newWaitTime, int newOriginalQueue, int newCurrentQueue){
        this.id = newId;
        this.waitTime = newWaitTime;
		this.originalQueue = newOriginalQueue;
		this.currentQueue = newCurrentQueue;
		setServiceTime();
    }

    public void setId(int newId){
        this.id = newId;
    }

	public void setWaitTime(int newWaitTime){
        this.waitTime = newWaitTime;
    }

	public void setServiceTime() {
		Random newRand = new Random();
		this.serviceTime = newRand.nextInt(5) + 1;   // randow number from 1 to 5
	}
	
	public void setOriginalQueue(int newOriginalQueue){
        this.originalQueue = newOriginalQueue;
    }
	
	public void setCurrentQueue(int newCurrentQueue){
        this.currentQueue = newCurrentQueue;
    }

	public int getId(){
        return this.id;
    }

	public int getWaitTime(){
        return this.waitTime;
    }
	
	public int getServiceTime() {
		return serviceTime;
	}

	
	public int getOriginalQueue(){
        return this.originalQueue;
    }
	
	public int getCurrentQueue(){
        return this.currentQueue;
    }
}
