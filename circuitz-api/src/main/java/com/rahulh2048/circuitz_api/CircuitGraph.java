package com.rahulh2048.circuitz_api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CircuitGraph {

    private Map<Integer, List<AdjacentEdge>> adjacencyList;

    public CircuitGraph() {
        adjacencyList = new HashMap<>();
    }

    public Map<Integer, List<AdjacentEdge>> getAdjacencyList() {
        return adjacencyList;
    }

    public void addEdge(int edge, int nodePos, int nodeNeg) {
        if (!adjacencyList.containsKey(nodePos)) {
            adjacencyList.put(nodePos, new ArrayList<>());
        }

        if (!adjacencyList.containsKey(nodeNeg)) {
            adjacencyList.put(nodeNeg, new ArrayList<>());
        }

        adjacencyList.get(nodePos).add(new AdjacentEdge(nodeNeg, edge, Terminal.POS, Terminal.NEG));
        adjacencyList.get(nodeNeg).add(new AdjacentEdge(nodePos, edge, Terminal.NEG, Terminal.POS));
    }

    public List<List<CycleEdge>> cycleBasis() {
        if (adjacencyList.isEmpty()) return List.of();

        Map<Integer, ParentEdge> spanningTree = new HashMap<>();
        spanningTree.put(0, null);

        List<List<CycleEdge>> cycles = new ArrayList<>();

        dfs(0, spanningTree, new HashSet<>(), cycles);

        return cycles;
    }

    private void dfs(
        int currentNode,
        Map<Integer, ParentEdge> spanningTree,
        Set<Integer> vistedEdges,
        List<List<CycleEdge>> cycles
    ) {
        for (AdjacentEdge adjacentEdge : adjacencyList.get(currentNode)) {
            if (vistedEdges.contains(adjacentEdge.edge())) {
                continue;
            }

            vistedEdges.add(adjacentEdge.edge());

            if (spanningTree.containsKey(adjacentEdge.nextNode())) {
                cycles.add(constructCycle(currentNode, adjacentEdge, spanningTree));
            } else {
                spanningTree.put(
                    adjacentEdge.nextNode(),
                    new ParentEdge(currentNode, adjacentEdge.edge(), adjacentEdge.farTerminal())
                );

                dfs(adjacentEdge.nextNode(), spanningTree, vistedEdges, cycles);
            }
        }
    }

    private List<CycleEdge> constructCycle(
        int startNode,
        AdjacentEdge backEdge,
        Map<Integer, ParentEdge> spanningTree
    ) {
        List<CycleEdge> cycle = new ArrayList<>();

        cycle.add(new CycleEdge(backEdge.edge(), backEdge.farTerminal()));

        int currentNode = startNode;

        while (currentNode != backEdge.nextNode()) {
            ParentEdge parentEdge = spanningTree.get(currentNode);
            cycle.add(new CycleEdge(parentEdge.edge(), parentEdge.farTerminal()));
            currentNode = parentEdge.parentNode();
        }

        return cycle;
    }
}
