package dijkstra;
public class Node {
    final private String id, name;
    public Node(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        return prime * result + ((id == null) ? 0 : id.hashCode());
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj != null && this == obj && getClass() == obj.getClass() && ((Node)obj).id.equals(id);
    }
    
    @Override
    public String toString(){
        return name;
    }
}
