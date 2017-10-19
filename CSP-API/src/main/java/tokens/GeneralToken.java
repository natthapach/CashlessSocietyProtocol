package tokens;

public class GeneralToken {
    public static final int DEFAULT_PORT = 981;
    public static final String BODY_SEP = "BODY";
    public static final String END_SEP = "END";
    public static final String TIME_FORMAT = "dd/MM/yyyy HH.mm.ss z";
    public static final String FIELD_SEP = ":";

    public static final class Header {
        public static final String VERSION = "version";
        public static final String UID = "uid";
        public static final String TIME_STAMP = "ts";
        public static final String BTAG = "btag";
    }
}
