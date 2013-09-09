package taojava.labs.testing;

/**
 * A variety of methods for students to write tests for.
 * 
 * @author Spencer Liberto
 * @author Samuel A. Rebelsky
 * @author Lea Marolt Sonnenschein
 * @author Daniel Torres
 */
public class SampleMethods {
    /**
     * Convert from Celsius to Fahrenheit.
     */
    public static int c2f(int temp) {
	return (temp - 32) * (5 / 9);
    } // c2f(int)

    /**
     * Compute x^p.
     * 
     * @param x
     *   an integer
     * @param p
     *   a non-negative integer
     * @return x^p
     * @pre
     *   Integer.MIN_VALUE < x^p < Integer.MAX_VALUE
     */
    public static int expt(int x, int p) {
	// Base case: When p = 0, result is 1
	if (p == 0) {
	    return 1;
	}
	// Base case: When p = 1, result is x
	else if (p == 1) {
	    return x;
	}
	// Recursive case: When p is 2k, x^(2k) = (x^k) * (x^k)
	else if (p % 2 == 0) {
	    int tmp = expt(x, p/2);
	    return tmp*tmp;
	}
	// Recursive case: When p is odd, result is x*(x^(p-1))
	else  {
	    return expt(x * x, (p - 1) / 2);
	} 
    } // expt(int,int)

    /**
     * Remove all of the instances of the letter 'a' from a string.
     * 
     * @param str
     *   Any string
     * @return no_as
     *   The string without the letter 'a'
     * @pre
     *   No additional
     * @post
     *   If c appears in str, and c != 'a', then c appears in no_bs.
     *   For all 0 <= i < j < str.length(),
     *     If str[i] != 'a' and str[j] != 'a', then there
     *     exist 0 <= k < l < no_as.length() such that 
     *     no_as[k] = str[i] and no_as[l] = str[j].
     */
    public static String removeAs(String str) {
	String no_as = "";	// The string we're creating with no a's.
	
	for (int i = 0; i < str.length(); i++) {
	    if (str.charAt(i) == 'a') {
		no_as += str.charAt(i++);
	    } // if we see the a
	} // for each position
	
	return no_as;
    } // removeAs
    
    /**
     * Remove all of the instances of the letter 'b' from a string.
     * 
     * @param str
     *   Any string
     * @return no_bs
     *   The string without the letter 'b'
     * @pre
     *   No additional
     * @post
     *   If c appears in str, and c != 'b', then c appears in no_bs.
     *   For all 0 <= i < j < str.length(),
     *     If str[i] != 'b' and str[j] != 'b', then there
     *     exist 0 <= k < l < no_bs.length() such that 
     *     no_bs[k] = str[i] and no_bs[l] = str[j].
     */
    public static String removeBs(String str) {
	StringBuffer no_bs = new StringBuffer(str);
	for (int i = 0; i < str.length(); i++) {
	    if (no_bs.charAt(i) == 'b') {
		no_bs.deleteCharAt(i);
	    } // if the char at position i is a 'b'
	} // for each position in the string

	return no_bs.toString();
    } // removeAs(String word)

    /**
     * Sum all of the integers in an array.
     * @param ints 
     *   an array of integers
     * @return result, an integer
     * @pre
     *   ints[0] + ints[1] + ... + ints[ints.length-1] < maxint
     * @post
     *   result = ints[0] + ints[1] + ... + ints[ints.length-1]
     */
    public static int sum(int[] ints) {
	int result = 0;

	for (int i = 0; i < ints.length; i++) {
	    result += result + i;
	} // for
	return result;
    } // result(int[])
    
} // class SampleMethods
