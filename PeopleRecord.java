/**
 * Represents a single person record from the "people.txt" file.
 * Each instance corresponds to one line in the file and serves as a node
 * in the BST, Heap, and HashMap data structures.
 *
 * The fields exactly match the 13 columns in the data file:
 * given name; family name; company name; address; city; county; state;
 * zip; phone; phone2; email; web; birthday
 */
class PeopleRecord {
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

