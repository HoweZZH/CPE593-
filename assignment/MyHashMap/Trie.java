import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Trie {
    private TrieNode root;
    private boolean isTerminal;
    private int size;
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

    // Returns if the word is in the trie.
    public boolean search(String word) {
        return search(word, root, 0);         
    }

    public boolean search(String word, TrieNode root, int idx){
        if(idx==word.length()) {
            isTerminal = true;
            return root.isWord;
        }
        int index = word.charAt(idx)-'a';
        if(root.children[index]==null) {
            isTerminal = false;
            return false;
        }

        return  search(word,root.children[index],idx+1);
    }
    private static class TrieNode {
        // Initialize your data structure here.
        public TrieNode[] children;
        public boolean isWord;
        public TrieNode() {
            children = new TrieNode[26];    
        }
    }
    /**
     * @param prefix
     * @return true if there is a word in the trie that starts with the given prefix.
     */
    public boolean isPrefix(String prefix) {	
        search(prefix);
        return isTerminal;
    }
    public int size(){
    	return this.size;
    }
    public static void buildDictionary(Trie dic) throws FileNotFoundException{
    	Scanner sc=new Scanner(new FileReader("dict.txt"));
		while(sc.hasNext()){
			String temp=sc.nextLine();
			dic.insert(temp);
		}
		sc.close();
		System.out.println("build dictionary done!");
    }
    public static void main(String[] args) throws FileNotFoundException{
    	Trie dic=new Trie();
    	buildDictionary(dic);
    	System.out.println("----------------------");
    	//System.out.println(dic.size());
    	Scanner sc=new Scanner(new FileReader("hw8.dat"));
		while(sc.hasNextLine()){
			String temp=sc.nextLine();
			if(dic.search(temp)){
				System.out.println(temp+"\t"+"true");
			}else{
				System.out.println(temp+"\t"+"false");
			}
		}
		sc.close();
    }
}
