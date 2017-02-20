package com.example.david.simplemath.models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.david.simplemath.R;

import java.util.ArrayList;

/**
 * Created by david on 20.2.2017..
 */

public class AdapterModel extends ArrayAdapter<HighscoreModel> {

    public AdapterModel(Context context, ArrayList<HighscoreModel> highscoreModelList) {
        super(context, 0, highscoreModelList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        HighscoreModel highscoreModel = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.highscore_item, parent, false);
        }

        TextView score = (TextView) convertView.findViewById(R.id.score_item);
        TextView date = (TextView) convertView.findViewById(R.id.date_item);

        score.setText(highscoreModel.getScore());
        date.setText(highscoreModel.getDate());

        return convertView;
    }
}
