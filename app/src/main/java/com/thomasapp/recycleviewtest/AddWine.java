package com.thomasapp.recycleviewtest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.thomasapp.recycleviewtest.R.drawable.roundimage;
import static com.thomasapp.recycleviewtest.R.drawable.roundimagetransparent;

/**
 * Created by thomasdechaseaux on 02/10/2017.
 */

public class AddWine extends Activity {
    EditText ET_chateaux, ET_cuvée, ET_commentaire;
    TextView TV_recap;
    ImageButton IB_rouge, IB_blanc, IB_rose, IB_champagne, IB_spiritueux;
    String chateaux, commentaire, type, usermail;
    //String cuvée;
    Button BT_addwine;
    ImageView IM_flecheblanche;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_wine);

        ET_chateaux = (EditText)findViewById(R.id.chateauxtoadd);
        //ET_cuvée = (EditText) findViewById(R.id.cuvéetoadd);
        ET_commentaire = (EditText) findViewById(R.id.commentairetoadd);
        TV_recap = (TextView) findViewById(R.id.typevin);
        IB_rouge = (ImageButton) findViewById(R.id.imageButtonrouge);
        IB_blanc = (ImageButton) findViewById(R.id.imageButtonblanc);
        IB_rose = (ImageButton) findViewById(R.id.imageButtonrose);
        IB_champagne = (ImageButton) findViewById(R.id.imageButtonchamp);
        IB_spiritueux = (ImageButton) findViewById(R.id.imageButtonspirit);
        BT_addwine= (Button) findViewById(R.id.buttonaddwinetobdd);
        IM_flecheblanche = (ImageView) findViewById(R.id.imageflecheblanche);

        SharedPreferences sharedPreferencess = getSharedPreferences("MYPREF", MODE_PRIVATE);
        usermail = sharedPreferencess.getString("display","");
        Toast.makeText(this,usermail, Toast.LENGTH_SHORT).show();


        IM_flecheblanche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(AddWine.this, Tab1winesList.class);
                //startActivity(i);
                finish();
            }
        });

        IB_rouge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TV_recap.setText("rouge");
                //IB_rouge.setImageResource(R.drawable.roundimage);
                IB_rouge.setBackgroundResource(R.drawable.roundimage);
                IB_blanc.setBackgroundColor(0);
                IB_rose.setBackgroundColor(0);
                IB_champagne.setBackgroundColor(0);
                IB_spiritueux.setBackgroundColor(0);
                try  {
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                }
            }
        });
        IB_blanc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TV_recap.setText("blanc");
                IB_rouge.setBackgroundColor(0);
                IB_blanc.setBackgroundResource(R.drawable.roundimage);
                IB_rose.setBackgroundColor(0);
                IB_champagne.setBackgroundColor(0);
                IB_spiritueux.setBackgroundColor(0);
                try  {
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                }
            }
        });
        IB_rose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TV_recap.setText("rose");
                IB_rouge.setBackgroundColor(0);
                IB_blanc.setBackgroundColor(0);
                IB_rose.setBackgroundResource(R.drawable.roundimage);
                //IB_rose.setBackgroundColor(0x80E57373);
                IB_champagne.setBackgroundColor(0);
                IB_spiritueux.setBackgroundColor(0);
                try  {
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                }
            }
        });
        IB_champagne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TV_recap.setText("champagne");
                IB_rouge.setBackgroundColor(0);
                IB_blanc.setBackgroundColor(0);
                IB_rose.setBackgroundColor(0);
                IB_champagne.setBackgroundResource(R.drawable.roundimage);
                IB_spiritueux.setBackgroundColor(0);
                try  {
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                }
            }
        });
        IB_spiritueux.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TV_recap.setText("spiritueux");
                IB_rouge.setBackgroundColor(0);
                IB_blanc.setBackgroundColor(0);
                IB_rose.setBackgroundColor(0);
                IB_champagne.setBackgroundColor(0);
                IB_spiritueux.setBackgroundResource(R.drawable.roundimage);
                try  {
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                }
            }
        });

    }

    public void addwinetobdd (View view){
        chateaux = ET_chateaux.getText().toString();
        chateaux = chateaux.replace("'", "\\'");
        //cuvée = ET_cuvée.getText().toString();
        commentaire = ET_commentaire.getText().toString();
        commentaire = commentaire.replace("'", "\\'");
        type = TV_recap.getText().toString();

        Toast.makeText(this,usermail, Toast.LENGTH_SHORT).show();
        String method ="addwinetobdd";
        BackgroundTaskWinesList backgroundTask = new BackgroundTaskWinesList (this);
        backgroundTask.execute(method,chateaux,commentaire,type,usermail);
        finish();
        Intent i = new Intent(AddWine.this, MainActivity.class);
        startActivity(i);
    }
}
