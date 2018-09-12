package android.apps.forecast.backbase.com.backbaseforecast.models;


public class MarkerModel {
    private int id;

    public MarkerModel(int id) {
        this.id = id;
    }

    public int getDeviceId() {
        return id;
    }

    public void setDeviceId(int deviceId) {
        this.id = deviceId;
    }

}
