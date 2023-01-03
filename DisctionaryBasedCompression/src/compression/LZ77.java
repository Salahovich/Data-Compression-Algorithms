package compression;

import java.io.*;
import java.util.*;

public class LZ77 {

	private ArrayList<Tag> myTags = new ArrayList<Tag>();
	private String target = "";
	private FileReader myReader;
	private FileWriter myWriter;
	public int posBits, lenBits, nextBits = 8, overHead;

	public LZ77(String path) throws IOException {
		myReader = new FileReader(path);
		int letter;
		while ((letter = myReader.read()) != -1) {
			target += (char) letter;
		}
		myReader.close();
	}

	public int exists(String pattern, int searchBuffer) {
		int len = pattern.length(), j = 0;
		for (int i = 0; i < searchBuffer && j < len; i++) {
			if (target.charAt(i) != pattern.charAt(j)) // Do nothing if not equals
				continue;
			else if (target.charAt(i) == pattern.charAt(j) && len == 1) // one character case
				return i;
			else {
				j = 1;
				for (int m = i + 1; m < searchBuffer && j < len; m++) {
					if (target.charAt(m) != pattern.charAt(j)) {
						j = 0;
						break;
					} else if (target.charAt(m) == pattern.charAt(j) && j < len)
						j++;
				}
				if (j == len)
					return i;
			}
		}
		return -1;
	}

	public ArrayList<Tag> encode() throws IOException {
		int index = 0, patternLen = 0;
		String pattern = "";
		int i = 0, j = i;
		for (i = 0; i < target.length(); i++) {
			pattern += target.charAt(i);
			int searchBuffer = exists(pattern, j);
			if (searchBuffer == -1) {
				int back = (i - patternLen) - index;
				if (pattern.length() == 1) {
					back = 0;
					patternLen = 0;
				}
				Tag myTag = new Tag(back, patternLen, target.charAt(i));
				myTags.add(myTag);
				pattern = "";
				j = i + 1;
			} else {
				patternLen = pattern.length();
				index = searchBuffer;
				if (i == target.length() - 1) {
					myTags.add(new Tag((target.length() - patternLen) - index, patternLen - 1, target.charAt(i)));
				}
			}
		}
		return myTags;
	}

	private void convertToBin() {
		for (Tag myTag : myTags) {
			String binaryStream = "";
			String tempPos = "", tempLen = "", tempNext = "";
			tempPos = Integer.toBinaryString(myTag.getPosition());
			tempLen = Integer.toBinaryString(myTag.getLength());
			tempNext = Integer.toBinaryString((int) myTag.getNext());
			int diff1 = posBits - tempPos.length(), diff2 = lenBits - tempLen.length(),
			diff3 = nextBits - tempNext.length();
			overHead = diff1;
			while (diff1 > 0) {
				binaryStream += "0";
				diff1--;
			}
			binaryStream += tempPos;
			while(diff2 > 0) {
				binaryStream += "0";
				diff2--;
			}
			binaryStream += tempLen;
			while(diff3 > 0) {
				binaryStream += "0";
				diff3--;
			}
			binaryStream += tempNext;
		}
	}

	public void decode(ArrayList<Tag> myTags) {
		String deCompressed = "";
		for (int i = 0; i < myTags.size(); i++) {
			for (int j = deCompressed.length() - myTags.get(i).getPosition(), m = 0; m < myTags.get(i).getLength()
					&& j < deCompressed.length(); j++, m++) {
				deCompressed += deCompressed.charAt(j);
			}
			deCompressed += myTags.get(i).getNext();
		}
	}

	public void displayTags() {
		for (Tag tag : myTags) {
			System.out.println(tag.toString());
		}
	}

	private void getMaxBits() {
		int max1 = myTags.get(0).getPosition();
		int max2 = myTags.get(0).getLength();
		for (Tag myTag : myTags) {
			if (myTag.getPosition() > max1)
				max1 = myTag.getPosition();
			if (myTag.getLength() > max2)
				max2 = myTag.getLength();
		}
		posBits = (int) (Math.log(max1) / Math.log(2)) + 1;
		lenBits = (int) (Math.log(max2) / Math.log(2)) + 1;
	}
}
