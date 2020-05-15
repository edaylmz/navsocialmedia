package com.example.nav;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowGoogleMap implements GoogleMap.InfoWindowAdapter {
    private Context context;
    private Event event;
    static Event p;



    public CustomInfoWindowGoogleMap(final Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public View getInfoWindow(Marker arg0) {
        return null;
    }

    @Override
    public View getInfoContents(Marker arg0) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v =  inflater.inflate(R.layout.custom_info_window, null);


        TextView eventEdit =  v.findViewById(R.id.eventEdit);
        TextView detailEdit =  v.findViewById(R.id.detailEdit);
        if (Consts.Companion.getEvent() != null) {
            eventEdit.setText(Consts.Companion.getEvent().getEvent());
            detailEdit.setText(Consts.Companion.getEvent().getDetail());
        }


       // final Dialog dialog = DialogFragment.createDialog(context,false);


        return v;
    }

//
//    public static void filllfields(Dialog dialog, Event event) {
//
//        final EditText t = dialog.findViewById(R.id.eventEdit);
//        t.setText(event.getEvent());
//
//        final EditText m = dialog.findViewById(R.id.detailEdit);
//        m.setText(event.getDetail());
//
//
//        p = event;
//
//
//    }


}

//public class CustomMarkerInfoWindowView implements GoogleMap.InfoWindowAdapter {
//
//    private final View markerItemView;
//
//    public CustomMarkerInfoWindowView() {
//        markerItemView = layoutInflater.inflate(R.layout.custom_info_window, null);  // 1
//    }
//
//    @Override
//    public View getInfoWindow(Marker marker) { // 2
//        User user = (User) marker.getTag();  // 3
//        if (user == null) return clusterItemView;
//        TextView itemNameTextView = markerItemView.findViewById(R.id.event);
//        TextView itemAddressTextView = markerItemView.findViewById(R.id.details);
//        itemNameTextView.setText(marker.getTitle());
//        itemAddressTextView.setText(user.getDetails());
//        return markerItemView;  // 4
//    }
//
//    @Override
//    public View getInfoContents(Marker marker) {
//        return null;
//    }
//}


//public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
//
//    private final View mWindow;
//    private Context context;
//
//    public CustomInfoWindowAdapter(Context context){
//        mContext = context;
//        mWindow = LayoutInflater.from(context).inflate(R.layout.custom_info_window,null);
//    }
//
////    public CustomInfoWindowGoogleMap(Context ctx){
////        context = ctx;
////    }
//
//    private void rendowWindowText(Marker marker,View view){
//        String title = marker.getTitle();
//        TextView tvTitle = (TextView)view.findViewById(R.id.event);
//
//        if (!title.equals("")){
//            tvTitle.setText(title);
//        }
//
//        String snippet = marker.getSnippet();
//        TextView tvSnippet = (TextView)view.findViewById(R.id.details);
//
//        if (!snippet.equals("")){
//            tvSnippet.setText(snippet);
//        }
//    }
//
//    @Override
//    public View getInfoWindow(Marker marker) {
//        rendowWindowText(marker, mWindow);
//        return null;
//    }
//
//    @Override
//    public View getInfoContents(Marker marker) {
//
//        rendowWindowText(marker,mWindow);
//
//        View view = ((Activity)context).getLayoutInflater().inflate(R.layout.custom_info_window,null);
//
////        TextView event = view.findViewById(R.id.event);
////        TextView details = view.findViewById(R.id.details);
//////        TextView type = view.findViewById(R.id.type);
////
////        event.setText(marker.getTitle());
////        details.setText(marker.getSnippet());
////
////        InfoWindowData  infoWindowData = (InfoWindowData)marker.getTag();
////
////        event.setText(infoWindowData.getDetails());
////        details.setText(infoWindowData.getDetails());
////        type.setText(infoWindowData.getType());
//
//
//
//        return null;
//    }
//}
