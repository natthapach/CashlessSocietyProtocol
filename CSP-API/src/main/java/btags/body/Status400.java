package btags.body;

import datas.Body;

import java.util.List;

public class Status400 implements Body {
    public static final String MISSING = "missing";
    public static final String WRONG = "wrong";
    private List<String> missings;
    private List<String> wrongs;

    public Status400(List<String> missings, List<String> wrongs) {
        this.missings = missings;
        this.wrongs = wrongs;
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
        if (WRONG.equalsIgnoreCase(key))
            return wrongs;
        return null;
    }

    public List<Integer> getIntegerList(String key) {
        return null;
    }

    public List<Double> getDoubleList(String key) {
        return null;
    }
}
