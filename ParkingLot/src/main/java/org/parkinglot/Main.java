package org.parkinglot;

import org.parkinglot.commands.CommandExecutorFactory;
import org.parkinglot.mode.FileMode;
import org.parkinglot.mode.InteractiveMode;
import org.parkinglot.service.ParkingLotService;
import org.parkinglot.service.ParkingLotServiceImpl;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        OutputPrinter outputPrinter = new OutputPrinter();
        ParkingLotService parkingLotService = new ParkingLotServiceImpl();
        CommandExecutorFactory commandExecutorFactory = new CommandExecutorFactory(parkingLotService);
        if (isInteractiveMode(args)) {
            new InteractiveMode(commandExecutorFactory, outputPrinter).process();
        } else if (isFileInputMode(args)) {
            new FileMode(commandExecutorFactory, outputPrinter, args[0]).process();
        } else {
            throw new RuntimeException();
        }

    }

    private static boolean isFileInputMode(final String[] args) {
        return args.length == 1;
    }

    /**
     * Checks whether the program is running using interactive shell mode.
     *
     * @param args Command line arguments.
     * @return Boolean indicating whether in interactive shell mode.
     */
    private static boolean isInteractiveMode(final String[] args) {
        return args.length == 0;
    }

}