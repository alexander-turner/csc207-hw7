package turneral1.grinnell.edu.hw7;

import edu.grinnell.glimmer.ushahidi.*;

/**
 * Reads UshahidiIncidents and finds their average latitude and longitude.
 * Produces a DoublyLinkedList of UshahidiIncidents whose latitude and longitude
 *  fall within a given range.
 * 
 * @author turneral1
 * 
 */
public class UshahidiLatitude {

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
	
	// Select the nodes that fall within distance of the centers.
	Predicate<UshahidiIncident> testLocation = 
		new WithinDistance(distance, averageLat, averageLong);
	DoublyLinkedList<UshahidiIncident> inProximity =
		(DoublyLinkedList<UshahidiIncident>) list.select(testLocation);
	
    } // main(String[])
} // class UshahidiLatitude

/**
 * Tests whether an UshahidiIncident falls within the given coordinate range.
 * @author turneral1
 *
 */
class WithinDistance implements Predicate<UshahidiIncident> {
    double deviation, latCenter, longCenter;
    
    /*
     * CONSTRUCTORS 
     */
    public WithinDistance(double deviation, double latCenter, double longCenter) throws Exception {
	this.deviation = deviation;
	this.latCenter = latCenter;
	this.longCenter = longCenter;
    } // WithinDistance(double,double,double)
    
    /**
     * Tests whether a value is within a given range of a given average.
     * @pre
     * The incident has a valid location.
     * @post
     * Tests if the incident's location falls within range of the latitude
     * and longitude centers.
     * @return true if center-deviation <= val <= center+deviation
     * @return false otherwise
     */
    public boolean test(UshahidiIncident incident) {
	UshahidiLocation coord = incident.getLocation();
        return (coord.getLongitude() >= this.longCenter-this.deviation) && 
               (coord.getLongitude() <= this.longCenter+this.deviation) &&
               (coord.getLatitude() >= this.latCenter-this.deviation) &&
               (coord.getLatitude() <= this.latCenter+this.deviation);
    } // test(UshahidiIncident)
} // WithinDistance