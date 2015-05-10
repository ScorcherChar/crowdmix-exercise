package com.squiressoftware.crowdmix.commands;

import com.squiressoftware.crowdmix.posts.Post;
import com.squiressoftware.crowdmix.users.User;
import org.joda.time.DateTime;

public class PostCommand implements Runnable {

    private User user;
    private Post post;

    public PostCommand (User user,Post post){
        this.user = user;
        this.post = post;
    }

    @Override
    public void run() {
        user.getPosts().add(post);
    }
}
