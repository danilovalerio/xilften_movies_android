package com.danilovalerio.xilftenmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

public class MovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

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


    }

}
