/**
 * Wrap up the Address information
 *
 * @author Mohammad Khatib &lt;&gt;
 * @version $Rev$
 */
public final class Address {
	// Attributes
    private String city,country,town,street;
	
    public Address(String country, String city, String town, String street) {
        this.city = city;
		this.country = country;
		this.town = town;
		this.street = street;
    }

	/**
	 * toString
	 *
	 * @param  
	 * @return String
	 */
	public String toString( ) {
		return (country + ", " + city + ", " + town + ", " + street);
	}

	
}
