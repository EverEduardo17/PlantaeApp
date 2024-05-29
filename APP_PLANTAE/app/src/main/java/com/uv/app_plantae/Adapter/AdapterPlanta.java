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
import com.uv.app_plantae.DetallePlantas;
import com.uv.app_plantae.R;
import com.uv.app_plantae.pojo.Plantas;

import java.util.List;

public class AdapterPlanta extends RecyclerView.Adapter<AdapterPlanta.viewholderplantas> {

    List<Plantas> plantasList;

    //Constructor que nos sirve para llamar la información desde el mainActivity
    public AdapterPlanta(List<Plantas> plantasList) {
        this.plantasList = plantasList;
    }

    @NonNull
    @Override
    public viewholderplantas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_plantas,parent, false);
        viewholderplantas holder = new viewholderplantas(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholderplantas holder, int position) {
        Plantas pt = plantasList.get(position);

        holder.tv_cientifico.setText(pt.getCientifico());
        //holder.tv_img.setText(pt.getImg());
        holder.tv_nombre.setText(pt.getNombre());

        //Parea imagen
        Glide.with(holder.itemView.getContext())
                .load(pt.getImg())
                .into(holder.img);

        //PARA SELECCIONAR UN ITEM **************************
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), DetallePlantas.class);
                intent.putExtra("itemDetail", pt);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return plantasList.size();
    }

    public class viewholderplantas extends RecyclerView.ViewHolder {

        TextView tv_cientifico, tv_img, tv_nombre;
        ImageView img;
        public viewholderplantas(@NonNull View itemView) {
            super(itemView);

            tv_cientifico = itemView.findViewById(R.id.textView_cientifico);
            //tv_img = itemView.findViewById(R.id.textView_img);
            tv_nombre = itemView.findViewById(R.id.textView_nombre);
            img = itemView.findViewById(R.id.imageView_planta);

        }
    }
}
