package com.codepath.apps.restclienttemplate.models;

import android.util.Log;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Tweet {
    public String body;
    public String createdAt;
    public User user;
    public String embedImgUrl;

    public Tweet() {};

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
//        if(jsonObject.has("full_text")) {
//            tweet.body = jsonObject.getString("full_text");
//        } else {
//            tweet.body = jsonObject.getString("text");
//        }
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        boolean hasMedia = jsonObject.getJSONObject("entities").has("media");
        if (hasMedia) {
            tweet.embedImgUrl = jsonObject.getJSONObject("entities").getJSONArray("media").getJSONObject(0).getString("media_url_https");
            Log.i("embed_img_url", tweet.embedImgUrl);
        }
        Log.i("tweet object", "json constructor in tweet class");
        return tweet;
    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }
}
