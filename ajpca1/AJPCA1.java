package ajpca1;
import dijkstra.*;
import java.util.LinkedList;
import java.util.List;
public class AJPCA1 {

    public static void main(String[] args) {
        List<Node> mrtlist = new LinkedList<>();
        mrtlist.add(new Node("1", "Changi Airport"));
        mrtlist.add(new Node("2", "MRT2"));
        mrtlist.add(new Node("3", "MRT3"));
        
        List<Link> linklist = new LinkedList<>();
        linklist.add(new Link("link1", mrtlist.get(0), mrtlist.get(1), 2, true));
        linklist.add(new Link("link2", mrtlist.get(1), mrtlist.get(2), 7, true));
        linklist.add(new Link("link3", mrtlist.get(2), mrtlist.get(0), 5, true));
        
        Algorithm a = new Algorithm(mrtlist, linklist);
        a.execute(mrtlist.get(0));
        LinkedList<Node> path = a.getPath(mrtlist.get(2));
        
        for(Node n : path){
            System.out.println(n);
        }
    }
}
