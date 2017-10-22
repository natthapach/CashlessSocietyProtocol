package parser;

import datas.Body;
import datas.RequestMessage;
import datas.ResponseMessage;
import exceptions.BadRequestException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public interface BtagParser {
    String getBtagName();
    Body parseToBody(BufferedReader reader) throws IOException, BadRequestException;
    String parseToString(RequestMessage request);
    String parseToString(ResponseMessage response);
}
