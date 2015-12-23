package com.setec2015.appsetec;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krets on 23/12/2015.
 */
public class ListAlertasAdapter extends ArrayAdapter {
    List list = new ArrayList();

    public ListAlertasAdapter(Context context, int resource) {
        super(context, resource);
    }

    static class ListAlertasHolder
    {
        TextView item_id, item_type, item_alert, item_value, item_data;
    }

    public void add(ListAlertas object) {
        list.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ListAlertasHolder listAlertasHolder;

        if(row == null) // if row is not available it needs to be created
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.alerta_list_item, parent, false);

            listAlertasHolder = new ListAlertasHolder();
            listAlertasHolder.item_id = (TextView) row.findViewById(R.id.item_id);
            listAlertasHolder.item_type = (TextView) row.findViewById(R.id.item_type);
            listAlertasHolder.item_alert = (TextView) row.findViewById(R.id.item_alert);
            listAlertasHolder.item_value = (TextView) row.findViewById(R.id.item_value);
            listAlertasHolder.item_data = (TextView) row.findViewById(R.id.item_data);
            row.setTag(listAlertasHolder);
        }
        else
        {
            listAlertasHolder = (ListAlertasHolder) row.getTag();
        }

        ListAlertas listAlertas = (ListAlertas) getItem(position);
        listAlertasHolder.item_id.setText(listAlertas.getId() + ".");
        listAlertasHolder.item_type.setText(listAlertas.getType());
        listAlertasHolder.item_alert.setText(listAlertas.getAlert());
        listAlertasHolder.item_value.setText(listAlertas.getValue());
        listAlertasHolder.item_data.setText(listAlertas.getData());

        return row;
    }
}
