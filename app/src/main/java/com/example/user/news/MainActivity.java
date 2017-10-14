package com.example.user.news;

import android.app.VoiceInteractor;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	ArrayAdapter<newsitem> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button button = (Button) findViewById(R.id.button);
		RequestQueue queue = Volley.newRequestQueue(this);

		JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.GET,
				"https://newsapi.org/v1/articles?source=bbc-news&sortBy=top&apiKey=213bf478145b4af28f3fec5c8f5dec94",
				null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						try {
							JSONArray newsItems = response.getJSONArray("articles");

							for (int i = 0; i < newsItems.length(); i++) {
								JSONObject temp = newsItems.getJSONObject(i);

								String title = temp.getString("title");
								String image = temp.getString("urlToImage");
								String content = temp.getString("description");
								String time = temp.getString("publishedAt");


								String link = temp.getString("url");

								adapter.add(new newsitem(title, content, time, link, image));
								adapter.notifyDataSetChanged();
							}
						} catch (JSONException e) {
							Log.i("myTag", e.toString());
						}
					}

				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.i("myTag", error.toString());
					}
				});


		myReq.setRetryPolicy(new DefaultRetryPolicy(
				10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
		));

		queue.add(myReq);


		adapter = new customAdapter(this, R.layout.my_layout);
		ListView listView = (ListView) findViewById(R.id.newsItems);
		listView.setAdapter(adapter);
	}


}

