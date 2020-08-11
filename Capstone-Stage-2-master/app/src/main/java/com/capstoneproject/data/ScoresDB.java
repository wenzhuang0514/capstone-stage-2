package com.capstoneproject.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;

import com.capstoneproject.Models.Score;

public class ScoresDB {

    static final String AUTHORITY_Uri = "content://" + ScoreContract.AUTHORITY;

    public void addScore(ContentResolver contentResolver, Score score) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ScoreContract.ScoreEntry.COLUMN_NAME, score.name);
        contentValues.put(ScoreContract.ScoreEntry.COLUMN_LEVEL, score.level);
        contentValues.put(ScoreContract.ScoreEntry.COLUMN_SCORE, score.scores + "");
        contentValues.put(ScoreContract.ScoreEntry.COLUMN_ID, score.id + "");
        contentValues.put(ScoreContract.ScoreEntry.COLUMN_DATE, score.date);
        contentResolver.insert(Uri.parse(AUTHORITY_Uri + "/scores"), contentValues);
    }

    public void removeScore(ContentResolver contentResolver, int id) {
        Uri uri = Uri.parse(AUTHORITY_Uri + "/" + id);
        contentResolver.delete(uri, null, new String[]{id + ""});
    }

    public void removeAllScore(ContentResolver contentResolver) {
        Uri uri = Uri.parse(AUTHORITY_Uri);
        contentResolver.delete(uri, null, null);
    }

    public ArrayList<Score> getAllScores(ContentResolver contentResolver) {
        Uri uri = Uri.parse(AUTHORITY_Uri + "/scores");
        Cursor cursor = contentResolver.query(uri, null, null, null, null, null);
        ArrayList<Score> scores = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Score score = new Score();
                score.id = cursor.getInt(cursor.getColumnIndex(ScoreContract.ScoreEntry.COLUMN_ID));
                score.name = cursor.getString(cursor.getColumnIndex(ScoreContract.ScoreEntry.COLUMN_NAME));
                score.level = cursor.getString(cursor.getColumnIndex(ScoreContract.ScoreEntry.COLUMN_LEVEL));
                score.scores = cursor.getInt(cursor.getColumnIndex(ScoreContract.ScoreEntry.COLUMN_SCORE));
                score.date = cursor.getString(cursor.getColumnIndex(ScoreContract.ScoreEntry.COLUMN_DATE));

                scores.add(score);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return scores;
    }

    public int getCount(ContentResolver contentResolver) {
        Uri uri = Uri.parse(AUTHORITY_Uri + "/scores");
        Cursor cursor = contentResolver.query(uri, null, null, null, null, null);
        return cursor.getCount();
    }

}
