package com.example.sye;

import android.content.ContentValues;
import android.icu.text.Edits;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

public class MyInfoFragment extends Fragment implements View.OnClickListener {

    public String userName="";
    public String userAddr="";
    public String userMobile="";
    public View view;
    public String gender="남성";
    public RadioButton maleRadioButton,femaleRadioButton;
    public RadioGroup radioGroup;
    public Button modifyButton;
    public TextView userIDTitle, birthTitle;
    public EditText nameEditText,addrEditText,moblieEditText;
    ArrayList<String> user_ary = new ArrayList<String>();
    ArrayList<String> user_Value = new ArrayList<String>();
    public User user = new User();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_myinfo,container,false);
        //return super.onCreateView(inflater, container, savedInstanceState);

        modifyButton = view.findViewById(R.id.modifyButton);
        userIDTitle =view.findViewById(R.id.userIDTitle);
        nameEditText =view.findViewById(R.id.nameEditText);
        birthTitle =view.findViewById(R.id.birthTitle);
        addrEditText =view.findViewById(R.id.addrEditText);
        moblieEditText =view.findViewById(R.id.moblieEditText);
        maleRadioButton = view.findViewById(R.id.maleRadioButton);
        femaleRadioButton = view.findViewById(R.id.femaleRadioButton);

        modifyButton.setOnClickListener(this);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);



        Bundle bundle = getArguments();
        String text = bundle.getString("user");
        Log.d("kk",text);

        try {
            JSONObject jsonObject = new JSONObject(text);
            user.setUserID(jsonObject.getString("userID"));
            user.setUserName(jsonObject.getString("userName"));
            user.setUserGender(jsonObject.getString("userGender"));
            user.setUserBirth(jsonObject.getString("userBirth"));
            user.setUserAddr(jsonObject.getString("userAddr"));
            user.setUsermobile(jsonObject.getString("usermobile"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        userIDTitle.setText(user.getUserID());
        nameEditText.setHint(user.getUserName());
        birthTitle.setText(user.getUserBirth());
        if(user.getUserGender().equals("남성")){
            maleRadioButton.setChecked(true);
            femaleRadioButton.setChecked(false);
            gender = "남성";
        }
        else{
            maleRadioButton.setChecked(false);
            femaleRadioButton.setChecked(true);
            gender = "여성";
        }
        addrEditText.setHint(user.getUserAddr());
        moblieEditText.setHint(user.getUsermobile());

        return view;
    }

    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(checkedId == R.id.maleRadioButton){
                gender = "남성";
            }
            else{
                gender = "여성";
            }
        }
    };

    @Override
    public void onClick(View v) {
        if(nameEditText.getText().toString().equals("")){
            userName = user.getUserName();
        }
        else{
            userName = nameEditText.getText().toString();
            user.setUserName(userName);
        }
        if(addrEditText.getText().toString().equals("")){
            userAddr = user.getUserAddr();
        }
        else{
            userAddr = addrEditText.getText().toString();
            user.setUserAddr(userAddr);
        }

        if(moblieEditText.getText().toString().equals("")){
            userMobile = user.getUsermobile();
        }
        else{
            userMobile = moblieEditText.getText().toString();
            user.setUsermobile(userMobile);
        }

        ContentValues values = new ContentValues();
        values.put("userID", user.getUserID());
        values.put("userName", userName);
        values.put("userGender", gender);
        values.put("userAddr", userAddr);
        values.put("usermobile", userMobile);

        NetworkTask networkTask = new NetworkTask(resulturl("MemberInfoModifyServlet"), values);
        networkTask.execute();

        nameEditText.setText("");
        addrEditText.setText("");
        moblieEditText.setText("");
        nameEditText.setHint(userName);
        if(gender.contains("남성")){
            maleRadioButton.setChecked(true);
            femaleRadioButton.setChecked(false);
            gender = "남성";
        }
        else{
            maleRadioButton.setChecked(false);
            femaleRadioButton.setChecked(true);
            gender = "여성";
        }
        addrEditText.setHint(userAddr);
        moblieEditText.setHint(userMobile);
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