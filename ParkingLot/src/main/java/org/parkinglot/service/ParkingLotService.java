package org.parkinglot.service;

import org.parkinglot.model.Car;
import org.parkinglot.model.Slot;
import org.parkinglot.parkinglots.ParkingLot;

import java.util.List;

public interface ParkingLotService {

    public void createParkingLot(ParkingLot parkingLot);

    public Integer park(Car car);

    public void leave(int slotNumber);

    public List<Slot> getOccupiedSlots();

    public void validateParkingLotExists();

    public List<Slot> getSlotsForColour(String colour);

    public Slot findSlotForRegNumber(String regNumber);

    public List<Slot> findAllSlotsWithCarOfGivenColour(String colour);
}
