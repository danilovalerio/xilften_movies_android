package com.danilovalerio.xilftenmovies.util;

import android.os.AsyncTask;

import com.danilovalerio.xilftenmovies.model.Category;

import java.util.List;

/*AsyncTask
Espera 3 propriedades
- sequência de parâmetros
- espera progress se quiser
- resultado da operação
 */
public class JsonDownloadTask extends AsyncTask<String, Void, List<Category>> {
    //main-thread (exibir uma progressbar)
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    // thread - background
    @Override
    protected List<Category> doInBackground(String... params) {
        return null;
    }

    //main-thread (ocultar uma progressbar)
    @Override
    protected void onPostExecute(List<Category> categories) {
        super.onPostExecute(categories);
    }
}
