package in.goflo.laberintov.Model;

/**
 * Created by goflo on 27/2/18.
 */

public class RoomDetails {

    private String roomName, roomID;

    public RoomDetails(String roomName, String roomID) {
        this.roomName = roomName;
        this.roomID = roomID;
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
