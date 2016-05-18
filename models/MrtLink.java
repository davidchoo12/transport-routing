package models;
import dijkstra.Link;
public class MrtLink extends Link{
    public MrtLink(String id, Mrt source, Mrt destination, int weight, boolean isTwoWay) {
        super(id, source, destination, weight, isTwoWay);
    }
}
