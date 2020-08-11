package com.capstoneproject.Widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import java.util.ArrayList;

import com.capstoneproject.R;
import com.capstoneproject.data.ScoreContract;

//THanks to http://dharmangsoni.blogspot.in/2014/03/collection-widget-with-event-handling.html
@SuppressLint("NewApi")
class WidgetDataProvider implements RemoteViewsFactory, LoaderManager.LoaderCallbacks<Cursor> {

    private static final int SCORE_LOADER = 0;
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> date = new ArrayList<>();
    ArrayList<String> level = new ArrayList<>();
    ArrayList<String> scores = new ArrayList<>();
    Context mContext = null;
    private Cursor cursor;

    public WidgetDataProvider(Context context, Intent intent) {
        mContext = context;
    }

    void setCursor(Cursor cursor) {
        this.cursor = cursor;
        ///  notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return scores.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews mView = new RemoteViews(mContext.getPackageName(),
                R.layout.custom_row_scores);
        mView.setTextViewText(R.id.date, date.get(position));
        mView.setTextColor(R.id.date, Color.BLACK);
        mView.setTextViewText(R.id.name, names.get(position));
        mView.setTextColor(R.id.name, Color.BLACK);
        mView.setTextViewText(R.id.level, level.get(position));
        mView.setTextColor(R.id.level, Color.BLACK);
        mView.setTextViewText(R.id.score, scores.get(position));
        mView.setTextColor(R.id.score, Color.BLACK);

        final Intent fillInIntent = new Intent();
        fillInIntent.setAction(WidgetProvider.ACTION_TOAST);
        final Bundle bundle = new Bundle();
        bundle.putString(WidgetProvider.EXTRA_STRING,
                scores.get(position));
        fillInIntent.putExtras(bundle);
        mView.setOnClickFillInIntent(R.id.container, fillInIntent);
        return mView;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onCreate() {
        initData();
    }

    @Override
    public void onDataSetChanged() {
        initData();
    }

    private void initData() {
        names.clear();
        level.clear();
        scores.clear();
        date.clear();
        mContext.grantUriPermission(mContext.getString(R.string.package_name), ScoreContract.CONTENT_URI, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        cursor = mContext.getContentResolver().query(ScoreContract.CONTENT_URI, ScoreContract.ScoreEntry.SCORE_COLUMNS, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                names.add(cursor.getString(ScoreContract.ScoreEntry.POSITION_NAME));
                level.add(cursor.getString(ScoreContract.ScoreEntry.POSITION_LEVEL));
                date.add(cursor.getString(ScoreContract.ScoreEntry.POSITION_DATE));
                scores.add(cursor.getString(ScoreContract.ScoreEntry.POSITION_SCORE));
            }
            cursor.close();
        }
        //  Log.e("Cursor", scores.toString());
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(mContext,
                ScoreContract.CONTENT_URI,
                ScoreContract.ScoreEntry.SCORE_COLUMNS,
                null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.getCount() != 0) {
            //  error.setVisibility(View.GONE);
        }
        setCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        setCursor(null);
    }

}
