package com.rahulh2048.circuitz_api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MultiGraph {

    // node -> edge(id, destination node)
    private Map<Integer, List<Edge>> adjacencyList;

    public MultiGraph() {
        adjacencyList = new HashMap<>();
    }

    public void addEdge(int edge, int nodeA, int nodeB) {
        if (!adjacencyList.containsKey(nodeA)) {
            adjacencyList.put(nodeA, new ArrayList<>());
        }

        if (!adjacencyList.containsKey(nodeB)) {
            adjacencyList.put(nodeB, new ArrayList<>());
        }

        adjacencyList.get(nodeA).add(new Edge(edge, nodeB));
        adjacencyList.get(nodeB).add(new Edge(edge, nodeA));
    }

    public List<List<Integer>> cycleBasis() {
        if (adjacencyList.isEmpty()) return List.of();

        Map<Integer, Edge> spanningTree = new HashMap<>();
        spanningTree.put(0, null);

        List<List<Integer>> cycles = new ArrayList<>();

        findCycles(0, spanningTree, new HashSet<>(), cycles);

        return cycles;
    }

    private void findCycles(
        int node,
        Map<Integer, Edge> spanningTree,
        Set<Integer> vistedEdges,
        List<List<Integer>> cycles
    ) {
        for (Edge edge : adjacencyList.get(node)) {
            if (vistedEdges.contains(edge.id())) {
                continue;
            }
            vistedEdges.add(edge.id());
            if (spanningTree.containsKey(edge.node())) {
                cycles.add(reconstructCycle(node, edge.node(), edge.id(), spanningTree));
            } else {
                spanningTree.put(edge.node(), new Edge(edge.id(), node));
                findCycles(edge.node(), spanningTree, vistedEdges, cycles);
            }
        }
    }

    private List<Integer> reconstructCycle(int startNode, int endNode, int backEdge, Map<Integer, Edge> spanningTree) {
        List<Integer> cycle = new ArrayList<>();

        cycle.add(backEdge);

        int node = startNode;

        while (node != endNode) {
            Edge parentEdge = spanningTree.get(node);
            cycle.add(parentEdge.id());
            node = parentEdge.node();
        }

        return cycle;
    }
}
