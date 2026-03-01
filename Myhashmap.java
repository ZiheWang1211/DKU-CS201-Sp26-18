import java.util.ArrayList;
import java.util.List;

/**
 * A hash map implementation that uses quadratic probing for collision handling.
 * Stores keys mapping to a list of PeopleRecord objects (allows multiple records per key).
 * Uses lazy deletion with a tombstone marker.
 */
class MyHashmap {
    private static class Entry {
        String key;
        ArrayList<PeopleRecord> value;
        boolean isDeleted;   // tombstone marker for lazy deletion

        Entry(String key, PeopleRecord record) {
            this.key = key;
            this.value = new ArrayList<>();
            this.value.add(record);
            this.isDeleted = false;
        }
    }

    private Entry[] table;
    private int capacity;
    private int size;
    private static final double LOAD_FACTOR = 0.5;

    public MyHashmap() {
        this(16);
    }

    public MyHashmap(int initialCapacity) {
        this.capacity = initialCapacity;
        this.table = new Entry[capacity];
        this.size = 0;
    }

    // Hash function: ensures non-negative index
    private int hash(String key) {
        return (key.hashCode() & 0x7fffffff) % capacity;
    }

    // Insert a record, updating if key exists
    public void put(String key, PeopleRecord record) {
        if ((double) size / capacity >= LOAD_FACTOR) {
            resize();
        }

        int index = hash(key);
        int firstAvailable = -1; // records the first deletable or empty slot encountered

        for (int i = 0; i < capacity; i++) {
            int probe = (index + i * i) % capacity;   // quadratic probing
            Entry entry = table[probe];

            if (entry == null) {
                if (firstAvailable == -1) {
                    firstAvailable = probe;           // first empty slot
                }
                // continue probing in case the key exists later (though unlikely)
            } else if (entry.isDeleted) {
                if (firstAvailable == -1) {
                    firstAvailable = probe;           // first deleted slot
                }
                // continue probing
            } else if (entry.key.equals(key)) {
                entry.value.add(record);               // same key, add record to existing list
                return;
            }
        }

        // No matching key found; insert into the first available slot
        if (firstAvailable != -1) {
            table[firstAvailable] = new Entry(key, record);
            size++;
        } else {
            throw new RuntimeException("Hash table full");
        }
    }

    // Retrieve all records associated with the key
    public List<PeopleRecord> get(String key) {
        int index = hash(key);
        for (int i = 0; i < capacity; i++) {
            int probe = (index + i * i) % capacity;
            Entry entry = table[probe];
            if (entry == null) {
                return null;
            } else if (!entry.isDeleted && entry.key.equals(key)) {
                return new ArrayList<>(entry.value);
            }
        }
        return null;
    }

    // Alias for get
    public List<PeopleRecord> search(String key) {
        return get(key);
    }

    // Delete the entire entry for the key (lazy deletion)
    public boolean delete(String key) {
        int index = hash(key);
        for (int i = 0; i < capacity; i++) {
            int probe = (index + i * i) % capacity;
            Entry entry = table[probe];
            if (entry == null) {
                return false;
            } else if (!entry.isDeleted && entry.key.equals(key)) {
                entry.isDeleted = true;
                size--;
                return true;
            }
        }
        return false;
    }

    // Double the table size and rehash all non-deleted entries
    private void resize() {
        int newCapacity = capacity * 2;
        Entry[] oldTable = table;
        this.capacity = newCapacity;
        this.table = new Entry[newCapacity];
        this.size = 0;
        // Reinsert all non-deleted entries
        for (Entry entry : oldTable) {
            if (entry != null && !entry.isDeleted) {
                for (PeopleRecord rec : entry.value) {
                    put(entry.key, rec);   // reinsert each record
                }
            }
        }
    }
}