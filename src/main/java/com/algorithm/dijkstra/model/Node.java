package com.algorithm.dijkstra.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Node {

  private String name;

  private LinkedList<Node> shortestPath = new LinkedList<>();

  private Integer distance = Integer.MAX_VALUE;

  private Map<Node, Integer> adjacentNodes = new HashMap<>();

  public Node(String name) {
    this.name = name;
  }

  public void addDestination(Node destination, int distance) {
    adjacentNodes.put(destination, distance);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Map<Node, Integer> getAdjacentNodes() {
    return adjacentNodes;
  }

  public void setAdjacentNodes(Map<Node, Integer> adjacentNodes) {
    this.adjacentNodes = adjacentNodes;
  }

  public Integer getDistance() {
    return distance;
  }

  public void setDistance(Integer distance) {
    this.distance = distance;
  }

  public List<Node> getShortestPath() {
    return shortestPath;
  }

  public void setShortestPath(LinkedList<Node> shortestPath) {
    this.shortestPath = shortestPath;
  }

  @Override
  public String toString() {
    return "Node{"
        + "name='"
        + name
        + '\''
        + ", shortestPath="
        + shortestPath
        + ", distance="
        + distance
        + '}';
  }

  public String getShortestPathInString() {
    var linkedList = new LinkedList<>(shortestPath);
    linkedList.add(this);
    StringBuilder sb = new StringBuilder();
    for (var current : linkedList) {
      var name = current.getName();
      var direction = "->";
      sb.append(name);
      sb.append(direction);
    }
    sb.delete(sb.length() - 2, sb.length());
    sb.append(" : ").append(this.getDistance());

    return sb.toString();
  }
}
