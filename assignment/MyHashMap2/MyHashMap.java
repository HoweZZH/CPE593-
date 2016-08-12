

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class MyHashMap{
	private static final int DEFAULT_TABLE_SIZE=16;
	private static final float LIMIT_FACTOR=0.75f;
	private List<Entry> [] bucket;
	private int currentSize;
	private int maxchecks;
	public MyHashMap()
	{
		this(DEFAULT_TABLE_SIZE);
	}
	/**
	 * Construct the hash table.
	 * @param size approximate table size.
	 */
	@SuppressWarnings("unchecked")
	public MyHashMap(int size){
		maxchecks=1;
		int capacity = 1;
		while (capacity < size)
			 capacity <<= 1;
		bucket=new LinkedList[capacity];
		for(int i=0;i<bucket.length;i++)
			bucket[i]=new LinkedList<Entry>();
	}
	
	/**
	 * Insert into the hash table. if the item is
	 * already present, then do nothing.
	 * @param x the item to insert.
	 */
	public void add(String key){
		List<Entry> EntryList = bucket[myhash(key)];
		int size=EntryList.size();
		Entry temp=new Entry(key,size+1);
		if(!EntryList.contains(temp)){
			this.maxchecks=Math.max(maxchecks, size+1);
			EntryList.add(0,temp);
			//Rehash; 
			if(++currentSize>bucket.length*LIMIT_FACTOR)
				rehash();
		}
	}
	/**
	 * Remove from the hash table.
	 * @param key the item to remove.
	 */
	public void remove(String key){
		List<Entry> EntryList=bucket[myhash(key)];
		Entry temp=new Entry(key,0);
		if(EntryList.contains(temp)){
			EntryList.remove(temp);
			currentSize--;
		}
	}
	/**
	 * Find an item in the hash table.
	 * @param key the item to search for.
	 * @return true if key is found. 
	 */
	public boolean contains(String key){
		List<Entry> whichList=bucket[myhash(key)];
		Entry temp=new Entry(key,0);
		return whichList.contains(temp);
	}
	/**
	 * Make the hash table logically empty.
	 */
	public void makeEmpty(){
		for(int i=0;i<bucket.length;i++)
			bucket[i].clear();
		currentSize=0;
	}
	/**
	 * return the words numbers in this map
	 * @return the words numbers in this map
	 */
	public int size(){
		return this.currentSize;
	}
	public int getMaxChecks(){
		return this.maxchecks;
	}
	public int[] values(){
		int[] res=new int[this.currentSize];
		for(int i=0,j=0;i<bucket.length;i++){
			if(bucket[i].size()!=0){
				for(Entry item:bucket[i]){
					res[j++]=item.getValue();
				}
			}
		}
		return res;
	}
	/**
	 * Doubles the size of the hash table and rehashes all the entries.
	 */
	@SuppressWarnings("unchecked")
	private void rehash(){
		List<Entry>[] oldBucket=bucket;
		//Create new double-sized, empty table
		bucket =new LinkedList[2*bucket.length];
		for(int i=0;i<bucket.length;i++)
			bucket[i]=new LinkedList<Entry>();
		//Copy table over
		currentSize=0;
		this.maxchecks=1;
		for(int i=0;i<oldBucket.length;i++)
			for(Entry item:oldBucket[i])
				this.add(item.key);
		
	}
	private int myhash(String key){
		int hashVal = hashCode(key);
		return hashVal&(bucket.length-1);
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
	
	private static class Entry{
		public boolean equals(Object rhs){
			return (rhs instanceof MyHashMap.Entry) && (key.equals(((Entry)rhs).key)); 
		}
		public int hashCode(){
			return key.hashCode();
		}
		public int getValue(){return value;}
		//public String getKey(){return key;}
		public Entry(String key,int value){this.key=key;this.value=value;}
		private String key;
		private int value;
		//Additional fields and methods	
	}
	public void load(String filename) throws FileNotFoundException{
		Scanner sc=new Scanner(new FileReader(filename));
		while(sc.hasNext()){
			String temp=sc.nextLine();
			if (!temp.trim().isEmpty())
				add(temp);
		}
		sc.close();
		System.out.println("build dictionary done!");
	}
	public void loadTest(String filename) throws FileNotFoundException{
		Scanner sc=new Scanner(new FileReader(filename));
		while(sc.hasNextLine()){
			String temp=sc.nextLine();
			if(!temp.trim().isEmpty()&&contains(temp)){
				System.out.format("%-30s true\n",temp);
			}else{
				System.out.format("%-30s false\n",temp);
			}
		}
		sc.close();
	}
	public static void display(MyHashMap dic){
		int[] values=dic.values();
		int[] checks=new int[dic.getMaxChecks()+1];
		for(int i=0;i<values.length;i++){
			int check=values[i];
			if(check>=11){
				check=11;
			}
				checks[check]++;
		}
		System.out.println("insert\t"+"count");
		int sum=0;
		for(int i=1;i<checks.length;i++){
			sum+=checks[i];
			if(checks[i]!=0)
				System.out.println(i+"\t"+checks[i]);
		}
		System.out.println("sum "+sum);
	}
	public static void main(String[] args) throws FileNotFoundException{
		MyHashMap dic=new MyHashMap();
		dic.load("dict.txt");
		System.out.println("----------read hw8.dat----------------");
		dic.loadTest("hw8.dat");
		System.out.println("-----------histogram----------------");
		display(dic); 
		System.out.println("words "+dic.size());
		
	}
}
