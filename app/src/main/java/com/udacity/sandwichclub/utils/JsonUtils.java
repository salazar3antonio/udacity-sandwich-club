package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static final String TAG = JsonUtils.class.getSimpleName();

    public static Sandwich parseSandwichJson(String json) {


        // COMPLETED 7/27/2018 Parse JSON string and return a Sandwich Object with stored data

        Sandwich sandwich = new Sandwich();
        List<String> aKasNames = new ArrayList<>();
        List<String> ingredients = new ArrayList<>();

        try {
            //access high level JSON Objects
            JSONObject mainObject = new JSONObject(json);
            JSONObject nameObject = mainObject.getJSONObject("name");
            JSONArray aKasJSONArray = nameObject.getJSONArray("alsoKnownAs");
            JSONArray ingredientsJSONArray = mainObject.getJSONArray("ingredients");

            //loop through JSONArray and add String name to alsoKnownAsNames ArrayList<>
            for (int i = 0; i < aKasJSONArray.length(); i++) {
                String aKasName = aKasJSONArray.getString(i);
                aKasNames.add(i, aKasName);
            }

            //loop through JSONArray and add String name to Ingredients ArrayList<>
            for (int i = 0; i < ingredientsJSONArray.length(); i++) {
                String ingredientName = ingredientsJSONArray.getString(i);
                ingredients.add(i, ingredientName);
            }

            //get Strings from the JSONObjects
            String mainName = nameObject.getString("mainName");
            String description = mainObject.getString("description");
            String placeOfOrigin = mainObject.getString("placeOfOrigin");
            String image = mainObject.getString("image");

            //set Sandwich Object properties parsed from JSONObjects
            sandwich.setIngredients(ingredients);
            sandwich.setAlsoKnownAs(aKasNames);
            sandwich.setMainName(mainName);
            sandwich.setDescription(description);
            sandwich.setPlaceOfOrigin(placeOfOrigin);
            sandwich.setImage(image);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //return Sandwich Object with stored data
        return sandwich;
    }
}
