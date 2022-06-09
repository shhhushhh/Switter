package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.w3c.dom.Text;

import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {

    // Pass in the context and list of tweets
    Context context;
    List<Tweet> tweets;

    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Tweet> list) {
        tweets.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    // For each row, inflate the layout for the tweet
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    // Bind values based on the position of the element
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data at position
        Tweet tweet = tweets.get(position);
        // Bitmap
        CustomTarget<Bitmap> target = new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                // TODO 1. Instruct Glide to load the bitmap into the `holder.ivProfile` profile image view
                Glide.with(context).load(resource).into(holder.ivProfileImage);
                // TODO 2. Use generate() method from the Palette API to get the vibrant color from the bitmap
                Palette palette = Palette.from(resource).generate();
                // Set the result as the background color for `holder.vPalette` view containing the contact's name.
                Palette.Swatch lightVibrantSwatch = palette.getLightVibrantSwatch();
                if (lightVibrantSwatch != null) {
                    // Set the background color of a layout based on the vibrant color
                    holder.vPalette.setBackgroundColor(lightVibrantSwatch.getRgb());
                    Log.i("palette_color", "Color: " + lightVibrantSwatch.getRgb());
                }
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
                // can leave empty
            }
        };
        // Instruct Glide to load the bitmap into the asynchronous target defined above
        Glide.with(context).asBitmap().load(tweet.getThumbnailDrawable()).centerCrop().into(target);
        Log.i("bitmap_load", "Did bitmap color change work?");
        // Bind the tweet with view holder
        holder.bind(tweet);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    // Define a viewholder
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProfileImage;
        TextView tvBody;
        TextView tvScreenName;
        ImageView ivEmbedImg;
        TextView tvTimeStamp;
        View vPalette;
        ImageView ivReply;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfile);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            ivEmbedImg = itemView.findViewById(R.id.ivEmbedImg);
            tvTimeStamp = itemView.findViewById(R.id.tvTimeStamp);
            vPalette = itemView.findViewById(R.id.vPalette);
            ivReply = itemView.findViewById(R.id.ivReply);
        }

        public void bind(Tweet tweet) {
            int radius = 100;
            tvBody.setText(tweet.body);
            tvScreenName.setText(tweet.user.screenName);
            tvTimeStamp.setText(tweet.getRelativeTimeAgo(tweet.createdAt));
            Glide.with(context).load(tweet.user.publicImageUrl).centerCrop().transform(new RoundedCorners(360)).into(ivProfileImage);
            if (tweet.embedImgUrl != null) {
                ivEmbedImg.setVisibility(View.VISIBLE);
            } else {
                ivEmbedImg.setVisibility(View.GONE);
            }
            Log.i("embed_img_url_actual", "is the ivEmbedImg working");
            Glide.with(context).load(tweet.embedImgUrl).centerCrop().transform(new RoundedCorners(radius)).into(ivEmbedImg);
            Log.i("embed_img_url_actual1", "is the ivEmbedImg working2");
        }
    }
}
