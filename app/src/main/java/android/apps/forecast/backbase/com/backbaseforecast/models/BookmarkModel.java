package android.apps.forecast.backbase.com.backbaseforecast.models;

public class BookmarkModel {
    private Integer id;
    private Double latitude;
    private Double longitude;
    private String name;

    public BookmarkModel(Integer id, String name, Double latitude, Double longitude){
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.setName(name);
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
