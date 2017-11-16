package com.thomasapp.recycleviewtest;

import android.widget.ImageView;

/**
 * Created by thomasdechaseaux on 09/10/2017.
 */

public class Wines {

    private String chateaux;
    private String cuvée;
    private String commentaire;
    public String type;


    public Wines (String chateaux, String cuvée, String commentaire, String type)
    {
        this.setChateaux(chateaux);
        this.setCuvée(cuvée);
        this.setCommentaire(commentaire);
        this.setType(type);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getChateaux() {
        return chateaux;
    }

    public void setChateaux(String chateaux) {
        this.chateaux = chateaux;
    }

    public String getCuvée() {
        return cuvée;
    }

    public void setCuvée(String cuvée) {
        this.cuvée = cuvée;
    }

}
