package routing;
import dijkstra.Algorithm;
import models.Mrt;
import dataReader.MrtData;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
public class MrtRoute {
    public MrtRoute() {
        MrtData.readData();
    }
    public LinkedList<Mrt> getMrtRoute(String start, String end) {
        return getMrtRoute(MrtData.MRTS.get(start), MrtData.MRTS.get(end));
    }
    private LinkedList<Mrt> getMrtRoute(Mrt start, Mrt end) {
        List<Mrt> mrtList = new ArrayList<Mrt>(MrtData.MRTS.values());
        Algorithm a = new Algorithm(mrtList, MrtData.MRT_LINKS);
        a.execute(start);
        return (LinkedList<Mrt>)a.getPath(end);
    }
}
