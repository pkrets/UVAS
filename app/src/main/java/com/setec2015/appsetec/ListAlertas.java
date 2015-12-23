package com.setec2015.appsetec;

/**
 * Created by Krets on 23/12/2015.
 */
public class ListAlertas {

    private String id, type, alert, value, data;

    // Constructor
    public ListAlertas(String id, String type, String alert, String value, String data)
    {
        this.setId(id);
        this.setType(type);
        this.setAlert(alert);
        this.setValue(value);
        this.setData(data);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
