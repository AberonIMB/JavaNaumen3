package com.kriger.CinemaManager.commandHandlers;

import com.kriger.CinemaManager.command.Command;

/**
 * Обработчик команд
 */
public interface CommandHandler {

    /**
     * Обрабатывает команду
     */
    void process(Command command);

    /**
     * Возвращает имя команды
     */
    String getCommandName();

    /**
     * Проверяет, что количество параметров команды совпадает с ожидаемым
     * @param command команда
     * @param expectedCount ожидаемое количество параметров
     */
    default void validateParamsCount(Command command, int expectedCount) {
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
    default void validateParamIsNumber(Command command, int index, String exceptionMessage) {
        if (!command.getCommandParams().get(index).matches("\\d+")) {
            throw new IllegalArgumentException(exceptionMessage);
        }
    }
}
