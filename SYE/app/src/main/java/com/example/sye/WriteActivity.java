package com.example.sye;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class WriteActivity extends AppCompatActivity {

    private static final String COGNITO_POOL_ID = "ap-northeast-2:4ad66815-3e97-487a-91f2-024b75ccc07e";
    private static final String BUCKET_NAME = "cbdpicture";

    private static final int PICK_IMAGE_REQUEST_CODE = 1;
    private static final String TAG = MainActivity.class.getCanonicalName();

    public Button finish_btn,image_pick_btn;
    public EditText write_text,write_title;
    public String writing_text,writing_title;
    public String user_info;
    public String textUpload;

    ImageView imageViewUpload;
    Uri imageUri;
    ProgressDialog progressDialogUpload;
    TransferUtility transferUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        createTransferUtility();

        finish_btn = findViewById(R.id.finish_btn);
        image_pick_btn = findViewById(R.id.image_pick_btn);
        write_text = findViewById(R.id.write_text);
        write_title = findViewById(R.id.write_title);
        imageViewUpload = (ImageView) findViewById(R.id.image_upload);

        Intent intent = getIntent();
        user_info = intent.getStringExtra("user_info");

        finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageUri != null) {
                    if (!TextUtils.isEmpty(textUpload)){
                        String objectKey = textUpload;
                        File file = null;
                        try {
                            file = createFileFromUri(imageUri, objectKey);
                            upload(file, objectKey);

                        } catch (IOException e) {
                            Log.e(TAG, "onClick: ", e);
                            Toast.makeText(WriteActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(WriteActivity.this, "Enter object key in EditText", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(WriteActivity.this, "Pick any image to upload", Toast.LENGTH_SHORT).show();
                }

                writing_text = write_text.getText().toString();
                writing_title = write_title.getText().toString();
                ContentValues values = new ContentValues();
                values.put("userID", user_info);
                values.put("boardTitle", writing_title);
                values.put("boardContent", writing_text);
                if(textUpload==null){
                    values.put("imgURL", "");
                }
                else{
                    values.put("imgURL", textUpload);
                }
                NetworkTask networkTask = new NetworkTask(resulturl("RegisterPosterServlet"), values);
                networkTask.execute();
                Intent MainActivity = new Intent(WriteActivity.this,MainActivity.class);
                MainActivity.putExtra("user_info",user_info);
                startActivity(MainActivity);
            }
        });

        image_pick_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE);
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

    private void createTransferUtility() {
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                COGNITO_POOL_ID,
                Regions.AP_NORTHEAST_2
        );
        AmazonS3Client s3Client = new AmazonS3Client(credentialsProvider);
        transferUtility = new TransferUtility(s3Client, getApplicationContext());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case PICK_IMAGE_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    imageUri = uri;
                    textUpload = getFileNameFromUri(uri);
                    imageViewUpload.setImageURI(uri);
                }
        }
    }

    String getFileNameFromUri(Uri uri) {
        Cursor returnCursor = getContentResolver().query(uri, null, null, null, null);
        int nameIndex = 0;
        if (returnCursor != null) {
            nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            returnCursor.moveToFirst();
            String name = returnCursor.getString(nameIndex);
            returnCursor.close();
            return name;
        } else {
            return "";
        }
    }

    File createFileFromUri(Uri uri, String objectKey) throws IOException {
        InputStream is = getContentResolver().openInputStream(uri);
        File file = new File(getCacheDir(), objectKey);
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        byte[] buf = new byte[2046];
        int read = -1;
        while ((read = is.read(buf)) != -1) {
            fos.write(buf, 0, read);
        }
        fos.flush();
        fos.close();
        return file;
    }

    void upload(File file, final String objectKey) {
        TransferObserver transferObserver = transferUtility.upload(
                BUCKET_NAME,
                objectKey,
                file
        );
        transferObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                Log.d(TAG, "onStateChanged: " + state);
                if (TransferState.COMPLETED.equals(state)) {
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {}

            @Override
            public void onError(int id, Exception ex) {
                Log.e(TAG, "onError: ", ex);
                Toast.makeText(WriteActivity.this, "Error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}