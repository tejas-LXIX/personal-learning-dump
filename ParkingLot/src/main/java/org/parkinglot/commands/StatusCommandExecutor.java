package org.parkinglot.commands;

import org.parkinglot.OutputPrinter;
import org.parkinglot.model.Command;
import org.parkinglot.model.Slot;
import org.parkinglot.service.ParkingLotService;

import java.util.List;

public class StatusCommandExecutor extends CommandExecutor {

    public static final String COMMAND_NAME = "status";

    public StatusCommandExecutor(ParkingLotService parkingLotService, OutputPrinter outputPrinter) {
        super(parkingLotService, outputPrinter);
    }


    @Override
    public boolean validate(Command command) {
        return command.getArgs().isEmpty();
    }

    @Override
    public void execute(Command command) {
        List<Slot> occupiedSlots = parkingLotService.getOccupiedSlots();
        outputPrinter.printWithNewLine("Slot No. Registration No Colour");
        for(Slot slot : occupiedSlots) {
            outputPrinter.printWithNewLine(slot.getNumber() + " " + slot.getParkedCar().getRegNumber() + " " + slot.getParkedCar().getColour());
        }
    }
}
