package dataReader;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import models.BusLink;
import models.BusService;
import models.BusStop;
public class BusData {
    private static HashSet<BusService> busServiceSet = new HashSet<>();
    public static List<BusService> BUS_SERVICES_SORTED;
    public static HashMap<String, BusStop> BUS_STOPS = new HashMap<>();
    public static List<BusStop> BUS_STOPS_SORTED;
    public static List<BusLink> BUS_LINKS = new ArrayList<>();
    public static HashMap<BusService, ArrayList<BusStop>> BUS_SERVICE_STOP_MAP = new HashMap<>();
    public static HashMap<BusStop, ArrayList<BusService>>  BUS_STOP_SERVICE_MAP = new HashMap<>();
    
    private static final String busServicesJsonPath = "src/dataReader/data/BusServices.json";
    private static final String busStopsJsonPath = "src/dataReader/data/BusStops.json";
    private static final String busRoutesJsonPath = "src/dataReader/data/BusRoutes.json";
    public static void readData() {
        readBusStops();
        readBusServices();
        readLinkData();
        Set<BusService> set = busServiceSet;
        BUS_SERVICES_SORTED = new ArrayList<>(set);
        Collections.sort(BUS_SERVICES_SORTED, (BusService b1, BusService b2) -> b1.getServiceNo().toUpperCase().compareTo(b2.getServiceNo().toUpperCase()));
        BUS_STOPS_SORTED = new ArrayList<>(BUS_STOPS.values());
        Collections.sort(BUS_STOPS_SORTED, (BusStop b1, BusStop b2) -> b1.getName().toUpperCase().compareTo(b2.getName().toUpperCase()));
    }
    private static void readBusStops(){
        try {
            String data = readTextFile(busStopsJsonPath);
            JsonElement jelem = new JsonParser().parse(data);
            JsonArray busStops = jelem.getAsJsonArray();
            for(JsonElement busStopElem : busStops) {
                JsonObject busStop = busStopElem.getAsJsonObject();
                String busStopCode = busStop.get("BusStopCode").getAsString();
                String roadName = busStop.get("RoadName").getAsString();
                String desc = busStop.get("Description").getAsString();
                double latitude = busStop.get("Latitude").getAsDouble();
                double longitude = busStop.get("Longitude").getAsDouble();
                BUS_STOPS.put(busStopCode, new BusStop(busStopCode, roadName, desc, latitude, longitude));
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }
    private static void readLinkData(){
        try {
            String data = readTextFile(busRoutesJsonPath);
            JsonElement jelem = new JsonParser().parse(data);
            JsonArray busRoutes = jelem.getAsJsonArray();
            
            BusStop prevBusStop = null;
            int prevDistance = 0;
            for(JsonElement busRouteElem : busRoutes) {
                JsonObject busRoute = busRouteElem.getAsJsonObject();
                JsonElement distanceElem = busRoute.get("Distance");
                String serviceNo = busRoute.get("ServiceNo").getAsString();
                String busStopCode = busRoute.get("BusStopCode").getAsString();
                if(!(distanceElem instanceof JsonNull)) { //skip route with null distance
                    int distance = (int)(busRoute.get("Distance").getAsFloat() * 1000);
                    
                    int direction = busRoute.get("Direction").getAsInt();
                    BusService temp = new BusService(serviceNo, direction);
                    BusService busService = busServiceSet.stream().filter(b -> b.equals(temp)).findFirst().get();
                    
                    BusStop busStop = BUS_STOPS.get(busStopCode);
                    
                    if(!BUS_STOP_SERVICE_MAP.containsKey(busStop))
                        BUS_STOP_SERVICE_MAP.put(busStop, new ArrayList<>());
                    BUS_STOP_SERVICE_MAP.get(busStop).add(busService);
                    if(!BUS_SERVICE_STOP_MAP.containsKey(busService))
                        BUS_SERVICE_STOP_MAP.put(busService, new ArrayList<>());
                    BUS_SERVICE_STOP_MAP.get(busService).add(busStop);
                    if(distance != 0) {
                        BusLink busLink = new BusLink(Integer.toString(BUS_LINKS.size() + 1), prevBusStop, busStop, distance - prevDistance, false);
                        if (!BUS_LINKS.contains(busLink))
                            BUS_LINKS.add(busLink);
                    }
                    prevBusStop = busStop;
                    prevDistance = distance;
                }
            }
        } catch (JsonSyntaxException ex) {
            ex.printStackTrace();
        }
    }
    
    private static void readBusServices(){
        try {
            String data = readTextFile(busServicesJsonPath);
            JsonElement jelem = new JsonParser().parse(data);
            JsonArray busServices = jelem.getAsJsonArray();
            for(JsonElement busServiceElem : busServices) {
                JsonObject busService = busServiceElem.getAsJsonObject();
                String serviceNo = busService.get("ServiceNo").getAsString();
                int direction = busService.get("Direction").getAsInt();
                busServiceSet.add(new BusService(serviceNo, direction));
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }
    private static String readTextFile(String path) {
        String data = "";
        try {
            ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(Paths.get(path));
            data = lines.stream().map((line) -> line).reduce(data, String::concat);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return data;
    }
}
