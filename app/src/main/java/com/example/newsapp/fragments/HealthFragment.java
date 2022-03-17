package com.example.newsapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.utils.NewsLoader;
import com.example.newsapp.R;

import java.util.Locale;

public class HealthFragment extends Fragment {
    private RecyclerView recyclerView;
    private static final  String CATEGORY="health";
    private static final String TITLE="Health";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_health,container,false);
        recyclerView=view.findViewById(R.id.recyclerView);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(TITLE);
        NewsLoader newsLoader=new NewsLoader(getContext(),recyclerView,CATEGORY,false,null);
        newsLoader.loadNews();
    }
}
