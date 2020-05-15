package com.example.nav;

class Location {

    public Double lng;
    public Double lat;

    public Location(){

    }

    public Location(double lng,double lat){
        this.lng=lng;
        this.lat=lat;

    }
     public double getLng(){ return lng;}

     public void setLng(double lng){this.lng=lng;}

     public double getLat(){return lat;}

     public void setLat(double lat){this.lat=lat;}

}
