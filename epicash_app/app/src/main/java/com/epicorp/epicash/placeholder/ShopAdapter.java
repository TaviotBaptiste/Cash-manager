package com.epicorp.epicash.placeholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.epicorp.epicash.R;
import com.epicorp.epicash.objects.Product;

import java.util.ArrayList;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder>{

    Context context;
    ArrayList<Product> data;


    public ShopAdapter(Context context, ArrayList<Product> data){
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_shop,null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context)
                .asBitmap()
                .load(data.get(position).getImage())
                .into(holder.shop_image);
        System.out.println("ICI  " + context + "  " + data + "   " + position);
        System.out.println("LALALA  " + data.get(position).getId());
        holder.shop_libelle.setText(data.get(position).getLibelle());
        holder.shop_prix.setText(data.get(position).getPrix());
        holder.shop_desc.setText(data.get(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView shop_image;
        TextView shop_libelle;
        TextView shop_prix;
        TextView shop_desc;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.shop_image = itemView.findViewById(R.id.shop_image);
            this.shop_libelle = itemView.findViewById(R.id.shop_libelle);
            this.shop_prix = itemView.findViewById(R.id.shop_prix);
            this.shop_desc = itemView.findViewById(R.id.shop_desc);
        }
    }
}
