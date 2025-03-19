package com.kriger.CinemaManager.command;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Command {

    private final String command;
    private final List<String> commandParams;

    public Command(String input) {
        List<String> args = parseInputString(input);

        command = args.getFirst();
        commandParams = args.subList(1, args.size());
    }

    private List<String> parseInputString(String input) {
        List<String> args = new ArrayList<>();
        Pattern pattern = Pattern.compile("\"(.*?)\"|(\\S+)");
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            if (matcher.group(1) != null) {
                args.add(matcher.group(1));
            } else {
                args.add(matcher.group(2));
            }
        }

        return args;
    }

    public String getCommand() {
        return command;
    }

    public List<String> getCommandParams() {
        return commandParams;
    }
}