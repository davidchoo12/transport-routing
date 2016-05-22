package routing;
import dijkstra.Algorithm;
import models.Mrt;
import dataReader.MrtData;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
public class MrtRoute {
    public MrtRoute() {
//        MrtData.readData();
    }
    public LinkedList<Mrt> getMrtRoute(String start, String end) {
        return getMrtRoute(MrtData.MRTS_MAP.get(start), MrtData.MRTS_MAP.get(end));
    }
    private LinkedList<Mrt> getMrtRoute(Mrt start, Mrt end) {
        Algorithm a = new Algorithm(MrtData.MRTS_LIST_SORTED, MrtData.MRT_LINKS);
        a.execute(start);
        return (LinkedList<Mrt>)a.getPath(end);
    }
}
