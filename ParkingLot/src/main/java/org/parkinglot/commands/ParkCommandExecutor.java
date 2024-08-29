package org.parkinglot.commands;

import org.parkinglot.OutputPrinter;
import org.parkinglot.exceptions.NoFreeSlotAvailableException;
import org.parkinglot.model.Car;
import org.parkinglot.model.Command;
import org.parkinglot.service.ParkingLotService;

import java.util.List;

public class ParkCommandExecutor extends CommandExecutor {

    public static final String COMMAND_NAME = "park";

    public ParkCommandExecutor(ParkingLotService parkingLotService, OutputPrinter outputPrinter) {
        super(parkingLotService, outputPrinter);
    }


    @Override
    public boolean validate(Command command) {
        return command.getArgs().size()==2;
    }

    @Override
    public void execute(Command command) {
        List<String> args = command.getArgs();
        Car car = new Car(args.get(0), args.get(1));
        try {
            Integer slot = parkingLotService.park(car);
            outputPrinter.printWithNewLine("Allocated slot number: " + slot);
        } catch (NoFreeSlotAvailableException e) {
            outputPrinter.parkingLotFull();
        }
    }
}
