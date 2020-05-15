package com.example.nav;
import com.example.nav.Location;

import java.util.UUID;

public class Event {
    public String event,detail,date,time,userId;
    String eventId;
    public Location location;


    public Event (String eventId,String event,String detail,String date,String time,double lng,double lat,String userId){
        this.eventId = eventId;
        this.event = event;
        this.detail = detail;
        this.date = date;
        this.time=time;
        this.location = new Location(lng,lat);
        this.userId=userId;


    }

    public Event(){

    }

    public Event(double Longitude,double Latitude){

    }



    public  String getEvent(){
        return event;

    }
    public void setEvent(String event){
        this.event=event;

    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
