package com.setec2015.appsetec;

/**
 * Created by Krets on 22/11/2015.
 */
public class    listaSensoresZona1 {
    private String sensor;
    private String valueType;
    private int iconID;
    private int value;

    public listaSensoresZona1(String sensor, String valueType, int iconID, int value) {
        super();
        this.sensor = sensor;
        this.valueType = valueType;
        this.iconID = iconID;
        this.value = value;
    }

    public String getSensor() {
        return sensor;
    }
    public String getValueType() {
        return valueType;
    }
    public int getIconID() {
        return iconID;
    }
    public int getValue() {
        return value;
    }
}
