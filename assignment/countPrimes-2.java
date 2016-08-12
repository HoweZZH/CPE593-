import java.util.Scanner;

public class countPrime {
	
	
	public static void main(String[] args) {
		
		Scanner input=new Scanner(System.in);
		System.out.println("Enter the first number:");
		
		do{	
		long a=input.nextLong();
		System.out.println("Enter the second number:");
		long b=input.nextLong();
		
		int res = countPrimes(a,b);
		System.out.println("The number of primes between "+a+" and "+ b+" inclusive= "+res);
		
		System.out.println("  Enter the first number(Enter the next pair of number):");
		
		}while(input.hasNextLine());
		input.close();
	}
	
	 
public static int countPrimes(long a,long b){
	
	if(a<0)a=0;
	if(b <2) return 0; 
	int MAX= (int)Math.pow(10, 7);
	boolean[] composite = new boolean[MAX+1];
    
    	composite[0]=true;
    	composite[1]=true;
    
    // first mark off even numbers
    for(int i = 4; i<=MAX;i+=2)
        composite[i] = true;
   // mark off other non-primes, skip the even numbers
    for(int i=3; i*i <=(int)Math.pow(10, 7); i++) {
        if(!composite[i]){
            for(int j = i*i; j <=MAX; j += 2*i) {
                composite[j] = true;
            }       
        }
    }  
    
    int count=0; 
    //if a<b<10^7
    if(b<=MAX){
    	 for(long i=a;i<=b;i++){
    	        if(!composite[(int)i]) count++;
    	    }
    }
    //if b>10^7
    else {
    	
    	int len=(int)(b-a);
    	boolean[] composite2=new boolean[len+1];
    	
    	if(a==0||a==1){
    		composite[0]=true;
    		composite[1]=true;
    	}
    	// first mark off even numbers
    	for(long i=a/2;2*i<=b;i++){
    		if(2*i<a)continue;
    		composite2[(int)(2*i-a)]=true;
    		
    	}
        		
    	
    	// mark off other non-primes
    	for(long i=3;i*i<=b;i++){
    		if(!composite[(int)i]){
    			long n=a/i;
    			for(long j =n; i*j<=b; j++) {
    				if(i*j<a)continue;
                    composite2[(int)(i*j-a)] = true;
                    
                }
    		}
    	}
    	//if a<10^7 && b>10^7
    	
    	for(int i=0;i<=len;i++)
    		if(!composite2[i]) count++;  
    		
    }
    
    return count;	
}

}
