package com.squiressoftware.crowdmix.commands;

import com.squiressoftware.crowdmix.users.User;

public class FollowCommand implements Runnable {

    private User follower;
    private User followed;

    public FollowCommand(User follower, User followed) {
        this.follower = follower;
        this.followed = followed;
    }

    @Override
    public void run() {
        follower.getFollowedUsers().add(followed);

    }
}
