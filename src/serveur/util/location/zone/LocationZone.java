package serveur.util.location.zone;

import java.util.ArrayList;

import serveur.util.security.key.Key;

public abstract class LocationZone {
    private Key signature;
    private ArrayList<LocationPoint> perimeter;
    private double sizeInKilometers;
    private double perimeterSize;

    public LocationZone(Key signature, ArrayList<LocationPoint> perimeter, double sizeInKilometers,
            double perimeterSize) {
        this.signature = signature;
        this.perimeter = perimeter;
        this.sizeInKilometers = sizeInKilometers;
        this.perimeterSize = perimeterSize;
    }

    public Key getSignature() {
        return signature;
    }

    public ArrayList<LocationPoint> getPerimeter() {
        return perimeter;
    }

    public double getPerimeterSize() {
        return perimeterSize;
    }

    public double getSizeInKilometers() {
        return sizeInKilometers;
    }

}
