package com.example.minecraftbuilders.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.minecraftbuilders.Constants;
import com.example.minecraftbuilders.handlers.LanguageHandler;
import com.example.minecraftbuilders.R;
import com.example.minecraftbuilders.models.Building;
import com.example.minecraftbuilders.models.SingletonBuildings;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;

public class FavoritesActivity extends AppCompatActivity {
    private static final String EXTRA_ID = "extra_id";

    private DrawerLayout mDrawerLayout;
    private RecyclerView mBuildingRecyclerView;
    private BuildingAdapter mBuildingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle(R.string.drawer_favourites);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(1).setChecked(true);
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

        mBuildingRecyclerView = findViewById(R.id.recycler_view);
        mBuildingRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        configureRecyclerView();
    }

    private void configureRecyclerView() {
        if(mBuildingAdapter == null) {
            mBuildingAdapter = new BuildingAdapter(SingletonBuildings.get().getFavorites(getSharedPreferences(Constants.APP_PREFERENCES, MODE_PRIVATE).getStringSet(Constants.FAVORITES_PREFERENCES, new HashSet<String>())));
            mBuildingRecyclerView.setAdapter(mBuildingAdapter);
        } else {
            mBuildingAdapter.setBuildings(SingletonBuildings.get().getFavorites(getSharedPreferences(Constants.APP_PREFERENCES, MODE_PRIVATE).getStringSet(Constants.FAVORITES_PREFERENCES, new HashSet<String>())));
            mBuildingAdapter.notifyDataSetChanged();
        }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setIconifiedByDefault(false);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        Locale newLocale = new Locale(LanguageHandler.getLanguage(newBase));
        Context context = LanguageHandler.wrap(newBase, newLocale);
        super.attachBaseContext(context);
    }

    private class BuildingHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTextViewTitle;
        private TextView mTextViewAuthor;
        private Building mBuilding;

        BuildingHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_building, parent, false));
            itemView.setOnClickListener(this);

            mTextViewTitle = itemView.findViewById(R.id.title);
            mTextViewAuthor = itemView.findViewById(R.id.author);
        }

        public void bind(Building building) {
            mBuilding = building;
            mTextViewTitle.setText(mBuilding.getTitle());
            mTextViewAuthor.setText(mBuilding.getAuthor());
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), BuildingActivity.class);
            intent.putExtra(EXTRA_ID, mBuilding.getId());
            startActivity(intent);
        }
    }

    private class BuildingAdapter extends RecyclerView.Adapter<BuildingHolder> {
        private List<Building> mBuildings;

        BuildingAdapter(List<Building> buildings) {
            this.mBuildings = buildings;
        }

        @NonNull
        @Override
        public BuildingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(FavoritesActivity.this);
            return new BuildingHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull BuildingHolder holder, int position) {
            holder.bind(mBuildings.get(position));
        }

        @Override
        public int getItemCount() {
            return mBuildings.size();
        }

        void setBuildings(List<Building> buildings) {
            mBuildings = buildings;
        }
    }
}
