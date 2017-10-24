package btags.body;

import datas.Body;

import java.util.List;

public class Status404 implements Body {
    public static final String MISSING = "missing";
    private List<String> missings;

    public Status404(List<String> missings) {
        this.missings = missings;
    }

    public String getStringValue(String key) {
        return null;
    }

    public int getIntValue(String key) {
        return 0;
    }

    public double getDoubleValue(String key) {
        return 0;
    }

    public List<String> getStringList(String key) {
        if (MISSING.equalsIgnoreCase(key))
            return missings;
        return null;
    }

    public List<Integer> getIntegerList(String key) {
        return null;
    }

    public List<Double> getDoubleList(String key) {
        return null;
    }
}
