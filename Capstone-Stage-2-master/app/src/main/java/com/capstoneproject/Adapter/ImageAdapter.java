package com.capstoneproject.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import com.capstoneproject.R;

public class ImageAdapter extends BaseAdapter {

    public static String[] mCatIds = {
            "History", "Arts"
            , "Mathematics", "Wildlife", "Music",
            "Films", "Sports", "Board Games", "Books"
    };

    public static int[] mImgUrls = {
            R.drawable.history, R.drawable.arts,
            R.drawable.math,
            R.drawable.wildlife,
            R.drawable.music,
            R.drawable.film,
            R.drawable.sports,
            R.drawable.game,
            R.drawable.book
    };

    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mImgUrls.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        ImageView imageView;
        LayoutInflater mInflater = (LayoutInflater) mContext
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            // if it's not recycled, initialize some attributes

            convertView = mInflater.inflate(R.layout.custom_row, null);
            holder = new ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.category);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);


            /* imageView.setPadding(0,4,0,4);*/
        } else {
            // imageView = (ImageView) convertView;
            holder = (ViewHolder) convertView.getTag();

        }

        // holder.imageView.setImageResource(mThumbIds[position]);
        Picasso.with(mContext)
                .load(mImgUrls[position])
                .error(R.drawable.app_logo)
                .into(holder.imageView);
        holder.txtTitle.setText(mCatIds[position]);
        return convertView;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
    }

}
