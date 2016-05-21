package routing;
import dijkstra.Algorithm;
import models.BusStop;
import dataReader.BusData;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
public class BusRoute {
    public BusRoute() {
        BusData.readData();
    }
    public LinkedList<BusStop> getBusRoute(String start, String end) {
        System.out.println(BusData.BUS_STOPS.get(start) +  " " + BusData.BUS_STOPS.get(end));
        return getBusRoute(BusData.BUS_STOPS.get(start), BusData.BUS_STOPS.get(end));
    }
    private LinkedList<BusStop> getBusRoute(BusStop start, BusStop end) {
        List<BusStop> busStopList = new ArrayList<BusStop>(BusData.BUS_STOPS.values());
        Algorithm a = new Algorithm(busStopList, BusData.BUS_LINKS);
        a.execute(start);
        return (LinkedList<BusStop>)a.getPath(end);
    }
}
