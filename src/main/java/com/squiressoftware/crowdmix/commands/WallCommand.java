package com.squiressoftware.crowdmix.commands;

import com.squiressoftware.crowdmix.posts.Post;
import com.squiressoftware.crowdmix.users.User;
import org.joda.time.DateTime;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class WallCommand implements Runnable{
    private User user;
    private PrintStream output;
    private DateTime now;

    public WallCommand(User user, PrintStream output, DateTime now){
        this.user = user;
        this.output = output;
        this.now = now;
    }

    @Override
    public void run() {
        List<Post> aggrigatedPosts = new ArrayList<>();
        aggrigatedPosts.addAll(user.getPosts());
        user.getFollowedUsers().stream().forEach(followedUser -> aggrigatedPosts.addAll(followedUser.getPosts()));
        aggrigatedPosts.sort((Post post1, Post post2) -> post1.getPostedTime().compareTo(post2.getPostedTime()));
        aggrigatedPosts.stream().forEach(post -> output.println(post.printWithName(now)));
    }
}
