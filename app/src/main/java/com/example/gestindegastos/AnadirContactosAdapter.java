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

public class AnadirContactosAdapter extends RecyclerView.Adapter<AnadirContactosAdapter.ViewHolder> {
    private List<Contacto> mData;
    private LayoutInflater mInflater;
    private Context context;


    public AnadirContactosAdapter(List<Contacto> itemList, Context context) {
        this.mInflater=LayoutInflater.from(context);
        this.context=context;
        this.mData = itemList;
    }

    @Override
    public int getItemCount(){return mData.size();}

    @Override
    public AnadirContactosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.buscador_usuario_element, null);
        return new AnadirContactosAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AnadirContactosAdapter.ViewHolder holder, final int position){
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
                    login.agregarUsuarioListaContactos(item.getNombre(),item.getID());
                    boton.setText("Agregado");
                }
            });
        }

    }
}
