
package turneral1.grinnell.edu.hw7;

import java.util.Iterator;

import edu.grinnell.glimmer.ushahidi.UshahidiIncident;

/**
 * Circular doubly linked lists.
 * 
 * @author Alex Turner '16
 * Code skeleton created by Sam Rebelsky.
 */
public class DoublyLinkedList<T> implements ListOf<T> {

    // FIELDS
    Node<T> dummy = new Node<T>(null);

    // CONSTRUCTORS
    /**
     * Create a new linked list.
     * 
     * @post
     * Initializes the dummy node links as
     *   dummy.prev = dummy = dummy.next
     */
    public DoublyLinkedList() {
	// Initialize the circular list
	this.dummy.next = this.dummy;
	this.dummy.prev = this.dummy;
    } // DoublyLinkedList

    // ITERABLE METHODS
    /**
     * Create an iterator at the beginning of the DoublyLinkedList.
     * @post
     * Creates an iterator that points to this.dummy.next
     */
    @Override
    public Iterator<T> iterator() {
	return new DoublyLinkedListIterator<T>(this.dummy.next);
    } // iterator()

    // LISTOF METHODS
    /**
     * (Some documentation taken from ListOf.java by Sam Rebelsky)
     * Insert an element at the location of the cursor (between two
     * elements).
     *
     * @pre
     *   c must be associated with the list and in the list.
     * @post
     *   The remainder of the list remains the same.
     *   A node containing val is inserted in the list before c.pos
     */
    public void insert(T val, Cursor c) throws Exception {
	DoublyLinkedListCursor<T> cursor = (DoublyLinkedListCursor<T>) c;
	Node<T> node = new Node<T>(val);
	// fix the node's pointers
	node.next = cursor.pos;
	node.prev = cursor.pos.prev;
	// fix the adjacent nodes' pointers
	cursor.pos.prev = node;
	node.prev.next = node;
    } // insert(T, Cursor)

    /**
     * Add a node to the end of the list.
     * 
     * @post
     * Appends a node that contains val to the list. 
     */
    public void append(T val) throws Exception {
	DoublyLinkedListCursor<T> end = new DoublyLinkedListCursor<T>(this.dummy);
	insert(val, end);
    } // append(T)

    /**
     * Add a node to the beginning of the list.
     * 
     * @post
     * Prepends a node that contains val to the list. 
     */
    public void prepend(T val) throws Exception {
	DoublyLinkedListCursor<T> start = (DoublyLinkedListCursor<T>) this.front();
	insert(val, start);
    } // prepend(T)

    /**
     * Removes cursor's node from the list.
     * 
     * @pre
     * c.pos is a node in the DoublyLinkedList.
     * @post
     * Removes c.pos from the list by redirecting the nodes that
     *  reference it.
     */
    public void delete(Cursor c) throws Exception {
	DoublyLinkedListCursor<T> cursor = (DoublyLinkedListCursor<T>) c;
	cursor.pos.prev.next = cursor.pos.next;
	cursor.pos.next.prev = cursor.pos.prev;  
    } // delete(Cursor)

    /**
     * Creates a cursor at the front of the list.
     * 
     * @pre
     * None.
     * @post
     * Returns a cursor that at the dummy.next node.
     */
    public Cursor front() throws Exception {
	return new DoublyLinkedListCursor<T>(this.dummy.next);
    } // front()

    /**
     * Advances a cursor to the next node in the list.
     * 
     * @pre
     * c is on the list.
     * @post
     * c.pos = c.pos.next
     */
    public void advance(Cursor c) throws Exception {
	DoublyLinkedListCursor<T> cursor = (DoublyLinkedListCursor<T>) c;
	cursor.pos = cursor.pos.next;
    } // advance(Cursor)

