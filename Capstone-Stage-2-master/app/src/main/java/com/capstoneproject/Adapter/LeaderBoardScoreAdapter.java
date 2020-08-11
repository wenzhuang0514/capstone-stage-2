package com.capstoneproject.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import com.capstoneproject.Models.LeaderBoardScores;
import com.capstoneproject.R;
import com.capstoneproject.ui.LeaderBoardActivity;

public class LeaderBoardScoreAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context mContext;
    private Cursor cursor;
    private ArrayList<LeaderBoardScores> lScores;

    public LeaderBoardScoreAdapter(ArrayList<LeaderBoardScores> lScores, LeaderBoardActivity leaderBoardActivity) {
        this.lScores = new ArrayList<>(lScores);
        this.mContext = leaderBoardActivity;
        inflater = (LayoutInflater) mContext.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return lScores.size();
    }

    @Override
    public Object getItem(int position) {
        return lScores.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.custom_leader_row, null);
        holder.rank = (TextView) rowView.findViewById(R.id.rank);
        holder.user_name = (TextView) rowView.findViewById(R.id.user_name);
        holder.name = (TextView) rowView.findViewById(R.id.name);
        holder.level = (TextView) rowView.findViewById(R.id.level);
        holder.score = (TextView) rowView.findViewById(R.id.score);
        holder.rank.setText(position + 1 + "");
        holder.user_name.setText(lScores.get(position).getUser_name());
        holder.name.setText(lScores.get(position).getName());
        holder.score.setText(lScores.get(position).getScores() + "");
        holder.level.setText(lScores.get(position).getLevel());
        return rowView;
    }

    public class Holder {
        TextView rank;
        TextView user_name;
        TextView name;
        TextView level;
        TextView score;
    }

}
