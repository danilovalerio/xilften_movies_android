package com.danilovalerio.xilftenmovies.util;

import android.app.ProgressDialog;
import android.content.Context;
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
        return null;
    }

    //main-thread (ocultar uma progressbar)
    @Override
    protected void onPostExecute(List<Category> categories) {
        super.onPostExecute(categories);
    }
}
