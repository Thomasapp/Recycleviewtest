package com.thomasapp.recycleviewtest;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by thomasdechaseaux on 02/10/2017.
 */

public class AddWine extends Activity {
    EditText ET_chateaux, ET_cuvée, ET_commentaire;
    TextView TV_recap,USERMAIL;
    ImageButton IB_rouge, IB_blanc, IB_rose, IB_champagne, IB_spiritueux;
    String chateaux, cuvée, commentaire, type, usermail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_wine);

        ET_chateaux = (EditText)findViewById(R.id.chateauxtoadd);
        ET_cuvée = (EditText) findViewById(R.id.cuvéetoadd);
        ET_commentaire = (EditText) findViewById(R.id.commentairetoadd);
        TV_recap = (TextView) findViewById(R.id.typevin);
        IB_rouge = (ImageButton) findViewById(R.id.imageButtonrouge);
        IB_blanc = (ImageButton) findViewById(R.id.imageButtonblanc);
        IB_rose = (ImageButton) findViewById(R.id.imageButtonrose);
        IB_champagne = (ImageButton) findViewById(R.id.imageButtonchamp);
        IB_spiritueux = (ImageButton) findViewById(R.id.imageButtonspirit);

        SharedPreferences sharedPreferences = getSharedPreferences("MYPREF", MODE_PRIVATE);
        String display = sharedPreferences.getString("display","");
        usermail = display;
        Toast.makeText(this,display, Toast.LENGTH_SHORT).show();

        IB_rouge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TV_recap.setText("rouge");
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
        cuvée = ET_cuvée.getText().toString();
        commentaire = ET_commentaire.getText().toString();
        type = TV_recap.getText().toString();

        Toast.makeText(this,usermail, Toast.LENGTH_SHORT).show();
        String method ="addwinetobdd";
        BackgroundTaskWinesList backgroundTask = new BackgroundTaskWinesList (this);
        backgroundTask.execute(method,chateaux,cuvée,commentaire,type,usermail);
        finish();
    }
}
