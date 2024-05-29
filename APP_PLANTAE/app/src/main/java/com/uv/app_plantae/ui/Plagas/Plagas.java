package com.uv.app_plantae.ui.Plagas;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uv.app_plantae.Adapter.AdapterMisPlantas;
import com.uv.app_plantae.Adapter.AdapterPlagas;
import com.uv.app_plantae.R;
import com.uv.app_plantae.pojo.PlagasC;

import java.util.ArrayList;
import java.util.List;

public class Plagas extends Fragment {


        DatabaseReference ref;

        List<PlagasC> list;
        RecyclerView rv;
        SearchView searchView;
        AdapterPlagas adapter;
        LinearLayoutManager lm;

        private PlagasViewModel mViewModel;

        public static Plagas newInstance() {
            return new Plagas();
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {

            View root = inflater.inflate(R.layout.fragment_plagas, container, false);

            //PARA OCULTAR FLECHA DE RETROCESO
            if (getActivity() != null) {
                ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setDisplayHomeAsUpEnabled(false);
                }
            }

            rv = root.findViewById(R.id.rv);
            searchView = root.findViewById(R.id.searchView1);
            lm = new LinearLayoutManager(getContext());

            //Conectando el RecyclerView con el LinerLayout
            rv.setLayoutManager(lm);

            return root;

        }

        @Override
        public void onResume() {
            super.onResume();

            ref = FirebaseDatabase.getInstance().getReference().child("Plagas");

            list = new ArrayList<>();
            adapter = new AdapterPlagas(list);
            rv.setAdapter(adapter);

            //Completar valores de la base de datos
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear(); //borramos la lista actual
                    if (snapshot.exists()){
                        for (DataSnapshot snapshot1 : snapshot.getChildren()){
                            PlagasC pg = snapshot1.getValue(PlagasC.class);
                            list.add(pg);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                //Si el estado del buscador cambia, se activa
                @Override
                public boolean onQueryTextChange(String s) {
                    buscar(s);
                    return true;
                }
            });
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            mViewModel = new ViewModelProvider(this).get(PlagasViewModel.class);
        }

        private void buscar(String s){
            ArrayList<PlagasC>milista = new ArrayList<>();
            for (PlagasC obj : list){
                if (obj.getNombre() !=null && obj.getNombre().toLowerCase().contains(s.toLowerCase())){
                    milista.add(obj);

                }
            }
            AdapterPlagas adapter = new AdapterPlagas(milista);
            rv.setAdapter(adapter);
        }

}


