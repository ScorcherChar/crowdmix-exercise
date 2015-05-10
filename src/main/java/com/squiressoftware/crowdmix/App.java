package com.squiressoftware.crowdmix;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.squiressoftware.crowdmix.time.Clock;
import com.squiressoftware.crowdmix.time.ClockImpl;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class App {

    private static Injector injector = Guice.createInjector(new CrowdMixExcersiseModule());

    public static void main(String[] args) throws IOException {
        while (true){
            String inputText = new BufferedReader(new InputStreamReader(System.in)).readLine();
            runCommand(inputText, System.out, injector.getInstance(Clock.class));
        }
    }

    public static void runCommand(String inputText, PrintStream output, Clock clock) {
        throw new NotImplementedException();
    }
}
