package com.uv.app_plantae;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.uv.app_plantae.pojo.PlagasC;

public class DetallePlagas extends AppCompatActivity {

    private ImageView imgPlaga;
    private TextView nombre, linea, intro, sintomas, Msintomas, prevencion, Mprevencion, solucion, Msolucion;
    private PlagasC itemDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_plagas);

        initViews();
        initValues();
    }

    private void initViews(){
        imgPlaga = findViewById(R.id.imageView_plaga);
        nombre = findViewById(R.id.textView_nombrePlaga);
        linea = findViewById(R.id.textView_linea);
        intro = findViewById(R.id.textView_descripcionPlaga);
        sintomas = findViewById(R.id.textView_SintomasPlaga);
        Msintomas = findViewById(R.id.textView_SintomasPlagaM);
        solucion = findViewById(R.id.textView_solucion);
        Msolucion = findViewById(R.id.textView_solucionM);
        prevencion = findViewById(R.id.textView_prevencion);
        Mprevencion = findViewById(R.id.textView_prevencionM);
    }

    private void initValues(){
        itemDetail = (PlagasC) getIntent().getExtras().getSerializable("itemDetailPlaga");

        //Seteamos valores
        String url_img = itemDetail.getImg();
        Glide.with(this).load(url_img).into(imgPlaga);

        nombre.setText(itemDetail.getNombre());
        intro.setText(itemDetail.getIntroduccion());
        Msintomas.setText(itemDetail.getSintomas());
        Msolucion.setText(itemDetail.getSoluciones());
        Mprevencion.setText(itemDetail.getPrevencion());
    }
}