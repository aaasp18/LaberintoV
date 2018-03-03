package in.goflo.laberintov.Model;

import java.util.ArrayList;

/**
 * Created by Anisha Mascarenhas on 27-02-2018.
 */

public class Data {

    private ArrayList<Fingerprint> data;
    private String roomid, dateTime, username, group;
    private long time;
    private ArrayList<String> accessPoints;

    public Data(String roomid, ArrayList<Fingerprint> data, String dateTime, long timestamp, ArrayList<String> accessPoints){
        this.data = data;
        this.roomid = roomid;
        this.dateTime = dateTime;
        this.time = timestamp;
        this.accessPoints = accessPoints;
    }

    public ArrayList<Fingerprint> getData() {
        return data;
    }

    public void setData(ArrayList<Fingerprint> data) {
        this.data = data;
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public long getTimestamp() {
        return time;
    }

    public void setTimestamp(long timestamp) {
        this.time = timestamp;
    }

    public ArrayList<String> getAccessPoints() {
        return accessPoints;
    }

    public void setAccessPoints(ArrayList<String> accessPoints) {
        this.accessPoints = accessPoints;
    }
}
