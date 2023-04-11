package com.example.statozone;

public class ProductCartList {
    public String cartid;
    public Integer Productprice;
    public String Productid;
    public Integer ProductItem;
    public String prName;
    public int Qty;

    public ProductCartList(String cartid, Integer Productprice, String Productid, Integer ProductItem, String prName, int Qty) {
        super();
        this.cartid = cartid;
        this.Productprice = Productprice;
        this.Productid =Productid;
        this.ProductItem=ProductItem;
        this.prName = prName;
        this.Qty = Qty;

    }
    public String getCartid() {

        return cartid;
    }
    public void setCartid(String cartid) {
        this.cartid = cartid;
    }
    public Integer getPrdctprice() {
        return Productprice;
    }
    public void setPrdctprice(Integer Productprice) {
        this.Productprice = Productprice;
    }
    public String getPrdcId() {
        return Productid;
    }
    public void setPrdcId(String Productid) {
        this.Productid = Productid;
    }
    public Integer getPrdctqty() {
        return ProductItem;
    }
    public void setPrdctqty(Integer ProductItem) {
        this.ProductItem = ProductItem;
    }


    public String getPrName() {

        return prName;
    }
    public void setPrName(String prName) {
        this.prName = prName;
    }

    public int getQty() {

        return Qty;
    }
    public void setQty(int Qty) {
        this.Qty = Qty;
    }
}
