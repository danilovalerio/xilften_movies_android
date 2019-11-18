package com.danilovalerio.xilftenmovies.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.danilovalerio.xilftenmovies.model.Category;
import com.danilovalerio.xilftenmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/*AsyncTask
Espera 3 propriedades
- sequência de parâmetros
- espera progress se quiser
- resultado da operação
 */
public class JsonDownloadTask extends AsyncTask<String, Void, List<Category>> {
    private final Context context;
    ProgressDialog dialog;

    public JsonDownloadTask(Context context){
        this.context = context;
    }

    //main-thread (exibir uma progressbar)
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = ProgressDialog
                .show(context, "Carregando", "",true);
    }

    // thread - background
    @Override
    protected List<Category> doInBackground(String... params) {
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
            
            List<Category> categories = getCategories(new JSONObject(jsonAsString));
            in.close();

            return categories;

        }catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    //JSON para obj java
    private List<Category> getCategories(JSONObject json) throws JSONException {
        List<Category> categories = new ArrayList<>();

        JSONArray categoryArray = json.getJSONArray("category"); //category é o objeto na api
        for(int i = 0; i < categoryArray.length(); i++){
            JSONObject category = categoryArray.getJSONObject(i);
            String title = category.getString("title");

            List<Movie> movies = new ArrayList<>();
            JSONArray movieArray = category.getJSONArray("movie");
            for (int j = 0; j <movieArray.length(); j++){
                JSONObject movie = movieArray.getJSONObject(j);

                String coverUrl = movie.getString("cover_url");

                Movie movieObj = new Movie();
                movieObj.setCoverUrl(coverUrl);

                movies.add(movieObj);

            }

            Category categoryObj = new Category();
            categoryObj.setName(title);
            categoryObj.setMovies(movies);

            categories.add(categoryObj);
        }

        return categories;
    }

    //main-thread (ocultar uma progressbar)
    @Override
    protected void onPostExecute(List<Category> categories) {
        super.onPostExecute(categories);
        dialog.dismiss();
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
}
