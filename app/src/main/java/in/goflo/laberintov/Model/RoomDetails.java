package in.goflo.laberintov.Model;

/**
 * Created by goflo on 27/2/18.
 */

public class RoomDetails {

    private String roomName, roomID, buildingID, locationID;

    public RoomDetails(String roomName, String roomID, String buildingID, String locationID) {
        this.roomName = roomName;
        this.roomID = roomID;
        this.buildingID = buildingID;
        this.locationID = locationID;
    }

    public String getLocationID() {
        return locationID;
    }

    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }

    public String getBuildingID() {
        return buildingID;
    }

    public void setBuildingID(String buildingID) {
        this.buildingID = buildingID;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }
}
