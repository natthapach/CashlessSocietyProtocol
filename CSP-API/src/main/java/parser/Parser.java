package parser;

import btags.BtagReader;
import tokens.GeneralToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Parser {
    private Map<String, BtagReader> btagReaders = new HashMap<String, BtagReader>();
    public Map<String,String> parseToMap(BufferedReader reader){
        Map<String, String> header = new HashMap<String, String>();
        String line = null;
        try {
            while ((line=reader.readLine()) != null){
                if (GeneralToken.BODY_SEP.equalsIgnoreCase(line)){

                }else if (GeneralToken.END_SEP.equalsIgnoreCase(line)) {

                }else{
                    String[] fields = line.split(GeneralToken.FIELD_SEP);
                    header.put(fields[0], fields[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return header;
    }

    public void regisBtagReader(BtagReader reader){
        btagReaders.put(reader.getBtagName(), reader);
    }
}
