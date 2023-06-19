package src.serveur.util.location.zone;

import src.serveur.util.security.key.Key;
import java.util.ArrayList;

import src.serveur.util.organisation.Association;

public class District extends LocationZone {
    private Association legislator;
    private String name;
    private int number;

    public District(Key signature, ArrayList<LocationPoint> perimeter, double sizeInKilometers, double perimeterSize,
            Association legislator, String name, int number) {
        super(signature, perimeter, sizeInKilometers, perimeterSize);
        this.legislator = legislator;
        this.name = name;
        this.number = number;

    }

    public Key getSignature() {
        return super.getSignature();
    }

    @Override
    public ArrayList<LocationPoint> getPerimeter() {
        return super.getPerimeter();
    }

    @Override
    public double getPerimeterSize() {
        return super.getPerimeterSize();
    }

    @Override
    public double getSizeInKilometers() {
        return super.getSizeInKilometers();
    }

    public String getName() {
        return name;
    }

    public Association getLegislator() {
        return legislator;
    }

    public int getNumber() {
        return number;
    }
}
