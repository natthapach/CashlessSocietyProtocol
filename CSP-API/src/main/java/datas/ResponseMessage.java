package datas;

import tokens.GeneralToken;
import tokens.ResponseToken;

import java.util.List;
import java.util.Map;

public class ResponseMessage {
    private int code;
    private String phrase;
    private double version;
    private String uid;
    private String ts;
    private String btagName;
    private double balance;
    private Body body;

    public ResponseMessage(String status, double version, String uid, String ts, String btagName, double balance, Body body) {
        String[] token = status.split(" ");
        this.code = Integer.parseInt(token[0]);
        this.phrase = token[1];
        this.version = version;
        this.uid = uid;
        this.ts = ts;
        this.balance = balance;
        this.btagName = btagName;
        this.body = body;
    }

    public ResponseMessage(Map<String, String> values, Body body) {
        code = Integer.parseInt(values.containsKey(ResponseToken.Header.CODE)?values.get(ResponseToken.Header.CODE):"0");
        phrase = values.get(ResponseToken.Header.PHRASE);
        version = Double.parseDouble(values.containsKey(GeneralToken.Header.VERSION)?values.get(GeneralToken.Header.VERSION):"0");
        uid = values.get(GeneralToken.Header.UID);
        ts = values.get(GeneralToken.Header.TIME_STAMP);
        btagName = values.get(GeneralToken.Header.BTAG);
        balance = Double.parseDouble(values.containsKey(ResponseToken.Header.BALANCE)?values.get(ResponseToken.Header.BALANCE):"0");
        this.body = body;
    }

    public int getStatusCode() {
        return code;
    }

    public String getStatusPhrase() {
        return phrase;
    }

    public String getStatus(){
        return code + " " + phrase;
    }

    public double getVersion() {
        return version;
    }

    public String getUserId() {
        return uid;
    }

    public String getTimeStamp() {
        return ts;
    }

    public String getBtagName() {
        return btagName;
    }

    public double getBalance() {
        return balance;
    }

    public String getStringValue(String key){
        return body.getStringValue(key);
    }
    public int getIntValue(String key){
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
