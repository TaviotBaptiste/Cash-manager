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

public class PanierApdater extends RecyclerView.Adapter<PanierApdater.ViewHolder> {

    Context context;
    ArrayList<Product> products;

    public PanierApdater(Context context, ArrayList<Product> data){
        this.context = context;
        this.products = data;
    }

    @NonNull
    @Override
    public PanierApdater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PanierApdater.ViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_item,null));
    }

    @Override
    public void onBindViewHolder(@NonNull PanierApdater.ViewHolder holder, int position) {
        Glide.with(context)
                .asBitmap()
                .load(products.get(position).getImage())
                .into(holder.imageView);
        holder.name.setText(products.get(position).getLibelle());
        holder.qtt.setText(products.get(position).getLibelle());
        holder.price.setText(products.get(position).getPrix() +  " EUR");
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name;
        TextView qtt;
        TextView price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.imageView4);
            this.name = itemView.findViewById(R.id.textView4);
            this.qtt = itemView.findViewById(R.id.textView8);
            this.price = itemView.findViewById(R.id.textView9);
        }
    }
}
