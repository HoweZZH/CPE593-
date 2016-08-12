package CPE593;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class MyHashMap {
	
	private static final int DEFAULT_TABLE_SIZE=11;
	private HashEntry[] array;
	private int currentSize;
	private int size;
	private int maxChecks=1;
	public MyHashMap(){
		this(DEFAULT_TABLE_SIZE);
	}
	public MyHashMap(int size){
		allocateArray(size);
		makeEmpty();
	}
	/**
	 * Make the hash table logically empty.
	 */
	public void makeEmpty(){
		currentSize=0;
		this.size=0;
		for(int i=0;i<array.length;i++)
			array[i]=null;
	}
	
	public boolean contains(String key){
		int currentPos =findPos(key)[0];
		return isActive(currentPos);
	}
	public void put(String key){
		//Insert x as active
		int[] temp=findPos(key);
		int currentPos=temp[0];
		int count=temp[1];
		if(isActive(currentPos))
			return;
		array[currentPos]=new HashEntry(key,count,true);
		//Rehash;
		size++;
		if(++currentSize>array.length/2)
			rehash();
	}
	/**
	 * Removes the mapping for the specified key from this map if present.
	 * @param key key whose mapping is to be removed from the map
	 */
	public void remove(String key){
		int currentPos=findPos(key)[0];
		if(isActive(currentPos)){
			this.size--;
			array[currentPos].isActive=false;
		}
	}
	/**
	 * Returns the number of key-value mappings in this map.
	 * @return number of key-value mappings in this map.
	 */
	public int size(){
		return this.size;
	}
	
	public int[] values(){
		int[] res= new int[this.array.length];
		for(int i=0,j=0;i<this.array.length;i++){
			HashEntry temp=this.array[i];
			if(temp!=null){
				res[j]=temp.getCount();	
				j++;
			}
		}
		return res;
	}
	public int getMaxChecks(){
		return this.maxChecks;
	}
	private  static class HashEntry{
		public String key;
		public int count;
		public boolean isActive;
		public HashEntry(String key,int count,boolean j)
		{this.key =key; this.count=count;isActive =j;}
		private int getCount(){
			return count;
		}
	}
	private void allocateArray(int arraySize){
		array = new HashEntry[arraySize];
	}
	/**
	 * Return true if currentPos exists and is active
	 * @param currentPos
	 * @return true if currentPos exists and is active
	 */
	private boolean isActive(int currentPos){
		return array[currentPos]!=null && array[currentPos].isActive;
	}
	/**
	 * Method that performs linear probing resolution.
	 * @param x the item to search for.
	 * @return the position where the search terminates.
	 */
	private int[] findPos(String x){
		int[] currentPos=new int[2];
		currentPos[0]=myhash(x);
		currentPos[1]=1;
		while(array[currentPos[0]]!=null && !array[currentPos[0]].key.equals(x))
		{
			currentPos[0]++; //Compute ith probe
			currentPos[1]++;
			maxChecks=Math.max(currentPos[1], maxChecks);
			if(currentPos[0]>=array.length)
				currentPos[0]-=array.length;
		}
		return currentPos;
	}
	
	private void rehash(){
		HashEntry[]oldArray=array;
		//Create a new double-sized, empty table
		allocateArray(nextPrime(2*oldArray.length));
		currentSize=0;
		this.size=0;
		maxChecks=1;
		//Copy table over
		for(int i=0;i<oldArray.length;i++)
			if(oldArray[i]!=null&&oldArray[i].isActive)
				put(oldArray[i].key);
	}
	
	private int myhash(String x){
		int hashVal = hashCode(x);
		hashVal%=array.length;
		if(hashVal<0)
			hashVal+=array.length;
		return hashVal;
	}
	
	private static int hashCode(String value) {
        int hash = 0;
        if (hash == 0 && value.length() > 0) {
            char val[] = value.toCharArray();
            for (int i = 0; i < value.length(); i++) {
                hash = 31 * hash + val[i];
            }
        }
        return hash;
    }
	private static int nextPrime(int n){
		for(int i=n;true;i++)
			if(isPrime(i))return i;
		
	}
	private static boolean isPrime(int num){
		if(num%2==0) return false;
		for(int i=3;i*i<num;i+=2){
			if(num%i==0) return false;
		}
		return true;
	}
	public static void buildDictionary(MyHashMap dic) throws FileNotFoundException{
		Scanner sc=new Scanner(new FileReader("dict.txt"));
		while(sc.hasNext()){
			String temp=sc.nextLine();
			if (!temp.trim().isEmpty())
				dic.put(temp);
		}
		sc.close();
		System.out.println("build dictionary done!");
	}
	public static void display(MyHashMap dic){
		int[] values=dic.values();
		int[] checks=new int[dic.getMaxChecks()+1];
		for(int i=0;i<values.length;i++){
			int check=values[i];
			if(check!=0) checks[check]++;
		}
		System.out.println("insert\t"+"count");
		int sum=0;
		for(int i=0;i<checks.length;i++){
			sum+=checks[i];
			if(checks[i]!=0)
				System.out.println(i+"\t"+checks[i]);
		}
		System.out.println("sum "+sum);
	}
	public static void main(String[] args) throws FileNotFoundException{
		MyHashMap dic=new MyHashMap();
		buildDictionary(dic);
		System.out.println("----------------------");
		Scanner sc=new Scanner(new FileReader("hw8.dat"));
		while(sc.hasNextLine()){
			String temp=sc.nextLine();
			if(!temp.trim().isEmpty()&&dic.contains(temp)){
				System.out.println(temp+"\t"+"true");
			}else{
				System.out.println(temp+"\t"+"false");
			}
		}
		sc.close();
		System.out.println("----------------------");
		display(dic);
		System.out.println("words "+dic.size());
		
	}
}
