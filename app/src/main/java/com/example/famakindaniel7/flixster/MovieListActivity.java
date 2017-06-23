package com.example.famakindaniel7.flixster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.famakindaniel7.flixster.models.Configuration;
import com.example.famakindaniel7.flixster.models.Movies;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieListActivity extends AppCompatActivity {
    public static final String API_URL = "https://api.themoviedb.org/3";
    public static final String API_KEY_PARAMETER = "api_key";
    public static final String TAG = "MovieListActivity";
    AsyncHttpClient client;
    String imageBaseUrl;
    String posterSize;
    ArrayList<Movies> movies;
    RecyclerView rvMovies;
    MovieAdapter adapter;
    Configuration config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        client = new AsyncHttpClient();
        movies = new ArrayList<>();
        adapter = new MovieAdapter(movies);
        rvMovies = (RecyclerView) findViewById(R.id.rvMovies);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        rvMovies.setAdapter(adapter);
        getConfig();
    }
    private void getNowPlaying() {
        String url = API_URL + "/movie/now_playing";
        RequestParams params = new RequestParams();
        //request API parameters
        params.put (API_KEY_PARAMETER, getString(R.string.api_key));
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        Movies movie = new Movies(results.getJSONObject(i));
                        movies.add(movie);
                        adapter.notifyItemInserted(movies.size() - 1);
                    }
                    Log.i(TAG, String.format("Loaded %s movies", results.length()));
                } catch (JSONException e) {
                    logError("Failed to parse now playing movies", e, true);
                }
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                logError("Failed to get data from now playing endpoint", throwable, true);
            }
        });
    }
public void getConfig() {
    String url = API_URL + "/configuration";
    RequestParams params = new RequestParams();
    //request API parameters
    params.put (API_KEY_PARAMETER, getString(R.string.api_key));
    client.get(url, params, new JsonHttpResponseHandler() {

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

            try {
                config = new Configuration(response);
                Log.i(TAG, String.format("Loaded configuration with imageBaseUrl %s and posterSize %s",
                        config.getImageBaseUrl(), config.getPosterSize()));
                adapter.setConfig(config);
                getNowPlaying();
            } catch (JSONException e) {
                logError("Failed parsing configuration", e, true);
            }
        }


        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            logError("Failed getting configuration", throwable, true);
        }
    });
}
   private void logError(String message, Throwable error, boolean alertUser) {
       Log.e(TAG, message, error);
       if (alertUser) {
           Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
       }
   }
}
