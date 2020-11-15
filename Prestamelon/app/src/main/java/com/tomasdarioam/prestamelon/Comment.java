package com.tomasdarioam.prestamelon;

import java.util.Date;
import java.util.List;

public class Comment {
    private String mUserUid;
    private String mMessage;
    private Date mDateTime;
    private List<Comment> mCommentReplies;

    public Comment() {

    }

    public Comment(String userId, String message, Date dateTime) {

    }

    public String getUserUid() {
        return mUserUid;
    }

    public void setUserUid(String userUid) {
        mUserUid = userUid;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Date getDateTime() {
        return mDateTime;
    }

    public void setDateTime(Date dateTime) {
        mDateTime = dateTime;
    }

    public List<Comment> getCommentReplies() {
        return mCommentReplies;
    }

    public void setCommentReplies(List<Comment> commentReplies) {
        mCommentReplies = commentReplies;
    }

    public void addCommentReply(Comment comment) {
        mCommentReplies.add(comment);
    }

}


