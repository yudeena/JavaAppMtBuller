/**
 * Represents accommodation at Mt Buller Resort.
 */
public class Accomodation {

    private int accomodationNo;
    private String type;
    private double pricePerDay;
    private boolean availability = true;
    static int nextId = 1;

    public Accomodation() {}

    /**
     * Constructor for Accommodation with specified type and price per day.
     *
     * @param type         The type of accommodation.
     * @param pricePerDay  The price per day of the accommodation.
     */
    public Accomodation (String type, double pricePerDay) {
        this.type = type;
        this.pricePerDay = pricePerDay;
        accomodationNo = nextId++;
    }
    public int getAccomodationNo () {
        return accomodationNo;
    }

    public String getType () {
        return type;
    }

    public double getPricePerDay () {
        return pricePerDay;
    }

    public boolean getAvailability () {
        return availability;
    }

    public void setType (String type) {
        this.type = type;
    }

    public void setPricePerDay (double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public void setAvailability (boolean availability) {
        this.availability = availability;
    }

    public void bookAccommodation(int accommodationNo) {
        if (this.accomodationNo == accommodationNo) {
            this.availability = false;
        }
    }
    /**
     * Returns a string representation of the Accommodation.
     *
     * @return A string containing accommodation details.
     */
    public String toString () {
        return "Accomodation #: " + accomodationNo + ", type: " + type + ", price per day: " + pricePerDay + ", available?  " + availability;
    }

}


