package com.example.sye;

public class MainData {
    private String primaryKey;
    private String iv_profile;
    private String tv_name;
    private String tv_content;
    private int itemViewType;
    private int num;
    private String vol_date;
    private String vol_time;
    private String place;
    private String vol_goal;
    private String activity_content;
    private String vol_prepare;
    private String etc;
    private int progress;
    private String vol_place,vol_prepare_progress,image;
    private String userid;


    //글쓰기
    public MainData(String primaryKey, String userid, String iv_profile, String tv_name, String tv_content,int itemViewType) {
        this.primaryKey = primaryKey;
        this.userid = userid;
        this.iv_profile = iv_profile;
        this.tv_name = tv_name;
        this.tv_content = tv_content;
        this.itemViewType=itemViewType;
    }

    //계획서
    public MainData(String tv_name,String userid, int num, String vol_date, String vol_time, String place, String vol_goal, String activity_content, String vol_prepare, String etc, int itemViewType) {
        this.tv_name = tv_name;
        this.userid=userid;
        this.num=num;
        this.itemViewType = itemViewType;
        this.vol_date=vol_date;
        this.vol_time=vol_time;
        this.place=place;
        this.vol_goal=vol_goal;
        this.activity_content=activity_content;
        this.vol_prepare=vol_prepare;
        this.etc=etc;
    }

    //보고서
    public MainData(String tv_name,int num,String userid,String vol_date,String vol_place,String activity_content, String vol_prepare_progress,String image,int progress,int itemViewType)
    {
        this.userid=userid;
        this.tv_name=tv_name;
        this.num=num;
        this.itemViewType=itemViewType;
        this.vol_date=vol_date;
        this.vol_place=vol_place;
        this.activity_content=activity_content;
        this.vol_prepare_progress=vol_prepare_progress;
        this.image=image;
        this.progress=progress;
    }


    public String getPrimaryKey() { return primaryKey; }

    public void setPrimaryKey(String primaryKey) { this.primaryKey = primaryKey; }

    public String getIv_profile() {
        return iv_profile;
    }

    public void setIv_profile(String iv_profile) {
        this.iv_profile = iv_profile;
    }

    public String getTv_name() {
        return tv_name;
    }

    public void setTv_name(String tv_name) {
        this.tv_name = tv_name;
    }

    public String getTv_content() {
        return tv_content;
    }

    public void setTv_content(String tv_content) {
        this.tv_content = tv_content;
    }

    public int getItemViewType() {
        return itemViewType;
    }

    public void setItemViewType(int itemViewType) {
        this.itemViewType = itemViewType;
    }

    public String getVol_date() {
        return vol_date;
    }

    public void setVol_date(String vol_date) {
        this.vol_date = vol_date;
    }

    public String getVol_time() {
        return vol_time;
    }

    public void setVol_time(String vol_time) {
        this.vol_time = vol_time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getVol_goal() {
        return vol_goal;
    }

    public void setVol_goal(String vol_goal) {
        this.vol_goal = vol_goal;
    }

    public String getActivity_content() {
        return activity_content;
    }

    public void setActivity_content(String activity_content) { this.activity_content = activity_content; }

    public String getVol_prepare() {
        return vol_prepare;
    }

    public void setVol_prepare(String vol_prepare) {
        this.vol_prepare = vol_prepare;
    }

    public String getEtc() {
        return etc;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getVol_place() {
        return vol_place;
    }

    public void setVol_place(String vol_place) {
        this.vol_place = vol_place;
    }

    public String getVol_prepare_progress() {
        return vol_prepare_progress;
    }

    public void setVol_prepare_progress(String vol_prepare_progress) { this.vol_prepare_progress = vol_prepare_progress; }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}