package com.example.minecraftbuilders.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minecraftbuilders.Constants;
import com.example.minecraftbuilders.handlers.LanguageHandler;
import com.example.minecraftbuilders.R;
import com.example.minecraftbuilders.models.Building;
import com.example.minecraftbuilders.models.SingletonBuildings;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class BuildingActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    private static final String API_KEY = "AIzaSyCLkwVWEqHCdDpFt3hjxl-RZJMccqcVlsw";
    private static final String VIDEO = "ra-fQSFK7Fo";
    private static final String EXTRA_ID = "extra_id";

    private DrawerLayout mDrawerLayout;
    private AlertDialog mAlertDialog;
    private Building mBuilding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);

        mBuilding = SingletonBuildings.get().getBuilding(getIntent().getIntExtra(EXTRA_ID, 0));

        ((TextView)findViewById(R.id.title)).setText(mBuilding.getTitle());
        ((TextView)findViewById(R.id.author)).setText(mBuilding.getAuthor());
        ((TextView)findViewById(R.id.title)).setText(mBuilding.getTitle());
        ((TextView)findViewById(R.id.description)).setText(mBuilding.getDescription());
        ((TextView)findViewById(R.id.x)).setText(String.valueOf(mBuilding.getSize()[0]));
        ((TextView)findViewById(R.id.y)).setText(String.valueOf(mBuilding.getSize()[1]));
        ((TextView)findViewById(R.id.z)).setText(String.valueOf(mBuilding.getSize()[2]));

        mDrawerLayout = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        mDrawerLayout.closeDrawers();
                        switch (menuItem.getItemId()) {
                            case R.id.buildings:
                                startActivity(intent);
                                finish();
                                return true;
                            case R.id.favourites:
                                startActivity(new Intent(getApplicationContext(), FavoritesActivity.class));
                                return true;
                            case R.id.russian:
                                if(!LanguageHandler.getLanguage(getApplicationContext()).equals("ru")) {
                                    LanguageHandler.wrap(getApplicationContext(), new Locale("ru"));
                                    startActivity(intent);
                                    finish();
                                }
                                return true;
                            case R.id.english:
                                if(!LanguageHandler.getLanguage(getApplicationContext()).equals("en")) {
                                    LanguageHandler.wrap(getApplicationContext(), new Locale("en"));
                                    startActivity(intent);
                                    finish();
                                }
                                return true;
                            case R.id.about_us:
                                return true;
                            case R.id.rate_us:
                                return true;
                        }
                        return true;
                    }
                });

        findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        YouTubePlayerView youTubeView = findViewById(R.id.video);
        youTubeView.initialize(API_KEY, this);

        initAlertDialogForBigImage();
        findViewById(R.id.screen_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.show();
            }
        });
        findViewById(R.id.screen_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.show();
            }
        });
        findViewById(R.id.screen_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.show();
            }
        });
        findViewById(R.id.screen_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.show();
            }
        });

        final ImageButton imageButtonFavorite = findViewById(R.id.favorite);
        if(isFavorite()) {
            imageButtonFavorite.setImageResource(R.drawable.ic_favorite);
        } else
            imageButtonFavorite.setImageResource(R.drawable.ic_not_favorite);
        imageButtonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFavorite()) {
                    removeFromFavorite();
                    imageButtonFavorite.setImageResource(R.drawable.ic_not_favorite);
                } else {
                    addToFavorite();
                    imageButtonFavorite.setImageResource(R.drawable.ic_favorite);
                }
            }
        });
    }

    private void initAlertDialogForBigImage() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(BuildingActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_big_image, null);
        builder.setTitle(null);
        builder.setCancelable(true);
        builder.setView(dialogView);
        mAlertDialog = builder.create();
        mAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private boolean isFavorite() {
        Set<String> favoriteSet = getSharedPreferences(Constants.APP_PREFERENCES, MODE_PRIVATE).getStringSet(Constants.FAVORITES_PREFERENCES, new HashSet<String>());
        return favoriteSet.contains(String.valueOf(mBuilding.getId()));
    }

    private void removeFromFavorite() {
        Set<String> favoriteSet = getSharedPreferences(Constants.APP_PREFERENCES, MODE_PRIVATE).getStringSet(Constants.FAVORITES_PREFERENCES, new HashSet<String>());
        Set<String> favoriteSetCopy = new HashSet<>(favoriteSet);
        favoriteSetCopy.remove(String.valueOf(mBuilding.getId()));
        getSharedPreferences(Constants.APP_PREFERENCES, MODE_PRIVATE).edit().putStringSet(Constants.FAVORITES_PREFERENCES, favoriteSetCopy).apply();
    }

    private void addToFavorite() {
        Set<String> favoriteSet = getSharedPreferences(Constants.APP_PREFERENCES, MODE_PRIVATE).getStringSet(Constants.FAVORITES_PREFERENCES, new HashSet<String>());
        Set<String> favoriteSetCopy = new HashSet<>(favoriteSet);
        favoriteSetCopy.add(String.valueOf(mBuilding.getId()));
        getSharedPreferences(Constants.APP_PREFERENCES, MODE_PRIVATE).edit().putStringSet(Constants.FAVORITES_PREFERENCES, favoriteSetCopy).apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.cueVideo(VIDEO);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        //TODO make error
        Toast.makeText(this, "Shit!" + youTubeInitializationResult.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        Locale newLocale = new Locale(LanguageHandler.getLanguage(newBase));
        Context context = LanguageHandler.wrap(newBase, newLocale);
        super.attachBaseContext(context);
    }
}
