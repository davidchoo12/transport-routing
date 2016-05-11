package dijkstra;
public class Link {
    private final String id;
    private final Node source;
    private final Node destination;
    private final int weight;
    private final boolean isTwoWay;
    
    public Link(String id, Node source, Node destination, int weight, boolean isTwoWay) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.weight = weight;
        this.isTwoWay = isTwoWay;
    }

    public String getId() {
        return id;
    }

    public Node getSource() {
        return source;
    }

    public Node getDestination() {
        return destination;
    }

    public int getWeight() {
        return weight;
    }
    
    public boolean isTwoWay() {
        return isTwoWay;
    }
    
    @Override
    public String toString(){
        return source + " " + destination;
    }
    
    public boolean isLinkOf(Node source, Node destination) {
        boolean sourceAndDestination = this.source.equals(source) && this.destination.equals(destination);
        return isTwoWay ? (sourceAndDestination || this.source.equals(destination) && this.destination.equals(source)) : sourceAndDestination;
    }
    
    public boolean isLinkOf(Node node) {
        return this.source.equals(node) || this.destination.equals(node);
    }
    
    public Node getLinkedNodeOf(Node node) {
        return this.source.equals(node) ? this.destination : this.source;
    }
}
