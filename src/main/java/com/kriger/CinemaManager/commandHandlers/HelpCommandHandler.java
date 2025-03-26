package com.kriger.CinemaManager.commandHandlers;

import com.kriger.CinemaManager.command.Command;
import com.kriger.CinemaManager.commandValidators.CommandValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Обработчик команды для получения справки
 */
@Component
public class HelpCommandHandler implements CommandHandler {

    private final CommandValidator commandValidator;

    @Autowired
    public HelpCommandHandler(@Qualifier("zeroParamsCountCommandValidator") CommandValidator commandValidator) {
        this.commandValidator = commandValidator;
    }

    @Override
    public void process(Command command) {
        try {
            commandValidator.validateCommand(command);
            printHelp();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String getCommandName() {
        return "help";
    }

    private void printHelp() {
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
}