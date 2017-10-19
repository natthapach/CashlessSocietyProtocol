package datas;

import tokens.GeneralToken;
import tokens.RequestToken;

import java.util.List;
import java.util.Map;

public class RequestMessage {
    private String method;
    private double version;
    private String uid;
    private String sid;
    private String ts;
    private double amt;
    private String btagName;
    private Body body;

    public RequestMessage(String method, double version, String uid, String sid, String ts, double amt, String btagName, Body body) {
        this.method = method;
        this.version = version;
        this.uid = uid;
        this.sid = sid;
        this.ts = ts;
        this.amt = amt;
        this.btagName = btagName;
        this.body = body;
    }

    public RequestMessage(Map<String, String> values, Body body) {
        method = values.get(RequestToken.Header.METHOD);
        version = Double.parseDouble(values.get(GeneralToken.Header.VERSION));
        uid = values.get(GeneralToken.Header.UID);
        sid = values.get(RequestToken.Header.SID);
        ts = values.get(GeneralToken.Header.TIME_STAMP);
        amt = Double.parseDouble(values.get(RequestToken.Header.AMOUNT));
        btagName = values.get(GeneralToken.Header.BTAG);
        this.body = body;
    }

    public String getMethod() {
        return method;
    }
    public double getVersion() {
        return version;
    }
    public String getUserId() {
        return uid;
    }
    public String getServiceId() {
        return sid;
    }
    public String getTimeStamp() {
        return ts;
    }
    public double getAmount() {
        return amt;
    }
    public String getBtagName() {
        return btagName;
    }

    public String getStringValue(String key){
        return body.getStringValue(key);
    }
    public int getIntegerValue(String key){
        return body.getIntValue(key);
    }
    public double getDoubleValue(String key){
        return body.getDoubleValue(key);
    }
    public List<String> getStringList(String key){
        return body.getStringList(key);
    }
    public List<Integer> getIntegerList(String key){
        return body.getIntegerList(key);
    }
    public List<Double> getDoubleList(String key){
        return body.getDoubleList(key);
    }
}
