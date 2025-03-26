package com.kriger.CinemaManager.commandValidators;

import com.kriger.CinemaManager.command.Command;
import org.springframework.stereotype.Component;

/**
 * Проверяет, что команда не содержит параметров
 */
@Component
public class ZeroParamsCountCommandValidator extends CommandValidator {

    @Override
    public void validateCommand(Command command) {
        validateParamsCount(command, 0);
    }
}