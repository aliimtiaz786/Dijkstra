package com.algorithm.dijkstra.service;

import com.algorithm.dijkstra.model.Graph;
import com.algorithm.dijkstra.model.Node;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

public class RoutingService {

  private RoutingService() {}

  public static Graph calculateShortestPathFromSource(Graph graph, Node source) {

    source.setDistance(0);

    Set<Node> settledNodes = new HashSet<>();
    Set<Node> unsettledNodes = new HashSet<>();
    unsettledNodes.add(source);

    while (!unsettledNodes.isEmpty()) {
      var currentNode = getLowestDistanceNode(unsettledNodes);
      unsettledNodes.remove(currentNode);
      var adjacentNodesEntrySet = getAdjacentNodesEntrySet(currentNode);
      for (Entry<Node, Integer> adjacencyPair : adjacentNodesEntrySet) {
        var adjacentNode = adjacencyPair.getKey();
        calculateMinimumDistance(
            settledNodes, unsettledNodes, currentNode, adjacencyPair, adjacentNode);
      }
      settledNodes.add(currentNode);
    }
    return graph;
  }

  private static void calculateMinimumDistance(
      Set<Node> settledNodes,
      Set<Node> unsettledNodes,
      Node currentNode,
      Entry<Node, Integer> adjacencyPair,
      Node adjacentNode) {
    Optional.ofNullable(adjacencyPair.getValue())
        .ifPresent(
            edgeWeigh -> {
              if (!settledNodes.contains(adjacentNode)) {
                calculateMinimumDistance(adjacentNode, edgeWeigh, currentNode);
                unsettledNodes.add(adjacentNode);
              }
            });
  }

  private static Set<Entry<Node, Integer>> getAdjacentNodesEntrySet(Node currentNode) {
    return Optional.ofNullable(currentNode)
        .map(Node::getAdjacentNodes)
        .map(Map::entrySet)
        .orElse(Collections.emptySet());
  }

  private static void calculateMinimumDistance(
      Node evaluationNode, Integer edgeWeigh, Node sourceNode) {
    Integer sourceDistance = sourceNode.getDistance();
    if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
      evaluationNode.setDistance(sourceDistance + edgeWeigh);
      LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
      shortestPath.add(sourceNode);
      evaluationNode.setShortestPath(shortestPath);
    }
  }

  private static Node getLowestDistanceNode(Set<Node> unsettledNodes) {
    Node lowestDistanceNode = null;
    int lowestDistance = Integer.MAX_VALUE;
    for (Node node : unsettledNodes) {
      int nodeDistance = node.getDistance();
      if (nodeDistance < lowestDistance) {
        lowestDistance = nodeDistance;
        lowestDistanceNode = node;
      }
    }
    return lowestDistanceNode;
  }
}
