package com.kriger.CinemaManager.commandHandlers;

import com.kriger.CinemaManager.command.Command;
import com.kriger.CinemaManager.commandValidators.CommandValidator;
import com.kriger.CinemaManager.model.Session;
import com.kriger.CinemaManager.service.interfaces.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Обработчик команды для удаления сеанса
 */
@Component
public class DeleteSessionCommandHandler implements CommandHandler {

    private final SessionService SessionService;
    private final CommandValidator commandValidator;

    @Autowired
    public DeleteSessionCommandHandler(SessionService SessionService,
                                       @Qualifier("paramOnlyIdCommandValidator") CommandValidator commandValidator) {
        this.SessionService = SessionService;
        this.commandValidator = commandValidator;
    }

    @Override
    public void process(Command command) {
        try {
            commandValidator.validateCommand(command);

            Long id = Long.parseLong(command.getCommandParams().getFirst());

            Session session = SessionService.deleteSession(id);
            System.out.println("Сеанс удален: " + session);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String getCommandName() {
        return "delete-session";
    }
}
