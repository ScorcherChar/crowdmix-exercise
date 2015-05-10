package com.squiressoftware.crowdmix.commands;


import com.squiressoftware.crowdmix.posts.Post;
import com.squiressoftware.crowdmix.users.User;
import org.joda.time.DateTime;

import java.io.PrintStream;
import java.util.List;

public class ReadCommand implements Runnable {

    private User user;
    private PrintStream output;
    private DateTime now;

    public ReadCommand(User user, PrintStream output, DateTime now) {
        this.user = user;
        this.output = output;
        this.now = now;
    }

    @Override
    public void run() {
        user.getPosts().sort((Post post1, Post post2) -> post1.getPostedTime().compareTo(post2.getPostedTime()));
        user.getPosts().stream().forEach(post -> output.println(post.print(now)));
    }
}
