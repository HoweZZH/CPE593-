import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class MyLinkedList {
	private int theSize;
	private Node beginMarker;
	private Node endMarker;
	
	private static class Node{
		public Integer val;
		public Node prev;
		public Node next;
		
		public Node(Integer d, Node p, Node n){
			val=d; prev=p; next=n;
		}
	}
	public MyLinkedList(){
		clear();
	}
	
	// Change the size of this collection to zero
	public void clear(){
		beginMarker=new Node(null,null,null);
		endMarker=new Node(null,beginMarker,null);
		beginMarker.next=endMarker;
		theSize=0;
	}
	public int size(){
		return theSize;
	}
	public boolean isEmpty(){
		return size()==0;
	}
	
	public void addFront(int x){
		Node newNode=new Node(x,beginMarker,beginMarker.next);
		beginMarker.next.prev=newNode;
		beginMarker.next=newNode;
		theSize++;
	}
	public void addFront(MyLinkedList newlist){
		Node temphead=newlist.beginMarker.next;
		Node tempend=newlist.endMarker.prev;
		
		this.beginMarker.next.prev=tempend;
		tempend.next=this.beginMarker.next;
		this.beginMarker.next=temphead;
		temphead.prev=this.beginMarker;
		
		this.theSize+=newlist.size();
		
	}
	public void addBack(int x){
		Node newNode=new Node(x,endMarker.prev,endMarker);
		endMarker.prev.next=newNode;
		endMarker.prev=newNode;
		theSize++;
	}
	public void addBack(MyLinkedList newlist){
		Node temphead=newlist.beginMarker.next;
		Node tempend=newlist.endMarker.prev;
		this.endMarker.prev.next=temphead;
		temphead.prev=this.endMarker.prev;
		tempend.next=this.endMarker;
		this.endMarker.prev=tempend;
		
		this.theSize+=newlist.size();
	}
	public Integer removeFront(){
		if(this.theSize==0)return -1;
		Node p = this.beginMarker.next;//node to be removed
		p.next.prev = p.prev;
		p.prev.next=p.next;
		theSize--;
		return p.val; 
	}
	public Integer removeBack(){
		if(this.theSize==0)return -1;
		Node temp= endMarker.prev;
		temp.next.prev=temp.prev;
		temp.prev.next=temp.next;
		theSize--;
		return temp.val;
	}
	public void removeFront(int num){
		for(int i=0;i<num;i++){
			removeFront();
		}
	}
	public void removeBack(int num){
		for(int i=0;i<num;i++){
			removeBack();
		}
	}
	public void output(){
		Node temp = this.beginMarker.next;
		while(temp.next!=null){
			System.out.print(temp.val+" ");
			temp=temp.next;
		}
		System.out.println();
	}
	public static void judgeInstruc(String line, MyLinkedList list){
		if(line.startsWith("ADD_FRONT")){
			String[] array=line.split("\\s+");
			MyLinkedList inputlist=new MyLinkedList();
			for(int i=1;i<array.length;i++){
				int temp=Integer.parseInt(array[i]);
				inputlist.addBack(temp);
			}
			list.addFront(inputlist);
		}
		else if(line.startsWith("ADD_BACK")){
			String[] array=line.split("\\s+");
			MyLinkedList inputlist=new MyLinkedList();
			for(int i=1;i<array.length;i++){
				int temp=Integer.parseInt(array[i]);
				inputlist.addBack(temp);    
			}
			list.addBack(inputlist);
		}
		else if(line.startsWith("REMOVE_FRONT")){
			String[] array=line.split("\\s+");
			for(int i=1;i<array.length;i++){
				int n=Integer.parseInt(array[1]);
				list.removeFront(n);
			}
		} 
		else if(line.startsWith("REMOVE_BACK")){
			String[] array=line.split("\\s+");
			for(int i=1;i<array.length;i++){
				int n=Integer.parseInt(array[1]);
				list.removeBack(n);
			}
		}
		else if(line.startsWith("OUTPUT")){
			list.output();
		}
	}

	
	public static void main(String [] args) throws FileNotFoundException{
		
		Scanner s=new Scanner(new FileReader("HW4b.txt"));
		MyLinkedList list=new MyLinkedList();
		while(s.hasNextLine()){
			String line =s.nextLine();
			judgeInstruc(line,list);
		}
		s.close();
	}

}
