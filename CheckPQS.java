/**
 * David Liu
 * COSC 311
 * HW 12/11
 * FALL 2018
 */
public class CheckPQS {
	public static void main(String[] args) {
        int [] pqsElements = {5,5,6,8,7,1023,1023};       // number of elements in each queue
		int[][] pqs = {{1,2,3,4,5},{1,1,0,1,1},{1,5,1,2,5,6},{1,2,2,3,2,2,17,4},  //  array of 7 arrays for 7 queues
		               {1,2,2,0,2,2,17,4}, new int[1023], new int[1023]};
		for (int x = 5; x < pqs.length; x++)                     //fill numbers for 5th and 6th elements in pqs
			for (int i = 0, k = 0; i < 10; i++)
				for (int j = 0; j < Math.pow(2, i); j++, k+=1) 
					pqs[x][k] = (int) Math.pow(2, i); 
		pqs[6][500] = 1;
		for (int i = 0, n = 0; i < pqs.length; i++) {
			n = isPQ(pqs[i], pqsElements[i]-1);
			if (n == -1) System.out.println("valid");
			else System.out.println("invalid - fails at index " + n);
		}
	}
	public static int isPQ(int[] arr, int i) {  // isPQ return -1 if node is valid, else return index of invalid node
        if ( arr[0] == 0 ) return 0;
        if ( i >= 1 ) {
			int n = isPQ( arr, i-1);     // recursive from last child node to first child node (index 1)
			if ( n != -1 ) return n;     // find invalid node, return parent node index
			    if (arr[i] <= 0 ) return i;                                      
			    else if( arr[i] < arr[(i+1)/2-1] ) return (i+1)/2-1 ; // return index of parent  
	        }                                                         // i is index of child, (i+1)/2-1 is index of parent
        return -1;     // valid node
    }
}
