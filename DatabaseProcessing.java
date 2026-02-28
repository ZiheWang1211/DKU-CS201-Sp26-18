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
     // Get the most frequent words (only from specified fields, alphabetic only, length >= len)
    public List<Map.Entry<String, Integer>> getMostFrequentWords(int count, int len)
            throws ShortLengthException {
        if (len <= 0) {
            throw new ShortLengthException("Length must be positive");
        }
        Map<String, Integer> freqMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("people.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(";");
                if (parts.length < 7) continue;   // Need at least first 7 fields
                // Process only the first 7 fields
                for (int i = 0; i < 7; i++) {
                    String field = parts[i].trim();
                    // Split by non-alphabetic characters
                    String[] words = field.split("[^a-zA-Z]+");
                    for (String word : words) {
                        if (word.length() >= len) {
                            String lower = word.toLowerCase();
                            freqMap.put(lower, freqMap.getOrDefault(lower, 0) + 1);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        // Sort by frequency descending, then alphabetically for ties
        List<Map.Entry<String, Integer>> list = new ArrayList<>(freqMap.entrySet());
        list.sort((a, b) -> {
            int cmp = b.getValue().compareTo(a.getValue());
            if (cmp == 0) {
                cmp = a.getKey().compareTo(b.getKey());
            }
            return cmp;
        });
        int end = Math.min(count, list.size());
        return list.subList(0, end);
    }
}