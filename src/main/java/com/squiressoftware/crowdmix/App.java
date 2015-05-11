package com.squiressoftware.crowdmix;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.squiressoftware.crowdmix.commands.CommandFactory;
import com.squiressoftware.crowdmix.commands.CommandFactoryImpl;
import com.squiressoftware.crowdmix.time.Clock;
import com.squiressoftware.crowdmix.time.RealClock;
import com.squiressoftware.crowdmix.users.InMemoryUserRepository;
import com.squiressoftware.crowdmix.users.UserRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class App {

    public static void main(String[] args) throws IOException {
        Injector injector = Guice.createInjector(new CrowdMixModule());
        PrintStream output = injector.getInstance(PrintStream.class);
        Clock clock =  injector.getInstance(Clock.class);
        UserRepository userRepository =  injector.getInstance(UserRepository.class);

        while (true){
            String inputText = new BufferedReader(new InputStreamReader(System.in)).readLine();
            run(inputText, output, clock, userRepository);
        }
    }

    public static void run(String inputText, PrintStream output, Clock clock, UserRepository userRepository) {
        CommandFactory commandFactory = new CommandFactoryImpl(userRepository, output, clock);
        Runnable command = commandFactory.CreateCommmand(inputText);
        command.run();
    }
}
