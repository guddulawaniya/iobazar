package com.example.statozone;

public class Order_detail_get {
    // string variables for our data
    // make sure that the variable name
    // must be similar to that of key value
    // which we are getting from our json file.
    private String prName;
    private String pricepr;
    private String Qtypr;
    private String PrId;

    public Order_detail_get(String prName, String pricepr, String Qtypr, String PrId) {
        this.prName = prName;
        this.pricepr = pricepr;
        this.Qtypr = Qtypr;
        this.PrId = PrId;
    }
    public String getprName() {
        return prName;
    }
    public void setprName(String prName) {
        this.prName = prName;
    }
    public String getpricepr() {
        return pricepr;
    }
    public void setpricepr(String pricepr) {
        this.pricepr = pricepr;
    }
    public String getQtypr() {
        return Qtypr;
    }
    public void setQtypr(String Qtypr) {
        this.Qtypr = Qtypr;
    }

    public String getPrId() {
        return PrId;
    }

    public void setPrId(String prId) {
        this.PrId = prId;
    }
}
