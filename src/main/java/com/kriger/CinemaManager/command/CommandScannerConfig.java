package com.kriger.CinemaManager.command;

import com.kriger.CinemaManager.CommandHandlerFactory;
import com.kriger.CinemaManager.commandHandlers.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
public class CommandScannerConfig {

    @Autowired
    private CommandHandlerFactory commandHandlerFactory;

    @Bean
    public CommandLineRunner commandScanner() {
        return args -> {
            try (Scanner scanner = new Scanner(System.in)) {
                System.out.println("Введите команду. 'exit' для выхода. 'help' для справки.");
                while (true) {
                    String input = scanner.nextLine();
                    if (input.isBlank()) {
                        continue;
                    } if (input.equals("exit")) {
                        System.out.println("Завершение работы");

                        System.exit(0);
//                        break;
                    }

                    Command command = new Command(input);
                    CommandHandler commandHandler = commandHandlerFactory.getCommandHandler(command.getCommand());
                    commandHandler.process(command);
                }
            }
        };
    }
}