package turneral1.grinnell.edu.hw7;

import edu.grinnell.glimmer.ushahidi.*;

/**
 * Reads UshahidiIncidents and prints summary information.
 * @author turneral1
 *
 */
public class UshahidiSummary {
    
    /**
     * DoublyLinkedList of UshahidiIncidents.
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
	UshahidiWebClient client = new UshahidiWebClient("https://farmersmarket.crowdmap.com/", 15);
	UshahidiLocation max, min;
	DoublyLinkedList<UshahidiIncident> list = new DoublyLinkedList<UshahidiIncident>();
	DoublyLinkedListCursor<UshahidiIncident> cursor;
	// distance is the allowed range for longitude and latitude
	double averageLong = 0, averageLat = 0, distance = 5, maxVal = 0, minVal = 0,
		currentLong, currentLat;
	int count = 0;
	
	// Read the incidents into a list.
	while(client.hasMoreIncidents()) {
	    list.append(client.nextIncident());
	} // while
	
	// Sum the longitudes and latitudes
	for (cursor = (DoublyLinkedListCursor<UshahidiIncident>) list.front();
		list.hasNext(cursor); list.advance(cursor), count++) {
	    UshahidiLocation coord = (list.get(cursor)).getLocation();
	    
	    currentLong = coord.getLongitude();
	    averageLong += currentLong;
	    currentLat = coord.getLatitude();
	    averageLat += currentLat;
	    
	    if (currentLong > maxVal || currentLat > maxVal) {
		if (currentLong > currentLat) {
		    maxVal = currentLong;
		} else {
		    maxVal = currentLat;
		} // if/else
		max = coord;
		
	    } else if (currentLong < minVal || currentLat < minVal) {
		if (currentLong > currentLat) {
		    minVal = currentLong;
		} else {
		    minVal = currentLat;
		} // if/else
		min = coord;
	    } // if/else if
	} // while
	
	// Average the longitudes and latitudes
	if (count == 0) {
	    throw new UnsupportedOperationException("Cannot divide by 0!");
	} else {
	    averageLong /= count;
	    averageLat /= count;
	} // else
	
	// Save the total number of incidents
	int totalCount = count;
	
	System.out.println("Incident Summary:");
	System.out.println("Average longitude: " + averageLong);
	System.out.println("Average latitude: " + averageLat);
	
	// Select the nodes that fall within distance of the centers.
	Predicate<UshahidiIncident> testLocation = 
		new WithinDistance(distance, averageLat, averageLong);
	DoublyLinkedList<UshahidiIncident> inProximity =
		(DoublyLinkedList<UshahidiIncident>) list.select(testLocation);
	
	// Sum the longitudes and latitudes
	for(cursor = (DoublyLinkedListCursor<UshahidiIncident>) inProximity.front(),
	     count = 0; inProximity.hasNext(cursor); inProximity.advance(cursor), count++);
	
	System.out.print("Percentage of incidents within " + distance + 
		           " degrees of (" + (int) averageLong + ", " + (int) averageLat +
		           "): ");
	System.out.format("%3.1f%%%n", (float) 100*(count/totalCount));
	System.out.println("Largest latitude or longitude value: " + maxVal);
	System.out.println("Smallest latitude or longitude value: " + minVal);
	
    } // main(String[])
} // class UshahidiSummary