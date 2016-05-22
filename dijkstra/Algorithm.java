package dijkstra;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
public class Algorithm {
    private final List<? extends Link> links;
    private Set<Node> settledNodes;
    private Set<Node> unsettledNodes;
    private Map<Node, Node> predecessors;
    private Map<Node, Integer> distance;
    public Algorithm(List<? extends Link> links){
        this.links = links;
    }
    public void execute(Node source){
        settledNodes = new HashSet<Node>();
        unsettledNodes = new HashSet<Node>();
        distance = new HashMap<Node, Integer>();
        predecessors = new HashMap<Node, Node>();
        distance.put(source, 0);
        unsettledNodes.add(source);
        while(unsettledNodes.size() > 0){
            Node node = getMinimum(unsettledNodes);
            settledNodes.add(node);
            unsettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }
    // get the node that has least distance from the set of nodes
    private Node getMinimum(Set<Node> nodes) {
        Node minimum = null;
        for (Node node : nodes) {
            if (minimum == null) {
                minimum = node;
            } else if (getShortestDistance(node) < getShortestDistance(minimum)) {
                minimum = node;
            }
        }
        return minimum;
    }
    
    private void findMinimalDistances(Node node) {
        List<Node> adjacentNodes = getNeighbors(node);
        for (Node target : adjacentNodes) {
            //if the target node doesnt exist in the map of distances of nodes, add the target node into the map of distances
            if (getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node) + getDistance(node, target));
                predecessors.put(target, node);
                unsettledNodes.add(target);
            }
        }
    }
    
    private int getShortestDistance(Node destination) {
        Integer d = distance.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }
    //get distance from 2 neighbouring nodes, target is always a neighbouring node
    private int getDistance(Node node, Node target) {
        for (Link link : links) {
            if (link.isLinkOf(node, target)) {
                return link.getWeight();
            }
        }
        throw new RuntimeException("Should not happen");
    }
    
    private List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<Node>();
        for (Link link : links) {
            //find neighbouring links sourcing from the node and destination node is not in settledNodes
            if (link.isTwoWay() && link.isLinkOf(node) && !settledNodes.contains(link.getLinkedNodeOf(node))) {
                neighbors.add(link.getLinkedNodeOf(node));
            }
            else if (link.getSource().equals(node) && !settledNodes.contains(link.getDestination())) {
                neighbors.add(link.getDestination());
            }
        }
        return neighbors;
    }
    
    public LinkedList<? extends Node> getPath(Node target) {
        LinkedList<Node> path = new LinkedList<>();
        Node step = target;
        // check if a path exists
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }
}
