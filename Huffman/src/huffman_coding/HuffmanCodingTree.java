package huffman_coding;

import java.util.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HuffmanCodingTree {
	private int sizeOfCompressed=0;
	private  ArrayList<HuffmanNode> myList;
	private PriorityQueue<HuffmanNode> myQueue; 
	private String myWord, source, destination, dic;
	private FileWriter myWriter;
	private FileReader myReader;
	private HuffmanNode rootNode;
	private HashMap<String, Character> dictionary = new HashMap<String, Character>();
	
	HuffmanCodingTree(){
		
	}
	HuffmanCodingTree(String source, String dest, String dic) throws IOException{
		myWord = "";
		this.source = source;
		this.destination = dest;
		this.dic = dic;
		myReader = new FileReader(this.source);
		int ascii;
		while((ascii = myReader.read()) != -1) {
				myWord += (char) ascii;
		}
		myReader.close();
		myList = new ArrayList<HuffmanNode>();
	}
	public void encode() throws IOException{
		createMyNodes();
		createMyTree();
		
		//Make compressed file
		myWriter = new FileWriter(this.destination);
		String encoded = "";
		for(int i=0; i<myWord.length(); i++) {
			HuffmanNode myNode = charExists(myWord.charAt(i));
			if(myNode != null)
				encoded += myNode.getCode();
		}
		this.sizeOfCompressed = encoded.length();
		myWriter.write(encoded);
		myWriter.close();
		
		// Make dictionary
		myWriter = new FileWriter(this.dic);
		String charAndCode="";
		for(int i= 0; i<myList.size(); i++) {
			charAndCode += (myList.get(i).getChar() + "|" + myList.get(i).getCode());
			if(i!=myList.size()-1)
				charAndCode += "|";
		}
		myWriter.write(charAndCode);
		myWriter.close();
		
	}
	public void decode(String source, String dest, String dic) throws IOException {
		// read dictionary
		myReader = new FileReader(dic);
		String word="";
		int letter, count=0;
		char myChar=' ';
		while((letter = myReader.read()) != -1) {
			if((char) letter == '|')
				count++;
			if(count%2!=0 && (char) letter == '|') {
				myChar = word.charAt(0);
				word="";
				continue;
			}else if(count%2==0 && (char) letter == '|'){
				dictionary.put(word, myChar);
				sizeOfCompressed += word.length();
				word="";
				continue;
			}
			word += (char) letter;
		}
		dictionary.put(word, myChar);
		myReader.close();
		
		// Make original Text
		myReader = new FileReader(source);
		myWriter = new FileWriter(dest);
		word="";
		myWord="";
		int counter=0;
		while((letter = myReader.read()) != -1) {
			counter++;
			 word += (char) letter;
			 if(dictionary.get(word) != null) {
				 myWord += dictionary.get(word);
				 word="";
			 }
		}
		sizeOfCompressed += counter;
		myWriter.write(myWord);
		myReader.close();
		myWriter.close();
	}
	private void createMyNodes() {
		HuffmanNode current;
		for(int i=0; i<myWord.length(); i++) {
			current = charExists(myWord.charAt(i));		// checks if the character has a node or not
			if(current != null)
				current.incrementCount();		// if has, then increment count
			else
				myList.add(new HuffmanNode(myWord.charAt(i), 1));	// if not, then create node with the char and set the count to one;
		}
		// setting the priority queue with the nodes in the list
		myQueue = new PriorityQueue<HuffmanNode>(myList.size(), new NodeCompartor());
		for(HuffmanNode node: myList)
			myQueue.add(node);
	}
	private void createMyTree() {
		HuffmanNode tempNode1 = null;
		HuffmanNode tempNode2 = null;
		while(!myQueue.isEmpty()) {
			if(tempNode1 == null)
				tempNode1 = myQueue.poll();
			else if(tempNode2 == null)
				tempNode2 = myQueue.poll();
			if(tempNode1 != null && tempNode2 != null) {
				myQueue.add(new HuffmanNode('?',tempNode1.getCount()+tempNode2.getCount(),tempNode1,tempNode2));
				tempNode1 = null;
				tempNode2 = null;
			}
		}
		rootNode = tempNode1;
		createCode(rootNode);
	}
	private void createCode(HuffmanNode root) {
		if(root.getLeft() != null)
			root.getLeft().setCode(root.getCode() + "0");
		if(root.getRight() != null)
			root.getRight().setCode(root.getCode() + "1");
		if(root.getLeft() == null)
			return;
		if(root.getRight() == null)
			return;
		
		createCode(root.getLeft());
		createCode(root.getRight());
		
	}

	private HuffmanNode charExists(char letter) {
		for(HuffmanNode node : myList) {
			if(node.getChar() == letter)
				return node;
		}
		return null;
	}
	public void printList() {
		for(HuffmanNode node : myList) 
			System.out.println(node.getChar() + ", " + node.getCount());
	}
	public void printBitsRatio() {
		System.out.println(myWord.length()*8 + "/" + (sizeOfCompressed + (dictionary.size()*8)) + "=" + (double)(myWord.length()*8) / sizeOfCompressed);
	}
}
