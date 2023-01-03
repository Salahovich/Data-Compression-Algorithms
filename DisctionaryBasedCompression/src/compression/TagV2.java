package compression;

public class TagV2 {
	private int position;
	private char next;
	
	public TagV2(int pos, char next) {
		this.position = pos;
		this.next = next;
	}
	
	public char getNext() {
		return this.next;
	}
	public int getPosition() {
		return this.position;
	}
	
	public String toString() {
		return "<" + position + "," + next + ">";
	}
}
