package com.example.nav;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

    Activity activity;
    public List<Event> eventList;
    public Context context;


    // kontrol et



    @Override
    public int getGroupCount() {
        return this.eventList.size();
    }

    public ExpandableListViewAdapter(Context context,List<Event> eventList){
        this.context= context;
        this.eventList = eventList;

    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.eventList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return "";
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_group, parent, false);
        }

        TextView txt_event = view.findViewById(R.id.tv_event);
        txt_event.setTypeface(null, Typeface.BOLD);
        txt_event.setText(eventList.get(groupPosition).getEvent());


        return view;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {


        final String childText = (String) getChild(groupPosition, childPosition);
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.expandaple_view, null);
        }
        final Event event = eventList.get(groupPosition);
        Consts.Companion.setEvent(event);


        Button detail = view.findViewById(R.id.button_detail);
        final Button location = view.findViewById(R.id.button_location);

        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.closeDrawer();
                final Dialog dialog = DialogFragment.createDialog(context, false);


                DialogFragment.fillfields(dialog, event);

                dialog.show();


            }
        });


        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.closeDrawer();
                MapTwo.getInstance().clearMap();

                MapTwo.getInstance().addMarker(new LatLng(eventList.get(groupPosition).getLocation().getLng(), eventList.get(groupPosition).getLocation().getLat()));


            }
        });

        return view;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
