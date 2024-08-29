package org.parkinglot.commands;

import org.parkinglot.OutputPrinter;
import org.parkinglot.model.Command;
import org.parkinglot.model.Slot;
import org.parkinglot.service.ParkingLotService;

import java.util.List;

public class RegistrationNumbersForCarsWithColourCommandExecutor extends CommandExecutor {

    public static final String COMMAND_NAME = "registration_numbers_for_cars_with_colour";

    public RegistrationNumbersForCarsWithColourCommandExecutor(ParkingLotService parkingLotService, OutputPrinter outputPrinter) {
        super(parkingLotService, outputPrinter);
    }


    @Override
    public boolean validate(Command command) {
        return command.getArgs().size()==1;
    }

    @Override
    public void execute(Command command) {
        String colour = command.getArgs().get(0);
        List<Slot> occupiedSlots = parkingLotService.findAllSlotsWithCarOfGivenColour(colour);
        String result = "";
        for(Slot slot : occupiedSlots) {
            if(result.equals("")) {
                result = String.valueOf(slot.getParkedCar().getRegNumber());
            } else {
                result = result + ", " + slot.getParkedCar().getRegNumber();
            }
        }
        outputPrinter.printWithNewLine(result);
    }
}
