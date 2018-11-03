package com.example.eduardorodriguez.phonebook;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends BaseAdapter implements Filterable {

    private static LayoutInflater inflater = null;
    Context context;
    ArrayList<SingleItem> data, tempArray;
    CustomFilter cs;

    public Adapter(Context context, ArrayList<SingleItem> data){
        this.context = context;
        this.data = data;
        this.tempArray = data;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        final View row = inflater.inflate(R.layout.custom_listview, null);
        TextView name = (TextView) row.findViewById(R.id.name);
        TextView phone_number = (TextView) row.findViewById(R.id.phoneNumber);
        TextView address = (TextView) row.findViewById(R.id.address);

        name.setText(data.get(position).getName());
        phone_number.setText(data.get(position).getContactNumber());
        address.setText(data.get(position).getAddress());

        ConstraintLayout item = (ConstraintLayout) row.findViewById(R.id.contactItem);
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent contactLook = new Intent(context, ContactLook.class);
                contactLook.putExtra("contact", data.get(position).getContactNumber());
                contactLook.putExtra("name", data.get(position).getName());
                contactLook.putExtra("address", data.get(position).getAddress());
                context.startActivity(contactLook);
            }
        });

        return row;
    }

    @Override
    public int getCount() { return data.size(); }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.indexOf(position);
    }

    @Override
    public Filter getFilter() {
        if(cs == null){
            cs = new CustomFilter();
        }
        return cs;
    }

    class CustomFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            ArrayList<SingleItem> filters = new ArrayList<>();
            if(constraint != null && constraint.length() > 0){
                constraint = constraint.toString().toUpperCase();

                for(SingleItem item: tempArray){
                    if(item.getName().toUpperCase().contains(constraint)){
                        filters.add(item);
                    }
                }
            }else{
                filters = data;
            }
            results.count = filters.size();
            results.values = filters;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            data = (ArrayList<SingleItem>) results.values;
            notifyDataSetChanged();
        }
    }
}
