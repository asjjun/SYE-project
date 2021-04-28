package com.example.sye;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class FundActivity extends AppCompatActivity implements View.OnClickListener {
    public Button oneButton,twoButton,threeButton,fourButton,fiveButton,sixButton,sevenButton,eightButtton,nineButton,zeroButton,confirmButton,cancelButton,backButton;
    Button fundbutton;
    TextView fundingMoneyText, user_money_view;
    String userid,fundingmoney;
    Intent intent;
    String charAtreg="";
    int current_UserMoney =0;
    EventModule module = new EventModule();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund);

        fundbutton=findViewById(R.id.fundbutton);
        user_money_view = (TextView) findViewById(R.id.user_money_view); //현재잔액
        oneButton = (Button) findViewById(R.id.oneButton);
        twoButton = (Button) findViewById(R.id.twoButton);
        threeButton = (Button) findViewById(R.id.threeButton);
        fourButton = (Button) findViewById(R.id.fourButton);
        fiveButton = (Button) findViewById(R.id.fiveButtton);
        sixButton = (Button) findViewById(R.id.sixButton);
        sevenButton = (Button) findViewById(R.id.sevenButton);
        eightButtton = (Button) findViewById(R.id.eightButton);
        nineButton = (Button) findViewById(R.id.nineButton);
        zeroButton = (Button) findViewById(R.id.zeroButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        backButton = (Button) findViewById(R.id.backButton);
        confirmButton = (Button) findViewById(R.id.confirmButton);
        fundingMoneyText = (TextView) findViewById(R.id.fundingMoneyText);

        oneButton.setOnClickListener(this);
        twoButton.setOnClickListener(this);
        threeButton.setOnClickListener(this);
        fourButton.setOnClickListener(this);
        fiveButton.setOnClickListener(this);
        sixButton.setOnClickListener(this);
        sevenButton.setOnClickListener(this);
        eightButtton.setOnClickListener(this);
        nineButton.setOnClickListener(this);
        zeroButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        intent=getIntent();
        final String num= intent.getStringExtra("primaryKey");
        userid= MainActivity.user_info;

        ContentValues current_values=new ContentValues();
        current_values.put("userID",userid);
        String current_response = "";
        NetworkTask current_networkTask = new NetworkTask(resulturl("UserSearchServlet"), current_values);
        try {
            current_response = current_networkTask.execute().get();
            JSONObject jsonObject = new JSONObject(current_response);
            current_UserMoney = jsonObject.getInt("userMoney");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }catch (JSONException e) {
            e.printStackTrace();
        }


        user_money_view.setText("현재잔액 : " + current_UserMoney + "원 입니다.");
        fundbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fundingmoney=fundingMoneyText.getText().toString();

                if(fundingmoney!=null && !fundingmoney.equals("")){
                    if(current_UserMoney >= Integer.parseInt(fundingmoney)){
                        ContentValues values=new ContentValues();
                        values.put("userID",userid);
                        values.put("primary",Integer.parseInt(num));
                        Log.d("dsf",num);
                        values.put("fundingMoney",fundingmoney);

                        String response = "";
                        NetworkTask networkTask = new NetworkTask(resulturl("UserMoneyMangerServlet"), values);
                        try {
                            response = networkTask.execute().get();
                            //받는값은 코드동작 후 유저가상화폐정보
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.d("결과값 : ",response);

                        module.setUserID(userid);
                        module.setMoney(String.valueOf(-1 *Integer.parseInt(fundingmoney)));

                        intent = new Intent(FundActivity.this,MyService.class);
                        intent.setAction("funding" + module.getUserID() + "&" + module.getMoney());
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            startForegroundService(intent);
                        }

                        intent=new Intent(FundActivity.this, MainActivity.class);
                        intent.putExtra("user_info",userid);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"현재 돈은 " + current_UserMoney +"원 밖에 없습니다.",Toast.LENGTH_SHORT).show();
                        fundingMoneyText.setText("");
                        ClickButtonEvent();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"금액을 입력하세요.",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void ClickButtonEvent(){
        if(fundingMoneyText.getText().toString().equals("")){
            cancelButton.setVisibility(View.INVISIBLE);
            backButton.setVisibility(View.INVISIBLE);
            user_money_view.setVisibility(View.VISIBLE);
        }
        else{
            cancelButton.setVisibility(View.VISIBLE);
            backButton.setVisibility(View.VISIBLE);
            user_money_view.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.oneButton : {
                fundingMoneyText.setText(fundingMoneyText.getText() + "1");
                ClickButtonEvent();
                break;
            }
            case R.id.twoButton : {
                fundingMoneyText.setText(fundingMoneyText.getText() + "2");
                ClickButtonEvent();
                break;
            }
            case R.id.threeButton : {
                fundingMoneyText.setText(fundingMoneyText.getText() + "3");
                ClickButtonEvent();
                break;
            }
            case R.id.fourButton : {
                fundingMoneyText.setText(fundingMoneyText.getText() + "4");
                ClickButtonEvent();
                break;
            }
            case R.id.fiveButtton : {
                fundingMoneyText.setText(fundingMoneyText.getText() + "5");
                ClickButtonEvent();
                break;
            }
            case R.id.sixButton : {
                fundingMoneyText.setText(fundingMoneyText.getText() + "6");
                ClickButtonEvent();
                break;
            }
            case R.id.sevenButton : {
                fundingMoneyText.setText(fundingMoneyText.getText() + "7");
                ClickButtonEvent();
                break;
            }
            case R.id.eightButton : {
                fundingMoneyText.setText(fundingMoneyText.getText() + "8");
                ClickButtonEvent();
                break;
            }
            case R.id.nineButton : {
                fundingMoneyText.setText(fundingMoneyText.getText() + "9");
                ClickButtonEvent();
                break;
            }
            case R.id.zeroButton : {
                fundingMoneyText.setText(fundingMoneyText.getText() + "0");
                ClickButtonEvent();
                break;
            }
            case R.id.cancelButton : {
                fundingMoneyText.setText("");
                ClickButtonEvent();
                break;
            }
            case R.id.backButton : {
                charAtreg = "";
                for(int i=0;i<fundingMoneyText.getText().toString().length() - 1;i++){
                    charAtreg += fundingMoneyText.getText().toString().charAt(i);
                }
                fundingMoneyText.setText(charAtreg);
                ClickButtonEvent();
                break;
            }
        }
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