package messages;

public class Response {


    public static final class Header {
        public static final String STATUS = "status";
    }

    public static final class Status {
        public static final String S200 = "200 OK";
        public static final String S400 = "400 Bad request";
        public static final String S404 = "404 ID not found";
        public static final String S418 = "418 Insufficient fund";
        public static final String S505 = "505 Version not support";
        public static final String S506 = "506 BTAG not support";
    }
}
