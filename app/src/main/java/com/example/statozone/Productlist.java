package com.example.statozone;

public class Productlist {
    public String name;
    public int price;
    public String image;
    public String ID;
    public String user_id;
    public int discount;

    public Productlist(String name, int price, String image,String user_id, String ID,int discount) {
        super();
        this.name = name;
        this.price = price;
        this.image =image;
        this.ID=ID;
        this.user_id = user_id;
        this.discount = discount;
    }
    public String getName() {

        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public String getUserid() {
        return user_id;
    }
    public void setUserid(String user_id) {
        this.user_id = user_id;
    }
    public String getId() {
        return ID;
    }
    public void setId(String ID) {
        this.ID = ID;
    }

    public int getDiscount() {
        return discount;
    }
    public void setDiscount(int discount) {
        this.discount = discount;
    }
}

