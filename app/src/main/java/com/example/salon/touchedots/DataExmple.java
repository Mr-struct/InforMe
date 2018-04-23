package com.example.salon.touchedots;

import java.util.ArrayList;

public class DataExmple {
    private ArrayList<Dot> dotsExmple = new ArrayList<Dot>();
    public String webPage;
    public DataExmple(int size,String WebPage){
        this.webPage = WebPage;
        for(int i = 0; i < size; i ++){
            dotsExmple.add(new Dot(false));
        }

    }

    public boolean exmpleDotsGetStatut(int index){
        return dotsExmple.get(index).statut;
    }
    public void setDotsExmple(int index, boolean statut) {
        this.dotsExmple.add(index,new Dot(statut));
    }
}
