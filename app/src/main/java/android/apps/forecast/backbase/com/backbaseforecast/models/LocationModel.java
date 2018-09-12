package android.apps.forecast.backbase.com.backbaseforecast.models;


public class LocationModel {
    private String name;
    private Double latitude;
    private Double longitude;
    private String imageName;

    public LocationModel(String name, String imageName, double latitude, double longitude) {
        this.name = name;
        this.imageName = imageName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
