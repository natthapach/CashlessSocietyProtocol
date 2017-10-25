package tokens;

public class RequestToken {

    public static final class Header {
        public static final String METHOD = "method";
        public static final String AGENT = "agent";
        public static final String SID = "sid";
        public static final String AMOUNT = "amt";
    }

    public static final class Method {
        public static final String BALANCE = "BALANCE";
        public static final String DEPOSIT = "DEPOSIT";
        public static final String WITHDRAW = "WITHDRAW";
        public static final String TRANSFER = "TRANSFER";
        public static final String PAY = "PAY";
    }
}
