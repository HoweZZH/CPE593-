package CPE593;

import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class MyQuickSort {
   private static final int CUTOFF = 20;
   private static int array[];
   private static int length;
   private static Random rand=new Random(0); //random generator
   private MyQuickSort(){}//not allowed to declare a object
   public static void quicksort(int[] inputArr) {
       if (inputArr == null || inputArr.length == 0) {
           return;
       }
       array = inputArr;
       length = inputArr.length;
       quickSort(0, length - 1);
   }
   private static void quickSort(int lowerIndex, int higherIndex) {
	   if (higherIndex-lowerIndex <= CUTOFF) {
           insertionSort(lowerIndex,higherIndex);
           return;
       }
       int i = lowerIndex;
       int j = higherIndex;
       int pivotIndex=lowerIndex+rand.nextInt(higherIndex-lowerIndex+1); //random pivot
       int pivot = array[pivotIndex];
       
       //swap random pivot with middle index
       swap(pivotIndex,lowerIndex+(higherIndex-lowerIndex)/2); 
       // Divide into two arrays
       while (i <= j) {
           while (array[i] < pivot) {
               i++;
           }
           while (array[j] > pivot) {
               j--;
           }
           if (i <= j) {
               swap(i, j);    
               i++;
               j--;
           }
       }
       // call quickSort() method recursively
       if (lowerIndex < j)
           quickSort(lowerIndex, j);
       if (i < higherIndex)
           quickSort(i, higherIndex);
   }

   private static void swap(int i, int j) {
       int temp = array[i];
       array[i] = array[j];
       array[j] = temp;
   }
   // sort from a[left] to a[right] using insertion sort
   private static void insertionSort(int left, int right) {
       for (int i = left; i <= right; i++)
           for (int j = i; j > left && array[j]<array[j-1]; j--)
               swap(j, j-1);
   }
   /**
    * Main Function Entry
    * @param args
 * @throws IOException 
    */
   public static void main(String[] args) throws IOException{
	   Scanner sc = new Scanner(new FileReader("hw3.dat"));
	   int n=sc.nextInt();
	   int[] arr=new int[n];
       for(int i=0;i<n;i++){
    	   arr[i]=sc.nextInt();
       }
       sc.close();
       
	   MyQuickSort.quicksort(arr);
	   for(int i=0;i<n;i++)
		   System.out.print(arr[i]+" ");
	   
	   
   }
}
