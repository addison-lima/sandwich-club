package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {

        JSONObject jsonObj = new JSONObject(json);
        JSONObject jsonName = jsonObj.getJSONObject("name");
        JSONArray jsonKnownAs = jsonName.getJSONArray("alsoKnownAs");
        JSONArray jsonIngredients = jsonObj.getJSONArray("ingredients");

        String mainName = jsonName.getString("mainName");
        List<String> alsoKnownAs = new ArrayList<>();
        String placeOfOrigin = jsonObj.getString("placeOfOrigin");
        String description = jsonObj.getString("description");
        String image = jsonObj.getString("image");
        List<String> ingredients = new ArrayList<>();

        for (int i = 0; i < jsonKnownAs.length(); i++) {
            alsoKnownAs.add(jsonKnownAs.getString(i));
        }

        for (int i = 0; i < jsonIngredients.length(); i++) {
            ingredients.add(jsonIngredients.getString(i));
        }

        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }
}
