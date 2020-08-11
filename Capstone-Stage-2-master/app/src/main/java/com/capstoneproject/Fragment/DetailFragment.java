package com.capstoneproject.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import com.capstoneproject.Adapter.ImageAdapter;
import com.capstoneproject.Models.Question;
import com.capstoneproject.R;
import com.capstoneproject.ui.QuestionsActivity;

public class DetailFragment extends Fragment {

    public static DetailFragment instance;
    public View detailFragmentView;
    TextView startQuiz;
    int question_id;
    private RadioGroup rg;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private int selectedId = 1;

    public DetailFragment() {
        instance = this;
    }

    public static DetailFragment newInstance(Question question) {
        DetailFragment fragment = new DetailFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        detailFragmentView = inflater.inflate(R.layout.fragment_detail, container, false);
        return detailFragmentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity().getIntent().getIntExtra(getActivity().getString(R.string.pos1), -23) != -23)
            question_id = getActivity().getIntent().getIntExtra(getActivity().getString(R.string.pos1), -23);
        // Log.e("Ques", question_id + "");
        Picasso.with(getContext())
                .load(ImageAdapter.mImgUrls[question_id])//this is optional the image to display while the url image is downloading
                .error(R.drawable.app_logo)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                .into((ImageView) detailFragmentView.findViewById(R.id.detailImage));
        TextView detailHead = (TextView) detailFragmentView.findViewById(R.id.detailHeading);
        detailHead.setText(ImageAdapter.mCatIds[question_id]);
        startQuiz = (TextView) detailFragmentView.findViewById(R.id.strq);
        rg = (RadioGroup) detailFragmentView.findViewById(R.id.radiogrp);
        rb1 = (RadioButton) detailFragmentView.findViewById(R.id.easy);
        rb2 = (RadioButton) detailFragmentView.findViewById(R.id.moderate);
        rb3 = (RadioButton) detailFragmentView.findViewById(R.id.hard);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == rb1.getId())
                    selectedId = 1;
                else if (checkedId == rb2.getId())
                    selectedId = 2;
                else
                    selectedId = 3;
                /*Toast.makeText(getBaseContext(), selectedId+"", Toast.LENGTH_SHORT).show();*/
            }
        });

        startQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), QuestionsActivity.class);
                intent.putExtra(getString(R.string.select_id), selectedId);
                intent.putExtra(getString(R.string.categg), CategoryNumber(question_id));
                startActivity(intent);
            }
        });
    }

    private int CategoryNumber(int question_id) {
        switch (question_id) {
            case 0:
                return 23;
            case 1:
                return 25;
            case 2:
                return 19;
            case 3:
                return 27;
            case 4:
                return 12;
            case 5:
                return 11;
            case 6:
                return 21;
            case 7:
                return 16;
            case 8:
                return 10;
            case 9:
                return 26;
            case 10:
                return 18;
            case 11:
                return 17;
            case 12:
                return 15;
            case 13:
                return 13;
            case 14:
                return 20;
            case 15:
                return 22;
            case 16:
                return 14;
            case 17:
                return 24;
            case 18:
                return 10;
            case 19:
                return 9;

        }
        return 9;
    }

}