    /**
     * Backtracks the cursor to the previous node in the list.
     * 
     * @pre
     * c is on the list.
     * @post
     * The cursor moves to the node preceding the node under cursor.
     */
    public void retreat(Cursor c) throws Exception {
	DoublyLinkedListCursor<T> cursor = (DoublyLinkedListCursor<T>) c;
	cursor.pos = cursor.pos.prev;
    } // retreat(Cursor)

    /**
     * Returns the element under the cursor.
     * 
     * @pre
     * c is on the list.
     * @post
     * Returns the value at c.pos.
     */
    public T get(Cursor c) throws Exception {
	DoublyLinkedListCursor<T> cursor = (DoublyLinkedListCursor<T>) c;
	return cursor.pos.val;
    } // get

    /**
     * Returns the element at the node immediately preceding the cursor.
     * 
     * @pre
     * c is on the list.
     * @post
     * Returns the value of the node preceding the cursor.
     */
    public T getPrev(Cursor c) throws Exception {
	DoublyLinkedListCursor<T> cursor = (DoublyLinkedListCursor<T>) c;
	return cursor.pos.prev.val;
    } // getPrev(Cursor)

    /**
     * Tests whether there is a node subsequent to the cursor's node.
     * 
     * @pre
     * c is on the list.
     * @post
     * Returns true if the dummy node does not come after the cursor's node.
     * Else, returns false.
     */
    public boolean hasNext(Cursor c) {
	DoublyLinkedListCursor<T> cursor = (DoublyLinkedListCursor<T>) c;
	return cursor.pos != this.dummy;
    } // hasNext

    /**
     * Tests whether there is a node preceding the cursor's node.
     * 
     * @pre
     * c is on the list.
     * @post
     * Returns true if the dummy node does not come before the cursor's node.
     * Else, returns false.
     */
    public boolean hasPrev(Cursor c) {
	DoublyLinkedListCursor<T> cursor = (DoublyLinkedListCursor<T>) c;
	return cursor.pos.prev != this.dummy;
    } // hasPrev

    /**
     * Swap the elements of the nodes under two cursors.
     * 
     * @pre
     * c1 and c2 are on the list.
     * @post
     * c1's node's element is set to get(c2).
     * c2's node's element is set to get(c1).
     */
    public void swap(Cursor c1, Cursor c2) throws Exception {
	DoublyLinkedListCursor<T> curs1 = (DoublyLinkedListCursor<T>) c1;
	DoublyLinkedListCursor<T> curs2 = (DoublyLinkedListCursor<T>) c2;
	T temp = get(curs1);

	curs1.pos.val = get(curs2);
	curs2.pos.val = temp;
    } // swap(Cursor, Cursor)

    /**
     * Search for a value that satisfies a predicate, moving the cursor to that
     * value's node.
     * 
     * @pre
     * c is on the list.
     * @post c is moved to the node of the first value satisfying pred.test
     *       if no such node exists, c is not moved.
     * @return true if a node with a val satisfying pred exists.
     */
    public boolean search(Cursor c, Predicate<T> pred) throws Exception {
	DoublyLinkedListCursor<T> cursor = (DoublyLinkedListCursor<T>) c;
	DoublyLinkedListCursor<T> temp;

	for(temp = (DoublyLinkedListCursor<T>) front();
		!pred.test(get(temp)) && hasNext(temp); advance(temp)) {
	} // for

	// if a value is found satisfying pred, move the cursor and return true
	if(hasNext(temp)) {
	    cursor.pos = temp.pos;
	} // if
	return hasNext(temp);
    } // search(Cursor, Predicate<T>)

    /**
     * Returns a doubly linked list of all nodes matching a given predicate.
     *
     * @post
     * Returns a list of the current list's nodes satisfying pred 
     *  (in order of appearance). If no such values exist,
     *  returns a list containing the dummy node.
     */
    public ListOf<T> select(Predicate<T> pred) throws Exception {
	DoublyLinkedList<T> list = new DoublyLinkedList<T>();
	DoublyLinkedListCursor<T> temp;

	for(temp = (DoublyLinkedListCursor<T>) list.front(); hasNext(temp);
		advance(temp)) {
	    // if the value satisfies pred, select the node.
	    if (pred.test(get(temp))) {
		list.append(get(temp));
	    } // if
	} // for
	return list;
    } // select(Predicate<T>)

