package com.example.sye;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {

    public View view;
    public ArrayList<MainData> arrayList;
    private RecyclerView recyclerView;
    private MainAdapter mainAdapter;
    private LinearLayoutManager linearLayoutManager;
    public Writing writing;
    public ArrayList<Writing> writingList = new ArrayList<Writing>();
    String[] JsonList = new String[5];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favorite,container,false);
        //return super.onCreateView(inflater, container, savedInstanceState);

        recyclerView = (RecyclerView)view.findViewById(R.id.favorite_rv);
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<MainData>();
        mainAdapter = new MainAdapter(arrayList);
        recyclerView.setAdapter(mainAdapter);

        Bundle bundle = getArguments();

        if(!(bundle.getString("update_favorite").equals("none"))){
            String text = bundle.getString("update_favorite");
            try {
                JsonList = text.split("\\}");
                for(int i =0;i<JsonList.length;i++){
                    JsonList[i] += "}";
                    Log.d("결과값값값값 : ",JsonList[i]);
                }

                for(int i =0;i<JsonList.length;i++){
                    writing = new Writing();
                    JSONObject jsonObject = new JSONObject(JsonList[i]);
                    writing.setPrimaryKey(jsonObject.getString("primaryKey"));
                    writing.setUserID(jsonObject.getString("userID"));
                    writing.setBoard_title(jsonObject.getString("boardTitle"));
                    writing.setBoard_content(jsonObject.getString("boardContent"));
                    writing.setBoard_imgurl(jsonObject.getString("imgURL"));
                    writingList.add(writing);
                    Log.d("ras",writingList.get(i).getPrimaryKey() + "/" +writingList.get(i).getUserID() + "/" + writingList.get(i).getBoard_title()+ "/" +writingList.get(i).getBoard_content()+ "/" +writingList.get(i).getBoard_imgurl());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for(int i=0;i<writingList.size();i++){
            MainData maindata = new MainData(writingList.get(i).getPrimaryKey(), writingList.get(i).getUserID(), writingList.get(i).getBoard_imgurl(),writingList.get(i).getBoard_title(),writingList.get(i).getBoard_content(), 0);
            arrayList.add(maindata);
        }
        mainAdapter.notifyDataSetChanged();

        return view;
    }
}
