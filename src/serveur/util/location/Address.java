package src.serveur.util.location;

public class Address {
    private String complements;
    private String number;
    private String street;
    private String city;
    private String postalCode;

    private int districtNumber;
    private String districtName;

    private String country;

    public Address(String complements, String number, String street, String city, String postalCode, int districtNumber,
            String districtName, String country) {
        this.complements = complements;
        this.number = number;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.districtNumber = districtNumber;
        this.country = country;
    }

    public Address(String number, String street, String city, String postalCode, String country) {
        this(null, number, street, city, postalCode, -1, null, country);
    }

    public String getComplements() {
        return complements;
    }

    public String getNumber() {
        return number;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public int getDistrictNumber() {
        return districtNumber;
    }

    public String getDistrictName() {
        return districtName;
    }

    public String getCountry() {
        return country;
    }

}
