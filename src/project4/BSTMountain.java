package project4;

import java.util.List;
/**
 * This class represents a Mountain using a BST
 * 
 * @author tonyliu
 *
 */
public class BSTMountain extends BST<RestStop> {
	
    /**
	 * Constructs a new, empty tree, sorted according to the natural ordering of its RestStops.
	 */
    public BSTMountain() {
        super(); 
    }
    
    /**
	 * Constructs a new tree containing the RestStops in the specified collection, sorted according
	 * to the natural ordering of its RestStops
	 * 
	 * @param array representing the restStops in order
	 */
    public BSTMountain(RestStop[] collection) {
    	
        super(collection);
    }
    /**
	 * validatePath overrides its parent method. Validates if a path is passable or not
	 * 
	 * 
	 * @param list of nodes representing a path
	 * @return boolean representing whether or not a path is valid
	 */
    public boolean validatePath(List<RestStop> nodelist) {
    	// if path has less reststops than height, path is not valid
    	if(nodelist.size()< height()) {
    		return false;
    	}
 
    	Hiker hiker = new Hiker();
    	boolean isPathValid = hiker.checkRestStops(nodelist);
    	
    	//print the valid path
    	if(isPathValid) {
	        StringBuffer sb = new StringBuffer(); 
	        for(int j = 0; j<nodelist.size(); j++)
	        {
	        	if(j>0)sb.append(" ");
	        	sb.append(nodelist.get(j).getLabel());	        	
	        }
	        System.out.println(sb.toString());	        
        }
    	return isPathValid;
    }

}
