package com.example.a2006_3.parkconnectorrunningapp.RoutePlanning;

/**
 * Created by Song Wei on 30/10/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a2006_3.parkconnectorrunningapp.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    Context context;
    List<DataAdapter> dataAdapters;

    public RecyclerViewAdapter(List<DataAdapter> getDataAdapter, Context context){
        super();
        this.dataAdapters = getDataAdapter;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        DataAdapter dataAdapter =  dataAdapters.get(position);
        //viewHolder.TextViewUsername.setText(String.valueOf(dataAdapter.getUsername()));
        viewHolder.TextViewDist.setText(Integer.toString(dataAdapter.getDist()));
        viewHolder.TextViewMins.setText(Integer.toString(dataAdapter.getMin()));
        viewHolder.TextViewSeconds.setText(Integer.toString(dataAdapter.getSec()));
        viewHolder.TextViewCal.setText(Integer.toString(dataAdapter.getCal()));
        viewHolder.TextViewDate.setText(dataAdapter.getDate());
    }

    @Override
    public int getItemCount() {
        return dataAdapters.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView TextViewUsername;
        public TextView TextViewDist;
        public TextView TextViewMins;
        public TextView TextViewSeconds;
        public TextView TextViewCal;
        public TextView TextViewDate;

        public ViewHolder(View itemView) {
            super(itemView);
            //TextViewUsername = (TextView) itemView.findViewById(R.id.textView2) ;
            TextViewDist = (TextView) itemView.findViewById(R.id.disttextView) ;
            TextViewMins = (TextView) itemView.findViewById(R.id.mintextView) ;
            TextViewSeconds = (TextView) itemView.findViewById(R.id.sectextView) ;
            TextViewCal = (TextView) itemView.findViewById(R.id.caltextView) ;
            TextViewDate = (TextView) itemView.findViewById(R.id.datetextView);
        }
    }

}
