package dataReader;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import models.BusLink;
import models.BusService;
import models.BusStop;
public class BusData {
    public static HashSet<BusService> BUSES = new HashSet<>();
    public static HashMap<String, BusStop> BUS_STOPS = new HashMap<>();
    public static List<BusLink> BUS_LINKS = new ArrayList<>();
    public static HashMap<BusService, ArrayList<BusLink>> BUS_ROUTES = new HashMap<>();
    
//    public static ArrayList<ArrayList<MrtLink>>
    private static String busServicesJsonPath = "src/dataReader/BusServices.json";
    private static String busStopsJsonPath = "src/dataReader/BusStops.json";
    private static String busRoutesJsonPath = "src/dataReader/BusRoutes.json";
    public static void readData() {
        readBusStops();
        readBusServices();
        readLinkData();
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
            BUS_STOPS.forEach((String key, BusStop obj) -> {
                System.out.println(key + ": " + obj.getName());
            });
//            System.out.println(BUS_STOPS.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void readLinkData(){
        try {
            String data = readTextFile(busRoutesJsonPath);
            JsonElement jelem = new JsonParser().parse(data);
            JsonArray busRoutes = jelem.getAsJsonArray();
            
//            String prevServiceNo;
            BusStop prevBusStop = null;
            int prevDistance = 0;
            for(JsonElement busRouteElem : busRoutes) {
                JsonObject busRoute = busRouteElem.getAsJsonObject();
                JsonElement distanceElem = busRoute.get("Distance");
//                int distance = 0;
                String serviceNo = busRoute.get("ServiceNo").getAsString();
                String busStopCode = busRoute.get("BusStopCode").getAsString();
                if(!(distanceElem instanceof JsonNull)) { //skip route with null distance
                    int distance = (int)(busRoute.get("Distance").getAsFloat() * 1000);
                    
                    int direction = busRoute.get("Direction").getAsInt();
                    BusService temp = new BusService(serviceNo, direction);
                    BusService busService = BUSES.stream().filter(b -> b.equals(temp)).findFirst().get();
                    
                    BusStop busStop = BUS_STOPS.get(busStopCode);
//                    if(busStop == null) {
//                        System.out.println("asdf" + busStopCode);
//                    }
                    if(distance != 0) {
                        BusLink busLink = new BusLink(Integer.toString(BUS_LINKS.size() + 1), prevBusStop, busStop, distance - prevDistance, false);
                        if (!BUS_LINKS.contains(busLink))
                            BUS_LINKS.add(busLink);
                        if(!BUS_ROUTES.containsKey(busService))
                            BUS_ROUTES.put(busService, new ArrayList<>());
                        BUS_ROUTES.get(busService).add(busLink);
                    }
                    prevBusStop = busStop;
                    prevDistance = distance;
                }
//                else
//                    System.out.println(serviceNo + ", " + busStopCode);
            }
//            ArrayList<BusService> bs = new ArrayList<>();
//            for (Map.Entry<BusService, ArrayList<BusLink>> entry : BUS_ROUTES.entrySet()) {
//                bs.add(entry.getKey());
//                if(entry.getKey().getServiceNo().equals("812"))
//                    for(BusLink b : entry.getValue())
//                        System.out.println(b);
//                System.out.println(entry.getKey().getServiceNo()+" : "+entry.getValue());
//            }
//            System.out.println(BUSES.size());
//            BUSES.removeAll(bs);
//            for (BusService b : BUSES)
//                System.out.println(b);
//            System.out.println(BUS_ROUTES.size());
//            for(BusLink b : BUS_LINKS)
//                System.out.println(b);
        } catch (Exception ex) {
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
                BUSES.add(new BusService(serviceNo, direction));
//                if(serviceNo.equals("307"))
//                    System.out.println("asdf");
//                System.out.println(BUSES.add(new BusService(serviceNo, direction)));
//                System.out.println(BUSES.contains(new BusService(serviceNo, direction)));
//                System.out.println(BUSES.add(new BusService(serviceNo, direction)));
            }
//            BUSES.forEach((BusService obj) -> {
//                System.out.println(obj.getServiceNo() + ", " + obj.getDirection());
//            });
//            System.out.println(BUSES.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    private static void getBusStops(String servno, int dir) {
//        BusService temp = new BusService(servno, dir);
//        BusService busService = null;
//        if(BUSES.stream().filter(b -> b.equals(temp)).findFirst().isPresent())
//            busService = BUSES.stream().filter(b -> b.getServiceNo().equals(servno)).findFirst().get();
//        else
//            System.out.println("no bus service");
//        for(BusLink bl : BUS_ROUTES.get(busService))
//            System.out.println(bl);
//    }
    private static String readTextFile(String path) {
        String data = "";
        try {
            ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(Paths.get(path));
            for(String line : lines)
                data += line;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return data;
    }
}
