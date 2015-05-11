package com.squiressoftware.crowdmix.commands;

import com.squiressoftware.crowdmix.time.Clock;

import java.io.PrintStream;

public interface CommandFactory {
    Runnable CreateCommmand(String inputText);
}
