package com.capstoneproject.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.capstoneproject.R;
import com.capstoneproject.data.ScoreContract;
import com.capstoneproject.ui.ProfileActivity;

public class ScoreAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    private Cursor cursor;

    public ScoreAdapter(ProfileActivity profileActivity) {
        // TODO Auto-generated constructor stub
        context = profileActivity;

        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        // return scores.size();
        int count = 0;
        if (cursor != null) {
            count = cursor.getCount();
        }
        return count;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        cursor.moveToPosition(position);
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.custom_row_scores, null);
        holder.date = (TextView) rowView.findViewById(R.id.date);
        holder.name = (TextView) rowView.findViewById(R.id.name);
        holder.level = (TextView) rowView.findViewById(R.id.level);
        holder.score = (TextView) rowView.findViewById(R.id.score);
        // holder.date.setText(scores.get(position).getDate());
        holder.date.setText(cursor.getString(ScoreContract.ScoreEntry.POSITION_DATE));
        holder.name.setText(cursor.getString(ScoreContract.ScoreEntry.POSITION_NAME));
        holder.level.setText(cursor.getString(ScoreContract.ScoreEntry.POSITION_LEVEL));
        holder.score.setText(cursor.getInt(ScoreContract.ScoreEntry.POSITION_SCORE) + "");
        /*holder.name.setText(scores.get(position).getName());
        holder.score.setText(scores.get(position).getScores()+"");
        holder.level.setText(scores.get(position).getLevel());*/
        return rowView;
    }

    public class Holder {
        TextView date;
        TextView name;
        TextView level;
        TextView score;
    }

}
