package com.example.minecraftbuilders.models;

import com.example.minecraftbuilders.App;
import com.example.minecraftbuilders.handlers.LanguageHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SingletonBuildings {
    private static SingletonBuildings sSingletonBuildings;

    private static Map<String, List<Building>> mBuildings;
    private static String[] mCategoriesEn;
    private static String[] mCategoriesRu;

    public static SingletonBuildings get() {
        if(sSingletonBuildings == null)
            sSingletonBuildings = new SingletonBuildings();
        return sSingletonBuildings;
    }

    private SingletonBuildings() {
        mBuildings = new HashMap<>();
    }

    public Map<String, List<Building>> getBuildings() {
        return mBuildings;
    }

    public void setBuildings(Map<String, List<Building>> buildings) {
        mBuildings = buildings;
    }

    public String[] getCategories() {
        if(LanguageHandler.getLanguage(App.get()).equals("ru"))
            return mCategoriesRu;
        return mCategoriesEn;
    }

    public void setCategories(String[] categoriesEn, String[] categoriesRu) {
        mCategoriesEn = categoriesEn;
        mCategoriesRu = categoriesRu;
    }

    public List<Building> getBuildingsByCategory(String category) {
        for(int i = 0; i < mCategoriesRu.length; i ++)
            if(mCategoriesRu[i].equals(category))
                return mBuildings.get(mCategoriesEn[i]);
        return mBuildings.get(category);
    }

    public List<Building> getBuildingsByQuery(String query) {
        List<Building> result = new ArrayList<>();
        for(Map.Entry<String, List<Building>> entry : mBuildings.entrySet())
            for(Building building : entry.getValue())
                if(building.getTitle().contains(query))
                    result.add(building);
        return result;
    }

    public List<Building> getBuildingsByComplexity(String complexity) {
        List<Building> result = new ArrayList<>();
        for(Map.Entry<String, List<Building>> entry : mBuildings.entrySet())
            for(Building building : entry.getValue())
                if(building.getComplexity().equals(complexity))
                    result.add(building);
        return result;
    }

    public List<Building> getBuildingsByAuthor(String author) {
        List<Building> result = new ArrayList<>();
        for(Map.Entry<String, List<Building>> entry : mBuildings.entrySet())
            for(Building building : entry.getValue())
                if(building.getAuthor().equals(author))
                    result.add(building);
        return result;
    }

    public Building getBuilding(int id) {
        for(Map.Entry<String, List<Building>> entry : mBuildings.entrySet())
        for(Building building : entry.getValue())
            if(building.getId() == id)
                return building;
        return null;
    }

    public List<Building> getFavorites(Set<String> favorites) {
        List<Building> result = new ArrayList<>();
        outerLoop:
        for(String id : favorites)
            for(Map.Entry<String, List<Building>> entry : mBuildings.entrySet())
                for(Building building : entry.getValue())
                    if(building.getId() == Integer.valueOf(id)) {
                        result.add(building);
                        continue outerLoop;
                    }
        return result;
    }
}
