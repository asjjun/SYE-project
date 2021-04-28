package com.example.sye;

public class ListViewItem {
    private String userid,replycontent;
    private int img;


    public ListViewItem(String userid, int img, String replycontent){
        this.img=img;
        this.userid=userid;
        this.replycontent=replycontent;
    }
    public ListViewItem(){

    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getReplycontent() {
        return replycontent;
    }

    public void setReplycontent(String replycontent) {
        this.replycontent = replycontent;
    }

}
