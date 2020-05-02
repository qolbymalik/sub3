package com.example.sub3.listmovies;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sub3.R;
import com.example.sub3.listmovies.detailmovie.DetailMovie;
import com.example.sub3.listmovies.pojo.ResponseMovies;
import com.example.sub3.listmovies.pojo.ResultsItem;
import androidx.lifecycle.Observer;

public class ListMoviesFragment extends Fragment {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private AlertDialog alertDialog;

    private Observer<ResponseMovies> getMovies = new Observer<ResponseMovies>() {
        @Override
        public void onChanged(ResponseMovies responseMovies) {
            if (responseMovies!=null){
                if (responseMovies.getAnError()==null){
                    ListMoviesAdapter mAdapter = new ListMoviesAdapter(responseMovies.getResults());
                    mAdapter.SetOnItemClickListener(new ListMoviesAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, ResultsItem model) {
                            Intent goToDetailMovie = new Intent(view.getContext(), DetailMovie.class);
                            goToDetailMovie.putExtra(DetailMovie.SELECTED_MOVIE,model);
                            startActivity(goToDetailMovie);
                        }
                    });
                    recyclerView.setAdapter(mAdapter);
                }else {
                    alertDialog.setMessage(responseMovies.getAnError().getMessage());
                    alertDialog.show();
                }

            }
            progressBar.setVisibility(View.GONE);
        }
    };
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_movies_fragment, container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    @SuppressWarnings("uncheked")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view);
        progressBar = view.findViewById(R.id.progressBar);
        alertDialog = new AlertDialog.Builder(view.getContext()).setTitle(getString(R.string.failure)).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).create();

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        ListMoviesViewModel mViewModel = ViewModelProviders.of(this).get(ListMoviesViewModel.class);
        mViewModel.doRequestListmovies();
        mViewModel.getMovies().observe(this,getMovies);
    }
}
