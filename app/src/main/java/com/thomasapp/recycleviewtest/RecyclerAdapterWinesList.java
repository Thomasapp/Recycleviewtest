package com.thomasapp.recycleviewtest;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by thomasdechaseaux on 25/09/2017.
 */

public class RecyclerAdapterWinesList extends RecyclerView.Adapter<RecyclerAdapterWinesList.RecyclerViewHolder> {

    ArrayList<Wines> arrayList = new ArrayList<>();
    public RecyclerAdapterWinesList (ArrayList<Wines>arrayList)
    {
        this.arrayList = arrayList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_wines_list,parent,false);
        RecyclerViewHolder recyclerViewHolder=new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        Wines wines =arrayList.get(position);

        holder.Chateaux.setText(wines.getChateaux());
        holder.Cuvée.setText(wines.getCuvée());
        holder.Commentaire.setText(wines.getCommentaire());
        holder.Type.setText(wines.getType());

        if (holder.Type.getText().toString().equals("rouge")){
            holder.Typeimage.setImageResource(R.drawable.rouge);}
        else if(holder.Type.getText().toString().equals("blanc")){
            holder.Typeimage.setImageResource(R.drawable.blanc);}
        else if(holder.Type.getText().toString().equals("rose")){
            holder.Typeimage.setImageResource(R.drawable.rose);}
        else if(holder.Type.getText().toString().equals("champagne")){
            holder.Typeimage.setImageResource(R.drawable.champagne);}
        else {holder.Typeimage.setImageResource(R.drawable.spiritueux);}

    }

    @Override
    public int getItemCount() {
       return arrayList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder
    {
        TextView Chateaux,Cuvée,Commentaire,Type;
        ImageView Typeimage;

        public RecyclerViewHolder (View view)
        {
            super(view);
            Chateaux = (TextView) view.findViewById(R.id.chateaux);
            Cuvée = (TextView) view.findViewById(R.id.cuvée);
            Commentaire = (TextView) view.findViewById(R.id.commentaire);
            Type = (TextView) view.findViewById(R.id.tvtousetype);
            Typeimage  = (ImageView) view.findViewById(R.id.imagetype);
        }

    }

}
