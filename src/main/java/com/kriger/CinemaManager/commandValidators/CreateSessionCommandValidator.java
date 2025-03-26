package com.kriger.CinemaManager.commandValidators;

import com.kriger.CinemaManager.command.Command;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Проверяет на корректность параметры команды создания сеанса
 */
@Component
public class CreateSessionCommandValidator extends CommandValidator {

    @Override
    public void validateCommand(Command command) {
        validateParamsCount(command, 5);

        validateParamIsNumber(command, 0, "ID сеанса должен быть числом");
        validateParamIsNumber(command, 2, "Номер зала должен быть числом");
        validateParamIsNumber(command, 3, "Продолжительность сеанса должна быть представлена целым числом");

        LocalDateTime startTime = LocalDateTime.parse(
                command.getCommandParams().get(1),
                DateTimeFormatter.ofPattern("dd-MM-yyyy-HH:mm"));

        if (startTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Нельзя создать сеанс в прошлом");
        }
    }
}
