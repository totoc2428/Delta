package src.serveur.util.user;

import java.time.LocalDate;
import java.util.ArrayList;

public class Identity {
    private String name;
    private String firstName;
    private ArrayList<String> otherName;
    private LocalDate birthDate;
    // private Address

    private boolean birthGender;

    public Identity(String name, String firstName, ArrayList<String> otherName, LocalDate birthDate,
            boolean birsthGender) {
        this.name = name;
        this.firstName = firstName;
        this.otherName = otherName;
        this.birthDate = birthDate;
        this.birthGender = birsthGender;
    }

    public Identity(String name, Stringg firstName, LocalDate birthDate, boolean birsthGender) {
        this(name, firstName, new ArrayList<String>(), birthDate, birsthGender);
    }

    public Identity(String name, String firstName, ArrayList<String> otherName, LocalDate birsthDate,
            String birsthGender) {
        this(name, firstName, otherName, birsthDate, !birsthGender.contains("female"));
    }

    public Identity(String name, String firstName, LocalDate birsthDate, String birsthGender) {
        this(name, firstName, new ArrayList<String>(), birsthDate, !birsthGender.contains("female"));
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
