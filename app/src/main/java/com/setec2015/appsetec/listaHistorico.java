package com.setec2015.appsetec;

/**
 * Created by Krets on 01/12/2015.
 */
public class listaHistorico {
    private int numHistorico;
    private int sensor1;
    private int sensor2;
    private int sensor3;
    private int sensor4;
    private int sensor5;
    private String historicoDate;

    public listaHistorico(int numHistorico, int sensor1, int sensor2, int sensor3, int sensor4, int sensor5, String historicoDate) {
        super();
        this.numHistorico = numHistorico;
        this.sensor1 = sensor1;
        this.sensor2 = sensor2;
        this.sensor3 = sensor3;
        this.sensor4 = sensor4;
        this.sensor5 = sensor5;
        this.historicoDate = historicoDate;
    }

    public int getNumHistorico() { return numHistorico; }
    public int getSensor1() {
        return sensor1;
    }
    public int getSensor2() {
        return sensor2;
    }
    public int getSensor3() {
        return sensor3;
    }
    public int getSensor4() {
        return sensor4;
    }
    public int getSensor5() {
        return sensor5;
    }
    public String getHistoricoDate() { return historicoDate ;}
}