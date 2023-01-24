package com.epicorp.epicash.placeholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.epicorp.epicash.R;
import com.epicorp.epicash.objects.Account;

import java.util.ArrayList;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.ViewHolder>{

    Context context;
    ArrayList<Account> data;

    public CardListAdapter(Context context, ArrayList<Account> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_card_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cardName.setText(data.get(position).getName());
        holder.cardNumber.setText("**** **** **** " + data.get(position).getCard_number().substring(12));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cardName;
        TextView cardNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cardName = itemView.findViewById(R.id.adapter_cardName);
            this.cardNumber = itemView.findViewById(R.id.adapter_cardNumber);
        }
    }
}
