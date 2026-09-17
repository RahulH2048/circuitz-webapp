package com.rahulh2048.circuitz_api;

public record Element(
    int id,
    ElementType type,
    double current,
    double voltage,
    double resistance,
    int nodePos,
    int nodeNeg
) {
    // Current or voltage source constructor
    public Element(int id, ElementType type, double current, double voltage, int nodePos, int nodeNeg) {
        this(id, type, current, voltage, 0, nodePos, nodeNeg);
    }
}
