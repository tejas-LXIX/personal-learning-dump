package org.parkinglot.model;

public class Slot {
    private final int number;

    private Car parkedCar;

    public Slot(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public Car getParkedCar() {
        return parkedCar;
    }

    public void assignCar(Car parkedCar) {
        this.parkedCar = parkedCar;
    }

    public void unassignCar() {
        this.parkedCar = null;
    }

    public boolean isSlotEmpty() {
        return parkedCar == null;
    }

}
