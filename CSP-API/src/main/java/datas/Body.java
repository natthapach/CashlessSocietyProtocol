package datas;

import java.util.List;

public interface Body {
    String getStringValue(String key);
    int getIntValue(String key);
    double getDoubleValue(String key);
    List<String> getStringList(String key);
    List<Integer> getIntegerList(String key);
    List<Double> getDoubleList(String key);
}
