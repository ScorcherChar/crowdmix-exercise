package com.squiressoftware.crowdmix;

import com.google.inject.AbstractModule;
import com.squiressoftware.crowdmix.time.Clock;
import com.squiressoftware.crowdmix.time.RealClock;
import com.squiressoftware.crowdmix.users.InMemoryUserRepository;
import com.squiressoftware.crowdmix.users.UserRepository;

import java.io.PrintStream;
//Dependency Injection
public class CrowdMixModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(PrintStream.class).toInstance(System.out);
        bind(Clock.class).to(RealClock.class);
        bind(UserRepository.class).toInstance(new InMemoryUserRepository());
    }
}
