package com.danilovalerio.xilftenmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

public class MovieActivity extends AppCompatActivity {
    private TextView txtTitle;
    private TextView txtDesc;
    private TextView txtCast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        //Mock
        txtTitle = findViewById(R.id.text_view_title);
        txtDesc = findViewById(R.id.text_view_desc);
        txtCast = findViewById(R.id.text_view_cast);

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

        txtTitle.setText("Batman Begins");
        txtDesc.setText("O jovem Bruce Wayne cai em um poço e é atacado por morcegos. Bruce acorda de um pesadelo bal balb albalbalblalb allalb al blabl a");
        txtCast.setText(getString(R.string.cast, "Fulano 1"+", Fulano 2"+", Fulano 3"+", Fulano 4"));



    }

}
