package com.univerisity.rentify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.univerisity.Common.profile.Profile;
import com.univerisity.Common.utils.Activities;

public class MoreFragment extends Fragment {

    LinearLayout moreOptions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more, container, false);

        moreOptions = view.findViewById(R.id.more_options);
        addButton(view.getContext());

        return view;
    }

    private void addButton(Context context){
        if(Profile.isTipo()){
            buttonProperties(context);
        }
    }

    private void buttonProperties(Context context){
        Button button = new Button(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = 10;
        button.setLayoutParams(params);
        button.setText("Mis propeidades");
        button.setTextColor(getResources().getColor(R.color.sky_blue_3));
        button.setTextSize(18);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MyPropertiesActivity.class);
                startActivity(intent);
            }
        });

        moreOptions.addView(button, 1);
    }
}