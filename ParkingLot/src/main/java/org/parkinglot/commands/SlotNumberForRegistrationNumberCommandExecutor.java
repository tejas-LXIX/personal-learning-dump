package org.parkinglot.commands;

import org.parkinglot.OutputPrinter;
import org.parkinglot.model.Car;
import org.parkinglot.model.Command;
import org.parkinglot.model.Slot;
import org.parkinglot.service.ParkingLotService;

import java.util.List;

public class SlotNumberForRegistrationNumberCommandExecutor extends CommandExecutor {

    public static final String COMMAND_NAME = "slot_number_for_registration_number";

    public SlotNumberForRegistrationNumberCommandExecutor(ParkingLotService parkingLotService, OutputPrinter outputPrinter) {
        super(parkingLotService, outputPrinter);
    }


    @Override
    public boolean validate(Command command) {
        return command.getArgs().size()==1;
    }

    @Override
    public void execute(Command command) {
        String regNumber = command.getArgs().get(0);
        Slot slot = parkingLotService.findSlotForRegNumber(regNumber);
        if(slot!=null) {
            outputPrinter.printWithNewLine("The car with regNumber: " + regNumber + " is located at slot number: " + slot.getNumber());
        }
    }
}
