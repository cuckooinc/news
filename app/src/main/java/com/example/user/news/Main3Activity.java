package com.example.user.news;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.Button;
import android.widget.Filter;
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

public class Main3Activity extends AppCompatActivity {

	ArrayAdapter<newsitem> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
		setSupportActionBar(myToolbar);

		getSupportActionBar().setTitle(null);

		Button button = (Button) findViewById(R.id.button);
		RequestQueue queue = Volley.newRequestQueue(this);

		JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.GET,
				"https://newsapi.org/v1/articles?source=buzzfeed&sortBy=top&apiKey=3ff3080f8f544912b07a9c8d28848cc3",
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
		final ListView listView = (ListView) findViewById(R.id.newsItems);
		listView.setAdapter(adapter);
		addClickListener();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_search, menu);
		MenuItem item = menu.findItem(R.id.menuSearch);
		SearchView searchView = (SearchView) item.getActionView();

		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				adapter.getFilter().filter(newText);

				return false;
			}
		});


		return super.onCreateOptionsMenu(menu);
	}

	private void addClickListener() {
		final ListView newsItems = (ListView) (findViewById(R.id.newsItems));
		newsItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(Main3Activity.this, "I'm here at least...!", Toast.LENGTH_SHORT).show();
				newsitem currentItem = (newsitem) adapter.getItem(position);
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(currentItem.getUrl()));
				startActivity(i);
			}
		});
	}

	class customAdapter extends ArrayAdapter<newsitem> {
		private ArrayList<newsitem> data = new ArrayList<>();

		public customAdapter(Context context, int textViewResourceId) {
			super(context, textViewResourceId, new ArrayList<newsitem>());
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
			TextView desc = (TextView) v.findViewById(R.id.desc);

			textView.setText(getItem(position).getNewsHeading());
			//imageView.setImageResource(R.mipmap.ic_launcher);
			desc.setText((getItem(position).getNewsDesc()));

			Picasso.with(Main3Activity.this)
					.load(getItem(position).getImageURL()).into(imageView);
			return v;

		}

		@Override
		public void add(newsitem object) {
			super.add(object);

			data.add(object);

			notifyDataSetChanged();
		}

		@Override
		public Filter getFilter() {
			return new F();
		}

		private class F extends Filter {

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				// Create a FilterResults object
				FilterResults results = new FilterResults();

				// If the constraint (search string/pattern) is null
				// or its length is 0, i.e., its empty then
				// we just set the `values` property to the
				// original contacts list which contains all of them
				if (constraint == null || constraint.length() == 0) {
					results.values = new ArrayList(data);
					results.count = data.size();
				} else {
					// Some search copnstraint has been passed
					// so let's filter accordingly
					ArrayList<newsitem> filteredContacts = new ArrayList<>();

					// We'll go through all the contacts and see
					// if they contain the supplied string
					for (newsitem c : data) {
						if (c.getNewsHeading().toLowerCase().contains(constraint.toString().toLowerCase()) || c.getNewsDesc().toLowerCase().contains(constraint.toString().toLowerCase())) {
							// if `contains` == true then add it
							// to our filtered list
							filteredContacts.add(c);
						}
					}

					// Finally set the filtered values and size/count
					results.values = filteredContacts;
					results.count = filteredContacts.size();
				}

				notifyDataSetChanged();
				// Return our FilterResults object
				return results;
			}

			@Override
			protected void publishResults(CharSequence constraint, FilterResults results) {
				try {
					clear();
					addAll((ArrayList<newsitem>) results.values);

					notifyDataSetChanged();
				} catch (Exception e) {
				}
			}
		}
	}
}
