package com.capstoneproject.ui;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.capstoneproject.Adapter.ImageAdapter;
import com.capstoneproject.R;
import com.capstoneproject.Widget.WidgetProvider;
import com.capstoneproject.data.ScoresDB;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public View v;
    public View s;
    GridView gridView;
    ScoresDB mdb;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            //  MainFragment.instance.movies.clear();
            // MainFragment.instance.movies =(ArrayList<Movies>)savedInstanceState.get("Movie_Saved");
        }
        setContentView(R.layout.main_activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //get firebase auth instance
        auth = FirebaseAuth.getInstance();
        mdb = new ScoresDB();
        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

        toolbar.setTitle(R.string.choose);
        v = findViewById(R.id.content_layout);
        gridView = (GridView) findViewById(R.id.gridView);
        FragmentManager manager = getSupportFragmentManager();
        gridView.setAdapter(new ImageAdapter(this));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                //  Toast.makeText(MainActivity.this, "" + position,
                //    Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(getString(R.string.pos12), position);
                startActivity(intent);

            }
        });
        if (!checknetconnection()) {
            Snackbar.make(v, R.string.no_in, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.clo, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    })
                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                    .show();

        }

        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        TextView name_drawer = (TextView) hView.findViewById(R.id.name_draw);
        name_drawer.setText(user.getDisplayName());
        TextView email_drawer = (TextView) hView.findViewById(R.id.email_draw);
        email_drawer.setText(user.getEmail());
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!checknetconnection()) {
            Snackbar.make(v, R.string.in1, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.co1, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                        }
                    })
                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                    .show();

        }


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (!checknetconnection()) {
            Snackbar.make(v, R.string.in2, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.co2, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    })
                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                    .show();

        }

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (!checknetconnection()) {
            Snackbar.make(v, R.string.in4, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.co3, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    })
                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                    .show();

        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        // outState.putParcelableArrayList("Movie_Saved", MainFragment.instance.movies);
        if (!checknetconnection()) {
            Snackbar.make(v, R.string.in5, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.co6, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    })
                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                    .show();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        int id = item.getItemId();

        if (id == R.id.profile) {
            // Handle the camera action
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.changeEmail) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.delete_profile) {
            Intent intent = new Intent(MainActivity.this, LeaderBoardActivity.class);
            startActivity(intent);
        } else if (id == R.id.about) {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            auth.signOut();
            mdb.removeAllScore(getContentResolver());
            Intent wIntent = new Intent(this, WidgetProvider.class);
            wIntent.setAction(getString(R.string.wigup));
            int[] ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), WidgetProvider.class));
            wIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
            sendBroadcast(wIntent);
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public boolean checknetconnection() {
        ConnectivityManager manager_c = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activenetwork = manager_c.getActiveNetworkInfo();
        boolean isConnected = activenetwork != null && activenetwork.isConnectedOrConnecting();
        return isConnected;
    }

}

