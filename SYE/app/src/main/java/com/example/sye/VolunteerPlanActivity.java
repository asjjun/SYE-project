package com.example.sye;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutionException;

public class VolunteerPlanActivity extends AppCompatActivity {
    Button button;
    Intent intent;
    EditText editText1, editText2,editText3,editText4,editText5,editText6,editText7;
    String vol_date,vol_time,place,vol_goal,activity_content,vol_prepare,etc;
    String userid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_plan);
        button=findViewById(R.id.vol_plan_btn);
        editText1=findViewById(R.id.planeditTextT1);
        editText2=findViewById(R.id.planeditTextT2);
        editText3=findViewById(R.id.planeditTextT3);
        editText4=findViewById(R.id.planeditTextT4);
        editText5=findViewById(R.id.planeditTextT5);
        editText6=findViewById(R.id.planeditTextT6);
        editText7=findViewById(R.id.planeditTextT7);
        intent=getIntent();
        userid=intent.getStringExtra("user_info");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vol_date=editText1.getText().toString();
                vol_time=editText2.getText().toString();
                place=editText3.getText().toString();
                vol_goal=editText4.getText().toString();
                activity_content=editText5.getText().toString();
                vol_prepare=editText6.getText().toString();
                etc=editText7.getText().toString();

                ContentValues values = new ContentValues();
                values.put("userID", userid);
                values.put("vol_date",vol_date);
                values.put("vol_time",vol_time);
                values.put("palce",place);
                values.put("vol_goal",vol_goal);
                values.put("activity_content", activity_content);
                values.put("vol_prepare",vol_prepare);
                values.put("etc",etc);


                String response = "";
                NetworkTask networkTask = new NetworkTask(resulturl("RegisterVolunteerPlanServlet"), values);
                try {
                    response = networkTask.execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                intent=new Intent(VolunteerPlanActivity.this, MainActivity.class);
                intent.putExtra("user_info",userid);
                startActivity(intent);
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
