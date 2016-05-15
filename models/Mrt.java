package models;
import dijkstra.Node;
import java.util.ArrayList;
import java.util.List;
public class Mrt extends Node{
    private List<String> mrtCodes;
    public Mrt(String id, String mrtCode, String name) {
        super(id, name);
        this.mrtCodes = new ArrayList<>();
        this.mrtCodes.add(mrtCode);
    }
    public void addMrtCode(String mrtCode) {
        this.mrtCodes.add(mrtCode);
    }
    public List<String> getMrtCodes() {
        return this.mrtCodes;
    }
}
