package com.thomasapp.recycleviewtest;

/**
 * Created by thomasdechaseaux on 11/09/2017.
 */

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class Tab2Contactes extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contacts, container, false);

        Button T = (Button) rootView.findViewById(R.id.buttonlogintobdd);
        T.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ContactsLogin.class);
                startActivity(i);
            }
        });

        Button TR = (Button) rootView.findViewById(R.id.buttonregistertobdd);
        TR.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ContactsRegister.class);
                startActivity(i);
            }
        });

        ImageButton IM = (ImageButton) rootView.findViewById(R.id.addcontactstobdd);
        IM.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AddContacts.class);
                startActivity(i);
            }
        });


        return rootView;
    }
}
