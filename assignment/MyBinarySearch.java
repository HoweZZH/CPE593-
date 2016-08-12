package CPE593;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class MyBinarySearch {
	
	private static int count;
	public static int search(int[]a, int key){
			  int lo = 0, hi = a.length-1;
			  count=0;
			  while (hi- lo > 1) {
				  count++;
			    int mid = (lo + hi) / 2;
			    if (a[ mid ] > key)
			      hi = mid;
			    else if (a[ mid ] < key)
			      lo = mid;
			    else{
			    	System.out.println(key+" "+count);
			    	return mid;
			    }
			  }
			  System.out.println("NOT FOUND "+count);
			  return -1;
			}
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Scanner s=new Scanner(new FileReader("HW3.txt"));
		s.nextLine();
		while(s.hasNext()){
			int n=s.nextInt();
			int[] arr=new int[n];
			for(int i=0;i<n;i++){
				arr[i]=s.nextInt();
			}
			int key= s.nextInt();
			search(arr,key);
		}
		s.close();
		
	}

}
