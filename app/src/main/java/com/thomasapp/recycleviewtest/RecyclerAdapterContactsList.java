package com.thomasapp.recycleviewtest;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by thomasdechaseaux on 16/11/2017.
 */

public class RecyclerAdapterContactsList extends RecyclerView.Adapter<RecyclerAdapterContactsList.RecyclerViewHolder> {

    private ArrayList<Contacts> arrayList = new ArrayList<>();
    private String usersfromrecycler;
    private Context context;

    public RecyclerAdapterContactsList (ArrayList<Contacts>arrayList)
    {
        this.arrayList = arrayList;
    }

    @Override
    public RecyclerAdapterContactsList.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_contacts_list,parent,false);
        context = parent.getContext();
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapterContactsList.RecyclerViewHolder holder, int position) {

        Contacts contacts =arrayList.get(position);
        holder.usermail.setText(contacts.getUsermail());

        if (holder.usermail.getText().toString() != ("")) {
            holder.imagelogo.setImageResource(R.drawable.logoo);
            /*usersfromrecycler = holder.usermail.getText().toString();
            SharedPreferences sharedpreferences = context.getSharedPreferences("MYPREFFF", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("displayyy", usersfromrecycler);
            editor.apply();*/
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder
    {
        TextView usermail;
        ImageView imagelogo;

        public RecyclerViewHolder (View view)
        {
            super(view);
            usermail = (TextView) view.findViewById(R.id.mailbackfrombdd);
            imagelogo  = (ImageView) view.findViewById(R.id.logosmall);


        }

    }
}
