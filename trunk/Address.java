//package .Users.mkhatib.Desktop.Network-Project.facelock;

/**
 * <<Class summary>>
 *
 * @author Mohammad Khatib &lt;&gt;
 * @version $Rev$
 */
public final class Address {
    private String city,country,town,street;
	// {{{ Address constructor
    /**
     * 
     */
    public Address(String country, String city, String town, String street) {
        this.city = city;
		this.country = country;
		this.town = town;
		this.street = street;
    }
	// }}}
	
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