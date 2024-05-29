package com.uv.app_plantae.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uv.app_plantae.R;
import com.uv.app_plantae.databinding.FragmentHomeBinding;

import java.net.URI;

public class HomeFragment extends Fragment {

    private Button btn_Salir;
    private Button btn_Perfil;

    private Button noticia;

    private Button btn_irVivero, btn_noticia;
    private ImageView img_fondo, img_noticia;

    private TextView tit_noticia, text_noticia;
    private TextView tit_noticia2, text_noticia2;
    private ImageView img_noticia2;
    private Button btn_noticia2;

    private TextView titulo_dato, texto_dato;

    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;



    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mDatabase = FirebaseDatabase.getInstance().getReference();


        //final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        //Se agrega el codigo
        mAuth = FirebaseAuth.getInstance();

        img_fondo = root.findViewById(R.id.imageView2);

        btn_irVivero = root.findViewById(R.id.irVivero);

        //Elementos para Dato del dia
        titulo_dato = root.findViewById(R.id.titulo_datoDia);
        texto_dato = root.findViewById(R.id.texto_datoDia);

        text_noticia = root.findViewById(R.id.titulo_noticia);
        tit_noticia = root.findViewById(R.id.titulo_noticia);
        img_noticia = root.findViewById(R.id.imagen_noticia);
        btn_noticia = root.findViewById(R.id.ir_noticia);

        text_noticia2 = root.findViewById(R.id.titulo_noticia2);
        tit_noticia2 = root.findViewById(R.id.titulo_noticia2);
        img_noticia2 = root.findViewById(R.id.imagen_noticia2);
        btn_noticia2 = root.findViewById(R.id.ir_noticia2);

        //MOSTRAR IMAGEN DEL HOME*************************
        DatabaseReference imgRef = mDatabase.child("Imagenes").child("Home").child("img");

        // Agregar un listener para obtener el valor de "img"
        imgRef.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String imageUrl = null;
                if (dataSnapshot.exists() && dataSnapshot.getValue() != null) {
                    imageUrl = dataSnapshot.getValue().toString();
                }
                if (imageUrl != null) {
                    Glide.with(getContext())
                            .load(imageUrl)
                            .into(img_fondo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        //****************************************************

        datoDelDia();

        irVivero();

        noticiaDelDia();

        irNoticia();






        /*
        //Metodo para el boton de Perfil
        btn_Perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Perfil.class));
            }
        });


*/

        //Esto no se borra
        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    //Metodo para mostrar el menuhome

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menuhome, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    private void datoDelDia(){

        mDatabase.child("Home").child("DatoDia").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String titulo = snapshot.child("Titulo").getValue().toString();
                    String texto = snapshot.child("Texto").getValue().toString();


                    titulo_dato.setText("Sabias que...");
                    texto_dato.setText(texto);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void irNoticia(){
        btn_noticia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.child("Home").child("NoticiaDia").child("1").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String link = snapshot.child("Link").getValue().toString();
                        Uri _link = Uri.parse(link);
                        Intent i = new Intent(Intent.ACTION_VIEW,_link);
                        startActivity(i);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        btn_noticia2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.child("Home").child("NoticiaDia").child("2").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String link2 = snapshot.child("Link").getValue().toString();
                        Uri _link = Uri.parse(link2);
                        Intent i = new Intent(Intent.ACTION_VIEW,_link);
                        startActivity(i);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    private void noticiaDelDia(){

        //NOTICIA 1
        mDatabase.child("Home").child("NoticiaDia").child("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String tituloN = snapshot.child("Titulo").getValue().toString();
                    String textoN = snapshot.child("Texto").getValue().toString();

                    tit_noticia.setText(tituloN);
                    text_noticia.setText(textoN);

                    String url_img = snapshot.child("img").getValue(String.class);
                    if (url_img != null) {
                        Glide.with(getContext()).load(url_img).into(img_noticia);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //NOTICIA 2
        mDatabase.child("Home").child("NoticiaDia").child("2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String tituloN2 = snapshot.child("Titulo").getValue().toString();
                    String textoN2 = snapshot.child("Texto").getValue().toString();

                    tit_noticia2.setText(tituloN2);
                    text_noticia2.setText(textoN2);

                    String url_img2 = snapshot.child("img").getValue(String.class);
                    if (url_img2 != null) {
                        Glide.with(getContext()).load(url_img2).into(img_noticia2);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void irVivero(){
        btn_irVivero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String direccion = "Viveros coatzacoalcos";
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + direccion);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
    }

}