package models;
import dijkstra.Node;
import java.util.ArrayList;
import java.util.List;
public class Mrt extends Node{
    private List<String> mrtCodes;
    private MrtLine mrtLine;
    public Mrt(String id, String mrtCode, String name, MrtLine mrtLine) {
        super(id, name);
        this.mrtCodes = new ArrayList<>();
        this.mrtCodes.add(mrtCode);
        this.mrtLine = mrtLine;
    }
    public void addMrtCode(String mrtCode) {
        this.mrtCodes.add(mrtCode);
    }
    public List<String> getMrtCodes() {
        return this.mrtCodes;
    }
    public boolean hasCode(String code) {
        return this.mrtCodes.contains(code);
    }

    @Override
    public String toString() {
        String mrtCodes = "(";
        for(String mrtCode : this.mrtCodes)
            mrtCodes += mrtCode + "|";
        mrtCodes = mrtCodes.substring(0, mrtCodes.length()-1); //remove last |
        mrtCodes += ")";
        return this.getName() + " " + mrtCodes;
    } 

    public MrtLine getMrtLine() {
        return mrtLine;
    }
}
