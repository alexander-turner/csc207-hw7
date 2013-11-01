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
	UshahidiWebClient client = new UshahidiWebClient("https://farmersmarket.crowdmap.com/", 10);
	DoublyLinkedList<UshahidiIncident> list = new DoublyLinkedList<UshahidiIncident>();
	DoublyLinkedListCursor<UshahidiIncident> cursor;
	// distance is the allowed range for longitude and latitude
	double averageLong = 0, averageLat = 0, distance = 5;
	int count = 0;
	
	// Read the incidents into a list.
	while(client.hasMoreIncidents()) {
	    list.append(client.nextIncident());
	} // while
	
	// Sum the longitudes and latitudes
	for(cursor = (DoublyLinkedListCursor<UshahidiIncident>) list.front(); 
		list.hasNext(cursor); list.advance(cursor), count++) {
	    UshahidiLocation coord = (list.get(cursor)).getLocation();
	    averageLong += coord.getLongitude();
	    averageLat += coord.getLatitude();
	} // while
	
	// Average the longitudes and latitudes
	if (count == 0) {
	    throw new UnsupportedOperationException("Cannot divide by 0!");
	} else {
	    averageLong /= count;
	    averageLat /= count;
	} // else
	
	int firstcount = count;
	
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
	
	System.out.println("Percentage of incidents within " + distance + 
		           " degrees of (" + averageLong + ", " + averageLat +
		           "): " + (count/firstcount));
	
    } // main(String[])
} // class UshahidiSummary