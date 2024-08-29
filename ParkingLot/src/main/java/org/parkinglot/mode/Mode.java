package org.parkinglot.mode;

import org.parkinglot.OutputPrinter;
import org.parkinglot.commands.CommandExecutor;
import org.parkinglot.commands.CommandExecutorFactory;
import org.parkinglot.model.Command;

import java.io.IOException;

public abstract class Mode {
    private CommandExecutorFactory commandExecutorFactory;

    protected OutputPrinter outputPrinter;

    public Mode(CommandExecutorFactory commandExecutorFactory, OutputPrinter outputPrinter) {
        this.commandExecutorFactory = commandExecutorFactory;
        this.outputPrinter = outputPrinter;
    }

    protected void processCommand(Command command) {
        CommandExecutor commandExecutor = commandExecutorFactory.getCommandExecutor(command);
        if(commandExecutor.validate(command)) {
            commandExecutor.execute(command);
        } else {
            throw new RuntimeException();
        }
    }

    public abstract void process() throws IOException;
}
