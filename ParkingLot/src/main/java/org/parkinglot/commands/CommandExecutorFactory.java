package org.parkinglot.commands;

import org.parkinglot.OutputPrinter;
import org.parkinglot.model.Command;
import org.parkinglot.service.ParkingLotService;

import java.util.HashMap;
import java.util.Map;

public class CommandExecutorFactory {
    private Map<String, CommandExecutor> commands = new HashMap<>();

    public CommandExecutorFactory(final ParkingLotService parkingLotService) {
        final OutputPrinter outputPrinter = new OutputPrinter();
        commands.put(
                CreateParkingLotCommandExecutor.COMMAND_NAME,
                new CreateParkingLotCommandExecutor(parkingLotService, outputPrinter));
        commands.put(
                ParkCommandExecutor.COMMAND_NAME,
                new ParkCommandExecutor(parkingLotService, outputPrinter));
        commands.put(
                LeaveCommandExecutor.COMMAND_NAME,
                new LeaveCommandExecutor(parkingLotService, outputPrinter));
        commands.put(
                StatusCommandExecutor.COMMAND_NAME,
                new StatusCommandExecutor(parkingLotService, outputPrinter));
        commands.put(
                RegistrationNumbersForCarsWithColourCommandExecutor.COMMAND_NAME,
                new RegistrationNumbersForCarsWithColourCommandExecutor(parkingLotService, outputPrinter));
        commands.put(
                SlotNumbersForCarsWithColourCommandExecutor.COMMAND_NAME,
                new SlotNumbersForCarsWithColourCommandExecutor(parkingLotService, outputPrinter));
        commands.put(
                SlotNumberForRegistrationNumberCommandExecutor.COMMAND_NAME,
                new SlotNumberForRegistrationNumberCommandExecutor(parkingLotService, outputPrinter));
        commands.put(
                ExitCommandExecutor.COMMAND_NAME,
                new ExitCommandExecutor(parkingLotService, outputPrinter));
    }

    public CommandExecutor getCommandExecutor(Command command) {
        CommandExecutor commandExecutor = commands.get(command.getCommandName());
        if (commandExecutor==null) {
            throw new RuntimeException();
        }
        return commandExecutor;
    }
}
