package com.kriger.CinemaManager.commandHandlers;

import com.kriger.CinemaManager.command.Command;

public interface CommandHandler {

    void process(Command command);
    String getCommandName();

    default void validateParamsCount(Command command, int expectedCount) {
        if (command.getCommandParams().size() != expectedCount) {
            throw new IllegalArgumentException("Неверное количество параметров");
        }
    }

    default void validateParamIsNumber(Command command, int index, String exceptionMessage) {
        if (!command.getCommandParams().get(index).matches("\\d+")) {
            throw new IllegalArgumentException(exceptionMessage);
        }
    }
}
