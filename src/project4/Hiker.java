package project4;

import java.util.List;

/**
 * Class representing a hiker object, the thing that actual traverses through the path
 * @author tonyliu
 *
 */
public class Hiker {
	
	private int food;
    private int tree;
	private int river;
	
	/**
	 * Constructs a new Hiker object with no supplies
	 */
	public Hiker() {
		 food = 0; 
         tree = 0;
    	 river = 0;
	}
	/**
	 * Method that checks if the path is passable or not
	 * @param nodelist representing the path
	 * @return boolean whether or not it is passable
	 */
	public boolean checkRestStops(List<RestStop> nodelist) {
   
    	for(int j = 0; j<nodelist.size(); j++) {
    		//Collect all supplies from the RestStop
    		for(int i =0; i<nodelist.get(j).getSupplyList().size();i++) {
				if(nodelist.get(j).getSupplyList().get(i).equals("food")) {
					food++;
				}
				if(nodelist.get(j).getSupplyList().get(i).equals("axe")) {
					tree++;
				}
				if(nodelist.get(j).getSupplyList().get(i).equals("raft")) {
					river++;
				}
			}
    		//Collect all Obstacles from the RestStop
			for(int i =0; i<nodelist.get(j).getObstacleList().size(); i++) {
				if(nodelist.get(j).getObstacleList().get(i).equals("fallen tree")) {
					tree--;
				}
				if(nodelist.get(j).getObstacleList().get(i).equals("river")) {
					river--;
				}
			}
			
			if(j<nodelist.size()-1) {
				food--;
			}
			//Checks remaining balance of food, trees, and rivers
			if(food<0 || tree<0 || river<0) {
    			return false;
    		}
			
    	}
    	return true;

	}
}
