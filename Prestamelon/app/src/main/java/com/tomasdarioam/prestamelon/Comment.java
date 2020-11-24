package com.tomasdarioam.prestamelon;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Comment extends BaseComment{

    private List<BaseComment> mCommentReplies = new ArrayList<>();;

    public Comment() {

    }

    public Comment(String userUid, String userName, String message) {
        super(userUid, userName, message);
        /*
        setUserUid(userUid);
        setMessage(message);
        setDateTime(Calendar.getInstance().getTime());
         */
    }

    public List<BaseComment> getCommentReplies() {
        return mCommentReplies;
    }

    public void setCommentReplies(List<BaseComment> commentReplies) {
        mCommentReplies = commentReplies;
    }

    public void addCommentReply(BaseComment comment) {
        mCommentReplies.add(comment);
    }

}

class BaseComment {
    private String mUserUid;
    private String mUserName;
    private String mMessage;
    private Date mDateTime;

    public BaseComment() {

    }

    public BaseComment(String userUid, String userName, String message) {
        setUserUid(userUid);
        setUserName(userName);
        setMessage(message);
        setDateTime(Calendar.getInstance().getTime());
    }

    public String getUserUid() {
        return mUserUid;
    }

    public void setUserUid(String userUid) {
        mUserUid = userUid;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
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
}


