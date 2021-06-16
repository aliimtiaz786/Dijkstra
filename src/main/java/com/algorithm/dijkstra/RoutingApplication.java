package com.algorithm.dijkstra;

import com.algorithm.dijkstra.model.Graph;
import com.algorithm.dijkstra.model.Node;
import com.algorithm.dijkstra.service.RoutingService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RoutingApplication {

  public static final String ROUTE_PREFIX = "route";
  public static final String ROUTE_SEPARATOR_PREFIX = "->";
  public static final String NEARBY_PREFIX = "nearby";
  public static final String NEARBY_SEPARATOR_PREFIX = ",";

  public static void main(String[] args) throws IOException {

    var reader = new BufferedReader(new InputStreamReader(System.in));
    System.out.print("Please Write no of routes you want : ");
    var noOfRoutes = Integer.parseInt(reader.readLine());
    List<String> routesList = getUserOperationsInput(reader, noOfRoutes, ROUTE_PREFIX);

    var graph = new Graph();
    initializeGraphFromUserInput(routesList, graph);
    System.out.println("Graph initialized successfully");

    System.out.print("Please Write no of operations you want : ");
    var noOfOperations = Integer.parseInt(reader.readLine());
    List<String> operations = getUserOperationsInput(reader, noOfOperations, "operation");
    var results = getOperationResults(operations, graph);
    System.out.println(results);
  }

  public static void initializeGraphFromUserInput(List<String> routeList, Graph graph) {

    for (String route : routeList) {
      var removeSpaces = route.trim().replace(" ", "");
      var splittedOperation = removeSpaces.split(":");
      var sourceDestination = splittedOperation[0].split(ROUTE_SEPARATOR_PREFIX);
      var source = sourceDestination[0];
      var destination = sourceDestination[1];
      var time = splittedOperation[1];

      var sourceNode = getOptionalNode(graph, source).orElse(new Node(source));
      var destinationNode = getOptionalNode(graph, destination).orElse(new Node(destination));
      sourceNode.addDestination(destinationNode, Integer.parseInt(time));
      graph.addNode(sourceNode);
      graph.addNode(destinationNode);
    }
  }

  public static List<String> getOperationResults(List<String> operationList, Graph graph) {

    List<String> results = new ArrayList<>();
    for (String operation : operationList) {
      if (operation.startsWith(ROUTE_PREFIX)) {
        var removeRoutePrefix = operation.trim().replace(ROUTE_PREFIX, "").replace(" ", "");
        var sourceDestination = removeRoutePrefix.split(ROUTE_SEPARATOR_PREFIX);
        var source = sourceDestination[0];
        var destination = sourceDestination[1];
        var sourceNode = getMandatoryNode(graph, source);
        var calculatedShortestPathFromSource =
            RoutingService.calculateShortestPathFromSource(graph, sourceNode);
        var destinationNode = getMandatoryNode(calculatedShortestPathFromSource, destination);
        var distanceInString = destinationNode.getShortestPathInString();
        results.add(distanceInString);
        graph = calculatedShortestPathFromSource;

      } else if (operation.startsWith(NEARBY_PREFIX)) {
        var removeNearByPrefix = operation.trim().replace(NEARBY_PREFIX, "").replace(" ", "");
        var nearbyDistance = removeNearByPrefix.split(NEARBY_SEPARATOR_PREFIX);
        var nearbyNode = nearbyDistance[0];
        var distance = Integer.parseInt(nearbyDistance[1]);

        var nearbyNodeCalculated = getMandatoryNode(graph, nearbyNode);
        var adjacentNodes =
            nearbyNodeCalculated.getAdjacentNodes().keySet().stream()
                .filter(integer -> integer.getDistance() <= distance)
                .map(integer -> integer.getName() + ":" + integer.getDistance())
                .collect(Collectors.joining(", "));
        results.add(adjacentNodes);
      } else {
        throw new IllegalArgumentException("OPERATION NOT SUPPORTED : " + operation);
      }
    }
    return results;
  }

  private static Node getMandatoryNode(Graph graph, String nodeName) {
    return getOptionalNode(graph, nodeName)
        .orElseThrow(
            () -> new IllegalArgumentException("REQUESTED NODE DOES NOT EXISTS : " + nodeName));
  }

  private static Optional<Node> getOptionalNode(Graph graph, String node) {
    return graph.getNodes().stream().filter(p -> p.getName().equals(node)).findFirst();
  }

  private static List<String> getUserOperationsInput(
      BufferedReader reader, int noOfOperations, String type) {
    return IntStream.range(0, noOfOperations)
        .mapToObj(
            p -> {
              System.out.printf("Please mention now next %s no [%s] : ", type, p + 1);
              String s = null;
              try {
                s = reader.readLine();
                return s;
              } catch (IOException e) {
                e.printStackTrace();
                return null;
              }
            })
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }
}
