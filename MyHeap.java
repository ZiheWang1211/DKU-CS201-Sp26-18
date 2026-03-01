import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * A generic Heap data structure implementation specifically designed for PeopleRecord objects.
 * This heap can be configured with a custom comparator to determine the ordering of elements.
 * By default, it orders PeopleRecord objects by their given name in case-insensitive ascending order.
 * The implementation uses an ArrayList as the underlying storage and maintains the heap property
 * through siftUp and siftDown operations.
 */
class MyHeap {
    // Underlying storage for heap elements using ArrayList for dynamic resizing
    private ArrayList<PeopleRecord> heap;

    // Comparator to define the ordering of elements in the heap
    private Comparator<PeopleRecord> comparator;

    /**
     * Default constructor that uses natural ordering by given name (case-insensitive ascending).
     * This creates a min-heap based on the given name comparison.
     */
    public MyHeap() {
        // Lambda expression comparing given names case-insensitively
        this((p1, p2) -> p1.getGivenName().compareToIgnoreCase(p2.getGivenName()));
    }

    /**
     * Constructor that allows custom ordering through a provided comparator.
     * @param comparator The comparator that defines the heap's ordering
     */
    public MyHeap(Comparator<PeopleRecord> comparator) {
        this.heap = new ArrayList<>();
        this.comparator = comparator;
    }

    /**
     * Inserts a new PeopleRecord into the heap while maintaining heap property.
     * The element is added at the end and then sifted up to its correct position.
     * Time complexity: O(log n)
     * @param record The PeopleRecord to insert
     */
    public void insert(PeopleRecord record) {
        heap.add(record);           // Add to the end
        siftUp(heap.size() - 1);    // Restore heap property by sifting up
    }

    /**
     * Removes and returns the root element (smallest according to comparator).
     * Replaces root with last element, removes last, and sifts down the new root.
     * Time complexity: O(log n)
     * @return The root PeopleRecord, or null if heap is empty
     */
    public PeopleRecord remove() {
        if (heap.isEmpty()) return null;

        PeopleRecord root = heap.get(0);        // Save root element
        int lastIdx = heap.size() - 1;

        // Move last element to root position
        heap.set(0, heap.get(lastIdx));
        heap.remove(lastIdx);                    // Remove last element

        if (!heap.isEmpty()) {
            siftDown(0);                         // Restore heap property by sifting down
        }
        return root;
    }

    /**
     * Restores heap property by moving an element up the tree.
     * Compares element with its parent and swaps if parent is greater (based on comparator).
     * Continues until heap property is satisfied or root is reached.
     * @param index Index of element to sift up
     */
    private void siftUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;        // Calculate parent index

            // If current element >= parent, heap property is satisfied
            if (comparator.compare(heap.get(index), heap.get(parent)) >= 0) {
                break;
            }

            swap(index, parent);                  // Swap with parent
            index = parent;                        // Continue checking from parent position
        }
    }

    /**
     * Restores heap property by moving an element down the tree.
     * Compares element with its children and swaps with smallest child if necessary.
     * Continues until heap property is satisfied or leaf is reached.
     * @param index Index of element to sift down
     */
    private void siftDown(int index) {
        int size = heap.size();

        while (index < size) {
            int left = 2 * index + 1;              // Left child index
            int right = 2 * index + 2;             // Right child index
            int smallest = index;                   // Assume current is smallest

            // Check if left child exists and is smaller than current smallest
            if (left < size && comparator.compare(heap.get(left), heap.get(smallest)) < 0) {
                smallest = left;
            }

            // Check if right child exists and is smaller than current smallest
            if (right < size && comparator.compare(heap.get(right), heap.get(smallest)) < 0) {
                smallest = right;
            }

            // If no swap needed, heap property is satisfied
            if (smallest == index) {
                break;
            }

            swap(index, smallest);                  // Swap with smallest child
            index = smallest;                        // Continue checking from child position
        }
    }

    /**
     * Utility method to swap two elements in the heap.
     * @param i Index of first element
     * @param j Index of second element
     */
    private void swap(int i, int j) {
        PeopleRecord temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    /**
     * Checks if the heap is empty.
     * @return true if heap contains no elements, false otherwise
     */
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    /**
     * Returns the number of elements in the heap.
     * @return Current size of the heap
     */
    public int size() {
        return heap.size();
    }
}