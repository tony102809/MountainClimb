package project4;

import java.util.ArrayList;
import java.util.List;


/**
 * This class represents a single rest stop. It stores the label of the rest stop 
 * along with a list of the supplies that a hiker can collect at this reststop 
 * and a list of obstacles that a hiker may encounter at this reststop  
 * 
 * @author Tony Liu
 *
 */
public class RestStop implements Comparable<RestStop> {
	
	private String label;
	private List<String> suppliesList = new ArrayList<String>(); 
	private List<String> obstacleList = new ArrayList<String>();
	
	
	/**
	 * Constructs a new RestStop object with specified label. 
	 * @param label of the rest stop
	 * @throws IllegalArgumentException if theLabel is invalid 
	 */
	public RestStop ( String labelString)  throws IllegalArgumentException {
		setLabel( labelString ); 		
	}
	/**
	 * Returns the label. 
	 * @return the label of rest stop
	 */
	public String getLabel() {
		return label;
	}	
	/**
	 * Returns the suppliesList. 
	 * @return the list of supplies
	 */
	public List<String> getSupplyList() {
		return suppliesList;
	}
	/**
	 * Returns the obstacleList. 
	 * @return the list of obstacles
	 */
	public List<String> getObstacleList() {
		return obstacleList;
	}
	/**
	 * Sets the label. 
	 * @param labelString to be set
	 */
	private void setLabel(String labelString)  throws IllegalArgumentException {
		this.label = labelString; 
	}
	/**
	 * Add supplies to suppliesList. 
	 * @param supplies to be added. 
	 */
	public void addSupply(String supplies) {
		this.suppliesList.add(supplies); 
	}
	/**
	 * Add obstacle to obstacleList. 
	 * @param obstacle to be added. 
	 */
	public void addObstacle(String obstacle){
		this.obstacleList.add(obstacle); 
	}
	/**
	 * implement inherited abstract compareTo method
	 * @param other rest stop to be compared
	 */	
    public int compareTo ( RestStop other ) {
            return this.getLabel().compareTo(other.getLabel());
    }
	 /**
	  * toString() method used to print a string representing the RestStop object. 
	  * 
	  * @return String representing the restStop
	  */
    public String toString(){ 
   	 	return label;
	}
    
}
