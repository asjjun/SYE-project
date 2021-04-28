package com.example.sye;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class NewsfeedFragment extends Fragment {

    public View view;
    public ArrayList<MainData> arrayList;
    private RecyclerView recyclerView;
    private MainAdapter mainAdapter;
    private LinearLayoutManager linearLayoutManager;
    public Writing writing;
    public Planmodule planmodule;
    public ReportModule reportModule;
    public ArrayList<Writing> writingList = new ArrayList<Writing>();
    ArrayList<Planmodule> planList = new ArrayList<Planmodule>();
    ArrayList<ReportModule> reportList=new ArrayList<ReportModule>();
    String[] JsonList = new String[9];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_newsfeed,container,false);
        //return super.onCreateView(inflater, container, savedInstanceState);

        recyclerView = (RecyclerView)view.findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<MainData>();
        mainAdapter = new MainAdapter(arrayList);
        recyclerView.setAdapter(mainAdapter);

        Bundle bundle = getArguments();
        String text = bundle.getString("update_poster");
        Log.d("결과값 : ",text);
        try {
            JsonList = text.split("\\}",10);
            for(int i =0;i<JsonList.length-1;i++){
                JsonList[i] += "}";
                Log.d("결과값 : ",JsonList[i]);
            }

            for(int i =0;i<5 ;i++){
                writing = new Writing();
                JSONObject jsonObject = new JSONObject(JsonList[i]);
                writing.setPrimaryKey(jsonObject.getString("primaryKey"));
                writing.setUserID(jsonObject.getString("userID"));
                writing.setBoard_title(jsonObject.getString("boardTitle"));
                writing.setBoard_content(jsonObject.getString("boardContent"));
                writing.setBoard_imgurl(jsonObject.getString("imgURL"));
                writingList.add(writing);
                Log.d("rasrasras",writingList.get(i).getPrimaryKey() + "/" +writingList.get(i).getUserID() + "/" + writingList.get(i).getBoard_title()+ "/" +writingList.get(i).getBoard_content()+ "/" +writingList.get(i).getBoard_imgurl());
            }

            for (int i = 5; i < 7; i++) {
                JSONObject jsonObject = new JSONObject(JsonList[i]);

                planmodule=new Planmodule();
                planmodule.setNum(jsonObject.getInt("num"));
                planmodule.setUserID(jsonObject.getString("userid"));
                planmodule.setVol_date(jsonObject.getString("vol_date"));
                planmodule.setVol_time(jsonObject.getString("vol_time"));
                planmodule.setPlace(jsonObject.getString("palce"));
                planmodule.setVol_goal(jsonObject.getString("vol_goal"));
                planmodule.setActivity_content(jsonObject.getString("activity_content"));
                planmodule.setVol_prepare(jsonObject.getString("vol_prepare"));
                planmodule.setEtc(jsonObject.getString("etc"));
                planList.add(planmodule);
            }

            for (int i = 7; i < 9; i++) {
                reportModule = new ReportModule();
                JSONObject jsonObject = new JSONObject(JsonList[i]);

                reportModule.setNum(jsonObject.getInt("num"));
                reportModule.setUserid(jsonObject.getString("userid"));
                reportModule.setVol_date(jsonObject.getString("vol_date"));
                reportModule.setVol_place(jsonObject.getString("vol_place"));
                reportModule.setActivity_content(jsonObject.getString("activity_content"));
                reportModule.setVol_prepare_progress(jsonObject.getString("vol_prepare_progress"));
                reportModule.setImage(jsonObject.getString("image"));
                reportModule.setPlan_Key(jsonObject.getInt("plan_Key"));
                reportModule.setProgress(jsonObject.getInt("progress"));
                reportList.add(reportModule);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for(int i=0;i<writingList.size();i++){
            MainData maindata = new MainData(writingList.get(i).getPrimaryKey(),writingList.get(i).getUserID(),writingList.get(i).getBoard_imgurl(),writingList.get(i).getBoard_title(),writingList.get(i).getBoard_content(), 0);
            arrayList.add(maindata);
        }

        for (int i = 0; i < planList.size(); i++) {
            MainData maindata = new MainData("봉사활동 계획서",planList.get(i).getUserID(),planList.get(i).getNum()
                    ,planList.get(i).getVol_date(),planList.get(i).getVol_time(),planList.get(i).getPlace(),planList.get(i).getVol_goal()
                    ,planList.get(i).getActivity_content(),planList.get(i).getVol_prepare(),planList.get(i).getEtc(), 1);
            arrayList.add(maindata);
        }

        for (int i = 0; i < reportList.size(); i++) {
            MainData maindata = new MainData("봉사활동 보고서: "+reportList.get(i).getUserid(),reportList.get(i).getNum(),reportList.get(i).getUserid(),reportList.get(i).getVol_date()
                    , reportList.get(i).getVol_place(),reportList.get(i).getActivity_content(),reportList.get(i).getVol_prepare_progress(),reportList.get(i).getImage(),reportList.get(i).getProgress(), 2);
            arrayList.add(maindata);
        }

        mainAdapter.notifyDataSetChanged();
        return view;
    }
}