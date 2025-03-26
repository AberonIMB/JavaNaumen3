package com.kriger.CinemaManager.commandHandlers;

import com.kriger.CinemaManager.command.Command;
import com.kriger.CinemaManager.commandValidators.CommandValidator;
import com.kriger.CinemaManager.model.Session;
import com.kriger.CinemaManager.service.interfaces.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Обработчик команды для получения сеанса
 */
@Component
public class GetSessionCommandHandler implements CommandHandler {

    private final SessionService sessionService;
    private final CommandValidator commandValidator;

    @Autowired
    public GetSessionCommandHandler(SessionService sessionService,
                                    @Qualifier("paramOnlyIdCommandValidator") CommandValidator commandValidator) {
        this.sessionService = sessionService;
        this.commandValidator = commandValidator;
    }

    @Override
    public void process(Command command) {
        try {
            commandValidator.validateCommand(command);

            Long id = Long.parseLong(command.getCommandParams().getFirst());
            Session session = sessionService.getSession(id);

            System.out.println(session);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String getCommandName() {
        return "show-session";
    }
}
