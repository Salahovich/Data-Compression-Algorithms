package compression;

import java.util.ArrayList;

public class LZ78 {
	private ArrayList<TagV2> myTags = new ArrayList<TagV2>();
	private ArrayList<String> dictionary = new ArrayList<String>();
	private String target;
	
	public LZ78(String str) {
		target = str;
		dictionary.add("");
	}
	
	private int exists(String pattern, int searchBuffer) {
		for(String index : dictionary) {
			if(index.equals(pattern))
				return dictionary.indexOf(index);
		}
		return -1;
	}
	
	public ArrayList<TagV2> encode(){
		String pattern="";
		int j=0, index=0;
		for(int i=0; i<target.length(); i++) {
			pattern += target.charAt(i);
			if(exists(pattern, j) == -1) {
				dictionary.add(pattern);
				myTags.add(new TagV2(index, target.charAt(i)));
				j=i+1;
				pattern="";
			}
			else {
				index = exists(pattern, j);
				if(i == target.length()-1)
					myTags.add(new TagV2(index, ' '));
			}
		}
		return myTags;
	}
	
	public String decode() {
		dictionary.clear();
		dictionary.add("");
		String deCompressed = "", temp="";
		for(TagV2 tag : myTags) {
			temp += dictionary.get(tag.getPosition()) + tag.getNext();
			dictionary.add(temp);
			deCompressed += temp;
			temp = "";
		}
		return deCompressed;
	}
	public void displayTags() {
		for(TagV2 tag : myTags) {
			System.out.println(tag);
		}
	}
}
