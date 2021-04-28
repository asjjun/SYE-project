package com.example.sye;

public class Writing {
    private String primaryKey;
    private String userID;
    private String board_title;
    private String board_content;
    private String board_imgurl;

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getBoard_title() {
        return board_title;
    }

    public void setBoard_title(String board_title) {
        this.board_title = board_title;
    }

    public String getBoard_content() {
        return board_content;
    }

    public void setBoard_content(String board_content) {
        this.board_content = board_content;
    }

    public String getBoard_imgurl() {
        return board_imgurl;
    }

    public void setBoard_imgurl(String board_imgurl) {
        this.board_imgurl = board_imgurl;
    }
}
