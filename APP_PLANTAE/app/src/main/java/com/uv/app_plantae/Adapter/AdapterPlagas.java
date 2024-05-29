package com.uv.app_plantae.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.uv.app_plantae.DetallePlagas;
import com.uv.app_plantae.R;
import com.uv.app_plantae.pojo.PlagasC;

import java.util.List;

public class AdapterPlagas extends RecyclerView.Adapter<AdapterPlagas.viewholderplagas> {

    List<PlagasC> plagasCList;


    public AdapterPlagas(List<PlagasC> plagasCList){
        this.plagasCList = plagasCList;
    }


    @NonNull
    @Override
    public AdapterPlagas.viewholderplagas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_plagas,parent,false);
        viewholderplagas holder = new viewholderplagas(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPlagas.viewholderplagas holder, int position) {
        PlagasC pg = plagasCList.get(position);

        holder.tv_nombre.setText(pg.getNombre());
        holder.tv_descripcion.setText(pg.getIntroduccion());

        //Para imagen
        Glide.with(holder.itemView.getContext())
                .load(pg.getImg())
                .into(holder.img);

        //PARA SELECCIONAR UN ITEM ******************
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), DetallePlagas.class);
                intent.putExtra("itemDetailPlaga", pg);
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (plagasCList == null) {
            return 0;
        } else {
            return plagasCList.size();
        }
    }

    public class viewholderplagas extends RecyclerView.ViewHolder {

        TextView tv_nombre, tv_descripcion;
        ImageView img;
        public viewholderplagas(@NonNull View itemView) {
            super(itemView);

            tv_nombre = itemView.findViewById(R.id.textView_nombre);
            tv_descripcion = itemView.findViewById(R.id.textView_descripcion);
            img = itemView.findViewById(R.id.imageView_plaga);
        }
    }
}
