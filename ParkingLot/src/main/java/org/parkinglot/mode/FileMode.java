package org.parkinglot.mode;

import org.parkinglot.OutputPrinter;
import org.parkinglot.commands.CommandExecutorFactory;
import org.parkinglot.model.Command;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileMode extends Mode {
    private String fileName;

    public FileMode(CommandExecutorFactory commandExecutorFactory, OutputPrinter outputPrinter, String fileName) {
        super(commandExecutorFactory, outputPrinter);
        this.fileName = fileName;
    }

    @Override
    public void process() throws IOException {
        File file = new File(fileName);
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            outputPrinter.invalidFile();
            return;
        }

        String input  = reader.readLine();
        while(input!=null) {
            Command command = new Command(input);
            processCommand(command);
            input = reader.readLine();
        }
    }
}
