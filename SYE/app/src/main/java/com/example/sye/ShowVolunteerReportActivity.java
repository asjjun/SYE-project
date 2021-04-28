package com.example.sye;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.File;

public class ShowVolunteerReportActivity extends AppCompatActivity {
    Intent intent;
    TextView textView1,textView2,textView3,textView4;
    ImageView reportshowimageView;
    String vol_date,vol_place,activity_content,vol_prepare_progress,image;

    public Context context;
    TransferUtility transferUtility;

    private static final String COGNITO_POOL_ID = "ap-northeast-2:4ad66815-3e97-487a-91f2-024b75ccc07e";
    private static final String BUCKET_NAME = "cbdpicture";
    private static final int PICK_IMAGE_REQUEST_CODE = 1;
    private static final String TAG = MainActivity.class.getCanonicalName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_volunteer_report);

        intent=getIntent();

        context = getApplicationContext();
        createTransferUtility();

        textView1=findViewById(R.id.reportshowtextView1);
        textView2=findViewById(R.id.reportshowtextView2);
        textView3=findViewById(R.id.reportshowtextView3);
        textView4=findViewById(R.id.reportshowtextView4);
        reportshowimageView= (ImageView)findViewById(R.id.reportshowimageView);

        vol_date=intent.getStringExtra("rpvol_date");
        vol_place=intent.getStringExtra("vol_place");
        activity_content=intent.getStringExtra("rpactivity_content");
        vol_prepare_progress=intent.getStringExtra("vol_prepare_progress");
        image=intent.getStringExtra("image");

        textView1.setText(vol_date);
        textView2.setText(vol_place);
        textView3.setText(activity_content);
        textView4.setText(vol_prepare_progress);

        final File fileDownload = new File(context.getCacheDir(), image);
        if(image!=null && !image.equals("")){
            TransferObserver transferObserver = transferUtility.download(
                    BUCKET_NAME,
                    image,
                    fileDownload
            );

            transferObserver.setTransferListener(new TransferListener(){

                @Override
                public void onStateChanged(int id, TransferState state) {
                    Log.d(TAG, "onStateChanged: " + state);
                    if (TransferState.COMPLETED.equals(state)) {
                        reportshowimageView.setImageBitmap(BitmapFactory.decodeFile(fileDownload.getAbsolutePath()));
                    }
                }

                @Override
                public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {}

                @Override
                public void onError(int id, Exception ex) {
                    Log.e(TAG, "onError: ", ex);
                }
            });
        }
    }

    private void createTransferUtility() {
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                context,
                COGNITO_POOL_ID,
                Regions.AP_NORTHEAST_2
        );
        AmazonS3Client s3Client = new AmazonS3Client(credentialsProvider);
        transferUtility = new TransferUtility(s3Client, context);
    }
}
