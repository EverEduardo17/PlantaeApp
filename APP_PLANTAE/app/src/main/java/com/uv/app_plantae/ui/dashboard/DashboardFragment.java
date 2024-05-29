package com.uv.app_plantae.ui.dashboard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.uv.app_plantae.Adapter.AdapterMisPlantas;
import com.uv.app_plantae.BusquedaPlantas;
import com.uv.app_plantae.R;
import com.uv.app_plantae.Sensor;
import com.uv.app_plantae.Sitios;
import com.uv.app_plantae.databinding.FragmentDashboardBinding;
import com.uv.app_plantae.pojo.Plantas;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {


    private FloatingActionButton busqueda;
    private FloatingActionButton sensor;
    private FloatingActionButton sitios;


    DatabaseReference ref;
    ArrayList<Plantas> list;
    RecyclerView rv;
    //SearchView searchView;
    AdapterMisPlantas adapter;

    LinearLayoutManager lm;
    FirebaseAuth mAuth;


    //Declaramos los TextView
    private TextView nombre, Mnombre, nombreC, MnombreC;
    private TextView margarita;
    private Button btn_eliminar;
    //Creamos objeto de tipo DatabaseReference
    private DatabaseReference mDatabase;


    //PARA IMAGENES
    private ImageView img_sensor;


    //Creamos objeto de tipo firestore
    private FirebaseFirestore mfirestore;
    //Referenciamos el storeReference
    StorageReference storageReference;

    ImageButton imagen;


    private FragmentDashboardBinding binding;

    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        //Se agrego
        View root = inflater.inflate(R.layout.fragment_dashboard, container,false);

        /*
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        */

        //PARA MOSTRAR EL RECYCLERVIEW
        mAuth = FirebaseAuth.getInstance();
        String id = mAuth.getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance().getReference().child("Users").child(id).child("MisPlantas");
        rv = root.findViewById(R.id.rv);
        //searchView = root.findViewById(R.id.searchView1);
        lm = new LinearLayoutManager(getContext());

        //Conectando el RecyclerView con el LinearLayoutManager
        rv.setLayoutManager(lm);

        list = new ArrayList<>();
        adapter = new AdapterMisPlantas(list);

        rv.setAdapter(adapter);

        /*
        //Iniciamos los textView
        nombre = (TextView) root.findViewById(R.id.nombre);
        Mnombre = (TextView) root.findViewById(R.id.Mnombre);
        nombreC = (TextView) root.findViewById(R.id.nombreC);
        MnombreC = (TextView) root.findViewById(R.id.MnombreC);
         */

        busqueda = (FloatingActionButton) root.findViewById(R.id.btn_irBusqueda);
        sensor = (FloatingActionButton) root.findViewById(R.id.btn_irSensor);
        img_sensor = (ImageView) root.findViewById(R.id.image_sensor);
        sitios = (FloatingActionButton) root.findViewById(R.id.btn_irSitios);



        //margarita = root.findViewById(R.id.Mmargarita);


        //PARA IMAGENES
        //Inicializamos el objeto de tipo firestore
        mfirestore = FirebaseFirestore.getInstance();
        //Referenciamos la carpeta donde se encuentran las imagenes
        //storageReference = FirebaseStorage.getInstance().getReference().child(storage_path);
        //imagen = root.findViewById(R.id.img_margarita);


        //Instanciamos el objeto de DatabaseReference
        mDatabase = FirebaseDatabase.getInstance().getReference();  //Hace referencia al nodo principal de la base de datos

        //Girasol
        //mostrarGirasol();
        /*
        imagen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Margarita.class));
            }
        });
        */


        //Ir a la pagina de busqueda
        busqueda.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), BusquedaPlantas.class));
            }
        });

        sensor.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Sensor.class));
            }
        });

        sitios.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Sitios.class));
            }
        });



        //PARA ELIMINAR DE MIS PLANTAS
        btn_eliminar = root.findViewById(R.id.btn_eliminar);



        //Completar la lista con los valores de la base de datos (MisPlantas)
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot snapshot1 : snapshot.getChildren()){
                        Plantas mpt = snapshot1.getValue(Plantas.class);
                        list.add(mpt);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return root;
    }

/*
    public void mostrarGirasol(){
        //Hacemos referencia el nodo que queramos acceder (Plantas)
        mDatabase.child("Plantas").child("0").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //validacion para ver si el objeto al que se hace referencia existe
                if (snapshot.exists()) {
                    //Retornar el nombre
                    String nombre = snapshot.child("nombre").getValue().toString();
                    Mnombre.setText(nombre);
                    //Retornar el nombre cientifico
                    String Ncientifico = snapshot.child("cientifico").getValue().toString();
                    MnombreC.setText(Ncientifico);
                    //Retornar la imagen
                    String url = snapshot.child("img").getValue().toString();
                    Glide.with(getContext()).load(url).into(imagen);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}