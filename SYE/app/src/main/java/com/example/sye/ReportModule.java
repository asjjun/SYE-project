package com.example.sye;

public class ReportModule {
    private  String vol_date,vol_place,
            activity_content,vol_prepare_progress,image,plan_key, userid;

    private int progress;

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    private  int num,plan_Key;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getPlan_Key() {
        return plan_Key;
    }

    public void setPlan_Key(int plan_Key) {
        this.plan_Key = plan_Key;
    }

    public String getVol_date() {
        return vol_date;
    }

    public void setVol_date(String vol_date) {
        this.vol_date = vol_date;
    }

    public String getVol_place() {
        return vol_place;
    }

    public void setVol_place(String vol_place) {
        this.vol_place = vol_place;
    }

    public String getActivity_content() {
        return activity_content;
    }

    public void setActivity_content(String activity_content) {
        this.activity_content = activity_content;
    }

    public String getVol_prepare_progress() {
        return vol_prepare_progress;
    }

    public void setVol_prepare_progress(String vol_prepare_progress) {
        this.vol_prepare_progress = vol_prepare_progress;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPlan_key() {
        return plan_key;
    }

    public void setPlan_key(String plan_key) {
        this.plan_key = plan_key;
    }
}
