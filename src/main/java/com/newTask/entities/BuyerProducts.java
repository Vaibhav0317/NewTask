package com.newTask.entities;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Buyer")
public class BuyerProducts {
    private int id;
    private String buyerName;
    private int buyerProductId;
    private Products buyerProduct;
    private int productRating;
    private int sellerRating;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public int getBuyerProductId() {
        return buyerProductId;
    }

    public void setBuyerProductId(int buyerProductId) {
        this.buyerProductId = buyerProductId;
    }

    public Products getBuyerProduct() {
        return buyerProduct;
    }

    public void setBuyerProduct(Products buyerProduct) {
        this.buyerProduct = buyerProduct;
    }

    public int getSellerRating() {
        return sellerRating;
    }

    public void setSellerRating(int sellerRating) {
        this.sellerRating = sellerRating;
    }

    public int getProductRating() {
        return productRating;
    }

    public void setProductRating(int productRating) {
        this.productRating = productRating;
    }

    @Override
    public String toString() {
        return "BuyerProducts{" +
                "id=" + id +
                ", buyerName='" + buyerName + '\'' +
                ", buyerProductId=" + buyerProductId +
                ", buyerProduct=" + buyerProduct +
                ", productRating=" + productRating +
                ", sellerRating=" + sellerRating +
                '}';
    }
}
