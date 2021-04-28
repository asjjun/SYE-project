package com.example.sye;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.File;
import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String COGNITO_POOL_ID = "ap-northeast-2:4ad66815-3e97-487a-91f2-024b75ccc07e";
    private static final String BUCKET_NAME = "cbdpicture";
    private static final int PICK_IMAGE_REQUEST_CODE = 1;
    private static final String TAG = MainActivity.class.getCanonicalName();

    TransferUtility transferUtility;

    private ArrayList<MainData> arrayList;
    Intent intent;
    public MainAdapter(ArrayList<MainData> arrayList) {
        this.arrayList = arrayList;
    }
    String tv_name;
    String tv_content;
    String vol_date,vol_time,place,vol_goal,activity_content,vol_prepare,etc;
    String rpvol_date,rpactivity_content,vol_place,vol_prepare_progress,image;
    String num;
    String primarykey;
    String userid;
    public static final int VIEW_TYPE_A = 0;
    public static final int VIEW_TYPE_B = 1;
    public static final int VIEW_TYPE_C = 2;
    public Context context;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();
        createTransferUtility();
        if(viewType==VIEW_TYPE_A){ //글쓰기 레이아웃
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_list,parent,false);
            WriteViewHolder holder = new WriteViewHolder(view);
            return holder;
        }
        else if (viewType==VIEW_TYPE_B){ //계획서 레이아웃
            View view1= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_planvol_item,parent,false);
            VolPlanViewHolder holder1 = new VolPlanViewHolder(view1);
            return holder1;
        }
        else{ //보고서 레이아웃
            View view2= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_report_item,parent,false);
            VolReportViewHolder holder2= new VolReportViewHolder(view2);
            return holder2;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

        if(getItemViewType(position)==VIEW_TYPE_A){
            String a = arrayList.get(position).getIv_profile();
            final File fileDownload = new File(context.getCacheDir(), a);
            
            if(a!=null && !a.equals("")){
                TransferObserver transferObserver = transferUtility.download(
                        BUCKET_NAME,
                        a,
                        fileDownload
                );

                transferObserver.setTransferListener(new TransferListener(){
                    @Override
                    public void onStateChanged(int id, TransferState state) {
                        Log.d(TAG, "onStateChanged: " + state);
                        if (TransferState.COMPLETED.equals(state)) {
                            ((WriteViewHolder)holder).iv_profile.setVisibility(View.VISIBLE);
                            ((WriteViewHolder)holder).iv_profile.setImageBitmap(BitmapFactory.decodeFile(fileDownload.getAbsolutePath()));
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

            ((WriteViewHolder)holder).user_id.setText(arrayList.get(position).getUserid());
            ((WriteViewHolder)holder).tv_name.setText(arrayList.get(position).getTv_name());
            ((WriteViewHolder)holder).tv_content.setText(arrayList.get(position).getTv_content());
            ((WriteViewHolder)holder).itemView.setTag(position);
            ((WriteViewHolder)holder).primaryKey = (arrayList.get(position).getPrimaryKey());

            ((WriteViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userid= ((WriteViewHolder)holder).user_id.getText().toString();
                    tv_name= ((WriteViewHolder)holder).tv_name.getText().toString();
                    tv_content= ((WriteViewHolder)holder).tv_content.getText().toString();
                    intent=new Intent(v.getContext(),ShowWriteActivity.class);
                    intent.putExtra("file",fileDownload);
                    intent.putExtra("user_id",userid);
                    intent.putExtra("num",((WriteViewHolder)holder).primaryKey);
                    intent.putExtra("tv_name",tv_name);
                    intent.putExtra("tv_content",tv_content);
                    v.getContext().startActivity(intent);
                }
            });
        }
        else if (getItemViewType(position)==VIEW_TYPE_B){
            ((VolPlanViewHolder)holder).volplan_id.setText(arrayList.get(position).getUserid());


            ((VolPlanViewHolder)holder).linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vol_date=arrayList.get(position).getVol_date();
                    vol_time=arrayList.get(position).getVol_time();
                    place=arrayList.get(position).getPlace();
                    vol_goal=arrayList.get(position).getVol_goal();
                    activity_content=arrayList.get(position).getActivity_content();
                    vol_prepare=arrayList.get(position).getVol_prepare();
                    etc=arrayList.get(position).getEtc();


                    intent=new Intent(v.getContext(), ShowVolunteerPlanActivity.class);
                    intent.putExtra("vol_date",vol_date);
                    intent.putExtra("vol_time",vol_time);
                    intent.putExtra("place",place);
                    intent.putExtra("vol_goal",vol_goal);
                    intent.putExtra("activity_content",activity_content);
                    intent.putExtra("vol_prepare",vol_prepare);
                    intent.putExtra("etc",etc);
                    v.getContext().startActivity(intent);


                }
            });


            ((VolPlanViewHolder)holder).gofundbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent=new Intent(v.getContext(),FundActivity.class);
                    num= String.valueOf((arrayList.get(position).getNum()));
                    intent.putExtra("primaryKey",num);
                    v.getContext().startActivity(intent);

                }
            });
        }
        else{
            ((VolReportViewHolder)holder).volreport_id.setText(arrayList.get(position).getUserid());

            ((VolReportViewHolder)holder).showreportlinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    intent=new Intent(v.getContext(), ShowVolunteerReportActivity.class);
                    rpvol_date=arrayList.get(position).getVol_date();
                    vol_place=arrayList.get(position).getVol_place();
                    rpactivity_content=arrayList.get(position).getActivity_content();
                    vol_prepare_progress=arrayList.get(position).getVol_prepare_progress();
                    image=arrayList.get(position).getImage();
                    intent.putExtra("rpvol_date",rpvol_date);
                    intent.putExtra("vol_place",vol_place);
                    intent.putExtra("rpactivity_content",rpactivity_content);
                    intent.putExtra("vol_prepare_progress",vol_prepare_progress);
                    intent.putExtra("image",image);
                    v.getContext().startActivity(intent);

                }
            });

            ((VolReportViewHolder)holder).govotebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //투표하기 액티비티 만드는곳

                    intent=new Intent(v.getContext(),VoteActivity.class);
                    primarykey=String.valueOf((arrayList.get(position).getNum()));
                    userid=arrayList.get(position).getUserid();
                    intent.putExtra("primary_key",primarykey);
                    intent.putExtra("userid",userid);
                    v.getContext().startActivity(intent);

                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(arrayList.get(position).getItemViewType()==0){
            return VIEW_TYPE_A;
        }else if (arrayList.get(position).getItemViewType()==1){
            return VIEW_TYPE_B;
        }
        else{
            return VIEW_TYPE_C;
        }
    }

    @Override
    public int getItemCount() {
        return (null !=arrayList ? arrayList.size():0);
    }



    public class WriteViewHolder extends RecyclerView.ViewHolder{

        protected String primaryKey;
        protected ImageView iv_profile;
        protected TextView tv_name;
        protected TextView tv_content;
        protected TextView user_id;

        public WriteViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_profile= (ImageView)itemView.findViewById(R.id.iv_profile);
            this.user_id = (TextView)itemView.findViewById(R.id.user_id);
            this.tv_name=(TextView)itemView.findViewById(R.id.tv_name);
            this.tv_content=(TextView)itemView.findViewById(R.id.tv_content);
        }
    }

    public class VolPlanViewHolder extends RecyclerView.ViewHolder{
        private TextView volplan_id;

        private LinearLayout linearLayout;
        private Button gofundbtn;

        public VolPlanViewHolder(@NonNull View itemView) {
            super(itemView);
            this.volplan_id=itemView.findViewById(R.id.volplan_id);

            this.linearLayout=itemView.findViewById(R.id.showplanLinear);
            this.gofundbtn=itemView.findViewById(R.id.gofundbtn);
        }
    }

    public class VolReportViewHolder extends RecyclerView.ViewHolder{
        private Button govotebtn;
        private TextView volreport_id;
        private LinearLayout showreportlinear;

        public VolReportViewHolder(@NonNull View itemView) {
            super(itemView);
            this.govotebtn=itemView.findViewById(R.id.govotebtn);
            this.volreport_id=itemView.findViewById(R.id.volreport_id);
            this.showreportlinear=itemView.findViewById(R.id.showreportLinear);


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
