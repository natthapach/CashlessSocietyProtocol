package datas;

import tokens.GeneralToken;
import tokens.ResponseToken;

import java.util.Map;

public class ResponseMessage {
    private int code;
    private String phrase;
    private double version;
    private String uid;
    private String ts;
    private String btagName;
    private Body body;

    public ResponseMessage(int code, String phrase, double version, String uid, String ts, String btagName, Body body) {
        this.code = code;
        this.phrase = phrase;
        this.version = version;
        this.uid = uid;
        this.ts = ts;
        this.btagName = btagName;
        this.body = body;
    }

    public ResponseMessage(Map<String, String> values, Body body) {
        code = Integer.parseInt(values.get(ResponseToken.Header.CODE));
        phrase = values.get(ResponseToken.Header.PHRASE);
        version = Double.parseDouble(values.get(GeneralToken.Header.VERSION));
        uid = values.get(GeneralToken.Header.UID);
        ts = values.get(GeneralToken.Header.TIME_STAMP);
        btagName = values.get(GeneralToken.Header.BTAG);
        this.body = body;
    }


}
