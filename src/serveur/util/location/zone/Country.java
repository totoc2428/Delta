package src.serveur.util.location.zone;

import java.util.ArrayList;

import src.serveur.util.organisation.Association;
import src.serveur.util.security.key.Key;

public class Country extends LocationZone {
    private Association legislator;
    private String name;

    public Country(Key signature, ArrayList<LocationPoint> perimeter, double sizeInKilometers, double perimeterSize,
            Association legislator, String name) {
        super(signature, perimeter, sizeInKilometers, perimeterSize);
        this.legislator = legislator;
        this.name = name;
    }

    @Override
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

}
