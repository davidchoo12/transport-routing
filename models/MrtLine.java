package models;

import java.awt.Color;
import java.util.List;

public class MrtLine {
    private String lineId;
    private String lineName;
    private Color lineColor;

    public MrtLine(String lineId, String lineName, Color lineColor) {
        this.lineId = lineId;
        this.lineName = lineName;
        this.lineColor = lineColor;
    }

    public String getLineId() {
        return lineId;
    }

    public String getLineName() {
        return lineName;
    }

    public Color getLineColor() {
        return lineColor;
    }
    
}
