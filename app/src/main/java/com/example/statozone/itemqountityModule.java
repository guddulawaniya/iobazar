package com.example.statozone;

public class itemqountityModule {
    String itemprice,itemgst,itemmrp,color;


    public itemqountityModule(String itemprice, String itemgst, String itemmrp, String color) {
        this.itemprice = itemprice;
        this.itemgst = itemgst;
        this.itemmrp = itemmrp;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getItemprice() {
        return itemprice;
    }

    public void setItemprice(String itemprice) {
        this.itemprice = itemprice;
    }

    public String getItemgst() {
        return itemgst;
    }

    public void setItemgst(String itemgst) {
        this.itemgst = itemgst;
    }

    public String getItemmrp() {
        return itemmrp;
    }

    public void setItemmrp(String itemmrp) {
        this.itemmrp = itemmrp;
    }
}
