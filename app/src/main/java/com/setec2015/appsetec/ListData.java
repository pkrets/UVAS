package com.setec2015.appsetec;

/**
 * Created by Krets on 12/12/2015.
 */
public class ListData {

    private String id, temp, lum, humSolo, humAr, pluv, data;

    // Constructor
    public ListData(String id, String temp, String lum, String humSolo, String humAr, String pluv, String data)
    {
        this.setId(id);
        this.setTemp(temp);
        this.setLum(lum);
        this.setHumSolo(humSolo);
        this.setHumAr(humAr);
        this.setPluv(pluv);
        this.setData(data);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getLum() {
        return lum;
    }

    public void setLum(String lum) {
        this.lum = lum;
    }

    public String getHumSolo() {
        return humSolo;
    }

    public void setHumSolo(String humSolo) {
        this.humSolo = humSolo;
    }

    public String getHumAr() {
        return humAr;
    }

    public void setHumAr(String humAr) {
        this.humAr = humAr;
    }

    public String getPluv() {
        return pluv;
    }

    public void setPluv(String pluv) {
        this.pluv = pluv;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
