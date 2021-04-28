package com.example.sye;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    public BottomNavigationView bottomNavigationView;
    public FragmentManager fm;
    public FragmentTransaction ft;
    public NewsfeedFragment newsfeed;
    public MyInfoFragment my_info;
    public FavoriteFragment favorite;
    public ChargingFragment charging;
    public static String user_info;
    boolean action_update_check;
    public int IsProgressReport;
    public int IsProgress;
    public int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        String s = "-4000";
        Log.d("wd",Integer.parseInt(s) + "");
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        bottomNavigationView = findViewById(R.id.bottomNavi);

        setFrag(0);

        Intent intent = getIntent();
        user_info = intent.getStringExtra("user_info");

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_newsfeed: {
                        setFrag(0);
                        break;
                    }
                    case R.id.action_favorite: {
                        setFrag(1);
                        break;
                    }
                    case R.id.action_my_info:{
                        setFrag(2);
                        break;
                    }
                    case R.id.action_charging:{
                        setFrag(3);
                        break;
                    }
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        MenuItem action_update = menu.findItem(R.id.action_update);
        MenuItem action_write_menu = menu.findItem(R.id.action_write_menu);
        MenuItem ReportItem=menu.findItem(R.id.action_volunteer_report);
        MenuItem PlanItem=menu.findItem(R.id.action_volunteer_write);

        if(action_update_check == true){
            action_update.setVisible(true);
            action_write_menu.setVisible(true);
        }else{
            action_update.setVisible(false);
            action_write_menu.setVisible(false);
        }

        //봉사계획서, 보고서 활성화 비활성화 기능
        ContentValues values1 = new ContentValues();
        values1.put("userID", user_info);
        String response1 = "";
        NetworkTask networkTask1 = new NetworkTask(resulturl("ProgressReportServlet"), values1);
        try {
            response1 = networkTask1.execute().get();
            IsProgressReport=Integer.parseInt(response1);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("보고서 진행상황: ",response1);


        ContentValues values = new ContentValues();
        values.put("userID", user_info);
        String response = "";
        NetworkTask networkTask = new NetworkTask(resulturl("ProgressPlanServlet"), values);
        try {
            response = networkTask.execute().get();
            IsProgress=Integer.parseInt(response);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("계획서 진행상황 : ",response);


        if(IsProgress!=0){
            PlanItem.setEnabled(false);

            if(IsProgressReport==0) {
                ReportItem.setEnabled(true);
            }else{
                ReportItem.setEnabled(false);
            }
        }else{
            PlanItem.setEnabled(true);
            ReportItem.setEnabled(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_write: {
                Intent write = new Intent(MainActivity.this, WriteActivity.class);
                write.putExtra("user_info",user_info);
                startActivity(write);
                break;
            }

            case R.id.action_update: {
                fm.beginTransaction().remove(newsfeed).commit();
                newsfeed = null;
                newsfeed = new NewsfeedFragment();
                fm.beginTransaction().add(R.id.main_frame,newsfeed).commit();
                setFrag(0);
                break;
            }

            case R.id.action_volunteer_write: {
                Intent volunwrite= new Intent(MainActivity.this, VolunteerPlanActivity.class);
                volunwrite.putExtra("user_info",user_info);
                startActivity(volunwrite);
                break;
            }
            case R.id.action_volunteer_report: {
                Intent reportvolunteer= new Intent(MainActivity.this, VolunteerReportActivity.class);
                reportvolunteer.putExtra("user_info",user_info);
                reportvolunteer.putExtra("num",num);
                startActivity(reportvolunteer);
                break;
            }

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void updatePoster(){
        ContentValues values = new ContentValues();
        values.put("refresh", "true");
        String response ="";
        NetworkTask networkTask = new NetworkTask(resulturl("UploadPosterServlet"), values);
        try {
            response = networkTask.execute().get();
            Bundle bundle = new Bundle();
            Log.d("로그",response);
            bundle.putString("update_poster",response);
            newsfeed.setArguments(bundle);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void UpdateFavorite(){
        ContentValues values = new ContentValues();
        values.put("userID", user_info);
        String response = "";
        NetworkTask networkTask = new NetworkTask(resulturl("UserSearchFavoriteServlet"), values);
        try {
            response = networkTask.execute().get();
            Bundle bundle = new Bundle();

            if(!response.equals("false")){
                bundle.putString("update_favorite",response);
                favorite.setArguments(bundle);
            }else{
                bundle.putString("update_favorite","none");
                favorite.setArguments(bundle);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void UserSet(){
        ContentValues values = new ContentValues();
        values.put("userID", user_info);
        String response = "";
        NetworkTask networkTask = new NetworkTask(resulturl("UserSearchServlet"), values);
        try {
            response = networkTask.execute().get();
            Bundle bundle = new Bundle();
            Log.d("s",response);
            bundle.putString("user",response);
            my_info.setArguments(bundle);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void UpdateCharging(){
        ContentValues values = new ContentValues();
        values.put("userID", user_info);
        String response = "";
        NetworkTask networkTask = new NetworkTask(resulturl("UserSearchServlet"), values);
        try {
            response = networkTask.execute().get();
            Bundle bundle = new Bundle();
            bundle.putString("user",response);
            charging.setArguments(bundle);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setFrag(int n){
        switch (n){
            case 0: {
                if(newsfeed == null) {
                    newsfeed = new NewsfeedFragment();
                    fm.beginTransaction().add(R.id.main_frame, newsfeed).commit();
                }
                if(newsfeed != null) fm.beginTransaction().show(newsfeed).commit();
                if(favorite != null) fm.beginTransaction().hide(favorite).commit();
                if(my_info != null) fm.beginTransaction().hide(my_info).commit();
                if(charging != null) fm.beginTransaction().hide(charging).commit();
                updatePoster();
                action_update_check = true;
                this.invalidateOptionsMenu();
                break;
            }

            case 1: {
                if(favorite != null){
                    fm.beginTransaction().remove(favorite).commit();
                    favorite =null;
                }

                if(favorite == null){
                    favorite = new FavoriteFragment();
                    fm.beginTransaction().add(R.id.main_frame,favorite).commit();
                }
                if(newsfeed != null) fm.beginTransaction().hide(newsfeed).commit();
                if(favorite != null) fm.beginTransaction().show(favorite).commit();
                if(my_info != null) fm.beginTransaction().hide(my_info).commit();
                if(charging != null) fm.beginTransaction().hide(charging).commit();
                UpdateFavorite();
                action_update_check = false;
                this.invalidateOptionsMenu();
                break;
            }

            case 2: {
                if(my_info == null) {
                    my_info = new MyInfoFragment();
                    fm.beginTransaction().add(R.id.main_frame, my_info).commit();
                }
                if(newsfeed != null) fm.beginTransaction().hide(newsfeed).commit();
                if(favorite != null) fm.beginTransaction().hide(favorite).commit();
                if(my_info != null) fm.beginTransaction().show(my_info).commit();
                if(charging != null) fm.beginTransaction().hide(charging).commit();
                UserSet();
                action_update_check = false;
                this.invalidateOptionsMenu();
                break;
            }

            case 3: {
                if(charging == null){
                    charging = new ChargingFragment();
                    fm.beginTransaction().add(R.id.main_frame,charging).commit();
                }
                if(newsfeed != null) fm.beginTransaction().hide(newsfeed).commit();
                if(favorite != null) fm.beginTransaction().hide(favorite).commit();
                if(my_info != null) fm.beginTransaction().hide(my_info).commit();
                if(charging != null) fm.beginTransaction().show(charging).commit();
                UpdateCharging();
                action_update_check = false;
                this.invalidateOptionsMenu();
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
            //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }
    }
}