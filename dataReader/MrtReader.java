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
import models.Mrt;
import models.MrtLink;
public class MrtReader {
    public static HashMap<String, Mrt> MRTS;
    public static List<MrtLink> MRT_LINKS;
//    public static ArrayList<ArrayList<MrtLink>>
    private static String mrtJsonPath = "src/dataReader/mrt.json";
    public static void readMrtData(){
        MRTS = new HashMap<String, Mrt>();
        MRT_LINKS = new ArrayList<>();
        try {
            String data = readMrtJson();
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
            MRTS.forEach((String key, Mrt obj) -> {
                System.out.print(key + ": ");
                obj.getMrtCodes().forEach((String code) -> {
                    System.out.print(code + ", ");
                });
                System.out.println();
            });
//            for(Mrt m : MRTS) {
//                System.out.println(m.getId() + m.getName());
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
//        try {
//            String mrtDataPathString = "src/dataReader/MRT.txt";
//            Path mrtDataPath = Paths.get(mrtDataPathString);
//            String mrtLineStart = "(start)";
//            String mrtLineEnd = "(end)";
//            int i = 1;
//            ArrayList<String> a = (ArrayList<String>) Files.readAllLines(mrtDataPath);
//            for(String s : a){
//                System.out.println(i++ % 2 == 0 ? "{\n    \"id\": \"" + s.trim() + "\"," : "    \"name\": \"" + s + "\"\n},");
//            };
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
    private static String readMrtJson() {
        String data = "";
        try {
            ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(Paths.get(mrtJsonPath));
            for(String line : lines)
                data += line;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return data;
    }
}
