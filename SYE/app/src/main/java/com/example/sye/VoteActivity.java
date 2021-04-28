package com.example.sye;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class VoteActivity extends AppCompatActivity {

    TextView ConfirmVotingTitle;
    RadioButton radioButton1;
    RadioButton radioButton2;
    Button votebtn,voteview;
    String userid;
    Intent intent;
    int plan_Key;
    String trueindex,falseindex;
    String cur_vote[];
    String userID="";
    String Money="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);

        ConfirmVotingTitle = findViewById(R.id.ConfirmVotingTitle);
        radioButton1=findViewById(R.id.vtradioButton1);
        radioButton2=findViewById(R.id.vtradioButton2);
        votebtn= findViewById(R.id.votebtn);
        voteview= findViewById(R.id.voteview);

        radioButton1.setChecked(true);
        intent=getIntent();
        final String reportnum = intent.getStringExtra("primary_key");
        final String u_id=intent.getStringExtra("userid"); // u_id 는 게시물을 쓴 사람 아이디

        userid= MainActivity.user_info; //본인이 로그인한 아이디
        votebtn.setEnabled(false);

        //투표하기 버튼
        votebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ContentValues values = new ContentValues();
                values.put("primary",Integer.parseInt(reportnum));

                values.put("userID", userid);

                if(radioButton1.isChecked())
                {
                    values.put("status","true");
                }
                if(radioButton2.isChecked())
                {
                    values.put("status","false");
                }

                //찬성이면 true 반대이면 false 대소문자 맞춰줘야함!!
                NetworkTask networkTask = new NetworkTask(resulturl("AddVoteResultServlet"), values);
                networkTask.execute();

                ContentValues SearchVotepeople = new ContentValues();
                Log.d("눌?",reportnum);
                SearchVotepeople.put("Num",reportnum);//보고서 Num값 보내기
                NetworkTask networkTask_searchvotepeople = new NetworkTask(resulturl("SearchVotePeopleServlet"), SearchVotepeople);
                String current_response="";
                try {
                    current_response = networkTask_searchvotepeople.execute().get();
                    Log.d("눌?",current_response);
                    cur_vote=new String[2];
                    cur_vote=current_response.split("&");

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(cur_vote[0].equals(cur_vote[1]))
                {
                    ContentValues finishvote = new ContentValues();
                    finishvote.put("reportNum",reportnum);//보고서 Num값 보내기
                    NetworkTask networkTask_finishvote = new NetworkTask(resulturl("FinishVoteServlet"), finishvote);
                    String response_module="";
                    try {
                        response_module= networkTask_finishvote.execute().get();
                        Log.d("눌?",response_module);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        JSONObject jsonObject = new JSONObject(response_module);
                        userID = jsonObject.getString("userID");
                        Money = jsonObject.getString("Money");

                        if(!Money.equals("0")) {
                            intent = new Intent(VoteActivity.this, MyService.class);
                            intent.setAction("compensate" + userID + "&" + Money);
                            startForegroundService(intent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                intent=new Intent(VoteActivity.this, MainActivity.class);
                intent.putExtra("user_info",userid);
                startActivity(intent);
            }
        });

        //투표보기 버튼
        voteview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values_plankey = new ContentValues();
                values_plankey.put("userID", u_id);
                String response_plankey = "";
                NetworkTask networkTask_plankey = new NetworkTask(resulturl("FindReportPlankeyServlet"), values_plankey);
                try {
                    response_plankey = networkTask_plankey.execute().get();
                    plan_Key=Integer.parseInt(response_plankey);
                    // 0이면 게시물 없음
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                ContentValues values = new ContentValues();
                values.put("userID", userid);
                values.put("plankey",plan_Key);
                values.put("primary",reportnum);

                Log.d("계획서num값",plan_Key+"");
                Log.d("보고서num값",reportnum);
                Log.d("이름뭐냐",u_id);

                String response = "";
                NetworkTask networkTask = new NetworkTask(resulturl("UserVoteMangerServlet"), values);
                try {
                    response = networkTask.execute().get();
                    //펀딩을 안한사람이면 false
                    //펀딩을 하고 투표를 한사람이면 json형태의 찬반표수
                    //펀딩을 하고 투표를 안한사람이면 trueNot
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("결과값 : ",response);

                try {
                    if(response.contains("trueNot")){
                        votebtn.setEnabled(true);
                        ConfirmVotingTitle.setText("해당 보고서에 펀딩이 되어 있습니다!\n신중하게 투표를 해주세요!");
                    }
                    else if (response.contains("false_Index")) {
                        JSONObject js=new JSONObject(response);
                        trueindex=js.getString("true_Index");//1 0 이 나온다
                        falseindex=js.getString("false_Index");
                        ConfirmVotingTitle.setText("투표까지 완료된 상태입니다!\n현재 투표진행 결과는??\n찬성 " + trueindex+"표 / 반대 " + falseindex + "표");
                    }
                    else{
                        ConfirmVotingTitle.setText("해당 보고서에 펀딩이 되어있지 않습니다!\n펀딩을 하신 후에 투표를 진행해주세요!");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public String resulturl(String url) {
        String resultUrl = "http://"+ MyService.hostname +":8080/" + url;
        return resultUrl;
    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {

        String result;
        String url;
        ContentValues values;

        NetworkTask(String url, ContentValues values){
            this.url = url;
            this.values = values;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progress bar를 보여주는 등등의 행위
        }

        @Override
        protected String doInBackground(Void... params) {
            String result;
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values);

            this.result = result;
            return result; // 결과가 여기에 담깁니다. 아래 onPostExecute()의 파라미터로 전달됩니다.
        }

        @Override
        protected void onPostExecute(String result) {
            // 통신이 완료되면 호출됩니다.
            // 결과에 따른 UI 수정 등은 여기서 합니다.

        }
    }
}
