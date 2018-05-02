package com.example.dana.applic;

import android.graphics.Point;
import static java.lang.Math.abs;

/*
Cette classe valide les formes scanné et met à jours la forme courrante
 */
public class Form {

    private Point p1, p2, p3, p4;
    private String formType = "";

    /**
     *
     * @param p1 Premier point scanne
     * @param p2 second point scanne
     * @param p3 troisieme point scanne
     * @param p4 quatrieme point scanne
     */
    public Form(Point p1, Point p2, Point p3, Point p4) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;

    }


    /**
     *
     * @return true si la forme est verticale avec deux points est detecte sinon retourn faux
     * met a jour le type de la forme
     */
    public boolean isVerticalLines2Points() {
        if ( abs(p1.x - p2.x ) < 70) {
            formType = "vertical_2";
            return true;
        }
        return false;
    }


    /**
     *
     * @return true si la forme est horizantale avec deux points detecte sinon retourn faux
     * met a jour le type de la forme
     */
    public boolean isHorizantalLines2points() {

        if ( abs(p1.y - p2.y ) < 70) {
            formType = "horizantal_2";
            return true;
        }
        return false;
    }


    /**
     *
     * @return true si la forme est verticale avec trois points detecte sinon retourn faux
     * met à jour le type de la forme
     */
    public boolean isVerticalLines3Points() {
        if (abs(p1.x - p2.x) < 70 && abs(p2.x - p3.x) < 70 && abs(p1.x - p3.x) < 70 && p1.y != p2.y && p2.y != p3.y && p1.y != p3.y) {
            formType = "vertical_3";
            return true;
        }
        return false;
    }


    /**
     *
     * @return true si la forme est une ligne horizantale avec deux trois detecte sinon retourn faux
     * met a jour le type de la forme
     */
    public boolean isHorizantalLines3points() {

        if (abs(p1.y - p2.y) < 70 && abs(p2.y - p3.y) < 70 && abs(p1.y - p3.y) < 70 && p1.x != p2.x && p2.x != p3.x && p1.x != p3.x) {
            formType = "horizantal_3";
            return true;
        }
        return false;
    }


    /**
     *
     * Methode interne pour le calcule des forme complexe rectangle et carre
     * @param p1 Premier point scanne
     * @param p2 second point scanne
     * @return
     */
    private boolean isVerticalLines2points(Point p1, Point p2) {
        if (abs(p1.x - p2.x ) < 70 && p1.y != p2.y ) {

            return true;
        }
        return false;
    }

    /**
     *
     * Methode interne pour le calcule des forme complexe rectangle et carre
     * @param p1 Premier point scanne
     * @param p2 second point scanne
     * @return
     */
    private boolean isHorizantalLines2Points(Point p1, Point p2) {

        if (abs(p1.y - p2.y ) < 70 && p1.x != p2.x) {
            return true;
        }
        return false;
    }


    /**
     *
     * @return true si la forme est un rectangle avec quatre points detecte sinon retourn faux
     * met a jour le type de la forme
     */
    public boolean isRectangle() {

        if ((isHorizantalLines2Points(p1, p2) && isHorizantalLines2Points(p3, p4) && isVerticalLines2points(p1, p3) && isVerticalLines2points(p2, p4) ) ||
                (isHorizantalLines2Points(p1, p2) && isHorizantalLines2Points(p3, p4) && isVerticalLines2points(p1, p4) && isVerticalLines2points(p2, p3) ) ||
                (isHorizantalLines2Points(p1, p3) && isHorizantalLines2Points(p2, p4) && isVerticalLines2points(p1, p2) && isVerticalLines2points(p3, p4) ) ||
                (isHorizantalLines2Points(p1, p4) && isHorizantalLines2Points(p2, p3) && isVerticalLines2points(p1, p2) && isVerticalLines2points(p4, p3) ) ||
                (isHorizantalLines2Points(p1, p3) && isHorizantalLines2Points(p2, p4) && isVerticalLines2points(p1, p4) && isVerticalLines2points(p3, p2) ) ||
                (isHorizantalLines2Points(p1, p4) && isHorizantalLines2Points(p3, p2) && isVerticalLines2points(p1, p3) && isVerticalLines2points(p4, p2) ) )  {
            formType = "rectangle";
            return true;
        }
        return false;
    }


    /**
     *
     * @return true si la forme est un  carre avec deux points detecte sinon retourn faux
     * met a jour le type de la forme
     */
    public  boolean isSquare (){
        if(isRectangle() && dist()) {
            formType = "square";
            return true;
        }
        return false;
    }


    /**
     *
     * @return true si la forme est verticale avec deux points détécté sinon retourn faux
     * met a jour le type de la forme
     */
    private boolean dist() {
        double dist1 = Math.sqrt((p2.y - p1.y)* (p2.y - p1.y) + (p2.x - p1.x)* (p2.x - p1.x));
        double dist2 = Math.sqrt((p3.y - p4.y)* (p3.y - p4.y) + (p3.x - p4.x)* (p3.x - p4.x));
        double dist3 = Math.sqrt((p1.y - p4.y)* (p1.y - p4.y) + (p1.x - p4.x)* (p1.x - p4.x));

        return (dist1 == dist2 && dist2 == dist3 && dist3 == dist1);
    }


    /**
     *
     * @return le type de la forme
     */
    public String getFormType() {
        return formType;
    }
}
