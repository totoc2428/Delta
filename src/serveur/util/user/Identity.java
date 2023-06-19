package src.serveur.util.user;

import java.time.LocalDate;
import java.util.ArrayList;

import src.serveur.util.location.Address;
import src.serveur.util.organisation.Familly;

public class Identity {
    private String name;
    private String firstName;
    private ArrayList<String> otherName;
    private LocalDate birthDate;
    private boolean birthGender;

    private Address address;
    private ArrayList<Familly> parentFamilies;
    private ArrayList<Familly> famillies;

    public Identity(String name, String firstName, ArrayList<String> otherName, LocalDate birthDate,
            boolean birsthGender, Address address, ArrayList<Familly> parentFamilies, ArrayList<Familly> famillies) {
        this.name = name;
        this.firstName = firstName;
        this.otherName = otherName;
        this.birthDate = birthDate;
        this.birthGender = birsthGender;
        this.address = address;
        this.parentFamilies = parentFamilies;
        this.famillies = famillies;
    }

    public Identity(String name, String firstName, LocalDate birthDate, boolean birsthGender) {
        this(name, firstName, new ArrayList<String>(), birthDate, birsthGender, null, null, null);
    }

    public Identity(String name, String firstName, ArrayList<String> otherName, LocalDate birsthDate,
            String birsthGender) {
        this(name, firstName, otherName, birsthDate, !birsthGender.contains("female"), null, new ArrayList<Familly>(),
                new ArrayList<Familly>());
    }

    public Identity(String name, String firstName, LocalDate birsthDate, String birsthGender) {
        this(name, firstName, new ArrayList<String>(), birsthDate, !birsthGender.contains("female"), null,
                new ArrayList<Familly>(), new ArrayList<Familly>());
    }

    public String getName() {
        return name;
    }

    public String getFirstName() {
        return firstName;
    }

    public ArrayList<String> getOtherName() {
        return otherName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public boolean getBirthGender() {
        return birthGender;
    }

}
