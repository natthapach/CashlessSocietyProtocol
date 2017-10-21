package parser;

import btags.BtagReader;
import com.sun.org.apache.regexp.internal.RE;
import datas.Body;
import datas.RequestMessage;
import datas.ResponseMessage;
import exceptions.BadRequestException;
import exceptions.BtagNotSupportException;
import tokens.GeneralToken;
import tokens.RequestToken;
import tokens.ResponseToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Parser {
    private Map<String, BtagParser> btagParsers = new HashMap<String, BtagParser>();

    public RequestMessage parseToRequest(BufferedReader reader) throws IOException, BadRequestException, BtagNotSupportException {
        String method = null;
        double version = 0;
        String uid = null;
        String sid = null;
        String ts = null;
        String agent = null;
        double amt = 0;
        boolean hasAmt = false;
        String btag = null;
        try{
            String line = null;
            while ((line = reader.readLine()) != null){
                if (GeneralToken.BODY_SEP.equalsIgnoreCase(line))
                    break;
                if (GeneralToken.END_SEP.equalsIgnoreCase(line))
                    throw new BadRequestException();
                String[] tokens = line.split(GeneralToken.FIELD_SEP);
                if (RequestToken.Header.METHOD.equalsIgnoreCase(tokens[0]))
                    method = (tokens.length>1)?tokens[1]:null;
                else if (GeneralToken.Header.VERSION.equalsIgnoreCase(tokens[0]))
                    version = Double.parseDouble((tokens.length>1)?tokens[1]:"0");
                else if (GeneralToken.Header.UID.equalsIgnoreCase(tokens[0]))
                    uid = (tokens.length>1)?tokens[1]:null;
                else if (RequestToken.Header.SID.equalsIgnoreCase(tokens[0]))
                    sid = (tokens.length>1)?tokens[1]:null;
                else if (GeneralToken.Header.TIME_STAMP.equalsIgnoreCase(tokens[0]))
                    ts = (tokens.length>1)?tokens[1]:null;
                else if (RequestToken.Header.AMOUNT.equalsIgnoreCase(tokens[0])) {
                    if (tokens.length > 1) {
                        amt = Double.parseDouble(tokens[1]);
                        hasAmt = true;
                    }
                }else if (GeneralToken.Header.BTAG.equalsIgnoreCase(tokens[0]))
                    btag = (tokens.length>1)?tokens[1]:null;
                else if (RequestToken.Header.AGENT.equalsIgnoreCase(tokens[0]))
                    agent = (tokens.length>1)?tokens[1]:null;
            }
        }catch (NumberFormatException e){
            throw new BadRequestException();
        }

        checkRequestHeader(method, uid, ts, agent, hasAmt);


        Body body = createBody(reader, btag);
        return new RequestMessage(method, version, uid, sid, ts, amt, agent, btag, body);
    }

    private Body createBody(BufferedReader reader, String btag) throws IOException, BtagNotSupportException, BadRequestException {
        Body body = null;
        if (btag == null){
            String line;
            while (true) {
                line = reader.readLine();
                if (line==null || GeneralToken.END_SEP.equalsIgnoreCase(line))
                    break;
            }
        }else{
            BtagParser btagParser = btagParsers.get(btag.toUpperCase());
            if (btagParser == null)
                throw new BtagNotSupportException();
            body = btagParser.parseToBody(reader);
        }
        return body;
    }

    private void checkRequestHeader(String method, String uid, String ts, String agent, boolean hasAmt) throws BadRequestException {
        if (method == null){
            throw new BadRequestException();
        }else if (uid == null){
            throw new BadRequestException();
        }else if (ts == null){
            throw new BadRequestException();
        }else if (agent == null) {
            throw new BadRequestException();
        }else if (!hasAmt){
            throw new BadRequestException();
        }
    }

    public ResponseMessage parseToResponse(BufferedReader reader) throws IOException, BadRequestException, BtagNotSupportException {
        int code = 0;
        String phrase = null;
        double version = 0;
        String uid = null;
        String ts = null;
        String btag = null;

        try{
            String line = null;
            while((line=reader.readLine()) != null){
                if (GeneralToken.BODY_SEP.equalsIgnoreCase(line))
                    break;
                if (GeneralToken.END_SEP.equalsIgnoreCase(line))
                    throw new BadRequestException();
                String[] tokens = line.split(GeneralToken.FIELD_SEP);
                if (ResponseToken.Header.STATUS.equalsIgnoreCase(tokens[0])){
                    if (tokens.length>1){
                        String[] status = tokens[1].split(" ");
                        if (status.length>1){
                            code = Integer.parseInt(status[0]);
                            phrase = status[1];
                        }
                    }
                }else if (GeneralToken.Header.VERSION.equalsIgnoreCase(tokens[0]))
                    version = Double.parseDouble((tokens.length>1)?tokens[1]:"0");
                else if (GeneralToken.Header.UID.equalsIgnoreCase(tokens[0]))
                    uid = (tokens.length>1)?tokens[1]:null;
                else if (GeneralToken.Header.TIME_STAMP.equalsIgnoreCase(tokens[0]))
                    ts = (tokens.length>1)?tokens[1]:null;
                else if (GeneralToken.Header.BTAG.equalsIgnoreCase(tokens[0]))
                    btag = (tokens.length>1)?tokens[1]:null;
            }
        }catch (NumberFormatException e){
            throw new BadRequestException();
        }

        checkResponseHeader(code, phrase, uid, ts);
        Body body = createBody(reader, btag);
        return new ResponseMessage(code, phrase, version, uid, ts, btag, body);
    }

    private void checkResponseHeader(int code, String phrase, String uid, String ts) throws BadRequestException {
        if (code==0 || phrase == null)
            throw new BadRequestException();
        if (uid == null)
            throw new BadRequestException();
        if (ts == null)
            throw new BadRequestException();
    }

    public String parseToString(RequestMessage request){
        return null;
    }
    public String parseToString(ResponseMessage response){
        return null;
    }

    public void regisBtagParser(BtagParser parser){
        btagParsers.put(parser.getBtagName().toUpperCase(), parser);
    }

}
