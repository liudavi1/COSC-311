/**
 * David Liu
 * 
 * COSC 311
 * HW 09/06
 * FALL 2018
 */
import java.util.Scanner; 
public class RepeatArray
{
    // instance variables - replace the example below with your own
   // private int x;

    /**
     * Constructor for objects of class RepeatArray
     */
    public static int [] RepeatArray( int [] arr, int result)
    {
        int [] newArr = {};
        if (result >0){
             newArr = new int [arr.length *result];
        
       
            for (int i = 0; i < newArr.length; i++ ){ 
                  newArr[i] = arr[ i % arr.length];
         
                }
            }
        
            
        return newArr;
   }

    public static void printArray(int[] array) {
      
      for (int i = 0; i < array.length; i++)
         System.out.print(" " + array[i] + " ");
      //System.out.println();
      
   }
   public static void main(String [] args){
       Scanner sc = new Scanner(System.in);
       System.out.println("Enter amount of times to repeat array: ");
       int num = sc.nextInt();
       int [] arr = {1,2,3,4};
      
       
       //int[] b = RepeatArray(arr, 2);
       printArray(RepeatArray(arr,num));
       
       
    }
}
