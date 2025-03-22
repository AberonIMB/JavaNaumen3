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
}
