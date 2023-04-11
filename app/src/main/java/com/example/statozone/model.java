package com.example.statozone;

public class model
{
    Integer id;
    String name;
    String image;
    String price;
    String pricet;
    String description;

    public model() {
    }

    public model(Integer id, String name, String image, String price, String pricet, String description) {
        this.id = id;
        this.name = name;
        // this.desig = desig;
        this.image = image;
        this.price=price;
        this.pricet=pricet;
        this.description=description;

    }

    //public String getId() {
    //    return id;
    //}

    //public void setId(String id) {
    //    this.id = id;
    // }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // public String getDesig() {
    // return desig;
    //  }

    ///public void setDesig(String desig) {
    //  this.desig = desig;
    // }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPricet() {
        return pricet;
    }

    public void setPricet(String pricet) {
        this.pricet = pricet;
    }

    public Integer getId(){
        return id;
    }
    public void setId(Integer id){
        this.id=id;
    }

    public  String getDescription(){
        return  description;

    }
    public void setDescription(String description){

        this.description=description;

    }


}
