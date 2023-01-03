package compression;

public class Tag {
	private int position;
	private int length;
	private char next;
	
	public Tag(int pos, int len, char next) {
		this.position = pos;
		this.length = len;
		this.next = next;
	}
	
	public char getNext() {
		return this.next;
	}
	public int getPosition() {
		return this.position;
	}
	public int getLength() {
		return this.length;
	}
	
	public String toString() {
		return "<" + position + ", " + length + ", " + next + ">";
	}
}
