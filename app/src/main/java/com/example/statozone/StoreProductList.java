package com.example.statozone;

public class StoreProductList {
    public String PrName;
    public String PrIds;
    public int PrPrice;
    public int PrQty;
    public String PrImg;
    public int PrDiscount;

    public StoreProductList(String PrName, String PrIds, int PrPrice, int PrQty, String PrImg, int PrDiscount){
        super();
        this.PrName=PrName;
        this.PrIds = PrIds;
        this.PrPrice = PrPrice;
        this.PrQty = PrQty;
        this.PrImg = PrImg;
        this.PrDiscount = PrDiscount;
    }

    public String getPrName() {

        return PrName;
    }
    public void setPrName(String PrName) {
        this.PrName = PrName;
    }

    public String getPrIds(){
        return PrIds;
    }

    public void setPrIds(String PrIds){
        this.PrIds = PrIds;
    }

    public int getPrPrice(){
        return PrPrice;
    }

    public void setPrPrice(int PrPrice){
        this.PrPrice = PrPrice;
    }

    public int getPrQty() {
        return PrQty;
    }

    public void setPrQty(int PrQty){
        this.PrQty = PrQty;
    }

    public String getPrImg() {
        return PrImg;
    }

    public void setPrImg(String PrImg){
        this.PrImg = PrImg;
    }

    public int getPrDiscount() {
        return PrDiscount;
    }

    public void setPrDiscount(int PrDiscount){
        this.PrDiscount = PrDiscount;
    }
}
