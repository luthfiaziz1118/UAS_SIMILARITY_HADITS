package com.mluthfiaziz.haditssimilarityapps.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Hadits")
public class Hadits extends Model {

    @Column(name = "Decription")
    public String description;

    @Column(name = "HaditsSatu")
    public String haditsSatu;

    @Column(name = "HaditsDua")
    public String haditsDua;

    public Hadits(){
    }

    public Hadits(String description, String haditsSatu, String haditsDua) {
        this.description = description;
        this.haditsSatu = haditsSatu;
        this.haditsDua = haditsDua;
    }
}
