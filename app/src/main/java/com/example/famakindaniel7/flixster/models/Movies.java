package com.example.famakindaniel7.flixster.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by famakindaniel7 on 6/22/17.
 */
@Parcel
public class Movies {
    public String title;
    public String overview;
    public String posterPath;
    public String backdropPath;
    public Double vote_average;

    public Movies(){}

    public Movies(JSONObject object) throws JSONException {
        title = object.getString("title");
        overview = object.getString("overview");
        posterPath = object.getString("poster_path");
        backdropPath = object.getString("backdrop_path");
        vote_average = object.getDouble("vote_average");
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public Double getVote_average() {
        return vote_average;
    }
}
