package com.kriger.CinemaManager.commandHandlers;

import com.kriger.CinemaManager.command.Command;
import org.springframework.stereotype.Component;

/**
 * Обработчик несуществующей команды
 */
@Component
public class UnknownCommandHandler implements CommandHandler {

    @Override
    public void process(Command command) {
        System.out.println("Неизвестная команда");
    }

    @Override
    public String getCommandName() {
        return "unknown";
    }
}
