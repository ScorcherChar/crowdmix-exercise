package com.squiressoftware.crowdmix.users;

import com.squiressoftware.crowdmix.posts.Post;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String name;
    private List<Post> posts = new ArrayList<>();
    private List<User> followedUsers = new ArrayList<>();

    public User (String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public List<User> getFollowedUsers() {
        return followedUsers;
    }
}
