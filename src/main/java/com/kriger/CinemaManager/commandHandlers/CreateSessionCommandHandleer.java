package com.kriger.CinemaManager.commandHandlers;

import com.kriger.CinemaManager.command.Command;
import com.kriger.CinemaManager.model.Session;
import com.kriger.CinemaManager.service.interfaces.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class CreateSessionCommandHandleer implements CommandHandler {

    private final SessionService sessionService;
//    private final HallService hallService;

    @Autowired
    public CreateSessionCommandHandleer(SessionService sessionService) {
        this.sessionService = sessionService;
//        this.hallService = hallService;
    }

    @Override
    public void process(Command command) {
        try {
            validateCommand(command);

            Long id = Long.parseLong(command.getCommandParams().getFirst());
            LocalDateTime startTime = LocalDateTime.parse(
                    command.getCommandParams().get(1),
                    DateTimeFormatter.ofPattern("dd-MM-yyyy-HH:mm")
            );

            Long hallId = Long.parseLong(command.getCommandParams().get(2));
            int duration = Integer.parseInt(command.getCommandParams().get(3));
            String movieTitle = command.getCommandParams().get(4);

            Session session = sessionService.createSession(id, startTime, hallId, movieTitle, duration);

            System.out.println("Сеанс добавлен: " + session);
        } catch (DateTimeException e) {
            System.out.println("Неверный формат даты: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String getCommandName() {
        return "create-session";
    }

    //возможно объединить парсинг и валидацию, но Long и int
    private void validateCommand(Command command) {
        validateParamsCount(command, 5);

        validateParamIsNumber(command, 0, "ID сеанса должен быть числом");
        validateParamIsNumber(command, 2, "Номер зала должен быть числом");
        validateParamIsNumber(command, 3, "Продолжительность сеанса должна быть целым числом");

        LocalDateTime startTime = LocalDateTime.parse(
                command.getCommandParams().get(1),
                DateTimeFormatter.ofPattern("dd-MM-yyyy-HH:mm"));

        if (startTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Нельзя создать сеанс в прошлом");
        }
    }
}
