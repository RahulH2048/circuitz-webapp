package com.rahulh2048.circuitz_api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class CircuitVerifierService {

    public CircuitVerifierService() {}

    public CircuitVerifierResponse verifyCircuit(List<Element> elements) {
        Map<Integer, Element> elementMap = new HashMap<>();
        CircuitGraph circuitGraph = new CircuitGraph();

        boolean[] flags = new boolean[3];

        for (Element element : elements) {
            elementMap.put(element.id(), element);

            circuitGraph.addEdge(element.id(), element.nodePos(), element.nodeNeg());

            if (element.voltage() != 0) {
                flags[2] = true;
            }

            if (element.type() == ElementType.CURRENT_SOURCE || element.type() == ElementType.VOLTAGE_SOURCE) {
                continue;
            }

            if (element.voltage() != element.current() * element.resistance()) {
                flags[0] = true;
            }
        }

        for (List<AdjacentEdge> adjacentEdges : circuitGraph.getAdjacencyList().values()) {
            int sum = 0;
            for (AdjacentEdge adjacentEdge : adjacentEdges) {
                int polarity = adjacentEdge.closeTerminal() == Terminal.NEG ? 1 : -1;

                sum += elementMap.get(adjacentEdge.edge()).current() * polarity;
            }
            if (sum != 0) {
                flags[1] = true;
                break;
            }
        }

        for (List<CycleEdge> cycle : circuitGraph.cycleBasis()) {
            flags[2] = false;
            int sum = 0;
            for (CycleEdge cycleEdge : cycle) {
                int polarity = cycleEdge.farTerminal() == Terminal.POS ? 1 : -1;

                sum += elementMap.get(cycleEdge.edge()).voltage() * polarity;
            }
            if (sum != 0) {
                flags[2] = true;
                break;
            }
        }

        return new CircuitVerifierResponse(!flags[0], !flags[1], !flags[2]);
    }
}
