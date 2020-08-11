package com.capstoneproject.ui;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.capstoneproject.Models.LeaderBoardScores;
import com.capstoneproject.Models.Score;
import com.capstoneproject.R;
import com.capstoneproject.Widget.WidgetProvider;
import com.capstoneproject.data.ScoresDB;

public class ResultActivity extends AppCompatActivity {

    private static final String TAG = ResultActivity.class.getSimpleName();
    ContentResolver contentResolver;
    ScoresDB mdb;
    private FirebaseAuth auth;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CircularProgressBar circularProgressBar = (CircularProgressBar) findViewById(R.id.progressBar);
        TextView result_score = (TextView) findViewById(R.id.resultText);
        Button reviewQuestion = (Button) findViewById(R.id.review);
        auth = FirebaseAuth.getInstance();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference(getString(R.string.sscr));
        userId = auth.getCurrentUser().getUid();
        mdb = new ScoresDB();
        int scored = 0;
        contentResolver = getContentResolver();
        Button homeButton = (Button) findViewById(R.id.Choose);
        toolbar.setTitle(R.string.resul);
        Intent intent = getIntent();
        String level = intent.getStringExtra(getString(R.string.lvl));
        String category = intent.getStringExtra(getString(R.string.catego));
        if (intent.getIntExtra(getString(R.string.sco), -1) == -1) {
            Toast.makeText(ResultActivity.this, R.string.error_msg, Toast.LENGTH_SHORT).show();
            Intent newIntet = new Intent(ResultActivity.this, MainActivity.class);
            startActivity(newIntet);
        } else {
            scored = intent.getIntExtra(getString(R.string.scod), -1);
            result_score.setText(scored * 5 + "%");
            int animationDuration = 2500; // 2500ms = 2,5s
            circularProgressBar.setProgressWithAnimation(scored * 5, animationDuration);
        }
        String date = new SimpleDateFormat(getString(R.string.datf)).format(new Date());

        Score score = new Score(mdb.getCount(contentResolver) + 1, category, scored * 5, date, level);
        mdb.addScore(contentResolver, score);
        Intent wIntent = new Intent(this, WidgetProvider.class);
        wIntent.setAction(getString(R.string.widup));
        int[] ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), WidgetProvider.class));
        wIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(wIntent);
        //Log.e("Score Added", "Move to database");
        LeaderBoardScores scores = new LeaderBoardScores(userId, auth.getCurrentUser().getDisplayName(), category, scored * 5, date, level);
        mFirebaseDatabase.push().setValue(scores); // add firebase exact user
        addScoreChangeListener();


        reviewQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResultActivity.this.onBackPressed();
            }
        });
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntet = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(newIntet);
            }
        });
        setSupportActionBar(toolbar);
    }

    /**
     * LeaderBoardScore data change listener
     */
    private void addScoreChangeListener() {
        // Score data change listener
        mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LeaderBoardScores scores = dataSnapshot.getValue(LeaderBoardScores.class);

                // Check for null
                if (scores == null) {
                    Log.e(TAG, getString(R.string.null_s));
                    return;
                }
                Log.e(TAG, getString(R.string.change_s) + scores.getName() + ", " + scores.getUser_name());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, getString(R.string.eroor), error.toException());
            }
        });
    }

}
