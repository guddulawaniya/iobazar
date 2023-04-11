package com.example.statozone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class qunttityAdapter extends RecyclerView.Adapter<qunttityAdapter.viewholder> {
    ArrayList<itemqountityModule> list;
    Context context;

    public qunttityAdapter(ArrayList<itemqountityModule> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.quntity_cart,parent,false);

        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        itemqountityModule module = list.get(position);
        holder.price.setText(module.getItemprice());
        holder.gst.setText(module.getItemgst());
        holder.mrp.setText(module.getItemmrp());
        holder.color.setText(module.getColor());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewholder extends RecyclerView.ViewHolder {

        TextView price,gst,mrp,color,plusitme,minusitem,totalitem;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.itemprice);
            gst = itemView.findViewById(R.id.itemgst);
            mrp = itemView.findViewById(R.id.itemmrp);
            color = itemView.findViewById(R.id.color);
            plusitme = itemView.findViewById(R.id.item_incre);
            minusitem = itemView.findViewById(R.id.item_descri);
            totalitem = itemView.findViewById(R.id.totalitem);



        }
    }
}
