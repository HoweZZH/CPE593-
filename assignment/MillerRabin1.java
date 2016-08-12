 
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MillerRabin
{
	 public static void main (String[] args) throws FileNotFoundException {
	        
	        List<Long> nums=new ArrayList<>();
	        	Scanner sc = new Scanner(new FileReader("F://Workspace_Java/CPE593/src/hw2.dat"));
	            
	            while (sc.hasNextLong()) {
	                long i = sc.nextLong();
	                nums.add(i);
	                //System.out.println(i);
	            }
	            sc.close();
	        
	        for(int i=0;i<nums.size();i++){ 
	        	
	        	if(isPrime(nums.get(i),5)){
	        		System.out.println(nums.get(i)+"\t"+"true");
	        	}else{
	        		System.out.println(nums.get(i)+"\t"+"false");
	        	}
	        }

	    }
    public static boolean isPrime(long n, int s)
    {
       
        if (n == 0 || n == 1)
            return false;
        
        if (n == 2)
            return true;
        
        if (n % 2 == 0)
            return false;
 
        long d = n - 1;
        long k=0;
        while (d % 2 == 0){
        	d /= 2;
        	k++;
        }
 
        Random rand = new Random();
        for (int i = 0; i < s; i++)
        {
            long r = Math.abs(rand.nextLong());            
            long a = r % (n - 1) + 1, temp = d;
            long mod = modPow(a, temp, n);
            if(mod==1||mod==n-1) //maybe true next WitnessLoop
            	continue;
           
            for(int j=0;j<k-1;j++)
            {
                mod = modPow(mod, 2, n);
                if(mod==1)return false; //composite
                if(mod==n-1){
                	break;       //maybe true next WitnessLoop
                }
            }
            if(mod==n-1) continue; //maybe true next WitnessLoop
            return false; //composite
        }
        return true;    //maybe true    
    }
   //Function to calculate (a ^ b) % c 
    private static long modPow(long base, long exponent, long modulus){
        if (base < 1 || exponent < 0 || modulus < 1)
            return -1;

       long result = 1;
        while (exponent > 0) {
           if ((exponent % 2) == 1) {
               result = (result * base) % modulus;
           }
           base = (base * base) % modulus;
           exponent = exponent / 2;
        }
        return result;
    }
    
   
   
}