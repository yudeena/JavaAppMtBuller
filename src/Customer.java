/**
 * Represents a customer at Mt Buller Resort.
 */
public class Customer {
    private int custId;
    private String name;
    private String skiingLevel;
    private int dealId;
    static int nextID = 100;

    /**
     * Constructor for Customer with specified name and skiing level.
     *
     * @param name         The name of the customer.
     * @param skiingLevel  The skiing level of the customer.
     */
    public Customer (String name, String skiingLevel) {
        this.name = name;
        this.skiingLevel = skiingLevel;
        custId = nextID++;
    }

    public String getSkiingLevel() {
        return skiingLevel;
    }

    public void setSkiingLevel(String skiingLevel) {
        this.skiingLevel = skiingLevel;
    }

    public int getDealId() {
        return dealId;
    }

    public void setDealId(int dealId) {
        this.dealId = dealId;
    }


    public int getCustId () {
        return custId;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    /**
     * Returns a string representation of the Customer.
     *
     * @return A string containing customer details.
     */

    public String toString() {
        return String.format("%-10d %-10s %-15s", custId, name, skiingLevel);
} }
