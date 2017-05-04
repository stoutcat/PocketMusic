package com.example.q.pocketmusic.model.bean.ask;

import com.example.q.pocketmusic.model.bean.MyUser;

import cn.bmob.v3.BmobObject;

/**
 * Created by Cloud on 2016/11/15.
 */

public class AskSongPost extends BmobObject {
    private com.example.q.pocketmusic.model.bean.MyUser user;//帖子的发布者，一对一
    private String title;//帖子标题
    private String content;//帖子内容
    private Integer commentNum;//回复数量


    public AskSongPost() {
    }

    public AskSongPost(com.example.q.pocketmusic.model.bean.MyUser user, String title, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.commentNum = 0;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public com.example.q.pocketmusic.model.bean.MyUser getUser() {
        return user;
    }

    public void setUser(com.example.q.pocketmusic.model.bean.MyUser user) {
        this.user = user;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

}

