package com.danilovalerio.xilftenmovies.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.danilovalerio.xilftenmovies.model.Category;
import com.danilovalerio.xilftenmovies.model.Movie;
import com.danilovalerio.xilftenmovies.model.MovieDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MovieDetailTask extends AsyncTask<String, Void, MovieDetail> {

    private final WeakReference<Context> context;
    private ProgressDialog dialog;
    private MovieDetailLoader movieDetailLoader;

    public MovieDetailTask(Context context){
        this. context = new WeakReference<>(context);
    }

    public void setMovideDetailLoader(MovieDetailLoader movieDetailLoader){
        this.movieDetailLoader = movieDetailLoader;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Context context = this.context.get();
        if(context != null)
            dialog = ProgressDialog
                    .show(context, "Carregando", "",true);

    }

    @Override
    protected MovieDetail doInBackground(String... params) {
        //vai pegar o 1º parâmetro que virá através do execute
        String url = params[0];

        try {
            URL requestUrl = new URL(url);

            HttpsURLConnection urlConnection = (HttpsURLConnection) requestUrl.openConnection();
            //espera somente 2 seg.
            urlConnection.setReadTimeout(2000);
            //quando a internet caiu, espera 2 seg. para mostrar o erro
            urlConnection.setConnectTimeout(2000);

            int responseCode = urlConnection.getResponseCode();
            if(responseCode > 400){
                throw new IOException("Erro na comunicação com o servidor");
            }

            //sequencia de bytes
            InputStream inputStream = urlConnection.getInputStream();

            BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());

            String jsonAsString = bytesToString(in);
//            Log.i("Teste", jsonAsString);

            MovieDetail movieDetail = getMovieDetail(new JSONObject(jsonAsString));
            in.close();

            return movieDetail;

        }catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private MovieDetail getMovieDetail(JSONObject json) throws JSONException {
        int id = json.getInt("id");
        String title = json.getString("title");
        String desc = json.getString("desc");
        String cast = json.getString("cast");
        String coverUrl = json.getString("cover_url");

        List<Movie> movies = new ArrayList<>();
        JSONArray movieArray = json.getJSONArray("movie");
        for(int i = 0; i < movieArray.length(); i++){
            JSONObject movie = movieArray.getJSONObject(i);
            String c = movie.getString("cover_url");
            int idSimilar = movie.getInt("id");

            Movie similar = new Movie();
            similar.setId(idSimilar);
            similar.setCoverUrl(c);

            movies.add(similar);
        }

        Movie movie = new Movie();
        movie.setId(id);
        movie.setCoverUrl(coverUrl);
        movie.setTitle(title);
        movie.setDesc(desc);
        movie.setCast(cast);

        return new MovieDetail(movie, movies);
    }

    @Override
    protected void onPostExecute(MovieDetail movieDetail) {
        super.onPostExecute(movieDetail);
        dialog.dismiss();
        if (movieDetailLoader != null)
            movieDetailLoader.onResult(movieDetail);
    }

    private String bytesToString(InputStream is) throws IOException {
        byte[] bytes = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int lidos;
        while ((lidos = is.read(bytes)) > 0){
            baos.write(bytes, 0, lidos);
        }

        return new String(baos.toByteArray());

    }

    //Iterface do onResult
    public interface MovieDetailLoader {
        void onResult(MovieDetail movieDetail);
    }
}
