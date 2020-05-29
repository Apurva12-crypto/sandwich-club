package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    public static final String NAME = "name";
    public static final String MAINNAME = "mainName";
    private static final String ALSOKNOWNAS = "alsoKnownAs";
    private static final String PLACEOFORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String INGREDIENTS = "ingredients";
    private static final String IMAGE = "image";

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        String description = "";
        String name = "";
        String origin = "";
        String imageSource = "";
        List<String> alsoKnownAs = new ArrayList<>();
        List<String> ingredients = new ArrayList<>();

        try {
            JSONObject SandwichObject = new JSONObject(json);
            JSONObject jName = new JSONObject(name);
            name = jName.optString(MAINNAME);

            JSONArray jAlsoKnownAs = jName.getJSONArray(ALSOKNOWNAS);
            for (int i = 0; i < jAlsoKnownAs.length(); i++) {
                alsoKnownAs.add(jAlsoKnownAs.getString(i));
            }
            origin = SandwichObject.getString(PLACEOFORIGIN);
            imageSource = SandwichObject.getString(IMAGE);
            description = SandwichObject.getString(DESCRIPTION);

            JSONArray jIngredients = jName.getJSONArray(INGREDIENTS);
            for (int j = 0; j < jIngredients.length(); j++) {
                ingredients.add(jIngredients.getString(j));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new Sandwich(name, alsoKnownAs , origin, description, imageSource, ingredients);
    }
}





