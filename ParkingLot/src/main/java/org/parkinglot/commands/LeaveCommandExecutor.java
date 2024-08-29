package org.parkinglot.commands;

import org.parkinglot.OutputPrinter;
import org.parkinglot.model.Car;
import org.parkinglot.model.Command;
import org.parkinglot.service.ParkingLotService;
import org.parkinglot.validator.IntegerValidator;

import java.util.List;

public class LeaveCommandExecutor extends CommandExecutor {

    public static final String COMMAND_NAME = "leave";

    public LeaveCommandExecutor(ParkingLotService parkingLotService, OutputPrinter outputPrinter) {
        super(parkingLotService, outputPrinter);
    }


    @Override
    public boolean validate(Command command) {
        List<String> args = command.getArgs();
        if (args.size()!=1) {
            return false;
        }
        return IntegerValidator.isInteger(args.get(0));
    }

    @Override
    public void execute(Command command) {
        int slotNumber = Integer.parseInt(command.getArgs().get(0));
        parkingLotService.leave(slotNumber);
        outputPrinter.printWithNewLine("Vacated slot number: " + slotNumber);
    }
}
