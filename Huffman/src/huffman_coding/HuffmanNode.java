package huffman_coding;

public class HuffmanNode {
	private char element;
	private int count;
	private String code;
	private HuffmanNode left;
	private HuffmanNode right;
	
	HuffmanNode(char ele, int count){
		this.element = ele;
		this.count = count;
		this.code = "";
	}
	HuffmanNode(char ele, int count, HuffmanNode l, HuffmanNode r){
		this.element = ele;
		this.count = count;
		this.code = "";
		this.left = l;
		this.right = r;
	}
	
	public void incrementCount() {
		this.count++;
	}
	public void setRight(HuffmanNode r) {
		this.right = r;
	}
	public void setLeft(HuffmanNode l) {
		this.left = l;
	}
	public int getCount() {
		return this.count;
	}
	public char getChar() {
		return this.element;
	}
	public HuffmanNode getRight() {
		return this.right;
	}
	public HuffmanNode getLeft() {
		return this.left;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCode() {
		return this.code;
	}
}
