package com.example.amisha.laberintov.Model;

import java.util.ArrayList;

/**
 * Created by Anisha Mascarenhas on 27-02-2018.
 */

public class Fingerprint {

    private ArrayList<AccessPoint> fingerprint;
    private double lat, lng;

    public Fingerprint (double lat, double lng, ArrayList<AccessPoint> fingerprint ){
        this.fingerprint = fingerprint;
        this.lat = lat;
        this.lng = lng;
    }

    public ArrayList<AccessPoint> getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(ArrayList<AccessPoint> fingerprint) {
        this.fingerprint = fingerprint;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
