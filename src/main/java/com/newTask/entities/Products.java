package com.newTask.entities;


import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Seller")
public class Products {
    private int id;
    private String productName;
    private String productPrice;
    private String productStatus;
    private String sellerName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    public String getSellerName() {
        return sellerName;
    }

    @Override
    public String toString() {
        return "Products{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", productPrice='" + productPrice + '\'' +
                ", productStatus='" + productStatus + '\'' +
                ", sellerName='" + sellerName + '\'' +
                '}';
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }


}
