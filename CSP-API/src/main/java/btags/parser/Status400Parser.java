package btags.parser;

import btags.body.Status400;
import datas.Body;
import datas.RequestMessage;
import datas.ResponseMessage;
import exceptions.BadRequestException;
import parser.BtagParser;
import tokens.GeneralToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class Status400Parser implements BtagParser {
    public static final String BTAG_NAME = "status 400";
    public String getBtagName() {
        return BTAG_NAME;
    }

    public Body parseToBody(BufferedReader reader) throws IOException, BadRequestException {
        ArrayList<String> missings = new ArrayList<String>();
        ArrayList<String> wrongs = new ArrayList<String>();
        String line;
        try{
            while ((line=reader.readLine()) != null) {
                if (GeneralToken.END_SEP.equalsIgnoreCase(line))
                    break;
                if (GeneralToken.BODY_SEP.equalsIgnoreCase(line))
                    continue;
                String[] tokens = line.split(GeneralToken.FIELD_SEP);
                if (Status400.MISSING.equalsIgnoreCase(tokens[0])) {
                    if (tokens.length > 1)
                        missings.add(tokens[1]);
                }
                else if (Status400.WRONG.equalsIgnoreCase(tokens[0])) {
                    if (tokens.length > 1)
                        wrongs.add(tokens[1]);
                }
            }
        }catch (NumberFormatException e){
            throw new BadRequestException();
        }
        return new Status400(missings, wrongs);
    }

    public String parseToString(RequestMessage request) {
        return null;
    }

    public String parseToString(ResponseMessage response) {
        String msg = "";
        for (String missing : response.getStringList(Status400.MISSING))
            msg += Status400.MISSING + GeneralToken.FIELD_SEP + missing + "\n";
        for (String wrong : response.getStringList(Status400.WRONG))
            msg += Status400.WRONG + GeneralToken.FIELD_SEP + wrong + "\n";
        return msg;
    }
}
