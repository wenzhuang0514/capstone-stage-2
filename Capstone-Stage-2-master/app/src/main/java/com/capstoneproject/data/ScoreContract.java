package com.capstoneproject.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class ScoreContract {

    public static final String AUTHORITY = "wen.com.capstoneproject";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public ScoreContract() {
    }

    public static final class ScoreEntry implements BaseColumns {
        public static final String TABLE_NAME = "scores";
        public static final String COLUMN_NAME = "category_name";
        public static final String COLUMN_LEVEL = "level";
        public static final String COLUMN_DATE = "quiz_date";
        public static final String COLUMN_SCORE = "score";
        public static final String COLUMN_ID = "id";
        public static final int POSITION_ID = 0;
        public static final int POSITION_NAME = 1;
        public static final int POSITION_LEVEL = 2;
        public static final int POSITION_SCORE = 3;
        public static final int POSITION_DATE = 4;
        public static final String[] SCORE_COLUMNS = {
                COLUMN_ID,
                COLUMN_NAME,
                COLUMN_LEVEL,
                COLUMN_SCORE,
                COLUMN_DATE
        };
    }

}
