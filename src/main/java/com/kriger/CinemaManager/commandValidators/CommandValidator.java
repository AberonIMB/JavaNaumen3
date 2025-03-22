package com.kriger.CinemaManager.commandValidators;

import com.kriger.CinemaManager.command.Command;

public abstract class CommandValidator {

    /**
     * Проверяет, что параметры команды корректны
     */
    public abstract void validateCommand(Command command);

    /**
     * Проверяет, что количество параметров команды совпадает с ожидаемым
     * @param command команда
     * @param expectedCount ожидаемое количество параметров
     */
    protected void validateParamsCount(Command command, int expectedCount) {
        if (command.getCommandParams().size() != expectedCount) {
            throw new IllegalArgumentException("Неверное количество параметров");
        }
    }

    /**
     * Проверяет, что параметр команды является числом
     * @param command команда
     * @param index индекс параметра
     * @param exceptionMessage сообщение об ошибке
     */
    protected void validateParamIsNumber(Command command, int index, String exceptionMessage) {
        if (!command.getCommandParams().get(index).matches("\\d+")) {
            throw new IllegalArgumentException(exceptionMessage);
        }
    }
}
