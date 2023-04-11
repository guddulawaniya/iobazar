package com.example.statozone;

public class Recommended {


    private String RecomPrName;
    private String RecomPrId;
    private int RecomPrPrice;
    private String RecomPrImage;
    private int RePrQty;
    private int RePrDiscount;

    public   Recommended(String RecomPrName, String RecomPrId, int RecomPrPrice, String RecomPrImage, int RePrQty, int RePrDiscount)
    {
      this.RecomPrName = RecomPrName;
      this.RecomPrId = RecomPrId;
      this.RecomPrPrice = RecomPrPrice;
      this.RecomPrImage = RecomPrImage;
      this.RePrQty = RePrQty;
      this.RePrDiscount = RePrDiscount;
    }

    public String getRecomPrName()
    {
        return RecomPrName;
    }

    public void setRecomPrName(String RecomPrName) {
        this.RecomPrName = RecomPrName;
    }
    public String getRecomPrId() {
        return RecomPrId;
    }
    public void setRecomPrId(String RecomPrId)
    {
        this.RecomPrId = RecomPrId;
    }


    public int getRecomPrPrice() {
        return RecomPrPrice;
    }

    public void setRecomPrPrice(int RecomPrPrice) {
        this.RecomPrPrice = RecomPrPrice;
    }

    public String getRecomPrImage() {
        return RecomPrImage;
    }

    public void setRePrQty(int RePrQty) {
        this.RePrQty = RePrQty;
    }

    public int getRePrQty() {
        return RePrQty;
    }

    public int getRePrDiscount()
    {
        return RePrDiscount;
    }

    public void setRePrDiscount(int RePrDiscount)
    {
        this.RePrDiscount = RePrDiscount;
    }
}

