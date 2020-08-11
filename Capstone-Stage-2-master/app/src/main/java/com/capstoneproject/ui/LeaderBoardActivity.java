package com.capstoneproject.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import com.capstoneproject.Adapter.LeaderBoardScoreAdapter;
import com.capstoneproject.Models.LeaderBoardScores;
import com.capstoneproject.R;

public class LeaderBoardActivity extends AppCompatActivity {

    private static final String TAG = LeaderBoardActivity.class.getSimpleName();
    ListView lv;
    LeaderBoardScoreAdapter leaderBoardScoreAdapter;
    ArrayList<LeaderBoardScores> lScores = new ArrayList<>();
    private FirebaseAuth auth;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lv = (ListView) findViewById(R.id.listView);
        leaderBoardScoreAdapter = new LeaderBoardScoreAdapter(lScores, this);
        lv.setAdapter(leaderBoardScoreAdapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        auth = FirebaseAuth.getInstance();
        mFirebaseInstance = FirebaseDatabase.getInstance();
        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference(getString(R.string.sco1));
        userId = auth.getCurrentUser().getUid();
        //  User user = new User(name, email);
        Query queryRef = mFirebaseDatabase.orderByChild(getString(R.string.sco2)).limitToLast(100);
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    LeaderBoardScores score = postSnapshot.getValue(LeaderBoardScores.class);
                    Log.e(TAG, getString(R.string.val12) + score.getUser_name() + " " + score.getScores());
                    lScores.add(score);
                    // Toast.makeText(getApplicationContext(),score.getScores(),Toast.LENGTH_SHORT).show();
                }
                Collections.reverse(lScores);
                leaderBoardScoreAdapter = new LeaderBoardScoreAdapter(lScores, LeaderBoardActivity.this);
                lv.setAdapter(leaderBoardScoreAdapter);
                leaderBoardScoreAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, getString(R.string.fail_msg), error.toException());
            }
        });
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
                    Log.e(TAG, getString(R.string.null_ss));
                    return;
                }
                Log.e(TAG, getString(R.string.data_s) + scores.getName() + ", " + scores.getUser_name());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, getString(R.string.fail_s), error.toException());
            }
        });
    }

}
