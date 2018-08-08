package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String TAG = JsonUtils.class.getSimpleName();
    private final static String S_NAME = "name";
    private final static String S_ORIGIN = "placeOfOrigin";
    private final static String S_DESC = "description";
    private final static String S_IMAGE = "image";
    private final static String S_INGREDIENTS = "ingredients";
    private final static String S_MAIN_NAME = "mainName";
    private final static String S_AKA = "alsoKnownAs";
    public static Sandwich sandwichSetter;


    public static Sandwich parseSandwichJson(String json) {

        List<String> otherNamesList, ingredientsList;
        try {

            sandwichSetter = new Sandwich();
            JSONObject sandwichJson = new JSONObject(json);
            //Image For ImageView
            String image = sandwichJson.getString(S_IMAGE);
            sandwichSetter.setImage(image);

            //sandwich Main name
            JSONObject sandwichName = sandwichJson.getJSONObject(S_NAME);
            String mainSandwichName = sandwichName.getString(S_MAIN_NAME);
            sandwichSetter.setMainName(mainSandwichName);

            //Also Known as from JSON Array
            JSONArray alsoKnownAs = sandwichName.getJSONArray(S_AKA);
            otherNamesList = new ArrayList<>();
            for (int j = 0; j < alsoKnownAs.length(); j++) {
                Log.d(TAG, "items In Name" + alsoKnownAs.getString(j));
                otherNamesList.add(alsoKnownAs.getString(j));

            }
            sandwichSetter.setAlsoKnownAs(otherNamesList);

            //sandwich Ingredients
            JSONArray ingredients = sandwichJson.getJSONArray(S_INGREDIENTS);
            ingredientsList = new ArrayList<>();
            for (int j = 0; j < ingredients.length(); j++) {
                Log.d(TAG, "items in Ingredients" + ingredients.getString(j));
                ingredientsList.add(ingredients.getString(j));

            }
            sandwichSetter.setIngredients(ingredientsList);

            //sandwich Origin
            String origin = sandwichJson.getString(S_ORIGIN);
            sandwichSetter.setPlaceOfOrigin(origin);
            //sandwich Description
            String description = sandwichJson.getString(S_DESC);
            sandwichSetter.setDescription(description);


        } catch (JSONException e) {
            Log.d(TAG, "JSON Not Parsed Correctly");
            e.printStackTrace();
        }


        return sandwichSetter;
    }


}
