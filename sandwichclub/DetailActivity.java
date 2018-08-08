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

import static com.udacity.sandwichclub.utils.JsonUtils.sandwichSetter;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    TextView aka, placeOfOrigin, ingredients, details;
    ImageView ingredientsIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsIv = findViewById(R.id.image_iv);
        aka = findViewById(R.id.also_known_tv);
        placeOfOrigin = findViewById(R.id.origin_tv);
        ingredients = findViewById(R.id.ingredients_tv);
        details = findViewById(R.id.description_tv);

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

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

        //Also Known As
        if (sandwichSetter.getAlsoKnownAs() == null) {

            aka.setText(R.string.detail_error_message_aka);

        } else {
            aka.setText(sandwichSetter.getAlsoKnownAs().toString().replace("[", "").replace("]", ""));
            if (aka.getText().equals("")) {
                aka.setText(R.string.detail_error_message_aka);
            }
        }

        //Ingredients
        if (sandwichSetter.getIngredients() == null) {

            ingredients.setText(getString(R.string.detail_error_message_ingredient));

        } else {
            ingredients.setText(sandwichSetter.getIngredients().toString().replace("[", "").replace("]", ""));
            if (ingredients.getText().equals("")) {
                ingredients.setText(R.string.detail_error_message_ingredient);
            }
        }

        //Place Of Origin
        if (sandwichSetter.getPlaceOfOrigin() == null || sandwichSetter.getPlaceOfOrigin().equals("")) {

            placeOfOrigin.setText(getString(R.string.detail_error_message_origin));

        } else {
            placeOfOrigin.setText(sandwichSetter.getPlaceOfOrigin());
        }

        //Description
        if (sandwichSetter.getDescription() == null) {

            details.setText(getString(R.string.detail_error_message_description));

        } else {
            details.setText(sandwichSetter.getDescription());
        }

    }
}
