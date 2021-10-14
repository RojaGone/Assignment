package com.example.assignment;

public class commentModel {
    private String user_img,username,comment_view;

    public commentModel(String user_img, String username, String comment_view) {
        this.user_img = user_img;
        this.username = username;
        this.comment_view = comment_view;
    }

    public String getUser_img() {
        return user_img;
    }

    public void setUser_img(String user_img) {
        this.user_img = user_img;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment_view() {
        return comment_view;
    }

    public void setComment_view(String comment_view) {
        this.comment_view = comment_view;
    }
}
