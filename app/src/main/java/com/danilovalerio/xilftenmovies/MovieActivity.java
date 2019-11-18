package com.danilovalerio.xilftenmovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.danilovalerio.xilftenmovies.model.Movie;
import com.danilovalerio.xilftenmovies.model.MovieDetail;
import com.danilovalerio.xilftenmovies.util.ImageDownloaderTask;
import com.danilovalerio.xilftenmovies.util.MovieDetailTask;

import java.util.ArrayList;
import java.util.List;

public class MovieActivity extends AppCompatActivity implements MovieDetailTask.MovieDetailLoader {
    private TextView txtTitle;
    private TextView txtDesc;
    private TextView txtCast;
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        //Mock
        txtTitle = findViewById(R.id.text_view_title);
        txtDesc = findViewById(R.id.text_view_desc);
        txtCast = findViewById(R.id.text_view_cast);
        recyclerView = findViewById(R.id.recycler_view_similar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //configuraões da toolbar
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        }

        //Pegar o drawable, e para manter compatibilidade com dispositivos antigos usamos o ContextCompat
        LayerDrawable drawable = (LayerDrawable) ContextCompat.getDrawable(this, R.drawable.shadows);

        //Realiza a troca da imagem que está lá no shadows
        if (drawable != null){
            Drawable movieCover = ContextCompat.getDrawable(this, R.drawable.movie);
//            drawable.setDrawableByLayerId(R.id.cover_drawable, movieCover);
//            ((ImageView) findViewById(R.id.image_view_cover)).setImageDrawable(drawable);
        }

        /*txtTitle.setText("Batman Begins");
        txtDesc.setText("O jovem Bruce Wayne cai em um poço e é atacado por morcegos. Bruce acorda de um pesadelo bal balb albalbalblalb allalb al blabl a");
        txtCast.setText(getString(R.string.cast, "Fulano 1"+", Fulano 2"+", Fulano 3"+", Fulano 4"));

        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            Movie movie = new Movie();
            movies.add(movie);
        }*/

        List<Movie> movies = new ArrayList<>();
        movieAdapter = new MovieAdapter(movies);
        recyclerView.setAdapter(movieAdapter);
        //o layout manager em formato de grid no tamanho de 3 colunas
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            int id = getIntent().getExtras().getInt("id");
            MovieDetailTask movieDetailTask = new MovieDetailTask(this);
            movieDetailTask.setMovideDetailLoader(this);
            movieDetailTask.execute("https://tiagoaguiar.co/api/netflix/1");
        }

    }

    @Override
    public void onResult(MovieDetail movieDetail) {
//        Log.i("Teste", movieDetail.toString());
        txtTitle.setText(movieDetail.getMovie().getTitle());
        txtDesc.setText(movieDetail.getMovie().getDesc());
        txtCast.setText(movieDetail.getMovie().getCast());

        movieAdapter.setMovies(movieDetail.getMoviesSimilar());
        movieAdapter.notifyDataSetChanged();
    }


    //Coleções Opções semelhantes
    private static class MovieHolder extends RecyclerView.ViewHolder {

        final ImageView imageViewCover;

        MovieHolder(@NonNull View itemView) {
            super(itemView);
            imageViewCover = itemView.findViewById(R.id.image_view_cover);
        }
    }

    private class MovieAdapter extends RecyclerView.Adapter<MovieHolder> {

        private final List<Movie> movies;

        private MovieAdapter(List<Movie> movies) {
            this.movies = movies;
        }

        void setMovies(List<Movie> movies){
            this.movies.clear();
            this.movies.addAll(movies);
        }

        @NonNull
        @Override
        public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MovieHolder(getLayoutInflater()
                    .inflate(R.layout.movie_item_similar, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
            Movie movie = movies.get(position);
            new ImageDownloaderTask(holder.imageViewCover).execute(movie.getCoverUrl());
//            holder.imageViewCover.setImageResource(movie.getCoverUrl());
        }

        @Override
        public int getItemCount() {
            return movies.size();
        }
    }

}
