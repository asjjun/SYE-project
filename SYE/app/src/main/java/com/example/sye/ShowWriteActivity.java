package com.example.sye;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.graphics.BitmapFactory;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.example.sye.MainActivity.user_info;

public class ShowWriteActivity extends AppCompatActivity {

    Intent intent;
    TextView textView1,textView2,user_id;
    ImageView imageView;
    String tv_name,tv_content;
    String userid,num;
    User user = new User();
    Button replybtn;
    EditText replyeditText;
    String replycontent;
    String[] JsonList ;
    JSONObject jsonObject;
    boolean action_favorite_check = true;
    private ListView listView;
    public ListViewItem listViewItem;
    private ListViewAdapter adapter;
    public ReplyModule replyModule;
    ArrayList<ReplyModule> replyModuleArrayList = new ArrayList<ReplyModule>();
    ArrayList<ListViewItem> arrayList;
    File filedownload;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_write);
        intent=getIntent();


        replybtn=findViewById(R.id.replybtn);
        replyeditText=findViewById(R.id.replyeditText);
        listView=(ListView)findViewById(R.id.listview);

        //헤더
        View header= getLayoutInflater().inflate(R.layout.header,null,false);
        listView.addHeaderView(header);
        user_id=header.findViewById(R.id.user_id);
        textView1=header.findViewById(R.id.hdtextView1);
        textView2=header.findViewById(R.id.hdtextView2);
        imageView=(ImageView)findViewById(R.id.hdimageView);


        arrayList=new ArrayList<ListViewItem>();
        adapter=new ListViewAdapter(arrayList);

        userid = intent.getStringExtra("user_id");
        num = intent.getStringExtra("num");
        tv_name=intent.getStringExtra("tv_name");
        tv_content=intent.getStringExtra("tv_content");
        filedownload = (File)intent.getSerializableExtra("file");

        user_id.setText(userid);
        textView1.setText(tv_name);
        textView2.setText(tv_content);

        Log.d("fq",filedownload.getAbsolutePath());
        if(!filedownload.getAbsolutePath().equals("/data/user/0/com.example.sye/cache")){

            imageView.setVisibility(View.VISIBLE);
            imageView.setImageBitmap(BitmapFactory.decodeFile(filedownload.getAbsolutePath()));
        }
        else{


        }

        update_reply(num);

        for(int i=0;i<replyModuleArrayList.size();i++){
            listViewItem=new ListViewItem(replyModuleArrayList.get(i).getUserId(),R.drawable.ic_baseline_person_24,replyModuleArrayList.get(i).getReplycontent());
            arrayList.add(listViewItem);
            Log.d("arraylist",arrayList.get(i).getUserid()+"/"+arrayList.get(i).getImg()+"/"+arrayList.get(i).getReplycontent());
        }
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //확인버튼
        replybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replycontent=replyeditText.getText().toString();

                ContentValues values_addreply = new ContentValues();
                values_addreply.put("userID", user_info);
                values_addreply.put("primary",num);
                values_addreply.put("replycontent",replycontent);

                NetworkTask networkTask_addreply = new NetworkTask(resulturl("AddReplyServlet"), values_addreply);
                networkTask_addreply.execute();
                Log.d("hihihi",replycontent);
                adapter.addItem(user_info,R.drawable.ic_baseline_person_24,replycontent);
                replyModuleArrayList.clear();
                update_reply(num);
                arrayList.clear();

                Log.d("ss",replyModuleArrayList.get(0).getUserId() + " " + replyModuleArrayList.get(0).getReplycontent());
                for(int i=0;i<replyModuleArrayList.size();i++){
                    listViewItem=new ListViewItem(replyModuleArrayList.get(i).getUserId(),R.drawable.ic_baseline_person_24,replyModuleArrayList.get(i).getReplycontent());
                    arrayList.add(listViewItem);
                    Log.d("arraylist",arrayList.get(i).getUserid()+"/"+arrayList.get(i).getImg()+"/"+arrayList.get(i).getReplycontent());
                }
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                replyeditText.setText("");
            }
        });
    }

    public void update_reply(String p_num){
        //댓글보기
        ContentValues reply_values = new ContentValues();
        reply_values.put("primary", p_num);
        String response = "";
        NetworkTask networkTask_reply = new NetworkTask(resulturl("UploadReflyServlet"), reply_values);
        try {
            response = networkTask_reply.execute().get();
            JsonList = response.split("\\}");
            for (int i = 0; i < JsonList.length; i++) {
                JsonList[i] += "}";
                Log.d("댓글? : ", JsonList[i]);
            }
            replyModuleArrayList.clear();
            if (!JsonList[0].equals("}"))
            {
                for(int i =0;i<JsonList.length ;i++){
                    replyModule = new ReplyModule();
                    jsonObject = new JSONObject(JsonList[i]);
                    replyModule.setPrimarykey(jsonObject.getString("primaryKey"));
                    replyModule.setUserId(jsonObject.getString("userID"));
                    replyModule.setReplycontent(jsonObject.getString("replycontent"));
                    replyModuleArrayList.add(replyModule);

                    Log.d("댓글리스트",i + "  " + replyModuleArrayList.get(i).getPrimarykey() + "/" +replyModuleArrayList.get(i).getUserId() + "/" + replyModuleArrayList.get(i).getReplycontent());
                }
            }


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        MenuItem action_update = menu.findItem(R.id.action_update);
        MenuItem action_write_menu = menu.findItem(R.id.action_write_menu);
        MenuItem action_favorite = menu.findItem(R.id.action_favorite);
        MenuItem action_favorite_border = menu.findItem(R.id.action_favorite_border);

        action_update.setVisible(false);
        action_write_menu.setVisible(false);

        ContentValues values = new ContentValues();
        values.put("userID", user_info);
        String response = "";
        NetworkTask networkTask = new NetworkTask(resulturl("UserSearchServlet"), values);

        try {
            response = networkTask.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("olllll",response);
        try{
            jsonObject = new JSONObject(response);

            user.setUserFavorite(jsonObject.getString("userFavorite"));

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        if(user.getUserFavorite().contains(num)){
            Log.d("contains","true");
            action_favorite_check = false;
        }



        if(action_favorite_check == true){
            action_favorite.setVisible(false);
            action_favorite_border.setVisible(true);
        }
        if(action_favorite_check == false){
            action_favorite.setVisible(true);
            action_favorite_border.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_favorite_border: {
                ContentValues values = new ContentValues();
                values.put("userID", user_info);
                values.put("primaryKey", num);
                values.put("status", "true");
                NetworkTask networkTask = new NetworkTask(resulturl("AddFavoriteServlet"), values);
                networkTask.execute();
                action_favorite_check = false;
                this.invalidateOptionsMenu();
                break;
            }
            case R.id.action_favorite: {
                ContentValues values = new ContentValues();
                values.put("userID", user_info);
                values.put("primaryKey", num);
                values.put("status", "false");
                NetworkTask networkTask = new NetworkTask(resulturl("AddFavoriteServlet"), values);
                networkTask.execute();
                action_favorite_check = true;
                this.invalidateOptionsMenu();
                break;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
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




