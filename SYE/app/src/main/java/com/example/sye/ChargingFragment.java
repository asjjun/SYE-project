package com.example.sye;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class ChargingFragment extends Fragment implements View.OnClickListener {

    public Button oneButton,twoButton,threeButton,fourButton,fiveButton,sixButton,sevenButton,eightButtton,nineButton,zeroButton,confirmButton,cancelButton,backButton;

    public View view;
    public TextView user_money_view;
    public Button quit_btn2;
    public User user = new User();
    public String text;
    public TextView chargeMoneyText;
    public Intent intent;
    public String charAtreg ="";
    public Intent intent_quit;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_charging,container,false);
        //return super.onCreateView(inflater, container, savedInstanceState);

        user_money_view = view.findViewById(R.id.user_money_view);//현재잔액
        oneButton = view.findViewById(R.id.oneButton);
        twoButton = view.findViewById(R.id.twoButton);
        threeButton = view.findViewById(R.id.threeButton);
        fourButton = view.findViewById(R.id.fourButton);
        fiveButton = view.findViewById(R.id.fiveButtton);
        sixButton = view.findViewById(R.id.sixButton);
        sevenButton = view.findViewById(R.id.sevenButton);
        eightButtton = view.findViewById(R.id.eightButton);
        nineButton = view.findViewById(R.id.nineButton);
        zeroButton = view.findViewById(R.id.zeroButton);
        cancelButton = view.findViewById(R.id.cancelButton);
        backButton = view.findViewById(R.id.backButton);
        confirmButton = view.findViewById(R.id.confirmButton);
        chargeMoneyText = view.findViewById(R.id.chargeMoneyText);

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

        quit_btn2 = view.findViewById(R.id.quit_btn2);

        Bundle bundle = getArguments();
        text = bundle.getString("user");

        try {
            JSONObject jsonObject = new JSONObject(text);
            user.setUserCode(jsonObject.getString("userCode"));
            user.setUserID(jsonObject.getString("userID"));
            user.setUserName(jsonObject.getString("userName"));
            user.setUserMoney(Integer.parseInt(jsonObject.getString("userMoney")));
            user_money_view.setText("현재잔액 : " + user.getUserMoney() + "원 입니다.");
            Log.d("ss", user.getUserID());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        confirmButton.setOnClickListener(new View.OnClickListener() {//충전버튼
            @Override
            public void onClick(View v) {
                String chargeMoney = chargeMoneyText.getText().toString();
                if(chargeMoney!=null && !chargeMoney.equals("")){
                    ContentValues values = new ContentValues();
                    values.put("userID", user.getUserID());
                    values.put("chargeMoney", chargeMoney);

                    String response = "";
                    NetworkTask networkTask = new NetworkTask(resulturl("UserChargeManagerServlet"), values);
                    try {
                        response = networkTask.execute().get();
                        //받는값은 코드동작 후 유저가상화폐정보
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    cancelButton.setVisibility(View.INVISIBLE);
                    backButton.setVisibility(View.INVISIBLE);
                    user_money_view.setVisibility(View.VISIBLE);
                    user_money_view.setText("현재잔액 : " + response + "원 입니다.");
                    chargeMoneyText.setText("");
                    intent = new Intent(getContext(),MyService.class);
                    intent.setAction("Charge"+user.getUserID() + "&" + chargeMoney);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        getContext().startForegroundService(intent);
                    }
                }
                else{
                    Toast.makeText(getContext(), "금액을 입력하세요.", Toast.LENGTH_SHORT).show();

                }
            }
        });

        quit_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent_quit = new Intent(getContext(),MyService.class);
                intent_quit.setAction("/quit");
                getContext().startForegroundService(intent_quit);
            }
        });

        return view;
    }

    public String resulturl(String url) {
        String resultUrl = "http://"+ MyService.hostname +":8080/" + url;
        return resultUrl;
    }

    public void ClickButtonEvent(){
        if(chargeMoneyText.getText().toString().equals("")){
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
                chargeMoneyText.setText(chargeMoneyText.getText() + "1");
                ClickButtonEvent();
                break;
            }
            case R.id.twoButton : {
                chargeMoneyText.setText(chargeMoneyText.getText() + "2");
                ClickButtonEvent();
                break;
            }
            case R.id.threeButton : {
                chargeMoneyText.setText(chargeMoneyText.getText() + "3");
                ClickButtonEvent();
                break;
            }
            case R.id.fourButton : {
                chargeMoneyText.setText(chargeMoneyText.getText() + "4");
                ClickButtonEvent();
                break;
            }
            case R.id.fiveButtton : {
                chargeMoneyText.setText(chargeMoneyText.getText() + "5");
                ClickButtonEvent();
                break;
            }
            case R.id.sixButton : {
                chargeMoneyText.setText(chargeMoneyText.getText() + "6");
                ClickButtonEvent();
                break;
            }
            case R.id.sevenButton : {
                chargeMoneyText.setText(chargeMoneyText.getText() + "7");
                ClickButtonEvent();
                break;
            }
            case R.id.eightButton : {
                chargeMoneyText.setText(chargeMoneyText.getText() + "8");
                ClickButtonEvent();
                break;
            }
            case R.id.nineButton : {
                chargeMoneyText.setText(chargeMoneyText.getText() + "9");
                ClickButtonEvent();
                break;
            }
            case R.id.zeroButton : {
                chargeMoneyText.setText(chargeMoneyText.getText() + "0");
                ClickButtonEvent();
                break;
            }
            case R.id.cancelButton : {
                chargeMoneyText.setText("");
                ClickButtonEvent();
                break;
            }
            case R.id.backButton : {
                charAtreg = "";
                for(int i=0;i<chargeMoneyText.getText().toString().length() - 1;i++){
                    charAtreg += chargeMoneyText.getText().toString().charAt(i);
                }
                chargeMoneyText.setText(charAtreg);
                ClickButtonEvent();
                break;
            }
        }
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
            //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }
    }
}