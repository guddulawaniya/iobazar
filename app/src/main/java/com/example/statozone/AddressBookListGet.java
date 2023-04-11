package com.example.statozone;

public class AddressBookListGet {

    int id;
    String Type;
    String CompleteAddress;
    String Floor;
    String NearBy;
    String Lat;
    String Langs;
    String SubLocality;

    public AddressBookListGet(int id, String Type, String CompleteAddress, String Floor, String NearBy, String Lat, String Langs, String SubLocality) {
  this.id = id;
  this.Type = Type;
  this.CompleteAddress = CompleteAddress;
  this.Floor = Floor;
  this.NearBy =NearBy;
  this.Lat = Lat;
  this.Langs = Langs;
  this.SubLocality = SubLocality;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        this.Type = type;
    }

    public String getCompleteAddress() {
        return CompleteAddress;
    }

    public void setCompleteAddress(String completeAddress) {
        this.CompleteAddress = completeAddress;
    }

    public String getFloor() {
        return Floor;
    }

    public void setFloor(String floor) {
        this.Floor = floor;
    }

    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        this.Lat = lat;
    }

    public String getLangs() {
        return Langs;
    }

    public void setLangs(String langs) {
        this.Langs = langs;
    }

    public String getNearBy() {
        return NearBy;
    }

    public void setNearBy(String nearBy) {
        this.NearBy = nearBy;
    }

    public String getSubLocality() {
        return SubLocality;
    }

    public void setSubLocality(String subLocality) {
        this.SubLocality = subLocality;
    }
}
