package huffman_coding;

import java.util.Comparator;

public class NodeCompartor implements Comparator<HuffmanNode>{
	public int compare(HuffmanNode n1, HuffmanNode n2) {
		return n1.getCount()-n2.getCount();
			
	}
}
