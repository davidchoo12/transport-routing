package ajpca1;
import dataReader.BusData;
import dijkstra.*;
import java.util.LinkedList;
import java.util.List;
import dataReader.MrtData;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import routing.*;
import models.Mrt;
import models.BusStop;
//import ui.MainUI;
public class AJPCA1 {

//    public static void main(String[] args) {
//        MrtRoute mr = new MrtRoute();
//        LinkedList<Mrt> llm = mr.getMrtRoute("Bishan", "Bukit Batok");
//        for(Mrt m : llm) {
//            System.out.println(m);
//        }
        
//        BusData.readData();
        
//        BusRoute mr = new BusRoute();
//        LinkedList<BusStop> llbs = mr.getBusRoute("19029", "19081");
//        for(BusStop bs : llbs) {
//            System.out.println(bs);
//        }
//        (new MainUI()).setVisible(true);
//    }
    public static void testAlgorithm() {
        List<Node> mrtlist = new LinkedList<>();
        mrtlist.add(new Node("1", "Changi Airport"));
        mrtlist.add(new Node("2", "MRT2"));
        mrtlist.add(new Node("3", "MRT3"));
        
        List<Link> linklist = new LinkedList<>();
        linklist.add(new Link("link1", mrtlist.get(0), mrtlist.get(1), 2, true));
        linklist.add(new Link("link2", mrtlist.get(1), mrtlist.get(2), 7, true));
        linklist.add(new Link("link3", mrtlist.get(2), mrtlist.get(0), 5, true));
        
        Algorithm a = new Algorithm(linklist);
        a.execute(mrtlist.get(0));
        LinkedList<Node> path = (LinkedList<Node>) a.getPath(mrtlist.get(2));
        
        for(Node n : path){
            System.out.println(n);
        }
    }
}
