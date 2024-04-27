package project4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;




/**
 * This is the class that is the program. 
 * This class is responsible for parsing and validating the command line arguments, 
 * reading and parsing the input file, producing any error messages, handling any 
 * exceptions thrown by other classes, and producing output.  
 * 
 * @author Tony Liu
 *
 */
public class MountainClimb {
	
	/**
	 * The main() method of this program. 
	 * @param args array of Strings provided on the command line when the program is started; 
	 */
	public static void main(String[] args) {
		
		//verify that the command line argument exists 
		if (args.length == 0 ) {
			System.err.println("Usage Error: the program expects file name as an argument.\n");
			System.exit(1);
		}
		
		//verify that command line argument contains a name of an existing file 
		File restStopFile = new File(args[0]); 
		if (!restStopFile.exists()){
			System.err.println("Error: the file "+restStopFile.getAbsolutePath()+" does not exist.\n");
			System.exit(1);
		}
		if (!restStopFile.canRead()){
			System.err.println("Error: the file "+restStopFile.getAbsolutePath()+
											" cannot be opened for reading.\n");
			System.exit(1);
		}
		
		//open the file for reading 
		Scanner inRestStop = null; 	
		
		try {
			inRestStop = new Scanner (restStopFile ) ;
		} catch (FileNotFoundException e) {
			System.err.println("Error: the file "+restStopFile.getAbsolutePath()+
											" cannot be opened for reading.\n");
			System.exit(1);
		}
		
			
		//read the content of the file and save the data in a list of rest stop

		List<RestStop> restStopList = new ArrayList<RestStop>();
		
		RestStop currentRestStop = null;
		String line = null;
		
		while (inRestStop.hasNextLine()) {
			try {
				
				line = inRestStop.nextLine();
				
				String[] elements = line.split(" ");
				if (elements.length < 1) {
					throw new NoSuchElementException("Invalid line");
				}
				else {
					currentRestStop = new RestStop(elements[0]);					
					// add supplies and obstacle
					for(int i = 1; i< elements.length; i++) {
						if("food".equals(elements[i]) || "raft".equals(elements[i]) || "axe".equals(elements[i])) {
							currentRestStop.addSupply(elements[i]);
						}else if("river".equals(elements[i])){	
							currentRestStop.addObstacle(elements[i]);
						}else if("fallen".equals(elements[i]) && i < elements.length - 1 && "tree".equals(elements[i+1]) ){							
							currentRestStop.addObstacle(elements[i] + " " + elements[i+1]);
							i++;
						}else {
							continue;
						}
						
					}
					
				}
				restStopList.add(currentRestStop);
								
			}
			catch (IllegalArgumentException|NoSuchElementException ex ) {
				//caused by a miss-formatted line in the input file
				System.err.println("miss-formatted line: " + line);
				System.exit(1); 	
			}
			
		}
		
		RestStop[] arr = restStopList.toArray(new RestStop[restStopList.size()]);
		BSTMountain bstMountain = new BSTMountain(arr);
		bstMountain.rootToLeafPathCheck();
		System.exit(1); 	

	}
}
