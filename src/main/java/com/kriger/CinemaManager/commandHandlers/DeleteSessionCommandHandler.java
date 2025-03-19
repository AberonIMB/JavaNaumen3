package com.kriger.CinemaManager.commandHandlers;

import com.kriger.CinemaManager.command.Command;
import com.kriger.CinemaManager.model.Session;
import com.kriger.CinemaManager.service.interfaces.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteSessionCommandHandler implements CommandHandler {

    private final SessionService SessionService;

    @Autowired
    public DeleteSessionCommandHandler(SessionService SessionService) {
        this.SessionService = SessionService;
    }

    @Override
    public void process(Command command) {
        try {
            validateCommand(command);

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

    private void validateCommand(Command command) {
        validateParamsCount(command, 1);
        validateParamIsNumber(command, 0, "ID сеанса должен быть числом");
    }
}
