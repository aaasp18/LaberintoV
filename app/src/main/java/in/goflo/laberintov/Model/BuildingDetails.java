package in.goflo.laberintov.Model;

/**
 * Created by goflo on 27/2/18.
 */

public class BuildingDetails {

    private String buildingName, buildingID;

    public BuildingDetails(String buildingName, String buildingID) {
        this.buildingName = buildingName;
        this.buildingID = buildingID;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getBuildingID() {
        return buildingID;
    }

    public void setBuildingID(String buildingID) {
        this.buildingID = buildingID;
    }
}
