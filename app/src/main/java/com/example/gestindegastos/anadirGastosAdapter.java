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

public class anadirGastosAdapter extends RecyclerView.Adapter<anadirGastosAdapter.ViewHolder> {
    private List<Contacto> mData;
    private LayoutInflater mInflater;
    private Context context;


    public anadirGastosAdapter(List<Contacto> itemList, Context context) {
        this.mInflater=LayoutInflater.from(context);
        this.context=context;
        this.mData = itemList;
    }

    @Override
    public int getItemCount(){return mData.size();}

    @Override
    public anadirGastosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.anadir_gastos_element, null);
        return new anadirGastosAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final anadirGastosAdapter.ViewHolder holder, final int position){
        holder.binData(mData.get(position));
    }

    public void setItems(List<Contacto> items){mData=items;}

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombre;
        Button boton;

        ViewHolder(View itemView){
            super(itemView);
            nombre= itemView.findViewById(R.id.nombre);
            boton= itemView.findViewById(R.id.añadirContacto);
        }

        void binData(final Contacto item){
            nombre.setText(item.getNombre());
            boton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(anadirGastos.contactos.contains(item)){
                        anadirGastos.contactos.remove(item);
                        boton.setText("Añadir");

                    }else{
                        anadirGastos.contactos.add(item);
                        boton.setText("Eliminar");
                    }
                }
            });
        }

    }
}
