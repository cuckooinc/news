package com.example.user.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class customAdapter extends ArrayAdapter<newsitem> {

    ArrayList<newsitem> newsItems = new ArrayList<>();

    public customAdapter(Context context, int textViewResourceId, ArrayList<newsitem> objects) {
        super(context, textViewResourceId, objects);
        newsItems = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.my_layout, null);
        TextView textView = (TextView) v.findViewById(R.id.heading);
        ImageView imageView = (ImageView) v.findViewById(R.id.image);
        TextView desc=(TextView) v.findViewById(R.id.desc);
        textView.setText(newsItems.get(position).getNewsHeading());
        imageView.setImageResource(R.mipmap.ic_launcher);
        desc.setText((newsItems.get(position).getNewsDesc()));
        return v;

    }

}