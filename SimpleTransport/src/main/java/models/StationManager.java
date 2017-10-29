package models;

import java.util.ArrayList;
import java.util.List;

public class StationManager {
    private List<String> stations;

    public StationManager() {
        initStations();
    }

    private void initStations() {
        stations = new ArrayList<String>();
        stations.add("station A");
        stations.add("station B");
        stations.add("station C");
        stations.add("station D");
    }

    public List<String> getStations() {
        return stations;
    }

    public double getPrice(String source, String destination){
        int isrc = stations.indexOf(source);
        int idest = stations.indexOf(destination);

        return Math.abs(isrc - idest)*10;
    }
}
