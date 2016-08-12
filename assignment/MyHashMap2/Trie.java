import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Trie {
    private TrieNode root;
    private boolean isTerminal;
    private int size;
    private static class TrieNode {
        // Initialize your data structure here.
        public TrieNode[] children;
        public boolean isWord;
        public TrieNode() {
            children = new TrieNode[26];    
        }
    }
    public Trie() {
    	size=0;
        root = new TrieNode();
    }

    // Inserts a word into the trie.
    public void insert(String word) {
        insert(word,root,0);
    }

    private void insert(String word, TrieNode root, int idx){
        if(idx==word.length()){
            root.isWord=true;
            this.size++;
            return;
        }      
        int index = word.charAt(idx)-'a';
        if(root.children[index]==null)
            root.children[index]=new TrieNode();
        insert(word, root.children[index], idx+1);
    }

    // Returns true if the word is in the trie.
    public boolean isWord(String word) {
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
    public boolean startWith(String prefix) {	
        isWord(prefix);
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
				insert(temp);
		}
		sc.close();
		System.out.println("build dictionary done!");
    }
    public void loadTest(String filename) throws FileNotFoundException{
    	Scanner sc=new Scanner(new FileReader(filename));
		while(sc.hasNextLine()){
			String temp=sc.nextLine();
			if(!temp.trim().isEmpty()&&isWord(temp)){
				System.out.format("%-30s true\n",temp);
			}else{
				System.out.format("%-30s false\n",temp);
			}
		}
		sc.close();
    }
    public static void main(String[] args) throws FileNotFoundException{
    	Trie dic=new Trie();
    	dic.load("dict.txt");
    	System.out.println("--------read hw8.dat--------------");
    	dic.loadTest("hw8.dat");
		//System.out.println(dic.size());
    }
}
