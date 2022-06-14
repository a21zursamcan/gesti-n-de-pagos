package com.example.gestindegastos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.gestindegastos.login.login;

import java.util.List;

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.ViewHolder> {
    private List<Contacto> mData;
    private LayoutInflater mInflater;
    private Context context;


    public MainActivityAdapter(List<Contacto> itemList, Context context) {
        this.mInflater=LayoutInflater.from(context);
        this.context=context;
        this.mData = itemList;
    }

    @Override
    public int getItemCount(){return mData.size();}

    @Override
    public MainActivityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.main_activity_element, null);
        return new MainActivityAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MainActivityAdapter.ViewHolder holder, final int position){
        holder.binData(mData.get(position));
    }

    public void setItems(List<Contacto> items){mData=items;}

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombre;
        TextView deuda;
        Button boton;

        ViewHolder(View itemView){
            super(itemView);
            nombre= itemView.findViewById(R.id.nombre);
            deuda= itemView.findViewById(R.id.deuda);
            boton= itemView.findViewById(R.id.liquidar);
        }

        void binData(final Contacto item){
            nombre.setText(item.getNombre());
            deuda.setText(item.getDeuda()+"€");
            boton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    login.databaseReference.child("usuarios").child(login.mAuth.getUid()).child("listaContactos").child(item.ID).child("tedebe").setValue("0");
                    login.databaseReference.child("usuarios").child(login.mAuth.getUid()).child("listaContactos").child(item.ID).child("debes").setValue("0");
                    login.databaseReference.child("usuarios").child(item.ID).child("listaContactos").child(login.mAuth.getUid()).child("debes").setValue("0");
                    login.databaseReference.child("usuarios").child(item.ID).child("listaContactos").child(login.mAuth.getUid()).child("tedebe").setValue("0");
                }
            });
        }

    }
}
