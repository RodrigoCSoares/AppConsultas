package com.example.rodrigosoares.appconsultas.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rodrigosoares.appconsultas.R;
import com.example.rodrigosoares.appconsultas.database.Consulta;
import com.example.rodrigosoares.appconsultas.database.Consultas;

import java.util.ArrayList;

public class ConsultasAdapter extends RecyclerView.Adapter<ConsultasAdapter.ViewHolder> {

    private ArrayList<Consulta> mDataset;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mNumeroConsulta;
        public TextView mData;
        public TextView mLocal;
        public TextView mObservacoes;


        public ViewHolder(View v) {
            super(v);
            mNumeroConsulta = v.findViewById(R.id.txt_numero_consultas);
            mData = v.findViewById(R.id.txt_data);
            mLocal = v.findViewById(R.id.txt_local);
            mObservacoes = v.findViewById(R.id.txt_observacoes);
        }
    }

    public ConsultasAdapter(ArrayList<Consulta> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public ConsultasAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View consulta = inflater.inflate(R.layout.consultas_item, parent, false);
        ViewHolder vh = new ViewHolder(consulta);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mNumeroConsulta.setText(Integer.toString(mDataset.get(position).getID()));
        holder.mData.setText(mDataset.get(position).getData_agenda());
        holder.mLocal.setText(mDataset.get(position).getLocal());
        holder.mObservacoes.setText(mDataset.get(position).getObservacao());

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
