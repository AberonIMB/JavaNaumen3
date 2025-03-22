package com.kriger.CinemaManager.commandValidators;

import com.kriger.CinemaManager.command.Command;
import org.springframework.stereotype.Component;

/**
 * Проверяет на корректность параметры команды, которая содержит только ID
 */
@Component
public class ParamOnlyIdCommandValidator extends CommandValidator {

    @Override
    public void validateCommand(Command command) {
        validateParamsCount(command, 1);
        validateParamIsNumber(command, 0, "ID должен быть числом");
    }
}
