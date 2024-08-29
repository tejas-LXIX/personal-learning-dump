package org.parkinglot.parkinglots;

import org.parkinglot.model.Car;
import org.parkinglot.model.Slot;
import org.parkinglot.parkinglotstrategies.ParkingLotStrategy;

import java.util.HashMap;
import java.util.Map;

public abstract class ParkingLot {

    private static final int MAX_CAPACITY = 100000;

    protected final int capacity;

    protected Map<Integer, Slot> slots;

    protected ParkingLotStrategy parkingLotStrategy;

    public ParkingLot(int capacity, ParkingLotStrategy parkingLotStrategy) {
        if (capacity > MAX_CAPACITY) {
            throw new RuntimeException();
        }
        this.capacity = capacity;
        this.slots = new HashMap<>();
        this.parkingLotStrategy = parkingLotStrategy;
    }

    public ParkingLot(ParkingLotStrategy parkingLotStrategy) {
        this.capacity = MAX_CAPACITY;
        this.slots = new HashMap<>();
        this.parkingLotStrategy = parkingLotStrategy;
    }

    public int getCapacity() {
        return capacity;
    }

    public Map<Integer, Slot> getSlots() {
        return slots;
    }

    public ParkingLotStrategy getParkingLotStrategy() {
        return parkingLotStrategy;
    }

    public void addSlot(int i) {
        slots.put(i, new Slot(i));
        parkingLotStrategy.addSlot(i);
    }

    public abstract Slot getSlot(int slotNumber);

    public abstract Slot park(Car car);

    public abstract void makeSlotFree(int slotNumber);
}
