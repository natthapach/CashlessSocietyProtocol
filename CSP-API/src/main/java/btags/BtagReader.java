package btags;

import java.io.BufferedReader;

public interface BtagReader {
    String getBtagName();
    void readBody(BufferedReader reader);
}
