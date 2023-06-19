package src.serveur.util.location.zone;

import java.util.ArrayList;

public class LocationZone {
    private ArrayList<LocationPoint> perimeter;
    private double sizeInKilometers;
    private double perimeterSize;

    public LocationZone(ArrayList<Double> perimeter, double sizeInKilometers, double perimeterSize) {
        this.perimeter = perimeter;
        this.sizeInKilometers = sizeInKilometers;
        this.perimeterSize = perimeterSize;
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
