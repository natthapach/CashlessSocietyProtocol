package btags.body;

import datas.Body;

import java.util.ArrayList;
import java.util.List;

public class Shop implements Body{
    public static final String PURCHASE_COUNT = "purchase-count";
    public static final String PURCHASE = "purchase";
    private int purchaseCount;
    private List<String> purchases = new ArrayList<String>();

    public Shop(int purchaseCount, List<String> purchases) {
        this.purchaseCount = purchaseCount;
        this.purchases = purchases;
    }

    public String getStringValue(String key) {
        return null;
    }

    public int getIntValue(String key) {
        return 0;
    }

    public double getDoubleValue(String key) {
        if (PURCHASE_COUNT.equalsIgnoreCase(key))
            return purchaseCount;
        return 0;
    }

    public List<String> getStringList(String key) {
        if (PURCHASE.equalsIgnoreCase(key))
            return purchases;
        return null;
    }

    public List<Integer> getIntegerList(String key) {
        return null;
    }

    public List<Double> getDoubleList(String key) {
        return null;
    }

}
