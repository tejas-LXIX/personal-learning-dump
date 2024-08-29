package org.parkinglot.commands;

import org.parkinglot.OutputPrinter;
import org.parkinglot.service.ParkingLotService;
import org.parkinglot.model.Command;

public abstract class CommandExecutor {
    protected ParkingLotService parkingLotService;

    protected OutputPrinter outputPrinter;

    public CommandExecutor(ParkingLotService parkingLotService, OutputPrinter outputPrinter) {
        this.parkingLotService = parkingLotService;
        this.outputPrinter = outputPrinter;
    }

    public abstract boolean validate(Command command);

    public abstract void execute(Command command);
}
