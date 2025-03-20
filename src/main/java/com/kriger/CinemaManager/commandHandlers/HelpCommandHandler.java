package com.kriger.CinemaManager.commandHandlers;

import com.kriger.CinemaManager.command.Command;
import org.springframework.stereotype.Component;

/**
 * Обработчик команды для получения справки
 */
@Component
public class HelpCommandHandler implements CommandHandler {

    @Override
    public void process(Command command) {
        System.out.println(
                """
                        Список команд:
                        - help - справка
                        - create-session <id сеанса> <время начала сеанса в формате dd-MM-yyyy-HH:mm> <ID зала> <продолжительность фильма в минутах> <название фильма> - создать сеанс
                        - delete-session <id сеанса> - удалить сеанс
                        - list-sessions - список сеансов
                        - show-session <id сеанса> - показать сеанс
                        - exit - выход
                        """
        );
    }

    @Override
    public String getCommandName() {
        return "help";
    }
}