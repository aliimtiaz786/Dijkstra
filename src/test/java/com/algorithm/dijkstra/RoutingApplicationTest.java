package com.algorithm.dijkstra;

import static org.assertj.core.api.Assertions.assertThat;

import com.algorithm.dijkstra.model.Graph;
import java.util.List;
import org.junit.jupiter.api.Test;

class RoutingApplicationTest {

  @Test
  void shouldMatchDesiredResults() {
    List<String> routeList =
        List.of(
            "A -> B: 240",
            "A -> C: 70",
            "A -> D: 120",
            "C -> B: 60",
            "D -> E: 480",
            "C -> E: 240",
            "B -> E: 210",
            "E -> A: 300");
    Graph graph = new Graph();
    RoutingApplication.initializeGraphFromUserInput(routeList, graph);

    List<String> operations = List.of("route A -> B", "nearby A, 130");
    var results = RoutingApplication.getOperationResults(operations, graph);
    assertThat(results)
        .isNotNull()
        .isNotEmpty()
        .containsExactly("A->C->B : 130", "C:70, D:120, B:130");
  }
}
