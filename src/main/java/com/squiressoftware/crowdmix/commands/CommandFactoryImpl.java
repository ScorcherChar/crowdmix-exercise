package com.squiressoftware.crowdmix.commands;

import com.squiressoftware.crowdmix.posts.Post;
import com.squiressoftware.crowdmix.time.Clock;
import com.squiressoftware.crowdmix.users.User;
import com.squiressoftware.crowdmix.users.UserRepository;

import java.io.PrintStream;

public class CommandFactoryImpl implements CommandFactory {

    private final String POST_KEYWORD = "->";
    private final String FOLLOW_KEYWORD = " follows ";
    private final String WALL_KEYWORD = " wall";
    private UserRepository userRepository;
    private PrintStream output;
    private Clock clock;

    public CommandFactoryImpl(UserRepository repository, PrintStream output, Clock clock) {
        this.userRepository = repository;
        this.output = output;
        this.clock = clock;
    }

    @Override
    public Runnable CreateCommmand(String inputText) {
        if (inputText.contains(POST_KEYWORD)) {
            String username = inputText.split(POST_KEYWORD)[0].trim();
            String message = inputText.split(POST_KEYWORD)[1].trim();
            User user = userRepository.getOrCreateUser(username);
            return new PostCommand(user, new Post(user, message, clock.getNow()));
        } else if (inputText.contains(FOLLOW_KEYWORD)) {
            String followerUsername = inputText.split(FOLLOW_KEYWORD)[0].trim();
            String followedUsername = inputText.split(FOLLOW_KEYWORD)[1].trim();
            User follower = userRepository.getUser(followerUsername).get();
            User followed = userRepository.getUser(followedUsername).get();
            return new FollowCommand(follower, followed);
        } else if (inputText.contains(WALL_KEYWORD)) {
            String username = inputText.split(WALL_KEYWORD)[0].trim();
            User user = userRepository.getUser(username).get();
            return new WallCommand(user, output, clock.getNow());
        } else {
            String username = inputText.trim();
            User user = userRepository.getUser(username).get();
            return new ReadCommand(user, output, clock.getNow());
        }
    }
}
