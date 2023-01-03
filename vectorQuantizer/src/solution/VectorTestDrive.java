package solution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class VectorTestDrive{


	
	public static void main(String[] args) {

		
		
		VectorQuantizer v = new VectorQuantizer("C:\\Users\\Turbo\\Desktop\\image.png", "C:\\Users\\Turbo\\Desktop");
		v.constructImage("C:\\Users\\Turbo\\Desktop\\compressedImage.txt", "C:\\Users\\Turbo\\Desktop\\codeBook.txt",
				2,2);
		
		
		/*Block b1 = new Block(2,2);
		b1.pixels[0][0] = 1;
		b1.pixels[0][1] = 2;
		b1.pixels[1][0] = 3;
		b1.pixels[1][1] = 4;
		Block b2 = new Block(2,2);
		b2.pixels[0][0] = 7;
		b2.pixels[0][1] = 9;
		b2.pixels[1][0] = 6;
		b2.pixels[1][1] = 6;
		Block b3 = new Block(2,2);
		b3.pixels[0][0] = 4;
		b3.pixels[0][1] = 11;
		b3.pixels[1][0] = 12;
		b3.pixels[1][1] = 12;
		Block b4 = new Block(2,2);
		b4.pixels[0][0] = 4;
		b4.pixels[0][1] = 9;
		b4.pixels[1][0] = 10;
		b4.pixels[1][1] = 10;
		Block b5 = new Block(2,2);
		b5.pixels[0][0] = 15;
		b5.pixels[0][1] = 14;
		b5.pixels[1][0] = 20;
		b5.pixels[1][1] = 18;
		Block b6 = new Block(2,2);
		b6.pixels[0][0] = 9;
		b6.pixels[0][1] = 9;
		b6.pixels[1][0] = 8;
		b6.pixels[1][1] = 8;
		Block b7 = new Block(2,2);
		b7.pixels[0][0] = 4;
		b7.pixels[0][1] = 3;
		b7.pixels[1][0] = 4;
		b7.pixels[1][1] = 5;
		Block b8 = new Block(2,2);
		b8.pixels[0][0] = 17;
		b8.pixels[0][1] = 16;
		b8.pixels[1][0] = 18;
		b8.pixels[1][1] = 18;
		Block b9 = new Block(2,2);
		b9.pixels[0][0] = 1;
		b9.pixels[0][1] = 4;
		b9.pixels[1][0] = 5;
		b9.pixels[1][1] = 6;
		ArrayList<Block> myList = new ArrayList<Block>();
		myList.add(b1);
		myList.add(b2);
		myList.add(b3);
		myList.add(b4);
		myList.add(b5);
		myList.add(b6);
		myList.add(b7);
		myList.add(b8);		
		myList.add(b9);		
		
		Block a1 = new Block(2,2);
		a1.pixels[0][0] = 6;
		a1.pixels[0][1] = 8;
		a1.pixels[1][0] = 9;
		a1.pixels[1][1] = 9;
		Block a2 = new Block(2,2);
		a2.pixels[0][0] = 7;
		a2.pixels[0][1] = 9;
		a2.pixels[1][0] = 10;
		a2.pixels[1][1] = 10;
		ArrayList<Block> tmp = new ArrayList<Block>();
		tmp.add(a1);
		tmp.add(a2);
		
		display((VectorQuantizer.inRightPlace(myList, tmp)));
		
		public static void display(ArrayList<Block>[] a) {
		for(ArrayList<Block> tmp : a) {
			System.out.println(tmp.size());
			for(Block tmpBlock : tmp) {
				for(int i=0; i<tmpBlock.rows; i++) {
					for(int j=0; j<tmpBlock.cols; j++)
						System.out.print(tmpBlock.pixels[i][j] + " ");
					System.out.println();
				}
			}
			System.out.println("****************");
		}
	}
		*/
	}
	
}
