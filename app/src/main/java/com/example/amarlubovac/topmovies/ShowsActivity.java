package com.example.amarlubovac.topmovies;


import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShowsActivity extends Fragment {

    private List<TVShow> shows;
    CustomAdapter customAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shows, container, false);
        shows = new ArrayList<>();
        ListView listView = rootView.findViewById(R.id.lista);
        customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);

        callWebService();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("id", shows.get(i).getId());
                intent.putExtra("type", "tv");
                startActivity(intent);
            }
        });

        return rootView;
    }

    private void callWebService() {

        String url = "https://api.themoviedb.org/3/tv/top_rated?api_key=598686d3b14ded918cd5f79728857dda&language=en-US&page=1";
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject objectJSON = new JSONObject(response);
                    JSONArray arrayOfMoviesJSON = objectJSON.getJSONArray("results");
                    shows.clear();
                    for (int i = 0; i<arrayOfMoviesJSON.length(); i++) {
                        JSONObject object = arrayOfMoviesJSON.getJSONObject(i);
                        TVShow m = new TVShow();
                        m.setName(object.getString("name"));
                        m.setId(object.getInt("id"));
                        String posterUrl = "https://image.tmdb.org/t/p/w300" + object.getString("poster_path");
                        m.setPosterpath(posterUrl);
                        shows.add(m);
                    }
                    customAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                requestQueue.stop();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.print("Error!");
                error.printStackTrace();
                requestQueue.stop();
            }
        });

        requestQueue.add(request);
    }

    private void callWebServiceSearchShows(String query) {
        String url = "https://api.themoviedb.org/3/search/tv?api_key=598686d3b14ded918cd5f79728857dda&language=en-US&query="+query+"&page=1&include_adult=false";

        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject objectJSON = new JSONObject(response);
                    JSONArray arrayOfMoviesJSON = objectJSON.getJSONArray("results");
                    shows.clear();
                    for (int i = 0; i<arrayOfMoviesJSON.length(); i++) {
                        JSONObject object = arrayOfMoviesJSON.getJSONObject(i);
                        TVShow m = new TVShow();
                        m.setName(object.getString("name"));
                        m.setId(object.getInt("id"));
                        String posterUrl = "https://image.tmdb.org/t/p/w300" + object.getString("poster_path");
                        m.setPosterpath(posterUrl);
                        shows.add(m);
                    }
                    customAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                requestQueue.stop();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.print("Error!");
                error.printStackTrace();
                requestQueue.stop();
            }
        });

        requestQueue.add(request);
    }

    public void filterListView (String s) {
        if (s.length()>=3) {
            callWebServiceSearchShows(s);
        }
        else callWebService();
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return shows.size();
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
            view = getLayoutInflater().inflate(R.layout.customlistview, null);

            ImageView slika = view.findViewById(R.id.slika);
            TextView naziv = view.findViewById(R.id.naziv);

            naziv.setText(shows.get(i).getName());
            Picasso.get().load(shows.get(i).getPosterpath()).into(slika);

            return view;
        }

    }
}
