package dataReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import models.Mrt;
import models.MrtLink;
public class MrtReader {
    public static List<Mrt> MRTS;
    public static List<MrtLink> MRT_LINKS;
//    public static ArrayList<ArrayList<MrtLink>>
    public static void read(){
        try {
            String mrtDataPathString = "src/dataReader/MRT.txt";
            Path mrtDataPath = Paths.get(mrtDataPathString);
            String mrtLineStart = "(start)";
            String mrtLineEnd = "(end)";
//            BufferedReader br = new BufferedReader(new FileReader(mrtDataPathString));
//            String tempLine;
//            while((tempLine = br.readLine()) != null) {
//                if(tempLine.equals(mrtLineStart)) {
//                    
//                }
//            }
            int i = 1;
            ArrayList<String> a = (ArrayList<String>) Files.readAllLines(mrtDataPath);
            for(String s : a){
                System.out.println(i++ % 2 == 0 ? "{\n    \"id\": \"" + s.trim() + "\"," : "    \"name\": \"" + s + "\"\n},");
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
