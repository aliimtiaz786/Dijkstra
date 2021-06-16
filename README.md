# Dijkstra Routing Service

This routing service is based on Dijkstra algorithm. which takes graph as an input and outputs the shortest path
for destination node and also gives you information about nearby nodes within specific range.


## Input
```
8
A -> B: 240
A -> C: 70
A -> D: 120
C -> B: 60
D -> E: 480
C -> E: 240
B -> E: 210
E -> A: 300
```
## Supported Operations

```
route A -> B
nearby A, 130
```

## Output

```
A -> C -> B: 130
C: 70, D: 120, B: 130
```