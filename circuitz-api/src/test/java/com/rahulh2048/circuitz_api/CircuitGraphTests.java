package com.rahulh2048.circuitz_api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CircuitGraphTests {

    @Test
    void detectsNoCyclesInStraightLineGraph() {
        CircuitGraph circuitGraph = new CircuitGraph();

        // 0 --- 1 --- 2 --- 3 --- 4 --- 5

        circuitGraph.addEdge(0, 0, 1);
        circuitGraph.addEdge(1, 1, 2);
        circuitGraph.addEdge(2, 2, 3);
        circuitGraph.addEdge(3, 3, 4);
        circuitGraph.addEdge(4, 4, 5);

        assertEquals(0, circuitGraph.cycleBasis().size());
    }

    @Test
    void detectsOneCycleInTriangleGraph() {
        CircuitGraph circuitGraph = new CircuitGraph();

        //     1
        //    / \
        //   0---2

        circuitGraph.addEdge(0, 0, 1);
        circuitGraph.addEdge(1, 1, 2);
        circuitGraph.addEdge(2, 2, 0);

        assertEquals(1, circuitGraph.cycleBasis().size());
    }

    @Test
    void detectsOneCycleInSquareGraph() {
        CircuitGraph circuitGraph = new CircuitGraph();

        // 0 ---- 1
        // |      |
        // |      |
        // 3 ---- 2

        circuitGraph.addEdge(0, 0, 1);
        circuitGraph.addEdge(1, 1, 2);
        circuitGraph.addEdge(2, 2, 3);
        circuitGraph.addEdge(3, 3, 0);

        assertEquals(1, circuitGraph.cycleBasis().size());
    }

    @Test
    void detectsTwoCyclesInSquareWithDiagonalGraph() {
        CircuitGraph circuitGraph = new CircuitGraph();

        // 0 ---- 1
        // |    / |
        // |  /   |
        // 3 ---- 2

        circuitGraph.addEdge(0, 0, 1);
        circuitGraph.addEdge(1, 1, 2);
        circuitGraph.addEdge(2, 2, 3);
        circuitGraph.addEdge(3, 3, 0);
        circuitGraph.addEdge(4, 1, 3);

        assertEquals(2, circuitGraph.cycleBasis().size());
    }

    @Test
    void detectsThreeCyclesInSquareWithCrossGraph() {
        CircuitGraph circuitGraph = new CircuitGraph();

        // 0 ---- 1
        // | \  / |
        // | /  \ |
        // 3 ---- 2

        circuitGraph.addEdge(0, 0, 1);
        circuitGraph.addEdge(1, 1, 2);
        circuitGraph.addEdge(2, 2, 3);
        circuitGraph.addEdge(3, 3, 0);
        circuitGraph.addEdge(4, 1, 3);
        circuitGraph.addEdge(5, 2, 0);

        assertEquals(3, circuitGraph.cycleBasis().size());
    }

    @Test
    void detectsOneCycleWhenTwoParallelEdges() {
        CircuitGraph circuitGraph = new CircuitGraph();

        // 0=1

        circuitGraph.addEdge(0, 0, 1);
        circuitGraph.addEdge(1, 0, 1);

        assertEquals(1, circuitGraph.cycleBasis().size());
    }

    @Test
    void detectsTwoCyclesWhenThreeParallelEdges() {
        CircuitGraph circuitGraph = new CircuitGraph();

        // 0 - (3 edges) - 1
        circuitGraph.addEdge(0, 0, 1);
        circuitGraph.addEdge(1, 0, 1);
        circuitGraph.addEdge(2, 0, 1);

        assertEquals(2, circuitGraph.cycleBasis().size());
    }

    @Test
    void detectsTwoCyclesWhenTriangleWithParallel() {
        CircuitGraph circuitGraph = new CircuitGraph();

        //       1
        //      / \
        //     /   \
        //    0=====2

        circuitGraph.addEdge(0, 0, 1);
        circuitGraph.addEdge(1, 1, 2);
        circuitGraph.addEdge(2, 2, 0);
        circuitGraph.addEdge(3, 2, 0);

        assertEquals(2, circuitGraph.cycleBasis().size());
    }

    @Test
    void detectsThreeCyclesWhenTriangleWithTwoParallel() {
        CircuitGraph circuitGraph = new CircuitGraph();

        //       1
        //      / \\
        //     /   \\
        //    0=====2

        circuitGraph.addEdge(0, 0, 1);
        circuitGraph.addEdge(1, 1, 2);
        circuitGraph.addEdge(2, 2, 0);
        circuitGraph.addEdge(3, 2, 0);
        circuitGraph.addEdge(4, 2, 1);

        assertEquals(3, circuitGraph.cycleBasis().size());
    }

    @Test
    void detectsTwoCyclesWhenTwoTrianglesSharingANode() {
        CircuitGraph circuitGraph = new CircuitGraph();

        //   1
        //  / \
        // 0---2
        // |
        // 3---4
        //  \ /
        //   5

        circuitGraph.addEdge(0, 0, 1);
        circuitGraph.addEdge(1, 1, 2);
        circuitGraph.addEdge(2, 0, 2);
        circuitGraph.addEdge(3, 0, 3);
        circuitGraph.addEdge(4, 3, 4);
        circuitGraph.addEdge(5, 4, 5);
        circuitGraph.addEdge(6, 5, 3);

        assertEquals(2, circuitGraph.cycleBasis().size());
    }
}
