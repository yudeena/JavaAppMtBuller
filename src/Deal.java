import java.io.*;
import java.time.*;
/**
 * Represents a deal made at Mt Buller Resort.
 */
public class Deal implements Serializable {

    private int dealId;
    private int custId;
    private int accomodationNo;
    private String type;
    private LocalDate startDate;
    private int duration;
    private double cost;
    private boolean hasLiftPass;
    private int numLessons = 0;
    private String liftPassType; // Added field to store the type of lift pass
    private double liftPassPrice;
    static int nextID = 10;
    /**
     * Default constructor for Deal.
     */
    public Deal() {
        dealId = nextID++;
    }
    /**
     * Constructor for Deal with specified customer ID, start date, and duration.
     *
     * @param custId    The customer ID associated with the deal.
     * @param startDate The start date of the deal.
     * @param duration  The duration of the deal in days.
     */
    public Deal(int custId, LocalDate startDate, int duration) {
        this.custId = custId;
        this.startDate = startDate;
        this.duration = duration;
        this.hasLiftPass = false;
        this.numLessons = 0;
        dealId = nextID++;
    }
    //Setters N Getters
    public boolean hasLiftPass() {
        return hasLiftPass;
    }

    public int getDealId() {
        return dealId;
    }

    public int getCustId() {
        return custId;
    }

    public int getNumLessons() {
        return numLessons;
    }

    // Setter for numLessons
    public void setNumLessons(int numLessons) {
        this.numLessons = numLessons;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public LocalDate getDate() {
        return startDate;
    }

    public int getDuration() {
        return duration;
    }

    public int getAccomodationNo() {
        return accomodationNo;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Method to add a lift pass to the deal.
     *
     * @param price The price of the lift pass.
     * @param type  The type of lift pass.
     */
    public void addLiftPass(double price, String type) {
        cost += price;
        hasLiftPass = true;
        liftPassType = type;
        liftPassPrice = price;
    }

    public double getLiftPassPrice() {
        if (hasLiftPass()) {
            // If a lift pass has been added, return the price
            return liftPassPrice; // Lift pass price is the difference in total cost with and without the lift pass
        } else {
            // If no lift pass has been added, return 0
            return 0.0;
        }
    }

    // Method to add lessons to the deal
    public void addLessons(int numOfLessons, double lessonPrice) {
        double totalLessonCost = numOfLessons * lessonPrice;
        cost += totalLessonCost;
        numLessons += numOfLessons;
    }



    public void setTotalCost(double cost) {
        this.cost = cost;
    }

    public void setDate(String dateStr) {
        startDate = LocalDate.parse(dateStr);
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setAccomodationNo(int accomodationNo) {
        this.accomodationNo = accomodationNo;
    }

    public double getTotalCost() {
        return cost;
    }
    /**
     * Returns a string representation of the Deal.
     *
     * @return A string containing deal details.
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        // Header
        result.append(String.format("%-15s %-15s %-25s %-15s\n",
                "Deal ID", "Customer ID", "Start Date", "Duration"));

        // Data
        result.append(String.format("%-20d %-25d %-15s %-15d\n",
                dealId, custId, startDate, duration));

        // Additional Details
        result.append(String.format("%-25s: %s\n", "Lift Pass", hasLiftPass ? liftPassType : "None purchased"));

        if (numLessons > 0) {
            result.append(String.format("%-25s: %d\n", "Number of skiing lessons", numLessons));
        }

        result.append(String.format("%-25s: $%.2f\n", "Total Cost", cost));

        return result.toString();
    }

}

