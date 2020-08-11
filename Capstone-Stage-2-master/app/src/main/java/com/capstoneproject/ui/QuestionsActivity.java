package com.capstoneproject.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import com.capstoneproject.Models.Question;
import com.capstoneproject.R;
import com.capstoneproject.service.MResultReceiver;
import com.capstoneproject.service.MyIntentService;

public class QuestionsActivity extends AppCompatActivity implements MResultReceiver.Receiver {

    static String level = "medium";
    private static int correct_ans = 0;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private RequestQueue mRequestQueue;
    private MResultReceiver mReceiver;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        correct_ans = 0;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());

        String category = getString(R.string.cat_1);
        int cat = 12;
        if (getIntent().getIntExtra(getString(R.string.select1), -1) != -1) {
            switch (getIntent().getIntExtra(getString(R.string.select12), 1)) {
                case 1:
                    level = getString(R.string.easy1);
                    break;
                case 2:
                    level = getString(R.string.med);
                    break;
                case 3:
                    level = getString(R.string.di);
                    break;
            }
        }
        if (getIntent().getIntExtra(getString(R.string.caa), -1) != -1) {
            cat = getIntent().getIntExtra(getString(R.string.caaa), 12);
            if (cat != 9)
                category = getString(R.string.catr) + cat;
            else
                category = "";
        }
        mReceiver = new MResultReceiver(new Handler());
        mReceiver.setReceiver(this);
        Intent intent = new Intent(Intent.ACTION_SYNC, null, this, MyIntentService.class);
        String url = getString(R.string.mlink) + category + getString(R.string.catr1);

        /* Send optional extras to Download IntentService */
        intent.putExtra(getString(R.string.url), url);
        intent.putExtra(getString(R.string.rec), mReceiver);
        intent.putExtra(getString(R.string.req), 101);

        startService(intent);

        // fetch(level,category,cat);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setOffscreenPageLimit(19);
        mViewPager.setAdapter(mSectionsPagerAdapter);



       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

    }


