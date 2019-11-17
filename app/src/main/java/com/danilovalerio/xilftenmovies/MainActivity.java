package com.danilovalerio.xilftenmovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.danilovalerio.xilftenmovies.model.Movie;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_main);

    }

    private static class MovieHolder extends RecyclerView.ViewHolder {

        public MovieHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private class MainAdapter extends RecyclerView.Adapter<MovieHolder>{

        private final List<Movie> movies;

        private MainAdapter(List<Movie> movies){
            this.movies = movies;
        }

        @NonNull
        @Override
        public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MovieHolder(getLayoutInflater().inflate(R.layout.movie_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
            Movie movie = movies.get(position);

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }
}
