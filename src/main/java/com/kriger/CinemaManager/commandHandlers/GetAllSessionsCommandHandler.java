package com.kriger.CinemaManager.commandHandlers;

import com.kriger.CinemaManager.command.Command;
import com.kriger.CinemaManager.commandValidators.CommandValidator;
import com.kriger.CinemaManager.model.Session;
import com.kriger.CinemaManager.service.interfaces.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Обработчик команды для получения списка сеансов
 */
@Component
public class GetAllSessionsCommandHandler implements CommandHandler {

    private final SessionService sessionService;
    private final CommandValidator commandValidator;

    @Autowired
    public GetAllSessionsCommandHandler(SessionService sessionService,
                                        @Qualifier("zeroParamsCountCommandValidator") CommandValidator commandValidator) {
        this.sessionService = sessionService;
        this.commandValidator = commandValidator;
    }

    @Override
    public void process(Command command) {
        try {
            commandValidator.validateCommand(command);

            List<Session> sessions = sessionService.getAllSessions();

            printSessions(sessions);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String getCommandName() {
        return "list-sessions";
    }

    /**
     * Выводит список сеансов
     */
    private void printSessions(List<Session> sessions) {
        System.out.println("Список сеансов: ");

        if (sessions.isEmpty()) {
            System.out.println("Сеансов нет\n");
            return;
        }

        sessions.forEach(System.out::println);
    }
}