package exceptions;

public class InsufficientFundException extends Exception {
    private String uid;
    private String ts;

    public InsufficientFundException() {
    }

    public InsufficientFundException(String message) {
        super(message);
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }
}
