package com.example.statozone;

public class OrderViewholder {

    private String StoreName;
    private String StoreLogo;
    private String StoreAddress;
    private String orderDate;
    private String orderTotal;
    private String OrderId;
    private int orderStatus;
    private String Order_code;
    private String StoreId;
    private String StoreRating;


    public OrderViewholder (String StoreName, String StoreLogo, String StoreAddress, String orderDate, String orderTotal, String OrderId, int orderStatus, String Order_code, String StoreId, String StoreRating)
    {
        this.StoreName = StoreName;
        this.StoreLogo = StoreLogo;
        this.StoreAddress = StoreAddress;
        this.orderDate = orderDate;
        this.orderTotal = orderTotal;
        this.OrderId = OrderId;
        this.orderStatus = orderStatus;
        this.Order_code = Order_code;
        this.StoreId = StoreId;
        this.StoreRating = StoreRating;
    }
    public String getStoreName() {
        return StoreName;
    }
    public void setStoreName (String StoreName)
    {
        this.StoreName = StoreName;
    }
    public String getStoreLogo()
    {
        return StoreLogo;
    }
    public void setStoreLogo( String StoreLogo)
    {
        this.StoreLogo = StoreLogo;
    }
    public String getStoreAddress() {
        return StoreAddress;
    }
    public void setStoreAddress(String StoreAddress)
    {
        this.StoreAddress = StoreAddress;
    }
    public String getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
    public String getOrderTotal() {
        return orderTotal;
    }
    public void setOrderTotal(String orderTotal) {
        this.orderTotal = orderTotal;
    }
    public String getOrderId() {
        return OrderId;
    }
    public void setOrderId(String orderId) {
        OrderId = orderId;
    }
    public int getOrderStatus() {
        return orderStatus;
    }
    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }
    public String getOrder_code() {
        return Order_code;
    }
    public void setOrder_code(String order_code) {
        Order_code = order_code;
    }
    public String getStoreId() {
        return StoreId;
    }
    public void setStoreId(String storeId) {
        StoreId = storeId;
    }

    public String getStoreRating() {
        return StoreRating;
    }

    public void setStoreRating(String storeRating) {
        this.StoreRating = storeRating;
    }
}
