package com.uv.app_plantae.ui.Perfil;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uv.app_plantae.EditarPerfil;
import com.uv.app_plantae.MainActivity;
import com.uv.app_plantae.R;

public class Perfil extends Fragment {

    private VerPerfilViewModel mViewModel;

    private TextView mNombre, mCorreo, mPhone, mUser;
    private Button btn_Salir, btn_editarPerfil, btn_borrar;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;





    public static Perfil newInstance() {
        return new Perfil();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ver_perfil, container, false);

        //PARA OCULTAR FLECHA DE RETROCESO
        if (getActivity() != null) {
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(false);
            }
        }

        btn_Salir = view.findViewById(R.id.btn_CerrarSesion);
        btn_editarPerfil = view.findViewById(R.id.btn_editarPerfil);
        btn_borrar = view.findViewById(R.id.btn_eliminarPerfil);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Obtiene el id del usuario actual
        String id = mAuth.getCurrentUser().getUid();

        mNombre = (TextView) view.findViewById(R.id.Mostrar_nombre);
        mCorreo = (TextView) view.findViewById(R.id.Mostrar_correo);
        mPhone = (TextView) view.findViewById(R.id.Mostrar_telefono);
        mUser = (TextView) view.findViewById(R.id.Mostrar_usuario);

        mostrarUsuario();


        btn_Salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(getContext());
                alerta.setMessage("¿Seguro que desea cerrar sesión?")
                        .setCancelable(false)
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                try {
                                    cerrarSesion();
                                } catch (Throwable e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                alerta.show();
            }
        });



        btn_borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alerta = new AlertDialog.Builder(getContext());
                alerta.setMessage("¿Realmente desea eliminar la cuenta?")
                        .setCancelable(false)
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                eliminarPerfil();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                alerta.show();
            }
        });

        btn_editarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editarPerfil();
            }
        });


        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VerPerfilViewModel.class);
        // TODO: Use the ViewModel
    }

    private void mostrarUsuario(){

        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String name = snapshot.child("name").getValue().toString();
                    String correo = snapshot.child("email").getValue().toString();
                    String phone = snapshot.child("phone").getValue().toString();
                    String user = snapshot.child("user").getValue().toString();

                    mNombre.setText(name);
                    mCorreo.setText(correo);
                    mPhone.setText(phone);
                    mUser.setText(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void cerrarSesion() throws Throwable {
        mAuth.signOut();
        startActivity(new Intent(getContext(), MainActivity.class));
        getActivity().finish();
    }

    private void editarPerfil(){
        Intent i = new Intent(getContext(), EditarPerfil.class);
        startActivity(i);
    }

    private void eliminarPerfil(){

        //obtiene la instancia actual del usuario autenticado en Firebase Authentication.
        FirebaseUser user = mAuth.getCurrentUser();

        //Elimina el registro del usuario actual de Athentication
        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getContext(), "Perfil eliminado con éxito", Toast.LENGTH_SHORT).show();
            }
        });

        String id = mAuth.getCurrentUser().getUid();
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Users");

        mDatabase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String id_Padre = snapshot.getKey();
                    // Obtener referencia al padre
                    DatabaseReference padreRef = snapshot.getRef().getParent();

                    usersRef.child(id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            Toast.makeText(getContext(), "Perfl Eliminado", Toast.LENGTH_SHORT).show();
                            mAuth.signOut();
                            startActivity(new Intent(getContext(), MainActivity.class));
                            try {
                                getActivity().finish();
                            } catch (Throwable e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}