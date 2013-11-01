package turneral1.grinnell.edu.hw7;

public interface Predicate<T> {
    /**
     * Determine if a value meets the predicate.
     */
    boolean test(T val);
} // Predicate<T>