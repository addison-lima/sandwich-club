package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

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
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        setTitle(sandwich.getMainName());

        updateImage(sandwich.getImage());

        updatePlaceOfOrigin(sandwich.getPlaceOfOrigin());

        StringBuilder alsoKnownAs = new StringBuilder();
        for (String knownAs : sandwich.getAlsoKnownAs()) {
            alsoKnownAs.append("- ");
            alsoKnownAs.append(knownAs);
            alsoKnownAs.append("\n");
        }
        updateAlsoKnownAs(alsoKnownAs.toString());

        StringBuilder ingredients = new StringBuilder();
        for (String ingredient : sandwich.getIngredients()) {
            ingredients.append("- ");
            ingredients.append(ingredient);
            ingredients.append("\n");
        }
        updateIngredients(ingredients.toString());

        updateDescription(sandwich.getDescription());
    }

    private void updateImage(String image) {
        ImageView ingredientsIv = findViewById(R.id.image_iv);
        Picasso.with(this)
                .load(image)
                .into(ingredientsIv);
    }

    private void updatePlaceOfOrigin(String placeOfOrigin) {
        TextView originTv = findViewById(R.id.origin_tv);
        if (placeOfOrigin.isEmpty()) {
            TextView originTitleTv = findViewById(R.id.origin_label_tv);
            originTitleTv.setVisibility(View.GONE);
            originTv.setVisibility(View.GONE);
        } else {
            String origin = placeOfOrigin + "\n";
            originTv.setText(origin);
        }
    }

    private void updateAlsoKnownAs(String alsoKnownAs) {
        TextView alsoKnownTv = findViewById(R.id.also_known_tv);
        if (alsoKnownAs.isEmpty()) {
            TextView alsoKnownTitleTv = findViewById(R.id.also_known_label_tv);
            alsoKnownTitleTv.setVisibility(View.GONE);
            alsoKnownTv.setVisibility(View.GONE);
        } else {
            alsoKnownTv.setText(alsoKnownAs);
        }
    }

    private void updateIngredients(String ingredients) {
        TextView ingredientsTv = findViewById(R.id.ingredients_tv);
        if (ingredients.isEmpty()) {
            TextView ingredientsTitleTv = findViewById(R.id.ingredients_label_tv);
            ingredientsTitleTv.setVisibility(View.GONE);
            ingredientsTv.setVisibility(View.GONE);
        } else {
            ingredientsTv.setText(ingredients);
        }
    }

    private void updateDescription(String description) {
        TextView descriptionTv = findViewById(R.id.description_tv);
        if (description.isEmpty()) {
            TextView descriptionTitleTv = findViewById(R.id.description_label_tv);
            descriptionTitleTv.setVisibility(View.GONE);
            descriptionTv.setVisibility(View.GONE);
        } else {
            descriptionTv.setText(description);
        }
    }
}
