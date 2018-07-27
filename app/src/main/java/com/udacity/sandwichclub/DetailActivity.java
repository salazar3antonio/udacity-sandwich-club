package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.function.ToDoubleBiFunction;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView mDescription;
    private TextView mPlaceOfOrigin;
    private TextView mAlsoKnownAsNames;
    private TextView mIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mDescription = findViewById(R.id.description_tv);
        mPlaceOfOrigin = findViewById(R.id.place_of_origin_tv);
        mAlsoKnownAsNames = findViewById(R.id.also_known_tv);
        mIngredients = findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        // COMPLETED 7/27/2018 Set Views with corresponding Sandwich properties

        if (sandwich.getAlsoKnownAs().isEmpty()) {
            mAlsoKnownAsNames.setText(R.string.detail_empty_also_known_as_label);
        } else {
            for (int i = 0; i < sandwich.getAlsoKnownAs().size(); i++) {
                String name = sandwich.getAlsoKnownAs().get(i);
                mAlsoKnownAsNames.append(name);
                // COMPLETED 7/27/2018 Handle end of list with a period
                if (i + 1 == sandwich.getAlsoKnownAs().size()) {
                    mAlsoKnownAsNames.append(".");
                } else {
                    mAlsoKnownAsNames.append(", ");
                }
            }
        }

        for (int i = 0; i < sandwich.getIngredients().size(); i++) {
            String name = sandwich.getIngredients().get(i);
            mIngredients.append(name);
            // COMPLETED 7/27/2018 Handle end of list with a period
            if (i + 1 == sandwich.getIngredients().size()) {
                mIngredients.append(".");
            } else {
                mIngredients.append(", ");
            }
        }

        mDescription.setText(sandwich.getDescription());

        // COMPLETED 7/27/2018 Handle empty place of orgin text
        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            mPlaceOfOrigin.setText(R.string.detail_empty_place_of_origin_label);
        } else {
            mPlaceOfOrigin.setText(sandwich.getPlaceOfOrigin());
        }

    }
}
