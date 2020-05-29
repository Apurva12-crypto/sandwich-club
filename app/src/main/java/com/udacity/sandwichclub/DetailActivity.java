package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    Sandwich sandwich;

    private TextView IngredientsView;
    private ImageView IngredientsImage;
    private TextView DescriptionView;
    private TextView AlsoKnownAsView;
    private TextView OriginView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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
        IngredientsImage = findViewById(R.id.image_iv);
        IngredientsView = findViewById(R.id.ingredients_tv);
        DescriptionView = findViewById(R.id.description_tv);
        AlsoKnownAsView = findViewById(R.id.also_known_tv);
        OriginView = findViewById(R.id.origin_tv);

        OriginView.setText(sandwich.getPlaceOfOrigin());

        TextUtils sandwich_names;
        AlsoKnownAsView.setText(TextUtils.join(",", sandwich.getAlsoKnownAs()));
        IngredientsView.setText(TextUtils.join(",", sandwich.getIngredients()));
        DescriptionView.setText((sandwich.getDescription()));
        List<String> sandwich_name = sandwich.getAlsoKnownAs();
        String  allNames = "";
        for (String a: sandwich_name) {
            allNames += a + ",";
        }
        if (allNames.length() > 0) {
            allNames = allNames.substring(0, allNames.length() - 2);
        }
        AlsoKnownAsView.setText(handleMissing(allNames));
        allNames= "";
        List<String> igList = sandwich.getIngredients();
        for (String s : igList) {
            allNames += s + "\n";
        }
        IngredientsView.setText(handleMissing(allNames));
    }

    private String handleMissing(String s) {
        if (s.equals("")) {

            return getString(R.string.data_missing);
        } else {

            return s;
        }

    }
}



