package serveur.util.ChainObject.location.zone;

public class LocationPoint {
    private double longitude;
    private double latitude;

    public LocationPoint(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String toWriteFormat() {
        return this.longitude + "" + this.latitude;
    }
}
