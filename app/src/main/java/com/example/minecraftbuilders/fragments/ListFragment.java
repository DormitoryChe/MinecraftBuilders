package com.example.minecraftbuilders.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.minecraftbuilders.R;
import com.example.minecraftbuilders.activities.BuildingActivity;
import com.example.minecraftbuilders.activities.SearchActivity;
import com.example.minecraftbuilders.models.Building;
import com.example.minecraftbuilders.models.SingletonBuildings;

import java.util.List;

public class ListFragment extends Fragment {
    private static final String ARGUMENT_CATEGORY = "argument_category";
    private static final String EXTRA_ID = "extra_id";
    private static final String EXTRA_COMPLEXITY = "extra_complexity";
    private static final String EXTRA_AUTHOR = "extra_author";

    private RecyclerView mRecyclerView;

    public static ListFragment getInstance(String category) {
        ListFragment listFragment = new ListFragment();
        Bundle arguments = new Bundle();
        arguments.putString(ARGUMENT_CATEGORY, category);
        listFragment.setArguments(arguments);
        return listFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        initRecycler();
        return view;
    }

    private void initRecycler(){
        BuildingAdapter adapter = new BuildingAdapter(SingletonBuildings.get().getBuildingsByCategory(getArguments().getString(ARGUMENT_CATEGORY)));
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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
            itemView.findViewById(R.id.complexity).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), SearchActivity.class);
                    intent.putExtra(EXTRA_COMPLEXITY, mBuilding.getComplexity());
                    startActivity(intent);
                }
            });
            itemView.findViewById(R.id.author).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), SearchActivity.class);
                    intent.putExtra(EXTRA_AUTHOR, mBuilding.getAuthor());
                    startActivity(intent);
                }
            });

        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), BuildingActivity.class);
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
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
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
