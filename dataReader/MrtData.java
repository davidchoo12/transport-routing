package dataReader;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import models.Mrt;
import models.MrtLink;
public class MrtData {
    public static HashMap<String, Mrt> MRTS = new HashMap<>();
    public static List<MrtLink> MRT_LINKS = new ArrayList<>();
//    public static ArrayList<ArrayList<MrtLink>>
    private static String mrtJsonPath = "src/dataReader/mrt.json";
    private static String linksJsonPath = "src/dataReader/links.json";
    public static void readData() {
        readMrtData();
        readLinkData();
    }
    private static void readMrtData(){
        try {
            String data = readTextFile(mrtJsonPath);
            JsonElement jelem = new JsonParser().parse(data);
            JsonArray mrtLines = jelem.getAsJsonArray();
            for(JsonElement mrtLineElem : mrtLines) {
                JsonObject mrtLine = mrtLineElem.getAsJsonObject();
                String lineName = mrtLine.get("lineName").getAsString();
                for(JsonElement mrtStationElem : mrtLine.get("stations").getAsJsonArray()) {
                    JsonObject mrtStation = mrtStationElem.getAsJsonObject();
                    String id = mrtStation.get("id").getAsString();
                    String name = mrtStation.get("name").getAsString();
                    
                    if(MRTS.containsKey(name))
                        MRTS.get(name).addMrtCode(id);
                    else
                        MRTS.put(name, new Mrt(Integer.toString(MRTS.size() + 1), id, name));
                }
            }
//            MRTS.forEach((String key, Mrt obj) -> {
//                System.out.print(key + ": ");
//                obj.getMrtCodes().forEach((String code) -> {
//                    System.out.print(code + ", ");
//                });
//                System.out.println();
//            });
            
//            for(Mrt m : MRTS) {
//                System.out.println(m.getId() + m.getName());
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void readLinkData(){
        try {
            String data = readTextFile(linksJsonPath);
            JsonElement jelem = new JsonParser().parse(data);
            JsonArray mrtLinks = jelem.getAsJsonArray();
            for(JsonElement mrtLinkElem : mrtLinks) {
                JsonObject mrtLink = mrtLinkElem.getAsJsonObject();
                String id1 = mrtLink.get("id1").getAsString();
                String id2 = mrtLink.get("id2").getAsString();
                int time = mrtLink.get("time").getAsInt();
                Mrt mrt1 = null, mrt2 = null;
                for(Map.Entry e : MRTS.entrySet()) {
                    Mrt mrt = (Mrt) e.getValue();
                    if(mrt.hasCode(id1))
                        mrt1 = mrt;
                    else if (mrt.hasCode(id2))
                        mrt2 = mrt;
                }
                MRT_LINKS.add(new MrtLink(Integer.toString(MRT_LINKS.size()+1), mrt1, mrt2, time, true));
            }
//            for(MrtLink ml : MRT_LINKS) {
//                System.out.println(ml.toString() + ml.getWeight());
//            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
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
