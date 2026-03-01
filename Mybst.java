import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Binary Search Tree (BST) for storing PeopleRecord objects
 * Supports custom comparator for sorting and handles duplicate keys
 */
class MyBST {
    /**
     * Inner class representing a BST node
     * Each node stores one or more records with the same key value
     */
    private class BSTNode {
        ArrayList<PeopleRecord> records;  // List of records with same key
        BSTNode left, right;              // Left and right child references

        /**
         * Constructor creates a new node with a single record
         * @param record The first record to store in this node
         */
        BSTNode(PeopleRecord record) {
            records = new ArrayList<>();
            records.add(record);
            left = right = null;
        }
    }

    private BSTNode root;                       // Root node of the BST
    private Comparator<PeopleRecord> comparator; // Comparator for ordering

    /**
     * Default constructor using natural ordering
     * Compares by given name first, then family name (case-insensitive)
     */
    public MyBST() {
        this((p1, p2) -> {
            int cmp = p1.getGivenName().compareToIgnoreCase(p2.getGivenName());
            if (cmp == 0) {
                cmp = p1.getFamilyName().compareToIgnoreCase(p2.getFamilyName());
            }
            return cmp;
        });
    }

    /**
     * Constructor with custom comparator
     * @param comparator The comparator to determine ordering of records
     */
    public MyBST(Comparator<PeopleRecord> comparator) {
        this.root = null;
        this.comparator = comparator;
    }

    /**
     * Public method to insert a record into the BST
     * @param record The PeopleRecord to insert
     */
    public void insert(PeopleRecord record) {
        root = insertRec(root, record);
    }

    /**
     * Recursive helper method for insertion
     * @param node Current node being examined
     * @param record Record to insert
     * @return Updated node (for reassignment)
     */
    private BSTNode insertRec(BSTNode node, PeopleRecord record) {
        // Base case: reached empty spot, create new node
        if (node == null) {
            return new BSTNode(record);
        }

        // Compare the new record with the current node's key
        int cmp = comparator.compare(record, node.records.get(0));

        if (cmp == 0) {
            // Same key: add to existing node's list
            node.records.add(record);
        } else if (cmp < 0) {
            // Smaller key: go to left subtree
            node.left = insertRec(node.left, record);
        } else {
            // Larger key: go to right subtree
            node.right = insertRec(node.right, record);
        }
        return node;
    }

    /**
     * Search for records by given name and family name
     * @param givenName First name to search for
     * @param familyName Last name to search for
     * @return List of matching records (empty list if none found)
     */
    public List<PeopleRecord> search(String givenName, String familyName) {
        // Create dummy record for comparison
        PeopleRecord dummy = new PeopleRecord(givenName, familyName, "", "", "", "", "", "", "", "", "", "", "");
        return searchRec(root, dummy);
    }

    /**
     * Recursive helper method for search
     * @param node Current node being examined
     * @param target Dummy record containing search criteria
     * @return List of matching records from this subtree
     */
    private List<PeopleRecord> searchRec(BSTNode node, PeopleRecord target) {
        if (node == null) {
            return new ArrayList<>();  // Not found
        }

        int cmp = comparator.compare(target, node.records.get(0));

        if (cmp == 0) {
            // Found matching node, return copy of its records
            return new ArrayList<>(node.records);
        } else if (cmp < 0) {
            // Target smaller: search left subtree
            return searchRec(node.left, target);
        } else {
            // Target larger: search right subtree
            return searchRec(node.right, target);
        }
    }

    /**
     * Get information about the tree structure
     * @return int array where [0] = number of nodes, [1] = height of tree
     */
    public int[] getInfo() {
        int[] info = new int[2];
        info[0] = countNodes(root);
        info[1] = height(root);
        return info;
    }

    /**
     * Count total number of nodes in the tree
     * @param node Root of subtree to count
     * @return Number of nodes in this subtree
     */
    private int countNodes(BSTNode node) {
        if (node == null) return 0;
        return 1 + countNodes(node.left) + countNodes(node.right);
    }

    /**
     * Calculate height of the tree
     * @param node Root of subtree
     * @return Height of subtree (-1 for empty tree)
     */
    private int height(BSTNode node) {
        if (node == null) return -1;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    /**
     * Get all records from the tree in sorted order (inorder traversal)
     * @return List containing all records
     */
    public List<PeopleRecord> getAllRecords() {
        List<PeopleRecord> all = new ArrayList<>();
        inorderCollect(root, all);
        return all;
    }

    /**
     * Inorder traversal to collect all records
     * @param node Current node
     * @param list List to add records to
     */
    private void inorderCollect(BSTNode node, List<PeopleRecord> list) {
        if (node == null) return;

        // Traverse left subtree
        inorderCollect(node.left, list);
        // Add current node's records
        list.addAll(node.records);
        // Traverse right subtree
        inorderCollect(node.right, list);
    }
}