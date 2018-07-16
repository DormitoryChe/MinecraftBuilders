package com.example.minecraftbuilders.models;

import com.example.minecraftbuilders.App;
import com.example.minecraftbuilders.handlers.LanguageHandler;
import com.google.gson.annotations.SerializedName;

public class Building {
    @SerializedName("id") private int mId;
    @SerializedName("title_ru") private String mTitleRu;
    @SerializedName("title_en") private String mTitleEn;
    @SerializedName("author_ru") private String mAuthorRu;
    @SerializedName("author_en") private String mAuthorEn;
    @SerializedName("description_ru") private String mDescriptionRu;
    @SerializedName("description_en") private String mDescriptionEn;
    @SerializedName("complexity") private String mComplexity;
    @SerializedName("main_image_url") private String mMainImagePath;
    @SerializedName("video_id") private String mVideoId;
    @SerializedName("size") private int[] mSize;
    @SerializedName("images_url") private String[] mImagesPath;

    public int getId() {
        return mId;
    }

    public String getTitle() {
        if(LanguageHandler.getLanguage(App.get()).equals("ru"))
            return mTitleRu;
        return mTitleEn;
    }

    public String getAuthor() {
        if(LanguageHandler.getLanguage(App.get()).equals("ru"))
            return mAuthorRu;
        return mAuthorEn;
    }

    public String getDescription() {
        if(LanguageHandler.getLanguage(App.get()).equals("ru"))
            return mDescriptionRu;
        return mDescriptionEn;
    }

    public String getComplexity() {
        return mComplexity;
    }

    public String getMainImagePath() {
        return mMainImagePath;
    }

    public String getVideoId() {
        return mVideoId;
    }

    public int[] getSize() {
        return mSize;
    }

    public String[] getImagesPath() {
        return mImagesPath;
    }
}
