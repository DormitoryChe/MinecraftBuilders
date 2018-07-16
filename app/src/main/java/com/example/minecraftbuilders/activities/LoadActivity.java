package com.example.minecraftbuilders.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.minecraftbuilders.R;
import com.example.minecraftbuilders.handlers.NetworkHandler;
import com.example.minecraftbuilders.models.Building;
import com.example.minecraftbuilders.models.SingletonBuildings;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

public class LoadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        if(NetworkHandler.isConnectingToInternet(getApplicationContext()))
            new FetchData(LoadActivity.this).execute();
        else
            Toast.makeText(getApplicationContext(), "Проверьте подклюение к интернету", Toast.LENGTH_SHORT).show();

    }

    private static class FetchData extends AsyncTask<Void, String, Void> {
        private WeakReference<LoadActivity> mWeakReference;
        private String json = "";

        FetchData(LoadActivity loadActivity) {
            mWeakReference = new WeakReference<>(loadActivity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            /*try {
                BufferedReader reader = null;
                try {
                    URL url = new URL("http://papiroskawortu.ru/pozdravkeniscetch/json.txt");
                    reader = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
                    StringBuilder buffer = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null)
                        buffer.append(line);
                    json = buffer.toString();
                } finally {
                    if (reader != null)
                        reader.close();
                }
            } catch (Exception ignored) { }*/
            json = "{\n" +
                    "  \"data\": {\n" +
                    "    \"Category 1\": [\n" +
                    "      {\n" +
                    "        \"id\": 0,\n" +
                    "        \"title_ru\": \"Название 1\",\n" +
                    "        \"title_en\": \"Title 1\",\n" +
                    "        \"author_ru\": \"Женя\",\n" +
                    "        \"author_en\": \"Yevgen\",\n" +
                    "        \"description_ru\": \"Лорем ипсум долор сит амет Лорем ипсум долор сит амет Лорем ипсум долор сит амет Лорем ипсум долор сит амет Лорем ипсум долор сит амет Лорем ипсум долор сит амет Лорем ипсум долор сит амет Лорем ипсум долор сит амет Лорем ипсум долор сит амет\",\n" +
                    "        \"description_en\": \"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minLorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad min\",\n" +
                    "        \"complexity\": \"Complexity\",\n" +
                    "        \"main_image_url\": \"Main image url\",\n" +
                    "        \"video_id\": \"Video id\",\n" +
                    "        \"size\": [\n" +
                    "          100,\n" +
                    "          100,\n" +
                    "          100\n" +
                    "        ],\n" +
                    "        \"images_url\": [\n" +
                    "          \"First url\",\n" +
                    "          \"Second url\",\n" +
                    "          \"Third url\",\n" +
                    "          \"Fourth url\"\n" +
                    "        ]\n" +
                    "      }\n" +
                    "    ],\n" +
                    "    \"Category 2\": [\n" +
                    "      {\n" +
                    "        \"id\": 1,\n" +
                    "        \"title_ru\": \"Название 2\",\n" +
                    "        \"title_en\": \"Title 2\",\n" +
                    "        \"author_ru\": \"Женя\",\n" +
                    "        \"author_en\": \"Yevgen\",\n" +
                    "        \"description_ru\": \"Лорем ипсум долор сит амет Лорем ипсум долор сит амет Лорем ипсум долор сит амет Лорем ипсум долор сит амет Лорем ипсум долор сит амет Лорем ипсум долор сит амет Лорем ипсум долор сит амет Лорем ипсум долор сит амет Лорем ипсум долор сит амет\",\n" +
                    "        \"description_en\": \"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minLorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad min\",\n" +
                    "        \"complexity\": \"Complexity\",\n" +
                    "        \"main_image_url\": \"Main image url\",\n" +
                    "        \"video_id\": \"Video id\",\n" +
                    "        \"size\": [\n" +
                    "          100,\n" +
                    "          100,\n" +
                    "          100\n" +
                    "        ],\n" +
                    "        \"images_url\": [\n" +
                    "          \"First url\",\n" +
                    "          \"Second url\",\n" +
                    "          \"Third url\",\n" +
                    "          \"Fourth url\"\n" +
                    "        ]\n" +
                    "      }\n" +
                    "    ]\n" +
                    "  },\n" +
                    "  \"categories_en\": [\n" +
                    "    \"Category 1\",\n" +
                    "    \"Category 2\"\n" +
                    "  ],\n" +
                    "  \"categories_ru\": [\n" +
                    "    \"Категория 1\",\n" +
                    "    \"Категория 2\"\n" +
                    "  ]\n" +
                    "}";
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(json);
                Map<String, List<Building>> data = new Gson().fromJson(jsonObject.getJSONObject("data").toString(), new TypeToken<Map<String, List<Building>>>(){}.getType());
                SingletonBuildings.get().setBuildings(data);
                String[] categoriesEn = new Gson().fromJson(jsonObject.getJSONArray("categories_en").toString(), new TypeToken<String[]>(){}.getType());
                String[] categoriesRu = new Gson().fromJson(jsonObject.getJSONArray("categories_ru").toString(), new TypeToken<String[]>(){}.getType());
                SingletonBuildings.get().setCategories(categoriesEn, categoriesRu);
                Log.e("Building", SingletonBuildings.get().getBuildings().toString());
            } catch (JSONException e) {
                Log.e("Building", e.toString());
            }
            mWeakReference.get().startActivity(new Intent(mWeakReference.get(), MainActivity.class));
            mWeakReference.get().finish();
        }
    }
}
