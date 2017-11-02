package com.example.user.news;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setTitle(null);


        Button button = (Button) findViewById(R.id.button);
        ListView lv;
        Context context;

        ArrayList prgmName;
        int[] Imageslist = {R.drawable.sports, R.drawable.lifestyle, R.drawable.money};
        String[] section = {"Sports", "lifestyle", "money"};


        lv = (ListView) findViewById(R.id.newsItems);

        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), section, Imageslist);
        lv.setAdapter(customAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                if (position == 0) {
                    //code specific to first list item
                    Intent myIntent = new Intent(view.getContext(), MainActivity.class);
                    startActivityForResult(myIntent, 0);

                }
                if (position == 1) {
                    //code specific to first list item
                    Intent myIntent = new Intent(view.getContext(), Main3Activity.class);
                    startActivityForResult(myIntent, 0);

                }
                if (position == 2) {
                    //code specific to first list item
                    Intent myIntent = new Intent(view.getContext(), Main4Activity.class);
                    startActivityForResult(myIntent, 0);

                }

            }
        });

    }




    class CustomAdapter extends BaseAdapter {

        Context context;
        String countryList[];
        int flags[];
        LayoutInflater inflter;

        public CustomAdapter(Context applicationContext, String[] countryList, int[] flags) {
            this.context = context;
            this.countryList = countryList;
            this.flags = flags;
            inflter = (LayoutInflater.from(applicationContext));
        }

        @Override
        public int getCount() {
            return countryList.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = inflter.inflate(R.layout.my_layout, null);
            TextView country = (TextView) view.findViewById(R.id.heading);
            TextView desc=(TextView) view.findViewById(R.id.desc);
            ImageView icon = (ImageView) view.findViewById(R.id.image);
            country.setText(countryList[i]);
            desc.setText("");
            icon.setImageResource(flags[i]);
            return view;
        }
    }
}



