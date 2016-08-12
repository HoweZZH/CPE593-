import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class MyArrayList {
	private static final int DEFAULT_CAPACITY=10;
	private int theSize;
	private int[] theItems;
	
	public MyArrayList(){
		clear();
	}
	public void clear(){
		theSize =0;
		ensureCapacity(DEFAULT_CAPACITY);
	}
	
	public int size(){
		return theSize;
	}
	public boolean isEmpty(){
		return size()==0;
	}
	public void trimToSize()
	{
		ensureCapacity(size());
	}
	
	public int get(int idx){
		if(idx<0||idx>=size())
			throw new ArrayIndexOutOfBoundsException();
		return theItems[idx];
	}
	
	public int set(int idx, int newVal)
	{
		if(idx<0||idx>=size())
			throw new ArrayIndexOutOfBoundsException();
		int old=theItems[idx];
		theItems[idx]=newVal;
		return old;
	}
	
	public void ensureCapacity(int newCapacity){
		if(newCapacity<size())
			return;
		int []old =theItems;
		theItems=new int [newCapacity];
		for(int i=0;i<size();i++)
			theItems[i]=old[i];
	}
	
	public boolean add(int x){
		add(size(),x);
		return true;
	}
	public void add(int idx,int x){
		if(theItems.length==size())
			ensureCapacity(size()*2+1);
		for(int i=theSize;i>idx;i--)
			theItems[i]=theItems[i-1];
		theItems[idx]=x;
		
		theSize++;
	}
	
	public int remove(int idx){
		int removedItem=theItems[idx];
		for(int i=idx;i<size()-1;i++)
			theItems[i]=theItems[i+1];
		
		theSize--;
		return removedItem;
	} 
	public void AddFront(int[] list){
		for(int i:list){
			this.add(0,i);
		}
	}
	public void AddFront(List<Integer> list){
		for(int i=list.size()-1;i>=0;i--){
			this.add(0,list.get(i));
		}
	}
	public void AddBack(int[] list){
		for(int i:list){
			this.add(i);
		}
	}
	public void AddBack(List<Integer> list){
		for(int i:list){
			this.add(i);
		}
	}
	public void RemoveFront(int n){
		if(n>=this.size()){
			this.clear();
		}
		else{
			for(int i=n,j=0;i<theSize;i++,j++){
				theItems[j]=theItems[i];
			}
			theSize-=n;
		}		
	}
	public void RemoveBack(int n){
		if(n>=this.size()){
			this.clear();
		}
		else{
			theSize-=n;
		}
	}
	public void Output(){
		if(this.isEmpty()) return;
		for(int i=0;i<this.size();i++)
			System.out.print(this.get(i)+" ");
		System.out.println();
	}
	public static void judgeInstruc(String line,MyArrayList list){
		if(line.startsWith("ADD_FRONT")){
			String[] array=line.split("\\s+");
			List<Integer> inputarr=new ArrayList<>();
			for(int i=1;i<array.length;i++){
				int temp=Integer.parseInt(array[i]);
				inputarr.add(temp);
			}
			list.AddFront(inputarr);
		}
		else if(line.startsWith("ADD_BACK")){
			String[] array=line.split("\\s+");
			List<Integer> inputarr=new ArrayList<>();
			for(int i=1;i<array.length;i++){
				int temp=Integer.parseInt(array[i]);
				inputarr.add(temp);
			}
			list.AddBack(inputarr);
		}
		else if(line.startsWith("REMOVE_FRONT")){
			String[] array=line.split("\\s+");
			for(int i=1;i<array.length;i++){
				int n=Integer.parseInt(array[1]);
				list.RemoveFront(n);
			}
		}
		else if(line.startsWith("REMOVE_BACK")){
			String[] array=line.split("\\s+");
			for(int i=1;i<array.length;i++){
				int n=Integer.parseInt(array[1]);
				list.RemoveBack(n);
			}
		}
		else if(line.startsWith("OUTPUT")){
			list.Output();
		}
	}
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Scanner s=new Scanner(new FileReader("HW4a.txt"));
		MyArrayList list=new MyArrayList();
		while(s.hasNextLine()){
			String line =s.nextLine();
			judgeInstruc(line,list);
		}
		s.close();
	}

}
