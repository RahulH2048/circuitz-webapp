package com.rahulh2048.circuitz_api;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CircuitVerifierServiceTests {

    private CircuitVerifierService circuitVerifierService;

    @BeforeEach
    void setUp() {
        circuitVerifierService = new CircuitVerifierService();
    }

    // ==================== 1. OHM'S LAW ====================

    @Test
    void singleResistorOhmsLawHolds() {
        List<Element> elements = List.of(new Element(0, ElementType.RESISTOR, 2.0, 6.0, 3.0, 0, 1));

        assertTrue(circuitVerifierService.verifyCircuit(elements).ohms());
    }

    @Test
    void singleResistorOhmsLawViolated() {
        List<Element> elements = List.of(new Element(0, ElementType.RESISTOR, 2.0, 7.0, 3.0, 0, 1));

        assertFalse(circuitVerifierService.verifyCircuit(elements).ohms());
    }

    @Test
    void singleResistorWithNoCurrentPassesEverything() {
        List<Element> elements = List.of(new Element(0, ElementType.RESISTOR, 0.0, 0.0, 10.0, 0, 1));

        assertTrue(circuitVerifierService.verifyCircuit(elements).ohms());
        assertTrue(circuitVerifierService.verifyCircuit(elements).kcl());
        assertTrue(circuitVerifierService.verifyCircuit(elements).kvl());
    }

    // ==================== 2. KCL ====================

    @Test
    void singleResistorWithCurrentFailsKCL() {
        List<Element> elements = List.of(new Element(0, ElementType.RESISTOR, 2.0, 6.0, 3.0, 0, 1));

        assertFalse(circuitVerifierService.verifyCircuit(elements).kcl());
    }

    @Test
    void balancedTwoNodeCircuitPassesKCL() {
        List<Element> elements = List.of(
            new Element(0, ElementType.RESISTOR, 2.0, 6.0, 3.0, 0, 1),
            new Element(1, ElementType.CURRENT_SOURCE, 2.0, 0.0, 1, 0)
        );

        assertTrue(circuitVerifierService.verifyCircuit(elements).kcl());
    }

    @Test
    void seriesCircuitKCLPassesKVLFails() {
        List<Element> elements = List.of(
            new Element(0, ElementType.RESISTOR, 2.0, 4.0, 2.0, 0, 1),
            new Element(1, ElementType.RESISTOR, 2.0, 6.0, 3.0, 1, 2),
            new Element(2, ElementType.CURRENT_SOURCE, 2.0, 0.0, 2, 0)
        );

        assertTrue(circuitVerifierService.verifyCircuit(elements).kcl());
        assertFalse(circuitVerifierService.verifyCircuit(elements).kvl());
    }

    @Test
    void seriesCircuitTwoNodesUnbalancedFailsKCL() {
        List<Element> elements = List.of(
            new Element(0, ElementType.RESISTOR, 1.0, 6.0, 3.0, 0, 1),
            new Element(1, ElementType.RESISTOR, 1.0, 1.0, 1.0, 0, 2),
            new Element(2, ElementType.RESISTOR, 1.0, 1.0, 1.0, 1, 2)
        );

        assertFalse(circuitVerifierService.verifyCircuit(elements).kcl());
    }

    // ==================== 3. KVL ====================

    @Test
    void seriesCircuitBalancedPassesKVL() {
        List<Element> elements = List.of(
            new Element(0, ElementType.RESISTOR, 2.0, 4.0, 2.0, 0, 1),
            new Element(1, ElementType.RESISTOR, 2.0, -4.0, -2.0, 1, 2),
            new Element(2, ElementType.CURRENT_SOURCE, 2.0, 0.0, 2, 0)
        );

        assertTrue(circuitVerifierService.verifyCircuit(elements).kvl());
    }

    @Test
    void seriesCircuitUnbalancedFailsKVL() {
        List<Element> elements = List.of(
            new Element(0, ElementType.RESISTOR, 2.0, 4.0, 2.0, 0, 1),
            new Element(1, ElementType.RESISTOR, 3.0, 3.0, 1.0, 1, 2),
            new Element(2, ElementType.CURRENT_SOURCE, 2.0, 0.0, 2, 0)
        );

        assertFalse(circuitVerifierService.verifyCircuit(elements).kvl());
    }

    void twoLoopsSharingBranchWithTwoCurrentSourcesPassesAll() {
        List<Element> elements = List.of(
            new Element(0, ElementType.RESISTOR, 6.0, 12.0, 2.0, 0, 1),
            new Element(1, ElementType.RESISTOR, 2.0, 6.0, 3.0, 1, 2),
            new Element(2, ElementType.RESISTOR, 4.0, 4.0, 1.0, 1, 3),
            new Element(3, ElementType.CURRENT_SOURCE, 2.0, -2.0, 2, 3),
            new Element(4, ElementType.CURRENT_SOURCE, 6.0, -16.0, 3, 0)
        );

        assertTrue(circuitVerifierService.verifyCircuit(elements).ohms());
        assertTrue(circuitVerifierService.verifyCircuit(elements).kcl());
        assertTrue(circuitVerifierService.verifyCircuit(elements).kvl());
    }

    // ==================== 4. EDGE CASES ====================

    @Test
    void emptyCircuitPassesEverything() {
        List<Element> elements = List.of();

        assertTrue(circuitVerifierService.verifyCircuit(elements).ohms());
        assertTrue(circuitVerifierService.verifyCircuit(elements).kcl());
        assertTrue(circuitVerifierService.verifyCircuit(elements).kvl());
    }

    @Test
    void zeroResistanceWithNonZeroVoltageFailsOhms() {
        List<Element> elements = List.of(new Element(0, ElementType.RESISTOR, 2.0, 5.0, 0.0, 0, 1));

        assertFalse(circuitVerifierService.verifyCircuit(elements).ohms());
    }

    @Test
    void zeroResistanceWithZeroVoltagePassesOhms() {
        List<Element> elements = List.of(new Element(0, ElementType.RESISTOR, 0.0, 0.0, 0.0, 0, 1));

        assertTrue(circuitVerifierService.verifyCircuit(elements).ohms());
    }

    @Test
    void negativeCurrentPassesOhmsLaw() {
        List<Element> elements = List.of(new Element(0, ElementType.RESISTOR, -2.0, 6.0, -3.0, 0, 1));

        assertTrue(circuitVerifierService.verifyCircuit(elements).ohms());
    }
}
