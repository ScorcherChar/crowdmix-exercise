package com.squiressoftware.crowdmix;

import com.google.inject.AbstractModule;
import com.squiressoftware.crowdmix.commands.CommandFactory;
import com.squiressoftware.crowdmix.commands.CommandFactoryImpl;
import com.squiressoftware.crowdmix.time.Clock;
import com.squiressoftware.crowdmix.time.ClockImpl;
import com.squiressoftware.crowdmix.users.InMemoryUserRepository;
import com.squiressoftware.crowdmix.users.UserRepository;

/**
 * Dependency injection via guice
 */
public class CrowdMixExcersiseModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Clock.class).to(ClockImpl.class);
        bind(CommandFactory.class).to(CommandFactoryImpl.class);
        bind(UserRepository.class).toInstance(new InMemoryUserRepository());
    }
}
