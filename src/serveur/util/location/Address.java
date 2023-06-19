package src.serveur.util.repot.location;

public class Address {
    private String complements;
    private String number;
    private String street;
    private String city;

    private int districtNumber;
    private String districtName;

    private String country;

    public Address(String complements, String number, String street, String city, int districtNumber,
            String districtName, String country) {
        this.complements = complements;
        this.number = number;
        this.street = street;
        this.city = city;
        this.districtNumber = districtNumber;
        this.country = country;
    }

    public Address(String number, String street, String city, String country) {
        this(null, number, street, city, null, null, country);
    }

}