    /**
     * Returns the nodes between the two cursors.
     *
     * @pre
     * start and end are on the list.
     * @post
     * Returns a list of elements between the start and end cursors.
     * @throws
     * an exception if start does not precede end.
     */
    public ListOf<T> subList(Cursor start, Cursor end) throws Exception {
	DoublyLinkedListCursor<T> start1 = (DoublyLinkedListCursor<T>) start;
	DoublyLinkedListCursor<T> end1 = (DoublyLinkedListCursor<T>) end;

	if (!precedes(start1,end1)) {
	    throw new UnsupportedOperationException("start does not precede end!");
	} else {
	    DoublyLinkedList<T> list = new DoublyLinkedList<T>();
	    DoublyLinkedListCursor<T> temp = (DoublyLinkedListCursor<T>) list.front();

	    // Add each intervening node to the list.
	    for(temp.pos = start1.pos; temp.pos != end1.pos; advance(temp)) {
		list.append(get(temp));
	    } // for

	    return list;
	} // else
    } // subList(Cursor, Cursor)

    /**
     * Tests whether c1 precedes c2 on the list.
     *
     * @pre
     * c1, c2 are on the list.
     * @post
     * Returns true if c1 precedes c2 in the list.
     * Returns false if c1 does not precede c2 in the list.
     */
    public boolean precedes(Cursor c1, Cursor c2) throws Exception {
	DoublyLinkedListCursor<T> curs1 = (DoublyLinkedListCursor<T>) c1;
	DoublyLinkedListCursor<T> curs2 = (DoublyLinkedListCursor<T>) c2;
	DoublyLinkedListCursor<T> temp;

	for(temp = new DoublyLinkedListCursor<T>(curs1.pos);
		hasNext(temp) && (temp.pos != curs2.pos); advance(temp));
	return temp.pos == curs2.pos;
    } // precedes(Cursor, Cursor)
} // class DoublyLinkedList

/**
 * Nodes in the list.
 */
class Node<T> {
    T val;
    Node<T> next;
    Node<T> prev;

    /**
     * Create a new node.
     */
    public Node(T val) {
	this.val = val;
	this.next = null;
	this.prev = null;
    } // Node(T)
} // Node<T>

/**
 * Cursors in the list.
 */
class DoublyLinkedListCursor<T> implements Cursor {
    Node<T> pos;

    /**
     * Create a new cursor that points to a node.
     */
    public DoublyLinkedListCursor(Node<T> pos) {
	this.pos = pos;
    } // DoublyLinkedListCursor
} // DoublyLinkedListCursor<T>

/**
 * Iterators in the list.
 * @author turneral1
 *
 * @param <T>
 */
class DoublyLinkedListIterator<T> implements Iterator<T> {
    Node<T> pos;
    
    /**
     * Create a new iterator that points to a node.
     */
    public DoublyLinkedListIterator(Node<T> pos) {
	this.pos = pos;
    } // DoublyLinkedListIterator(Node<T>)
    
    /**
     * Returns the next value in the iteration.
     * @pre
     * The iterator is on the list.
     * @post
     * Returns the next element in the iteration.
     */
    @Override
    public T next() {
	T val = this.pos.val;
	this.pos = this.pos.next;
	return val;
    } // next(Iterator<T>)
    
    /**
     * Checks whether the node that the iterator points to has a successor.
     * @return true if the value of the successor of the iterator's node 
     *          is not null.
     */
    @Override
    public boolean hasNext() {
	return this.pos.next.val != null;
    } // hasNext(Iterator<T>)
    
    /**
     * Remove the last returned element.
     */
    public void remove() {
	throw new UnsupportedOperationException();
    } // remove()
} // DoublyLinkedListIterator<T>