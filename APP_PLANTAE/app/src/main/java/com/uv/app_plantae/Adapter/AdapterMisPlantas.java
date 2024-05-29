package com.uv.app_plantae.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.model.Document;
import com.uv.app_plantae.DetalleMisPlantas;
import com.uv.app_plantae.DetallePlantas;
import com.uv.app_plantae.R;
import com.uv.app_plantae.pojo.Plantas;
import com.uv.app_plantae.ui.dashboard.DashboardFragment;

import java.util.List;

public class AdapterMisPlantas extends RecyclerView.Adapter<AdapterMisPlantas.viewholderMisPlantas> {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    //Creamos objeto para base en realTime
    DatabaseReference mDatabase;

    //Optiene el id del usuario actual
    String id = mAuth.getCurrentUser().getUid();

    List<Plantas> misplantasList;

    //Para eliniar de misplantas
   


    //Constructor que nos sirve para llamar la información desde el mainActivity
    public AdapterMisPlantas(List<Plantas> misplantasList){
        this.misplantasList = misplantasList;
    }


    @NonNull
    @Override
    public viewholderMisPlantas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_misplantas,parent,false);
        viewholderMisPlantas holder = new viewholderMisPlantas(v);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMisPlantas.viewholderMisPlantas holder, int position) {
        Plantas mpt = misplantasList.get(position);
        String idMiPlanta = mpt.getId();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        String idUser= mAuth.getCurrentUser().getUid();

        holder.tv_cientifico.setText(mpt.getCientifico());
        holder.tv_nombre.setText(mpt.getNombre());




        //Para imagen
        Glide.with(holder.itemView.getContext())
                .load(mpt.getImg())
                .into(holder.img);

        //PARA SELECCIONAR UN ITEM ****************************
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), DetalleMisPlantas.class);
                intent.putExtra("itemDetail", mpt);
                holder.itemView.getContext().startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return misplantasList.size();
    }

    public class viewholderMisPlantas extends RecyclerView.ViewHolder {
        TextView tv_cientifico, tv_img, tv_nombre;
        ImageView img;
        Button eliminar;

        public viewholderMisPlantas(@NonNull View itemView) {
            super(itemView);

            tv_cientifico = itemView.findViewById(R.id.textView_cientifico);
            tv_nombre = itemView.findViewById(R.id.textView_nombre);
            img = itemView.findViewById(R.id.imageView_planta);
            eliminar = itemView.findViewById(R.id.btn_eliminar);

        }
    }

    //para eliminar misplantas
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        OnItemClickListener mListener = listener;
    }

}
