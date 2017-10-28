package exceptions;

import java.util.ArrayList;
import java.util.List;

public class IdNotFoundException extends Exception {
    private String uid;
    private String ts;
    private List<String> missings = new ArrayList<String>();
    public IdNotFoundException() {
    }

    public IdNotFoundException(String message) {
        super(message);
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public List<String> getMissings() {
        return missings;
    }

    public void addMissing(String missing){
        missings.add(missing);
    }
}
