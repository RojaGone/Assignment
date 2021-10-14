package com.example.assignment;

public class MyListDataModel {
    private String username,user_dob,like,comment,user_img,user_post_img,user_post_video,description;

    public MyListDataModel() {
    }

    public MyListDataModel(String username, String user_dob, String like, String comment, String user_img, String user_post_img, String user_post_video, String description) {
        this.username = username;
        this.user_dob = user_dob;
        this.like = like;
        this.comment = comment;
        this.user_img = user_img;
        this.user_post_img = user_post_img;
        this.user_post_video = user_post_video;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser_dob() {
        return user_dob;
    }

    public void setUser_dob(String user_dob) {
        this.user_dob = user_dob;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUser_img() {
        return user_img;
    }

    public void setUser_img(String user_img) {
        this.user_img = user_img;
    }

    public String getUser_post_img() {
        return user_post_img;
    }

    public void setUser_post_img(String user_post_img) {
        this.user_post_img = user_post_img;
    }

    public String getUser_post_video() {
        return user_post_video;
    }

    public void setUser_post_video(String user_post_video) {
        this.user_post_video = user_post_video;
    }
}
