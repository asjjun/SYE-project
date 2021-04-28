package com.example.sye;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutionException;

public class MembershipActivity extends AppCompatActivity {
    public Intent intent;
    public Intent LoginActivity;
    String gender_membership;
    public Button login_btn_f,id_check_btn;
    public EditText id_membership,pw_membership,name_membership,birth_membership,phone_membership,addr_membership;
    private RadioGroup member_radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership);

        login_btn_f = findViewById(R.id.member_btn_f);
        id_check_btn = findViewById(R.id.button);
        id_membership = findViewById(R.id.id_membership);
        pw_membership = findViewById(R.id.pw_membership);
        name_membership = findViewById(R.id.name_membership);
        birth_membership = findViewById(R.id.birth_membership);
        phone_membership = findViewById(R.id.phone_membership);
        addr_membership = findViewById(R.id.addr_membership);
        member_radioGroup = (RadioGroup) findViewById(R.id.member_radioGroup);


        RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if(i == R.id.member_radioButton1){
                    gender_membership = "남성";
                }
                else if(i == R.id.member_radioButton2){
                    gender_membership = "여성";
                }
            }
        };
        member_radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);

        login_btn_f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID =id_membership.getText().toString();
                ContentValues values = new ContentValues();
                values.put("userID", id_membership.getText().toString());
                values.put("userPassword", SignatureUtil.applySHA256(pw_membership.getText().toString()));
                values.put("userName", name_membership.getText().toString());
                values.put("userGender", gender_membership);
                values.put("userBirth", birth_membership.getText().toString());
                values.put("usermobile", phone_membership.getText().toString());
                values.put("userAddr", addr_membership.getText().toString());
                values.put("userMoney", 0);
                values.put("userFavorite", "");

                NetworkTask networkTask = new NetworkTask(resulturl("UserRegisterServlet"), values);
                networkTask.execute();
                Toast.makeText(getApplicationContext(),"회원가입 완료",Toast.LENGTH_SHORT).show();

                intent = new Intent(MembershipActivity.this,MyService.class);
                intent.setAction("newUser" + userID);
                startForegroundService(intent);

                LoginActivity = new Intent(MembershipActivity.this, LoginActivity.class);
                LoginActivity.putExtra("mService","true");
                startActivity(LoginActivity);
            }
        });

        id_check_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("userID", id_membership.getText().toString());

                String response = "";
                NetworkTask networkTask = new NetworkTask(resulturl("UserIDSearchServlet"), values);
                try {
                    response = networkTask.execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("결과값 : ",response);

                if(response.equals("true")){
                    Toast.makeText(getApplicationContext(),"사용가능한 아이디입니다",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"이미 사용중인 아이디입니다",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

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