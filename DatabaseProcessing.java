import java.io.*;
import java.util.*;

public class DatabaseProcessing {
    private MyBST bst;

    public DatabaseProcessing() {
        bst = new MyBST();
    }

    // loading
    public void loadData(String fileName) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                PeopleRecord record = PeopleRecord.parse(line);
                bst.insert(record);
            }
        }
    }
    // Search records by given name and family name
    public List<PeopleRecord> search(String givenName, String familyName) {
        return bst.search(givenName, familyName);
    }

    // Get BST info (node count and height)
    public int[] getBSTInfo() {
        return bst.getInfo();
    }

    // Sort all records by given name in ascending order using heap sort
    public List<PeopleRecord> sort() {
        List<PeopleRecord> allRecords = bst.getAllRecords();
        // Comparator: first by given name, then by family name
        MyHeap heap = new MyHeap((p1, p2) -> {
            int cmp = p1.getGivenName().compareToIgnoreCase(p2.getGivenName());
            if (cmp == 0) {
                cmp = p1.getFamilyName().compareToIgnoreCase(p2.getFamilyName());
            }
            return cmp;
        });
        for (PeopleRecord rec : allRecords) {
            heap.insert(rec);
        }
        List<PeopleRecord> sorted = new ArrayList<>();
        while (!heap.isEmpty()) {
            sorted.add(heap.remove());
        }
        return sorted;
    }
}