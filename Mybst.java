import java.util.Objects;  // 需要添加这个导入

/**
 * Represents a single person record from the "people.txt" file.
 * Each instance corresponds to one line in the file and serves as a node
 * in the BST, Heap, and HashMap data structures.
 *
 * The fields exactly match the 13 columns in the data file:
 * given name; family name; company name; address; city; county; state;
 * zip; phone; phone2; email; web; birthday
 */
class PeopleRecord implements Comparable<PeopleRecord> {  // 添加 implements
    // Personal information fields
    private String givenName;      // First name of the person
    private String familyName;     // Last name of the person
    private String companyName;    // Company where the person works
    private String address;        // Street address
    private String city;           // City of residence
    private String county;         // County of residence
    private String state;          // State (two-letter code)
    private String zip;            // ZIP/Postal code
    private String phone;          // Primary phone number
    private String phone2;         // Secondary/alternative phone number
    private String email;          // Email address
    private String web;            // Website URL
    private String birthday;       // Date of birth (MM/dd/yyyy format)

    /**
     * Constructs a PeopleRecord object with all 13 fields.
     *
     * @param givenName    Person's first name
     * @param familyName   Person's last name
     * @param companyName  Company name
     * @param address      Street address
     * @param city         City name
     * @param county       County name
     * @param state        State code (e.g., "CA", "NY")
     * @param zip          ZIP/Postal code
     * @param phone        Primary phone number
     * @param phone2       Secondary phone number
     * @param email        Email address
     * @param web          Website URL
     * @param birthday     Date of birth (MM/dd/yyyy)
     */
    public PeopleRecord(String givenName, String familyName, String companyName,
                        String address, String city, String county, String state,
                        String zip, String phone, String phone2, String email,
                        String web, String birthday) {
        this.givenName = givenName;
        this.familyName = familyName;
        this.companyName = companyName;
        this.address = address;
        this.city = city;
        this.county = county;
        this.state = state;
        this.zip = zip;
        this.phone = phone;
        this.phone2 = phone2;
        this.email = email;
        this.web = web;
        this.birthday = birthday;
    }

    // ==================== Getters ====================

    /** @return The person's first name */
    public String getGivenName() { return givenName; }

    /** @return The person's last name */
    public String getFamilyName() { return familyName; }

    /** @return The company name */
    public String getCompanyName() { return companyName; }

    /** @return The street address */
    public String getAddress() { return address; }

    /** @return The city of residence */
    public String getCity() { return city; }

    /** @return The county of residence */
    public String getCounty() { return county; }

    /** @return The state code */
    public String getState() { return state; }

    /** @return The ZIP/Postal code */
    public String getZip() { return zip; }

    /** @return The primary phone number */
    public String getPhone() { return phone; }

    /** @return The secondary phone number */
    public String getPhone2() { return phone2; }

    /** @return The email address */
    public String getEmail() { return email; }

    /** @return The website URL */
    public String getWeb() { return web; }

    /** @return The date of birth in MM/dd/yyyy format */
    public String getBirthday() { return birthday; }

    /**
     * Generates a unique key for this person record.
     * Used as a key in HashMap and for name-based searching.
     * The pipe symbol '|' is used as a separator to avoid conflicts
     * with characters that might appear in names.
     *
     * @return A string combining given name and family name with a separator
     */
    public String getFullName() {
        return givenName + "|" + familyName;
    }

    /**
     * Parses a line from the "people.txt" file and creates a PeopleRecord object.
     * The expected format is: field1;field2;field3;...;field13
     * Each field is trimmed to remove leading/trailing whitespace.
     *
     * @param line A single line from the data file
     * @return A new PeopleRecord object populated with the parsed data
     * @throws IllegalArgumentException If the line doesn't contain exactly 13 fields
     *
     * Reference: Basic string splitting approach adapted from
     * Java String.split() documentation
     */
    public static PeopleRecord parse(String line) {
        // Split the line by semicolon delimiter
        String[] parts = line.split(";");

        // Validate that we have all 13 required fields
        if (parts.length != 13) {
            throw new IllegalArgumentException("Invalid line format: " + line);
        }

        // Trim whitespace from each field (files often have spaces after semicolons)
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }

        // Create and return a new PeopleRecord with all fields
        return new PeopleRecord(
                parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6],
                parts[7], parts[8], parts[9], parts[10], parts[11], parts[12]
        );
    }

    /**
     * Compares this record with another for BST ordering.
     * Primary sort by given name (case-insensitive), secondary by family name (case-insensitive).
     * This ensures consistent ordering in the BST and matches the default comparator in MyBST.
     *
     * @param other The other PeopleRecord to compare to
     * @return negative if this < other, 0 if equal, positive if this > other
     */
    @Override
    public int compareTo(PeopleRecord other) {
        // 先比较givenName（忽略大小写）
        int givenNameCompare = this.givenName.compareToIgnoreCase(other.givenName);
        if (givenNameCompare != 0) {
            return givenNameCompare;
        }
        // 如果givenName相同，再比较familyName（忽略大小写）
        return this.familyName.compareToIgnoreCase(other.familyName);
    }

    /**
     * Compares this record with another for equality.
     * Two records are considered equal if they have the same given name and family name.
     * This matches the behavior of compareTo when it returns 0.
     *
     * @param obj The object to compare with
     * @return true if the objects are equal (same given name and family name)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PeopleRecord that = (PeopleRecord) obj;
        // 使用equalsIgnoreCase来匹配compareToIgnoreCase的行为
        return this.givenName.equalsIgnoreCase(that.givenName) &&
                this.familyName.equalsIgnoreCase(that.familyName);
    }

    /**
     * Generates a hash code based on given name and family name.
     * Consistent with equals() - equal objects have equal hash codes.
     *
     * @return The hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(givenName.toLowerCase(), familyName.toLowerCase());
    }

    /**
     * Returns a string representation of the person record.
     * Primarily used for debugging and displaying search results.
     * Format: "givenName familyName (companyName)"
     *
     * @return A concise string representation of the person
     */
    @Override
    public String toString() {
        return String.format("%s %s (%s)", givenName, familyName, companyName);
    }
}