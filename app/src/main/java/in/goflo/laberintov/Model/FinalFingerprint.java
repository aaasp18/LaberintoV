package in.goflo.laberintov.Model;

import java.util.ArrayList;

/**
 * Created by Anisha Mascarenhas on 03-03-2018.
 */

public class FinalFingerprint {

    private ArrayList<AccessPoint> fingerprint;
    private long timestamp;

    public FinalFingerprint (long timestamp, ArrayList<AccessPoint> fingerprint ){

        this.fingerprint = fingerprint;
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public ArrayList<AccessPoint> getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(ArrayList<AccessPoint> fingerprint) {
        this.fingerprint = fingerprint;
    }

}
