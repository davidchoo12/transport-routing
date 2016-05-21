package models;
import dijkstra.Link;
public class BusLink extends Link {
    public BusLink(String id, BusStop source, BusStop destination, int weight, boolean isTwoWay) {
        super(id, source, destination, weight, isTwoWay);
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && 
                ((Link)obj).getSource().equals(this.getSource()) && 
                ((Link)obj).getDestination().equals(this.getDestination()) && 
                ((Link)obj).getWeight() == this.getWeight() && 
                ((Link)obj).isTwoWay() == this.isTwoWay();
    }
    
    @Override
    public String toString() {
        return this.getSource().getName()+ " - " + this.getDestination().getName() + " > " + this.getWeight();
    }
}
