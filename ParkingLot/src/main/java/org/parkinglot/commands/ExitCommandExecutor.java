package org.parkinglot.commands;

import org.parkinglot.OutputPrinter;
import org.parkinglot.model.Command;
import org.parkinglot.service.ParkingLotService;

/**
 * Executor to handle command of exiting from the parking lot service. This is used in interactive
 * mode to exit.
 */
public class ExitCommandExecutor extends CommandExecutor {
    public static String COMMAND_NAME = "exit";

    public ExitCommandExecutor(
            final ParkingLotService parkingLotService, final OutputPrinter outputPrinter) {
        super(parkingLotService, outputPrinter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validate(final Command command) {
        return command.getArgs().isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final Command command) {
        outputPrinter.end();
    }
}