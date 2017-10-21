package parser;

import datas.Body;
import exceptions.BadRequestException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public interface BtagParser {
    String getBtagName();
    Body parseToBody(BufferedReader reader) throws IOException, BadRequestException;
}
