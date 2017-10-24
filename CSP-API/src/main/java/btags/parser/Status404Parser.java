package btags.parser;

import btags.body.Status404;
import datas.Body;
import datas.RequestMessage;
import datas.ResponseMessage;
import exceptions.BadRequestException;
import parser.BtagParser;
import tokens.GeneralToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class Status404Parser implements BtagParser {
    public static final String BTAG_NAME = "status 404";
    public String getBtagName() {
        return BTAG_NAME;
    }

    public Body parseToBody(BufferedReader reader) throws IOException, BadRequestException {
        ArrayList<String> missings = new ArrayList<String>();
        String line;
        try{
            while ((line=reader.readLine()) != null){
                if (GeneralToken.END_SEP.equalsIgnoreCase(line))
                    break;
                if (GeneralToken.BODY_SEP.equalsIgnoreCase(line))
                    continue;
                String[] tokens = line.split(GeneralToken.FIELD_SEP);
                if (Status404.MISSING.equalsIgnoreCase(tokens[0]))
                    if (tokens.length>1)
                        missings.add(tokens[1]);
            }
        }catch (NumberFormatException e){
            throw new BadRequestException();
        }
        return new Status404(missings);
    }

    public String parseToString(RequestMessage request) {
        return null;
    }

    public String parseToString(ResponseMessage response) {
        String msg = "";
        for (String missing : response.getStringList(Status404.MISSING))
            msg += Status404.MISSING + GeneralToken.FIELD_SEP + missing + "\n";
        return msg;
    }
}
