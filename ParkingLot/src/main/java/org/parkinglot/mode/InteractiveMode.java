package org.parkinglot.mode;

import org.parkinglot.OutputPrinter;
import org.parkinglot.commands.CommandExecutorFactory;
import org.parkinglot.commands.ExitCommandExecutor;
import org.parkinglot.model.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InteractiveMode extends Mode {
    public InteractiveMode(CommandExecutorFactory commandExecutorFactory, OutputPrinter outputPrinter) {
        super(commandExecutorFactory, outputPrinter);
    }

    @Override
    public void process() throws IOException {
        outputPrinter.welcome();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            String input = reader.readLine();
            Command command = new Command(input);
            processCommand(command);
            if (command.getCommandName().equals(ExitCommandExecutor.COMMAND_NAME)) {
                break;
            }
        }
    }


}
