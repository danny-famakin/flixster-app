package com.example.famakindaniel7.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by famakindaniel7 on 6/22/17.
 */

public class Configuration {
    String imageBaseUrl;
    String posterSize;
    String backdropSize;


    public Configuration(JSONObject object) throws JSONException {
        JSONObject images = object.getJSONObject("images");
        imageBaseUrl = images.getString("secure_base_url");
        JSONArray posterSizeOptions = images.getJSONArray("poster_sizes");
        posterSize = posterSizeOptions.optString(3, "w342");
        JSONArray backdropSizeOptions= images.getJSONArray("backdrop_sizes");
        backdropSize = backdropSizeOptions.optString(1, "w780");
    }
    public String getImageUrl(String size, String path) {
        return String.format("%s%s%s", imageBaseUrl, size, path);
    }

    public String getImageBaseUrl() {
        return imageBaseUrl;
    }

    public String getPosterSize() {
        return posterSize;
    }

    public String getBackdropSize() {
        return backdropSize;
    }
}
