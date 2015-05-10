package com.squiressoftware.crowdmix.users;

import com.squiressoftware.crowdmix.posts.Post;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String name;
    private List<Post> posts = new ArrayList<>();

    public User (String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
