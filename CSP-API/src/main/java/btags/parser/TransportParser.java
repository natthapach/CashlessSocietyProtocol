package btags.parser;

import btags.body.Transport;
import datas.Body;
import datas.RequestMessage;
import datas.ResponseMessage;
import exceptions.BadRequestException;
import parser.BtagParser;
import tokens.GeneralToken;

import java.io.BufferedReader;
import java.io.IOException;

public class TransportParser implements BtagParser {
    public static final String BTAG_NAME = "transport";
    public String getBtagName() {
        return BTAG_NAME;
    }

    public Body parseToBody(BufferedReader reader) throws IOException, BadRequestException {
        String line;
        String source=null;
        String destination=null;
        try{
            while ((line=reader.readLine()) != null){
                if (GeneralToken.END_SEP.equalsIgnoreCase(line))
                    break;
                if (GeneralToken.BODY_SEP.equalsIgnoreCase(line))
                    continue;
                String[] tokens = line.split(GeneralToken.FIELD_SEP);
                if (Transport.SOURCE.equalsIgnoreCase(tokens[0]))
                    source = (tokens.length>1)?tokens[1]:null;
                else if (Transport.DESTINATION.equalsIgnoreCase(tokens[0]))
                    destination = (tokens.length>1)?tokens[1]:null;
            }
        }catch (NumberFormatException e){
            throw new BadRequestException();
        }
        return new Transport(source, destination);
    }

    public String parseToString(RequestMessage request) {
        String msg = "";
        if (request.getStringValue(Transport.SOURCE) != null)
            msg += Transport.SOURCE + GeneralToken.FIELD_SEP + request.getStringValue(Transport.SOURCE) + "\n";
        if (request.getStringValue(Transport.DESTINATION) != null)
            msg += Transport.DESTINATION + GeneralToken.FIELD_SEP + request.getStringValue(Transport.DESTINATION) + "\n";
        return msg;
    }

    public String parseToString(ResponseMessage response) {
        return null;
    }
}
