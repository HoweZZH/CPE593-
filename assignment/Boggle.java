import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Scanner;


class Trie {
    public TrieNode root;
    private boolean isTerminal;
    private int size;
    public static class TrieNode {
        // Initialize your data structure here.
        public TrieNode[] children;
        public boolean isWord;
        public String word;
        public TrieNode() {
            children = new TrieNode[26*2+1];    
        }
    }
    public Trie() {
    	size=0;
        root = new TrieNode();
    }

    // Inserts a word into the trie.
    public void add(String word) {
        add(word,root,0);
    }

    private void add(String word, TrieNode root, int idx){
        if(idx==word.length()){
        	root.word=word;
            root.isWord=true;
            this.size++;
            return;
        }    
        int index = word.charAt(idx)-'A';
        if(word.charAt(idx)=='-'){
        	index=52;
        }
        else if(index>25){
        	index-=6;
        }
        if(root.children[index]==null)
            root.children[index]=new TrieNode();
        add(word, root.children[index], idx+1);
    }

    // Returns true if the word is in the trie.
    public boolean contains(String word) {
        return search(word, root, 0);         
    }

    public boolean search(String word, TrieNode root, int idx){
        if(idx==word.length()) {
            isTerminal = true;
            return root.isWord;
        }
        int index = word.charAt(idx)-'a';
        if(index<0||index>25||root.children[index]==null) {
            isTerminal = false;
            return false;
        }
        return  search(word,root.children[index],idx+1);
    }
    
    /**
     * @param prefix
     * @return true if there is a word in the trie that starts with the given prefix.
     */
    public boolean containsPrefix(String prefix) {	
        contains(prefix);
        return isTerminal;
    }
    public int size(){
    	return this.size;
    }
    public  void load(String filename) throws FileNotFoundException{
    	Scanner sc=new Scanner(new FileReader(filename));
		while(sc.hasNext()){
			String temp=sc.nextLine();
			if(!temp.trim().isEmpty())
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
}
    
public class Boggle {
	public void findWords(char[][] board,Trie dic,HashSet<String> set) {
	    for(int i = 0; i < board.length; i++) {
	        for(int j = 0; j < board[0].length; j++) {
	        	board[i][j]=Character.toLowerCase(board[i][j]);
	            dfs(board, i, j,dic.root,set);
	            board[i][j]=Character.toUpperCase(board[i][j]);
	        }
	    }
	    for(int i = 0; i < board.length; i++) {
	        for(int j = 0; j < board[0].length; j++) {
	        	board[i][j]=Character.toUpperCase(board[i][j]);
	            dfs(board, i, j,dic.root,set);
	            board[i][j]=Character.toLowerCase(board[i][j]);
	        }
	    }
	    return;
	}
	public void dfs(char[][] board, int i, int j, Trie.TrieNode p,HashSet<String> set) {
	    char c = board[i][j];
	    if(c == '#' || p.children[validIndex(c,p)] == null) return;
	    p = p.children[validIndex(c,p)];
	    if(p.word != null&&p.word.length()>=3&&!set.contains(p.word)) {   // found one
	        System.out.println(p.word+" is a word");
	        set.add(p.word);  // de-duplicate
	    }
	    board[i][j] = '#';
	    if(i > 0) 						dfs(board, i - 1, j ,p,set); 
	    if(j > 0) 						dfs(board, i, j - 1, p,set);
	    if(i < board.length - 1) 		dfs(board, i + 1, j, p,set); 
	    if(j < board[0].length - 1) 	dfs(board, i, j + 1, p,set);
	    
	    if(i>0&&j>0) 								dfs(board,i-1,j-1,p,set);
	    if(i>0&&j<board[0].length-1) 				dfs(board,i-1,j+1,p,set);
	    if(i<board.length-1&&j > 0) 				dfs(board,i+1,j-1,p,set);
	    if(i<board.length-1&&j < board[0].length-1) dfs(board,i+1,j+1,p,set);
	    board[i][j] = c;
	} 
	private  int validIndex(char c){
		int index = c-'A';
        if(c=='-'){
        	index=52;
        }
        else if(index>25){
        	index-=6;
        }
        return index;
	}
	private  int validIndex(char c,Trie.TrieNode p){
		int index = c-'A';
        if(c=='-'){
        	index=52;
        }
        else if(index>25){
        	index-=6;
        }
        if(index!=52&&p.children[index]==null){
        	if(index>25&&p.children[index-26]!=null){
        		index-=26;
        	}
        	else if(index<26&&p.children[index+26]!=null){
        		index+=26;
        	}
        }
        return index;
	}
	private  int[] validIndex1(char c,Trie.TrieNode p){
		int[] index=new int[2];//index[1] represent LowerCase, index[0] represent Uppercase
		int idx = c-'A';
        if(c=='-'){
        	index[0]=52;
        	index[1]=52;
        }
        else if(idx<=25){
        	index[0]=idx;//uppercase
        	index[1]=idx+26;//lowercase
        }
        else if(idx>25){
        	index[1]=idx-6;//lowercase
        	index[0]=index[1]-26;//upperc
        }
        return index;
	}
	public char[][] readBoggle(String filename) throws FileNotFoundException{
		Scanner sc=new Scanner(new FileReader(filename));
		int n=sc.nextInt();
		System.out.println(n);
		char[][] board=new char[n][n];
		for(int i=0;i<n;i++){
			String temp=sc.nextLine();
			while(temp.trim().isEmpty()){
				temp=sc.nextLine();
			}
			temp=temp.replaceAll(" ","");
			for(int j=0;j<n;j++){
				board[i][j]=temp.charAt(j);
				System.out.print(temp.charAt(j)+" ");
			}
			System.out.println();
		}
		return board;
	}
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Trie dic=new Trie();
    	System.out.println("--------read dict.txt--------------");
    	dic.load("dictionary.txt");
    	
    	Boggle boggle=new Boggle();
    	System.out.println("--------read boggle.dat--------------");
    	char[][] board=boggle.readBoggle("boggle2.dat");
    	System.out.println("--------Result--------------");
    	boggle.findWords(board,dic,new HashSet<String>());
	}

}
