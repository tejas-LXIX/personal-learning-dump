package org.parkinglot.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Command {

    private static final String SPACE = " ";

    private String commandName;

    private List<String> args;

    public String getCommandName() {
        return commandName;
    }

    public List<String> getArgs() {
        return args;
    }

    //get the command name and the arguments from the input line and set them.
    public Command(final String inputLine) {
        final List<String> tokens = Arrays.stream(inputLine.trim().split(SPACE)).map(String::trim).filter(token -> token.length() > 0).collect(Collectors.toList());
        if (tokens.isEmpty()) {
            throw new RuntimeException();
        }
        commandName = tokens.get(0);
        tokens.remove(0);
        args = tokens;
    }
}
