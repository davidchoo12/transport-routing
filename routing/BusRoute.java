package routing;
import dijkstra.Algorithm;
import models.BusService;
import models.BusStop;
import dataReader.BusData;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
public class BusRoute {
    public BusRoute() {
//        BusData.readData();
    }
    public LinkedList<BusStop> getBusRoute(String start, String end) {
//        System.out.println(BusData.BUS_STOPS.get(start) +  " " + BusData.BUS_STOPS.get(end));
        return getBusRoute(BusData.BUS_STOPS.get(start), BusData.BUS_STOPS.get(end));
    }
    private LinkedList<BusStop> getBusRoute(BusStop start, BusStop end) {
        List<BusStop> busStopList = new ArrayList<BusStop>(BusData.BUS_STOPS.values());
        Algorithm a = new Algorithm(busStopList, BusData.BUS_LINKS);
        a.execute(start);
        return (LinkedList<BusStop>)a.getPath(end);
    }
    public LinkedList<BusSubRoute> getBusSubRoutes(LinkedList<BusStop> busStops){
        List<BusService> eligibleBuses = new ArrayList<>();
        LinkedList<BusSubRoute> result = new LinkedList<>();
        ListIterator<BusStop> it = busStops.listIterator();
        int startSubRouteIndex = 0;
        List<BusService> copy = null;
        while(it.hasNext()) {
            BusStop current = it.next();
            List<BusService> busServicesAvailable = BusData.BUS_STOP_SERVICE_MAP.get(current);
            if(eligibleBuses.isEmpty())
                eligibleBuses.addAll(busServicesAvailable);
            else {
                copy = new ArrayList<>(eligibleBuses);
                if(eligibleBuses.retainAll(busServicesAvailable)){ //if there are changes
                    if(eligibleBuses.isEmpty()) { //if no more intersecting bus service
                        BusSubRoute subRoute = new BusSubRoute(busStops.subList(startSubRouteIndex, it.previousIndex()), copy);
                        result.add(subRoute);
                        startSubRouteIndex = it.previousIndex() - 1;
                        it.previous();
                    }
                }
            }
        }
        BusSubRoute subRoute = new BusSubRoute(busStops.subList(startSubRouteIndex, it.nextIndex()), copy);
        result.add(subRoute);
                        
        return result;
    }
    public LinkedList<BusSubRoute> getBusSubRoutes(String start, String end) {
        return getBusSubRoutes(BusData.BUS_STOPS.get(start), BusData.BUS_STOPS.get(end));
    }
    private LinkedList<BusSubRoute> getBusSubRoutes(BusStop start, BusStop end) {
        LinkedList<BusStop> busStops = getBusRoute(start, end);
        return getBusSubRoutes(busStops);
    }
    public class BusSubRoute {
        public LinkedList<BusStop> busStops;
        public ArrayList<BusService> busServices;

        public BusSubRoute(List<BusStop> busStops, List<BusService> busServices) {
            this.busStops = new LinkedList<>(busStops);
            this.busServices = new ArrayList<BusService>(busServices);
        }

        @Override
        public String toString() {
            String result = "You can take bus number(s) ";
            for(BusService b : busServices) {
                result += b.getServiceNo() + ", ";
            }
            result = result.substring(0, result.length() - 2) + "\nFor the following " + busStops.size() + " bus stops:\n"; //remove last ", "
            for(BusStop b : busStops) {
                result += b.getName() + "\n";
            }
            return result;
        }
        
    }
}
