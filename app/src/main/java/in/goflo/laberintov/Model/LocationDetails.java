package in.goflo.laberintov.Model;

/**
 * Created by goflo on 20/2/18.
 */

public class LocationDetails {

    private String locationName, locationID;

    public LocationDetails(String locationName, String locationID) {
        this.locationName = locationName;
        this.locationID = locationID;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getLocationID() {
        return locationID;
    }
}
