package com.example.amarlubovac.topmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
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

import java.util.HashMap;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {

    Integer id;
    String type;
    TextView name;
    ImageView imageView;
    TextView opis;
    TextView status;
    TextView budget;
    TextView text1;
    TextView text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        id = getIntent().getIntExtra("id", 0);
        type = getIntent().getStringExtra("type");
        status = findViewById(R.id.status);
        budget = findViewById(R.id.budget);
        name = findViewById(R.id.name);
        imageView = findViewById(R.id.image);
        opis = findViewById(R.id.opis);
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        if (type.equals("movie")) {
        callWBMovieDetails();}
        else {
            text1.setText("Number of episodes:");
            text2.setText("Number of seasons:");
            callWBShowDetails();
        }

    }

    private void callWBMovieDetails() {

        String url = "https://api.themoviedb.org/3/" + type + "/"+id.toString()+"?api_key=598686d3b14ded918cd5f79728857dda&language=en-US";

        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject objectJSON = new JSONObject(response);
                    name.setText(objectJSON.getString("title"));
                    opis.setText(objectJSON.getString("overview"));
                    String posterUrl = "https://image.tmdb.org/t/p/original" + objectJSON.getString("backdrop_path");
                    Picasso.get().load(posterUrl).into(imageView);
                    budget.setText(objectJSON.getString("budget"));
                    status.setText(objectJSON.getString("status"));


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

    private void callWBShowDetails() {

        String url = "https://api.themoviedb.org/3/" + type + "/"+id.toString()+"?api_key=598686d3b14ded918cd5f79728857dda&language=en-US";

        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject objectJSON = new JSONObject(response);
                    name.setText(objectJSON.getString("name"));
                    opis.setText(objectJSON.getString("overview"));
                    String posterUrl = "https://image.tmdb.org/t/p/original" + objectJSON.getString("backdrop_path");
                    Picasso.get().load(posterUrl).into(imageView);
                    budget.setText(objectJSON.getString("number_of_episodes"));
                    status.setText(objectJSON.getString("number_of_seasons"));


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
}
