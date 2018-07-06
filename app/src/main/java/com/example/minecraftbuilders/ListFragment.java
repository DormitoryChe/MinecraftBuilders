package com.example.minecraftbuilders;

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

public class ListFragment extends Fragment {
    private static final String ARGUMENT_CATEGORY = "argument_category";

    private RecyclerView mRecyclerView;
    private BuildingAdapter mAdapter;

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
        mAdapter = new BuildingAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private class BuildingHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        BuildingHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_building, parent, false));
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            startActivity(new Intent(getActivity(), BuildingActivity.class));
        }
    }

    private class BuildingAdapter extends RecyclerView.Adapter<BuildingHolder> {

        @NonNull
        @Override
        public BuildingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            return new BuildingHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull BuildingHolder holder, int position) {
        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }
}
