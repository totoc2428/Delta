package serveur.util.location.zone;

public class LocationPoint {
    double latitude;
    double longitude;

    public LocationPoint(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
