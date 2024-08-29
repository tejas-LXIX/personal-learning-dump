package org.parkinglot.commands;

import org.parkinglot.OutputPrinter;
import org.parkinglot.exceptions.ParkingLotException;
import org.parkinglot.parkinglots.ParkingLot;
import org.parkinglot.parkinglots.SimpleParkingLot;
import org.parkinglot.service.ParkingLotService;
import org.parkinglot.model.Command;
import org.parkinglot.validator.IntegerValidator;

import java.util.List;

public class CreateParkingLotCommandExecutor extends CommandExecutor {
    public static final String COMMAND_NAME = "create_parking_lot";

    public CreateParkingLotCommandExecutor(final ParkingLotService parkingLotService, final OutputPrinter outputPrinter) {
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
        int capacity = Integer.parseInt(command.getArgs().get(0));
        ParkingLot parkingLot = new SimpleParkingLot(capacity);
        parkingLotService.createParkingLot(parkingLot);
        outputPrinter.printWithNewLine(
                "Created a parking lot with " + parkingLot.getCapacity() + " slots");
    }
}
