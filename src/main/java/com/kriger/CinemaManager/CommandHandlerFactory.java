package com.kriger.CinemaManager;

import com.kriger.CinemaManager.commandHandlers.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Хранит обработчики команд
 */
@Component
public class CommandHandlerFactory {

    private final Map<String, CommandHandler> commandHandlers = new HashMap<>(5);

    /**
     * Получает все обработчики команд
     */
    @Autowired
    public CommandHandlerFactory(CommandHandler... commandHandlers) {
        for (CommandHandler commandHandler : commandHandlers) {
            this.commandHandlers.put(commandHandler.getCommandName(), commandHandler);
        }
    }

    /**
     * Возвращает нужный обработчик команды по её названию или возвращает обработчик неизвестной команды
     */
    public CommandHandler getCommandHandler(String command) {
        return commandHandlers.getOrDefault(command, commandHandlers.get("unknown"));
    }
}
