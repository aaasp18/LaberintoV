package com.example.amisha.laberintov.Model;

/**
 * Created by Anisha Mascarenhas on 27-02-2018.
 */

public class AccessPoint {

    private String ssid, bssid;
    private int level;

    public AccessPoint (String ssid, String bssid, int level){
        this.ssid = ssid;
        this.bssid = bssid;
        this.level = level;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getBssid() {
        return bssid;
    }

    public void setBssid(String bssid) {
        this.bssid = bssid;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
