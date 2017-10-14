public class Request {
    public static final String BODY_SEP = "BODY";
    public static final String END_SEP = "END";
    public static final String TIME_FORMAT = "dd/MM/yyyy HH.mm.ss z";

    public static final class Header {
        public static final String METHOD = "method";
        public static final String VERSION = "version";
        public static final String AGENT = "agent";
        public static final String UID = "uid";
        public static final String SID = "sid";
        public static final String AMOUNT = "amt";
        public static final String TIME_STAMP = "ts";
    }

    public static final class Method {
        public static final String DEPOSIT = "DEPOSIT";
        public static final String WITHDRAW = "WITHDRAW";
        public static final String TRANSFER = "TRANSFER";
        public static final String PAY = "PAY";
    }
}
