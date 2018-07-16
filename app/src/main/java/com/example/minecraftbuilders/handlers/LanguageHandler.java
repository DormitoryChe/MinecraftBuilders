package com.example.minecraftbuilders.handlers;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;

import com.example.minecraftbuilders.Constants;

import java.util.Arrays;
import java.util.Locale;

public class LanguageHandler extends android.content.ContextWrapper {

    public LanguageHandler(Context base) {
        super(base);
    }

    public static LanguageHandler wrap(Context context, Locale newLocale) {
        context.getSharedPreferences(Constants.APP_PREFERENCES, MODE_PRIVATE).edit().putString(Constants.LANGUAGE_PREFERENCES, newLocale.getLanguage()).apply();
        Resources res = context.getResources();
        Configuration configuration = res.getConfiguration();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
            configuration.setLocale(newLocale);
            LocaleList localeList = new LocaleList(newLocale);
            LocaleList.setDefault(localeList);
            configuration.setLocales(localeList);
            context = context.createConfigurationContext(configuration);
        } else {
            configuration.locale = newLocale;
            res.updateConfiguration(configuration, res.getDisplayMetrics());
        }
        return new LanguageHandler(context);
    }

    public static String  getLanguage(Context context) {
        if(Arrays.asList(new String[]{"en", "ru" }).contains(Locale.getDefault().getLanguage()))
            return context.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.LANGUAGE_PREFERENCES, Locale.getDefault().getLanguage());
        else
            return context.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE).getString(Constants.LANGUAGE_PREFERENCES, "en");
    }
}