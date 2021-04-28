package com.example.sye;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    public EditText id_text, pw_text;
    public Button login_btn, member_btn, quit_btn;
    private MyService mService;
    private boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        id_text = findViewById(R.id.id_text);
        pw_text = findViewById(R.id.pw_text);
        login_btn = findViewById(R.id.login_btn);
        member_btn = findViewById(R.id.member_btn);
        quit_btn = findViewById(R.id.quit_btn);

        Intent serviceIntent = getIntent();
        if(serviceIntent.getStringExtra("mService") != null){
            mBound = true;
        }
        else {
            mBound = false;
            Log.d("ss","mBound false");
        }

        if(!mBound){
            Intent intent = new Intent(LoginActivity.this,MyService.class);
            intent.setAction("Access");
            startForegroundService(intent);
        }

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = "";
                userID = id_text.getText().toString();
                ContentValues values = new ContentValues();
                values.put("userID", id_text.getText().toString());
                values.put("userPassword", SignatureUtil.applySHA256(pw_text.getText().toString()));

                String response = "";
                NetworkTask networkTask = new NetworkTask(resulturl("LoginProcServlet"), values);
                try {
                    response = networkTask.execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(response.equals("true")){
                    Toast.makeText(getApplicationContext(),"로그인 되었습니다",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this,MyService.class);
                    intent.setAction("Login&"+userID);
                    startForegroundService(intent);

                    Intent MainActivity = new Intent(LoginActivity.this,MainActivity.class);
                    MainActivity.putExtra("user_info",id_text.getText().toString());
                    startActivity(MainActivity);
                }
                else{
                    Toast.makeText(getApplicationContext(),"아이디 또는 비밀번호가 틀립니다",Toast.LENGTH_SHORT).show();
                }
            }
        });

        member_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MembershipActivity = new Intent(LoginActivity.this,MembershipActivity.class);
                startActivity(MembershipActivity);
            }
        });

        quit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,MyService.class);
                intent.setAction("/quit");
                startForegroundService(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(!mBound){
            Intent intent = new Intent(this,MyService.class);
            bindService(intent,mConnection,BIND_AUTO_CREATE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("??","onStop()");
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.MyBinder binder = (MyService.MyBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("dd","onServiceDisconnected()");
            //예기치않은 상황에서 끊어졌을때 호출됨.
        }
    };

    public String resulturl(String url) { //ip 값 바꿔주는 부분
        String resultUrl = "http://"+ MyService.hostname +":8080/" + url;
        return resultUrl;
    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {

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
            return result; // 결과가 여기에 담깁니다. 아래 onPostExecute()의 파라미터로 전달됩니다.
        }

        @Override
        protected void onPostExecute(String result) {
            // 통신이 완료되면 호출됩니다.
            // 결과에 따른 UI 수정 등은 여기서 합니다.
            //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }
    }
}