package models;

import dijkstra.Node;

public class BusStop extends Node{
    private String roadName;
    private double latitude;
    private double longitude;

    public BusStop(String id, String roadName, String name, double latitude, double longitude) {
        super(id, name);
        this.roadName = roadName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    
}
