package com.setec2015.appsetec;

/**
 * Created by Krets on 23/11/2015.
 */
public class listaAlertas {
    private int numAlert;
    private String sensor;
    private String alertType;
    private int iconID;
    private int value;
    private String alertDate;

    public listaAlertas(int numAlert, String sensor, String alertType, int iconID, int value, String alertDate) {
        super();
        this.numAlert = numAlert;
        this.sensor = sensor;
        this.alertType = alertType;
        this.iconID = iconID;
        this.value = value;
        this.alertDate = alertDate;
    }

    public int getNumAlert() { return  numAlert; }
    public String getSensor() {
        return sensor;
    }
    public String getAlertType() {
        return alertType;
    }
    public int getIconID() {
        return iconID;
    }
    public int getValue() {
        return value;
    }
    public String getAlertDate() { return alertDate ;}
}
