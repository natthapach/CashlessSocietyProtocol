package btags.body;

import datas.Body;

import java.util.List;

public class Transport implements Body {
    public static final String SOURCE = "source-station";
    public static final String DESTINATION = "destination-station";
    private String source;
    private String destination;

    public Transport(String source, String destination) {
        this.source = source;
        this.destination = destination;
    }

    public String getStringValue(String key) {
        if (SOURCE.equalsIgnoreCase(key))
            return source;
        if (DESTINATION.equalsIgnoreCase(key))
            return destination;
        return null;
    }

    public int getIntValue(String key) {
        return 0;
    }

    public double getDoubleValue(String key) {
        return 0;
    }

    public List<String> getStringList(String key) {
        return null;
    }

    public List<Integer> getIntegerList(String key) {
        return null;
    }

    public List<Double> getDoubleList(String key) {
        return null;
    }
}
