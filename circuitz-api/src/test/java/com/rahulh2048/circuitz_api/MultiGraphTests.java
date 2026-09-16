package com.rahulh2048.circuitz_api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MultiGraphTests {

    @Test
    void detectsNoCyclesInStraightLineGraph() {
        MultiGraph multiGraph = new MultiGraph();

        // 0 --- 1 --- 2 --- 3 --- 4 --- 5

        multiGraph.addEdge(0, 0, 1);
        multiGraph.addEdge(1, 1, 2);
        multiGraph.addEdge(2, 2, 3);
        multiGraph.addEdge(3, 3, 4);
        multiGraph.addEdge(4, 4, 5);

        assertEquals(0, multiGraph.cycleBasis().size());
    }

    @Test
    void detectsOneCycleInTriangleGraph() {
        MultiGraph multiGraph = new MultiGraph();

        //     1
        //    / \
        //   0---2

        multiGraph.addEdge(0, 0, 1);
        multiGraph.addEdge(1, 1, 2);
        multiGraph.addEdge(2, 2, 0);

        assertEquals(1, multiGraph.cycleBasis().size());
    }

    @Test
    void detectsOneCycleInSquareGraph() {
        MultiGraph multiGraph = new MultiGraph();

        // 0 ---- 1
        // |      |
        // |      |
        // 3 ---- 2

        multiGraph.addEdge(0, 0, 1);
        multiGraph.addEdge(1, 1, 2);
        multiGraph.addEdge(2, 2, 3);
        multiGraph.addEdge(3, 3, 0);

        assertEquals(1, multiGraph.cycleBasis().size());
    }

    @Test
    void detectsTwoCyclesInSquareWithDiagonalGraph() {
        MultiGraph multiGraph = new MultiGraph();

        // 0 ---- 1
        // |    / |
        // |  /   |
        // 3 ---- 2

        multiGraph.addEdge(0, 0, 1);
        multiGraph.addEdge(1, 1, 2);
        multiGraph.addEdge(2, 2, 3);
        multiGraph.addEdge(3, 3, 0);
        multiGraph.addEdge(4, 1, 3);

        assertEquals(2, multiGraph.cycleBasis().size());
    }

    @Test
    void detectsThreeCyclesInSquareWithCrossGraph() {
        MultiGraph multiGraph = new MultiGraph();

        // 0 ---- 1
        // | \  / |
        // | /  \ |
        // 3 ---- 2

        multiGraph.addEdge(0, 0, 1);
        multiGraph.addEdge(1, 1, 2);
        multiGraph.addEdge(2, 2, 3);
        multiGraph.addEdge(3, 3, 0);
        multiGraph.addEdge(4, 1, 3);
        multiGraph.addEdge(5, 2, 0);

        assertEquals(3, multiGraph.cycleBasis().size());
    }

    @Test
    void detectsOneCycleWhenTwoParallelEdges() {
        MultiGraph multiGraph = new MultiGraph();

        // 0=1

        multiGraph.addEdge(0, 0, 1);
        multiGraph.addEdge(1, 0, 1);

        assertEquals(1, multiGraph.cycleBasis().size());
    }

    @Test
    void detectsTwoCyclesWhenThreeParallelEdges() {
        MultiGraph multiGraph = new MultiGraph();

        // 0 - (3 edges) - 1
        multiGraph.addEdge(0, 0, 1);
        multiGraph.addEdge(1, 0, 1);
        multiGraph.addEdge(2, 0, 1);

        assertEquals(2, multiGraph.cycleBasis().size());
    }

    @Test
    void detectsTwoCyclesWhenTriangleWithParallel() {
        MultiGraph multiGraph = new MultiGraph();

        //       1
        //      / \
        //     /   \
        //    0=====2

        multiGraph.addEdge(0, 0, 1);
        multiGraph.addEdge(1, 1, 2);
        multiGraph.addEdge(2, 2, 0);
        multiGraph.addEdge(3, 2, 0);

        assertEquals(2, multiGraph.cycleBasis().size());
    }

    @Test
    void detectsThreeCyclesWhenTriangleWithTwoParallel() {
        MultiGraph multiGraph = new MultiGraph();

        //       1
        //      / \\
        //     /   \\
        //    0=====2

        multiGraph.addEdge(0, 0, 1);
        multiGraph.addEdge(1, 1, 2);
        multiGraph.addEdge(2, 2, 0);
        multiGraph.addEdge(3, 2, 0);
        multiGraph.addEdge(4, 2, 1);

        assertEquals(3, multiGraph.cycleBasis().size());
    }

    @Test
    void detectsTwoCyclesWhenTwoTrianglesSharingANode() {
        MultiGraph multiGraph = new MultiGraph();

        //   1
        //  / \
        // 0---2
        // |
        // 3---4
        //  \ /
        //   5

        multiGraph.addEdge(0, 0, 1);
        multiGraph.addEdge(1, 1, 2);
        multiGraph.addEdge(2, 2, 0);
        multiGraph.addEdge(3, 0, 3);
        multiGraph.addEdge(4, 3, 4);
        multiGraph.addEdge(5, 4, 5);
        multiGraph.addEdge(6, 5, 3);

        assertEquals(2, multiGraph.cycleBasis().size());
    }
}
