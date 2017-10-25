package tokens;

public class ResponseToken {


    public static final class Header {
        public static final String STATUS = "status";
        public static final String CODE = "code";
        public static final String PHRASE = "phrase";
        public static final String BALANCE = "balance";
    }

    public static final class Status {
        public static final String S200 = "200 OK";
        public static final String S400 = "400 Bad request";
        public static final String S404 = "404 ID not found";
        public static final String S418 = "418 Insufficient fund";
        public static final String S505 = "505 Version not support";
        public static final String S506 = "506 BTAG not support";

        public static final int C200 = 200;
        public static final int C400 = 400;
        public static final int C404 = 404;
        public static final int C418 = 418;
        public static final int C505 = 505;
        public static final int C506 = 506;

        public static final String P200 = "OK";
        public static final String P400 = "Bad request";
        public static final String P404 = "ID not found";
        public static final String P418 = "Insufficient fund";
        public static final String P505 = "Version not support";
        public static final String P506 = "BTAG not support";
    }
}