// Using Intent Service instead of Volley
    /*private void fetch(String difficulty, final String category,final int cat) {

     //   String url = "https://opentdb.com/api.php?amount=20" + category + "&difficulty=" + difficulty + "&type=multiple";
        String url = "https://opentdb.com/api.php?amount=20" + category  + "&type=multiple";
        Log.e("URL",url.toString());
        JsonObjectRequest request = new JsonObjectRequest(
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        // TODO: Parse the JSON
                        try {
                            JSONArray items = jsonObject.getJSONArray("results");
                            JSONObject questionObj;
                            for (int i = 0; i < items.length(); i++) {
                                questionObj = items.getJSONObject(i);

                                JSONArray st = questionObj.getJSONArray("incorrect_answers");
                                String[] inc = new String[st.length()];
                                for (int j = 0; j < st.length(); j++)
                                { inc[j] = st.getString(j).replaceAll("&quot;","\"").replaceAll("&#039;","'");

                                Log.e("Inc",inc[j]);}

                                Question question = new Question(i, questionObj.getString("question").replaceAll("&quot;","\"").replaceAll("&#039;","'"), cat, questionObj.getString("correct_answer").replaceAll("&quot;","\"").replaceAll("&#039;","'"), inc, questionObj.getString("difficulty"));
                                mSectionsPagerAdapter.addQuestion(question);
                                mSectionsPagerAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(QuestionsActivity.this, "Unable to fetch data: " + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        mRequestQueue.add(request);
    }
*/
   /* public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_questions, menu);
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

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
            case MyIntentService.STATUS_RUNNING:
                setProgressBarIndeterminateVisibility(true);
                break;
            case MyIntentService.STATUS_FINISHED:
                /* Hide progress & extract result from bundle */
                setProgressBarIndeterminateVisibility(false);

                //  String[] results = resultData.getStringArray("result");
                ArrayList<Question> questions = (ArrayList<Question>) resultData.getSerializable(getString(R.string.res));
                for (Question question : questions) {
                    mSectionsPagerAdapter.addQuestion(question);
                    mSectionsPagerAdapter.notifyDataSetChanged();
                }

                break;
            case MyIntentService.STATUS_ERROR:
                /* Handle the error */
                String error = resultData.getString(Intent.EXTRA_TEXT);
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
                break;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String ARG_QUESTION = "QUESTION";
        private static final String ARG_CORRRECT_ANS = "ANSWER";
        private static final String ARG_INCORRECT_1 = "INC1";
        private static final String ARG_INCORRECT_2 = "INC2";
        private static final String ARG_INCORRECT_3 = "INC3";
        private static final String ARG_LEVEL = "LEVEL";
        private static final String CATEGORY = "CATEGORY";


        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, Question question) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putString(ARG_QUESTION, question.getName());
            args.putString(ARG_CORRRECT_ANS, question.getCorrect_ans());
            args.putString(ARG_INCORRECT_1, question.getIncorrect_ans1());
            args.putString(ARG_INCORRECT_2, question.getIncorrect_ans2());
            args.putString(ARG_INCORRECT_3, question.getIncorrect_ans3());
            args.putString(ARG_LEVEL, QuestionsActivity.level);
            args.putString(CATEGORY, question.category);
            Log.e("CAT", question.category);
            fragment.setArguments(args);
            return fragment;
        }


        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

        }

        @Override
        public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_questions, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.Question);
            final Button button1 = (Button) rootView.findViewById(R.id.ans1);
            final Button button2 = (Button) rootView.findViewById(R.id.ans2);
            final Button button3 = (Button) rootView.findViewById(R.id.ans3);
            final Button button4 = (Button) rootView.findViewById(R.id.ans4);
            final Button buttonResult = (Button) rootView.findViewById(R.id.results);
            textView.setText(getArguments().getString(ARG_QUESTION));
            final String level = getArguments().getString(ARG_LEVEL);
            final String category = getArguments().getString(CATEGORY);
            List<String> list = new ArrayList<String>();
            list.add(getArguments().getString(ARG_CORRRECT_ANS));
            list.add(getArguments().getString(ARG_INCORRECT_3));
            list.add(getArguments().getString(ARG_INCORRECT_1));
            list.add(getArguments().getString(ARG_INCORRECT_2));
            Log.e("List", list.toString());
            java.util.Collections.shuffle(list);
            button1.setText(list.get(0));
            button2.setText(list.get(1));
            button3.setText(list.get(2));
            button4.setText(list.get(3));
            switch (list.indexOf(getArguments().getString(ARG_CORRRECT_ANS))) {
                case 0:
                    button1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            button1.setBackground(getResources().getDrawable(R.drawable.my_button_correct));
                            button1.setClickable(false);
                            button2.setClickable(false);
                            button3.setClickable(false);
                            button4.setClickable(false);
                            QuestionsActivity.correct_ans++;
                            Toast.makeText(getContext(), QuestionsActivity.correct_ans + "", Toast.LENGTH_SHORT).show();
                        }
                    });
                    button2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            button2.setBackground(getResources().getDrawable(R.drawable.my_button_incorrect));
                            button1.setBackground(getResources().getDrawable(R.drawable.my_button_correct));
                            button1.setClickable(false);
                            button2.setClickable(false);
                            button3.setClickable(false);
                            button4.setClickable(false);
                        }
                    });
                    button3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            button3.setBackground(getResources().getDrawable(R.drawable.my_button_incorrect));
                            button1.setBackground(getResources().getDrawable(R.drawable.my_button_correct));
                            button1.setClickable(false);
                            button2.setClickable(false);
                            button3.setClickable(false);
                            button4.setClickable(false);
                        }
                    });
                    button4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            button4.setBackground(getResources().getDrawable(R.drawable.my_button_incorrect));
                            button1.setBackground(getResources().getDrawable(R.drawable.my_button_correct));
                            button1.setClickable(false);
                            button2.setClickable(false);
                            button3.setClickable(false);
                            button4.setClickable(false);
                        }
                    });
                    break;
                case 1:
                    button1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            button1.setBackground(getResources().getDrawable(R.drawable.my_button_incorrect));
                            button2.setBackground(getResources().getDrawable(R.drawable.my_button_correct));
                            button1.setClickable(false);
                            button2.setClickable(false);
                            button3.setClickable(false);
                            button4.setClickable(false);
                        }
                    });
                    button2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            button2.setBackground(getResources().getDrawable(R.drawable.my_button_correct));
                            button1.setClickable(false);
                            button2.setClickable(false);
                            button3.setClickable(false);
                            button4.setClickable(false);
                            QuestionsActivity.correct_ans++;
                            Toast.makeText(getContext(), QuestionsActivity.correct_ans + "", Toast.LENGTH_SHORT).show();
                        }
                    });
                    button3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            button3.setBackground(getResources().getDrawable(R.drawable.my_button_incorrect));
                            button2.setBackground(getResources().getDrawable(R.drawable.my_button_correct));
                            button1.setClickable(false);
                            button2.setClickable(false);
                            button3.setClickable(false);
                            button4.setClickable(false);
                        }
                    });
                    button4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            button4.setBackground(getResources().getDrawable(R.drawable.my_button_incorrect));
                            button2.setBackground(getResources().getDrawable(R.drawable.my_button_correct));
                            button1.setClickable(false);
                            button2.setClickable(false);
                            button3.setClickable(false);
                            button4.setClickable(false);
                        }
                    });
                    break;
                case 2:
                    button1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            button1.setBackground(getResources().getDrawable(R.drawable.my_button_incorrect));
                            button1.setClickable(false);
                            button2.setClickable(false);
                            button3.setClickable(false);
                            button4.setClickable(false);
                            button3.setBackground(getResources().getDrawable(R.drawable.my_button_correct));
                        }
                    });
                    button2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            button2.setBackground(getResources().getDrawable(R.drawable.my_button_incorrect));
                            button3.setBackground(getResources().getDrawable(R.drawable.my_button_correct));
                            button1.setClickable(false);
                            button2.setClickable(false);
                            button3.setClickable(false);
                            button4.setClickable(false);
                        }
                    });
                    button3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            button3.setBackground(getResources().getDrawable(R.drawable.my_button_correct));
                            button1.setClickable(false);
                            button2.setClickable(false);
                            button3.setClickable(false);
                            button4.setClickable(false);
                            QuestionsActivity.correct_ans++;
                            Toast.makeText(getContext(), QuestionsActivity.correct_ans + "", Toast.LENGTH_SHORT).show();
                        }
                    });
                    button4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            button4.setBackground(getResources().getDrawable(R.drawable.my_button_incorrect));
                            button1.setClickable(false);
                            button2.setClickable(false);
                            button3.setClickable(false);
                            button4.setClickable(false);
                            button3.setBackground(getResources().getDrawable(R.drawable.my_button_correct));
                        }
                    });
                    break;
                case 3:
                    button1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            button1.setBackground(getResources().getDrawable(R.drawable.my_button_incorrect));
                            button4.setBackground(getResources().getDrawable(R.drawable.my_button_correct));
                            button1.setClickable(false);
                            button2.setClickable(false);
                            button3.setClickable(false);
                            button4.setClickable(false);
                        }
                    });
                    button2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            button2.setBackground(getResources().getDrawable(R.drawable.my_button_incorrect));
                            button4.setBackground(getResources().getDrawable(R.drawable.my_button_correct));
                            button1.setClickable(false);
                            button2.setClickable(false);
                            button3.setClickable(false);
                            button4.setClickable(false);
                        }
                    });
                    button3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            button3.setBackground(getResources().getDrawable(R.drawable.my_button_incorrect));
                            button4.setBackground(getResources().getDrawable(R.drawable.my_button_correct));
                            button1.setClickable(false);
                            button2.setClickable(false);
                            button3.setClickable(false);
                            button4.setClickable(false);
                        }
                    });
                    button4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            button4.setBackground(getResources().getDrawable(R.drawable.my_button_correct));
                            button1.setClickable(false);
                            button2.setClickable(false);
                            button3.setClickable(false);
                            button4.setClickable(false);
                            QuestionsActivity.correct_ans++;
                            Toast.makeText(getContext(), QuestionsActivity.correct_ans + "", Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
            }
            if ((getArguments().getInt(ARG_SECTION_NUMBER)) == 20) {
                buttonResult.setVisibility(View.VISIBLE);
                buttonResult.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ResultActivity.class);
                        intent.putExtra("SCORE", QuestionsActivity.correct_ans);
                        intent.putExtra("LEVEL", level);
                        intent.putExtra("CATEGORY", category);
                        startActivity(intent);
                    }
                });
            }
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public ArrayList<Question> questions = new ArrayList<Question>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1, questions.get(position));
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return questions.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return questions.get(position).getCategory() + "";
        }

        public void addQuestion(Question question) {
            questions.add(question);
        }
    }

}