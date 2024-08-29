package org.parkinglot.parkinglots;

import org.parkinglot.model.Car;
import org.parkinglot.model.Slot;
import org.parkinglot.parkinglotstrategies.SimpleParkingLotStrategy;
import java.util.Map;

public class SimpleParkingLot extends ParkingLot {

    public SimpleParkingLot(int capacity) {
        super(capacity, new SimpleParkingLotStrategy());
    }

    public SimpleParkingLot() {
        super(new SimpleParkingLotStrategy());
    }

    public int getCapacity() {
        return capacity;
    }

    public Map<Integer, Slot> getSlots() {
        return slots;
    }

    @Override
    public Slot getSlot(int slotNumber) {
        if (slotNumber > getCapacity() || slotNumber <= 0) {
            throw new RuntimeException();
        }
        if (!slots.containsKey(slotNumber)) {
            slots.put(slotNumber, new Slot(slotNumber));
        }
        return slots.get(slotNumber);
    }

    @Override
    public Slot park(Car car) {
        int slotNumber = parkingLotStrategy.getNextOptimalSlot();
        Slot slot = getSlot(slotNumber);
        if(!slot.isSlotEmpty()) {
            throw new RuntimeException();
        }
        slot.assignCar(car);
        parkingLotStrategy.removeSlot(slotNumber);
        return slot;
    }

    @Override
    public void makeSlotFree(int slotNumber) {
        Slot slot = getSlot(slotNumber);
        slot.unassignCar();
        parkingLotStrategy.addSlot(slotNumber);
    }
}
