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
 * Created by Krets on 12/12/2015.
 */
public class ListDataAdapter extends ArrayAdapter {

    List list = new ArrayList();

    public ListDataAdapter(Context context, int resource) {
        super(context, resource);
    }

    static class ListDataHolder
    {
        TextView item_id, item_temp, item_lum, item_humSolo, item_humAr, item_pluv, item_data;
    }

        public void add(ListData object) {
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
            ListDataHolder listDataHolder;

            if(row == null) // if row is not available it needs to be created
            {
                LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = layoutInflater.inflate(R.layout.historico_list_item, parent, false);

                listDataHolder = new ListDataHolder();
                    listDataHolder.item_id = (TextView) row.findViewById(R.id.item_id);
                    listDataHolder.item_temp = (TextView) row.findViewById(R.id.item_temp);
                    listDataHolder.item_lum = (TextView) row.findViewById(R.id.item_lum);
                    listDataHolder.item_humSolo = (TextView) row.findViewById(R.id.item_humSolo);
                    listDataHolder.item_humAr = (TextView) row.findViewById(R.id.item_humAr);
                    listDataHolder.item_pluv = (TextView) row.findViewById(R.id.item_pluv);
                    listDataHolder.item_data = (TextView) row.findViewById(R.id.item_data);
                row.setTag(listDataHolder);
            }
            else
            {
                listDataHolder = (ListDataHolder) row.getTag();
            }

            ListData listData = (ListData) getItem(position);
                listDataHolder.item_id.setText(listData.getId() + ".");
                listDataHolder.item_temp.setText(listData.getTemp() + " ºC");
                listDataHolder.item_lum.setText(listData.getLum() + " lux");
                listDataHolder.item_humSolo.setText(listData.getHumSolo() + " %");
                listDataHolder.item_humAr.setText(listData.getHumAr() + " %");
                listDataHolder.item_pluv.setText(listData.getPluv() + " mm^3/h");
                listDataHolder.item_data.setText(listData.getData());

            return row;
        }
}
