package solution;

import java.util.ArrayList;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class VectorQuantizer {
	private BufferedImage myImage;
	private File myInputFile, myOutputFile;
	private FileWriter myWriter;
	private FileReader myReader;
    String imageSource,imageDestination,compressedStream="";
	private ArrayList<Block> codeBook = new ArrayList<Block>();
	private ArrayList<Block> imageBlocks;
	private int[][] imagePixels;
	private int imageRows,imageCols,blockRows=2,blockCols=2,codeBooksNumber=4;
	
	VectorQuantizer(String source, String dest){
		this.imageSource = source;
		this.imageDestination = dest;
		myImage = null;
		myInputFile = new File(imageSource);
		try {
			myImage = ImageIO.read(myInputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.imageRows = myImage.getHeight();
		this.imageCols = myImage.getWidth();
		imagePixels = new int[this.imageRows][this.imageCols];
	}

	public void compress() {
		readImage();
		creatBlocks();
		ArrayList<Block>[] arrTemp = creatCodeBook();
		creatCodeBookLabels();
		creatImageLabels(arrTemp);
		creatCompressedStream();
		writeCodeBookToFile();
	}
	// utility methods
	private void readImage() {
		for(int i=0; i<this.imageRows; i++) {
			for(int j=0; j<this.imageCols; j++) {
				imagePixels[i][j] =  new Color(myImage.getRGB(j,i)).getRed();
			}
		}
		imageBlocks = new ArrayList<Block>();
	}
	public void constructImage(String compressed, String book, int rows, int cols) {
		readCompressedStream(compressed);
		readCodeBook(book, rows, cols);
		creatCodeBookLabels();

		 BufferedImage newImage = new BufferedImage(myImage.getWidth(), myImage.getHeight(), BufferedImage.TYPE_INT_RGB);
		try {
		myOutputFile = new File(this.imageDestination + "\\newGrey.png");
		String word = "";
		int startRow=0, startCol=0;
		for(int i=0; i<compressedStream.length(); i++) {
			boolean enter =false;
			if(compressedStream.charAt(i) == ' ') {				
				enter = true;
			}
			else 
				word += compressedStream.charAt(i);
			if(enter) {				
				Block vector = inCodeBook(word);
				if(vector != null) {
					word = "";
					for(int m=startRow, a=0; a<vector.rows && m<newImage.getHeight(); m++, a++) {
						for(int n=startCol, b=0; b<vector.cols && n<newImage.getWidth(); n++, b++) {
							Color c = new Color(vector.pixels[a][b], vector.pixels[a][b], vector.pixels[a][b]);
							newImage.setRGB(n, m, c.getRGB());
						}
					}
					startCol+=blockCols;
					if(startCol == newImage.getWidth()) {
						startCol = 0;
						startRow+=blockRows;
					}
				}
			}
		}
			ImageIO.write(newImage, "png", myOutputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void creatBlocks() {
		int startRow=0, startCol=0;
		while(startRow<imageRows) {
			Block newBlock = new Block(blockRows, blockCols);
			
			for(int m=0, i=startRow; m<blockRows; i++,m++) {
				for(int n=0, j=startCol; n<blockCols; j++,n++) {
					newBlock.pixels[m][n] = imagePixels[i][j];
				}
			}
			
			imageBlocks.add(newBlock);
			startCol+=blockCols;
			if(startCol == imageCols) {
				startCol = 0;
				startRow+=blockRows;
			}
		}
		
	}
	private Block inCodeBook(String label) {
		for(Block b : this.codeBook) {
			if(b == null)
				continue;
			if(label.equals(b.label))
				return b;
		}
		return null;
	}
	private ArrayList<Block>[] creatCodeBook() {
		ArrayList<Block> avgTemp = new ArrayList<Block>();
		ArrayList<Block>[] arrTemp = new ArrayList[1];
		arrTemp[0] = this.imageBlocks;
		while(this.codeBook.size() < this.codeBooksNumber) {
			if(this.codeBook.size() != 0) {
				ArrayList<Block> tempBlocks = splitBlock(avgTemp);
				arrTemp = inRightPlace(this.imageBlocks, tempBlocks);				
			}
			avgTemp.clear();
			for(int i=0; i<arrTemp.length; i++)
				avgTemp.add(calcAverage(arrTemp[i]));
			this.codeBook = avgTemp;			
		}
		return arrTemp;
	}
	
	private void creatCodeBookLabels() {
		double temp = Math.log(this.codeBook.size())/Math.log(2);
		int numberOfBits;
		if(temp%1==0) {
			numberOfBits = (int) temp;
		}else {
			numberOfBits = (int) temp + 1;
		}
		int i = 0;
		
		for(Block vector:this.codeBook) {
			if(vector == null)
				continue;
			vector.label = Integer.toBinaryString(i);
			i++;
		}
	}
	private void writeCodeBookToFile() {
		try {
			new File(this.imageDestination + "\\codeBook.txt").delete();
			myWriter = new FileWriter(this.imageDestination + "\\codeBook.txt", true);
			for(Block b : this.codeBook) {
				if(b == null)
					continue;
				b.writeToFile(myWriter);
			}
			myWriter.close();
			this.codeBook.clear();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	private void creatCompressedStream() {
		try {
			new File(this.imageDestination + "\\compressedImage.txt").delete();
			myWriter = new FileWriter(this.imageDestination + "\\compressedImage.txt", true);
		for(int i=0; i<this.imageBlocks.size(); i++) {
			myWriter.write(imageBlocks.get(i).label + " ");
		}
		myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void readCodeBook(String path, int rows, int cols) {
		try {
			Block current = new Block(rows, cols);
			myReader = new FileReader(path);
			int letter, currRow=0, currCol=0;
			String number="";
			while((letter = myReader.read()) != -1) {
				if((char)letter != ' ')
					number += (char) letter;
				else {
					number = number.replace("\r\n", "");
					number.trim();
					current.pixels[currRow][currCol] = Integer.parseInt(number);
					currCol++;
					if(currCol==cols) {
						currCol = 0;
						currRow++;
					}
					number="";
				}
				if(currRow==rows) {
					this.codeBook.add(current);
					current = new Block(rows, cols);
					currRow=0; currCol=0;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	private void readCompressedStream(String path) {
		try {
			myReader = new FileReader(path);
			int letter;
			while((letter = myReader.read()) != -1) {
				this.compressedStream += (char) letter;
			}
			myReader.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void creatImageLabels(ArrayList<Block>[] arrTemp) {
		
		for(int i=0; i<arrTemp.length; i++) {
			for(Block b : arrTemp[i]) {
				b.label = this.codeBook.get(i).label;
			}
		}
	}
	
private Block increaseOne (Block iBlock) {
		Block newBlock = new Block(blockRows, blockCols);
		for(int i=0; i<newBlock.rows; i++) {
			for(int j=0; j<newBlock.cols; j++) {
				newBlock.pixels[i][j] = iBlock.pixels[i][j] + 1;
			}
		}
		return newBlock;
	}
	
	private Block decreaseOne (Block iBlock) {
		Block newBlock = new Block(iBlock.rows, iBlock.cols);
		for(int i=0; i<newBlock.rows; i++) {
			for(int j=0; j<newBlock.cols; j++) {
				newBlock.pixels[i][j] = iBlock.pixels[i][j] - 1;
			}
		}
		return newBlock;
	}
	
	private Block calcAverage(ArrayList<Block> iBlock) {
		Block newBlock = new Block(blockRows, blockCols);

		if(iBlock.size() == 0) {
			return null;
		}
		// add all the blocks together in one block
		for(Block tmp : iBlock) {
			for(int i=0; i<tmp.rows; i++) {
				for(int j=0; j<tmp.cols; j++) {
					newBlock.pixels[i][j] += tmp.pixels[i][j];
				}
			}
		}
		
		// divide each number on the size of the list to get the average
		for(int i=0; i<newBlock.rows; i++) {
			for(int j=0; j<newBlock.cols; j++) {
				newBlock.pixels[i][j] /= iBlock.size();
			}
		}
		return newBlock;
	}
	
	private int getDistance(Block a, Block b) {
		int distance = 0;
		for(int i=0; i<a.rows; i++) {
			for(int j=0; j<a.cols; j++) {
				distance += Math.pow(a.pixels[i][j]-b.pixels[i][j],2);
			}
		}
		return distance;
	}
	
	 private ArrayList<Block> splitBlock(ArrayList<Block> iBlock){
		ArrayList<Block> newBlocks = new ArrayList<Block>(iBlock.size()*2);
		for(Block tmp: iBlock) {
			newBlocks.add(increaseOne(tmp));
			newBlocks.add(decreaseOne(tmp));
		}
		return newBlocks;
	}
	
	 private ArrayList<Block>[] inRightPlace(ArrayList<Block> imageBlocks, ArrayList<Block> iBlock){
		ArrayList<Block>[] myPackage = new ArrayList[iBlock.size()];
		for(int i=0; i<myPackage.length; i++) {
			myPackage[i] = new ArrayList<Block>();
		}
		for(Block imageBlock: imageBlocks) {
			int correctArray=0;
			int minDistance = getDistance(imageBlock, iBlock.get(0)), currentDistance=0;
			for(int i=0; i<iBlock.size(); i++) {
				currentDistance = getDistance(imageBlock, iBlock.get(i));
				if(currentDistance <= minDistance) {
					minDistance = currentDistance;
					correctArray = i;
				}
			}
			myPackage[correctArray].add(imageBlock);
		}
		return myPackage;
	}
}