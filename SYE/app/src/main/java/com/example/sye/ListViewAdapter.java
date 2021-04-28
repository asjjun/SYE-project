package com.example.sye;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    private ImageView iconImageView;
    private TextView titleTextView;
    private TextView contentTextView;

    private ArrayList<ListViewItem> listViewItemList=new ArrayList<ListViewItem>();

    public ListViewAdapter(ArrayList<ListViewItem> arrayList){
        this.listViewItemList=arrayList;
    }

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Context context=parent.getContext();

        if(convertView==null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.listview_item,parent,false);
        }

        titleTextView=(TextView)convertView.findViewById(R.id.title1);
        iconImageView=(ImageView)convertView.findViewById(R.id.icon);
        contentTextView=(TextView)convertView.findViewById(R.id.content);

        titleTextView.setText(listViewItemList.get(position).getUserid());
        iconImageView.setImageResource(listViewItemList.get(position).getImg());
        contentTextView.setText(listViewItemList.get(position).getReplycontent());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

   public void addItem(String title, int icon, String content){
        ListViewItem item=new ListViewItem();

        item.setUserid(title);
        item.setImg(icon);
        item.setReplycontent(content);

        listViewItemList.add(item);
    }
}
