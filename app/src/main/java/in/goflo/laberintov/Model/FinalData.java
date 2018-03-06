package in.goflo.laberintov.Model;

import java.util.ArrayList;

/**
 * Created by Anisha Mascarenhas on 03-03-2018.
 */

public class FinalData {

    private ArrayList<FinalFingerprint> data;
    private String userId, email, roomId, buildingId, dateTime;
    private double latitude, longitude;
    private long timestamp;
    private ArrayList<String> accessPoints;

    public FinalData(String userId, String email, String roomId, String buildingId, double latitude, double longitude,
                     String dateTime, long timestamp, ArrayList<FinalFingerprint>dataFingerprint, ArrayList<String>listAPs){
        this.userId = userId;
        this.email = email;
        this.roomId = roomId;
        this.buildingId = buildingId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dateTime = dateTime;
        this.timestamp = timestamp;
        this.data = dataFingerprint;
        this.accessPoints = listAPs;
    }

    public ArrayList<FinalFingerprint> getData() {
        return data;
    }

    public void setData(ArrayList<FinalFingerprint> data) {
        this.data = data;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public ArrayList<String> getAccessPoints() {
        return accessPoints;
    }

    public void setAccessPoints(ArrayList<String> accessPoints) {
        this.accessPoints = accessPoints;
    }
}
