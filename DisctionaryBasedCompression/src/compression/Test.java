package compression;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Test {
	
	public static void main(String[] args) throws IOException {
		
		LZ77 compress = new LZ77("C:\\Users\\Turbo\\eclipse-workspace\\Data_Structures\\test.txt");
		compress.encode();
		compress.displayTags();
		/*while(true) {
			Scanner read = new Scanner(System.in);
			System.out.println("Welcome to our LZ77 Algorithm");
			System.out.println("1- Encode");
			System.out.println("2- Decode");
			System.out.println("3- Exit");
			int userInput = read.nextInt();
			if(userInput == 1) {
				System.out.println("Please Enter your text: ");
				String text = read.next();
				LZ77 comp = new LZ77(text);
				ArrayList<Tag> tags = new ArrayList<Tag>();
				tags = comp.encode();
				comp.displayTags();
			}
			else if(userInput == 2) {
				LZ77 comp = new LZ77("");
				ArrayList<Tag> myTags = new ArrayList<Tag>();
				System.out.println("Please enter your tags, each per line and space separated. enter the last element 0 if finished: ");
				int pos = 0, len = 0;
				char next = ' ';
				while(next != '0') {
					pos = read.nextInt();
					len = read.nextInt();
					next = read.next().charAt(0);
					if(next != '0')
						myTags.add(new Tag(pos, len, next));
				}
				System.out.println("Your text is: " + comp.decode(myTags));

			}
			else {
				break;
			}
		}*/
	}
	
}