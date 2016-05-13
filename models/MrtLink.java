package models;
import dijkstra.Link;
import dijkstra.Node;
public class MrtLink extends Link{
    public MrtLink(String id, Node source, Node destination, int weight, boolean isTwoWay) {
        super(id, source, destination, weight, isTwoWay);
    }
}
