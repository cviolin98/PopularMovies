package com.chrisblackledge.popularmovies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.chrisblackledge.popularmovies.MainActivityFragment;
import com.chrisblackledge.popularmovies.R;
import com.chrisblackledge.popularmovies.model.MovieParcel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieListAdapter extends ArrayAdapter<MovieParcel> {
    private Context mContext;
    private LayoutInflater mInflater;
    private MainActivityFragment mActivityFragment;
    private String mBaseImgStr = "";

    public MovieListAdapter(Context context, MainActivityFragment activityFragment, List<MovieParcel> imageUrls) {
        super(context, R.layout.movie_grid_image, imageUrls);

        this.mContext = context;
        this.mActivityFragment = activityFragment;
        this.mBaseImgStr = mActivityFragment.getString(R.string.api_base_image_url);

        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            MovieParcel movieParcel = getItem(position);

            if (null == convertView) {
                convertView = mInflater.inflate(R.layout.movie_grid_image, parent, false);
            }

            // get the number of columns
            //mActivityFragment.mNumGridViewCols = mActivityFragment.mGridView.getNumColumns();

            // get the screen width
            int screenWidth = mActivityFragment.getDisplayMetricsWidth();

            // eventual width of each image
            int newImageWidth = (screenWidth / mActivityFragment.getNumGridViewCols());

            // original dimensions : mImageWidth and mImageHeight
            int newImageHeight = ((newImageWidth * mActivityFragment.getImageHeight()) / mActivityFragment.getImageWidth());

            // get the poster URL
            String posterURL = movieParcel.getImagePosterURL();

            // sometimes, the poster URL from the API returns null;
            // if it does, load a temporary image if there is no image available
            if ((posterURL == null) || (posterURL.isEmpty()) || (posterURL.equals("null"))) {
                ImageView tempImageView = (ImageView) (convertView.findViewById(R.id.gridImage));
                tempImageView.setImageResource(R.drawable.no_image_thumb);
            } else {
                Picasso
                    .with(mContext)
                    .load(mBaseImgStr + posterURL)
                        .resize(newImageWidth, newImageHeight)
                        .into((ImageView) (convertView.findViewById(R.id.gridImage)));
            }
        }
        catch(IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return convertView;
    }
}
