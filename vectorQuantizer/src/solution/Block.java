package solution;

import java.io.FileWriter;
import java.io.IOException;

public class Block {
	int[][] pixels;
	int rows;
	int cols;
	String label;
	
	public Block(int width, int height) {
		this.rows = width;
		this.cols = height;
		pixels = new int[width][height];
	}
	
	
	public void display() {
		for(int i=0; i<rows; i++) {
			for(int j=0; j<cols; j++)
				System.out.print(pixels[i][j] + " ");
			System.out.println();
		}
		System.out.println(label);
	}
	
	public void writeToFile(FileWriter myFile) throws IOException {
		for(int i=0; i<rows; i++) {
			for(int j=0; j<cols; j++)
				myFile.write(pixels[i][j] + " ");
			myFile.write("\r\n");
		}
	}
}
