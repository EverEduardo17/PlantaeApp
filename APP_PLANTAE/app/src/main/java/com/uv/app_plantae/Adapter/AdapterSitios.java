package com.uv.app_plantae.Adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uv.app_plantae.AgregarSitio;
import com.uv.app_plantae.Barra_menu;
import com.uv.app_plantae.EditarSitio;
import com.uv.app_plantae.R;
import com.uv.app_plantae.Sitios;
import com.uv.app_plantae.pojo.SitiosC;
import com.uv.app_plantae.ui.dashboard.DashboardFragment;

import java.util.List;

public class AdapterSitios extends RecyclerView.Adapter<AdapterSitios.viewholdersitios>{

    List<SitiosC> sitiosCList;

    public AdapterSitios(List<SitiosC> sitiosCList){this.sitiosCList = sitiosCList;}

    @NonNull
    @Override
    public AdapterSitios.viewholdersitios onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sitios,parent, false);
        viewholdersitios holder = new viewholdersitios(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSitios.viewholdersitios holder, @SuppressLint("RecyclerView") int position) {
        SitiosC st = sitiosCList.get(position);
        if (st != null) {
            holder.tv_nombre.setText(st.getNombre());
            holder.tv_descripcion.setText(st.getDescripcion());
            holder.tv_plantas.setText(st.getPlantas());

            // Configurar OnClickListener para el botón
            holder.eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    eliminarSitio(position,holder);
                    Intent intent = new Intent(holder.itemView.getContext(), Barra_menu.class);
                    holder.itemView.getContext().startActivity(intent);
                }
            });

            holder.editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Pasar los datos del sitio a la actividad EditarSitio
                    Intent intent = new Intent(holder.itemView.getContext(), EditarSitio.class);
                    intent.putExtra("sitioId", st.getId());
                    intent.putExtra("nombre", st.getNombre());
                    intent.putExtra("descripcion", st.getDescripcion());
                    intent.putExtra("plantas", st.getPlantas());
                    holder.itemView.getContext().startActivity(intent);
                }
            });
        }
    }

    private void eliminarSitio(int position, AdapterSitios.viewholdersitios holder) {
        SitiosC st = sitiosCList.get(position);
        String id = st.getId(); // Obtener el ID del elemento a eliminar

        // Obtener la referencia al nodo padre del usuario
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            DatabaseReference sitioRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("Sitios").child(id);

            // Eliminar el elemento de Firebase
            sitioRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        // Eliminación exitosa
                        Toast.makeText(holder.itemView.getContext(), "Sitio eliminado exitosamente", Toast.LENGTH_SHORT).show();
                        sitiosCList.remove(position); // Eliminar el elemento de la lista
                        notifyItemRemoved(position); // Notificar al adaptador sobre el cambio en la posición
                        notifyItemRangeChanged(position, sitiosCList.size()); // Actualizar los índices de los elementos restantes
                    } else {
                        // Error al eliminar
                        Toast.makeText(holder.itemView.getContext(), "Error al eliminar el sitio", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }











    @Override
    public int getItemCount() {
        if(sitiosCList == null){
            return 0;
        }else {
            return sitiosCList.size();
        }
    }

    public class viewholdersitios extends RecyclerView.ViewHolder {

        TextView tv_nombre, tv_descripcion, tv_plantas;
        Button eliminar, editar;
        public viewholdersitios(@NonNull View itemView) {
            super(itemView);

            tv_nombre = itemView.findViewById(R.id.textView_nombre);
            tv_descripcion = itemView.findViewById(R.id.textView_descripcion);
            tv_plantas = itemView.findViewById(R.id.textView_plantas);
            eliminar = itemView.findViewById(R.id.btn_eliminar);
            editar = itemView.findViewById(R.id.btn_modificar);
        }
    }
}
