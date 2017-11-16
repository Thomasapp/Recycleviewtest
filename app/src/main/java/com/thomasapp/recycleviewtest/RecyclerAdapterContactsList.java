package com.thomasapp.recycleviewtest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by thomasdechaseaux on 16/11/2017.
 */

public class RecyclerAdapterContactsList extends RecyclerView.Adapter<RecyclerAdapterContactsList.RecyclerViewHolder> {

    ArrayList<Contacts> arrayList = new ArrayList<>();
    public RecyclerAdapterContactsList (ArrayList<Contacts>arrayList)
    {
        this.arrayList = arrayList;
    }

    @Override
    public RecyclerAdapterContactsList.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_contacts_list,parent,false);
        RecyclerAdapterContactsList.RecyclerViewHolder recyclerViewHolder=new RecyclerAdapterContactsList.RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapterContactsList.RecyclerViewHolder holder, int position) {

        Contacts contacts =arrayList.get(position);

        holder.usermail.setText(contacts.getUsermail());

        if (holder.usermail.getText().toString() != ("")){
            holder.imagelogo.setImageResource(R.drawable.logo);}
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder
    {
        TextView usermail;
        ImageView imagelogo;

        public RecyclerViewHolder (View view)
        {
            super(view);
            usermail = (TextView) view.findViewById(R.id.usernamebackfrombdd);
            imagelogo  = (ImageView) view.findViewById(R.id.logosmall);
        }

    }
}
